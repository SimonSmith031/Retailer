package com.example.retailer.service;

import com.example.retailer.dao.AccountMapper;
import com.example.retailer.dao.BookMapper;
import com.example.retailer.dao.DealMapper;
import com.example.retailer.dao.ItemMapper;
import com.example.retailer.domain.Book;
import com.example.retailer.domain.Deal;
import com.example.retailer.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
public class BookService {
    private final Logger logger = LoggerFactory.getLogger(BookService.class);
    private List<Deal> dealList;

    @Autowired
    private DealMapper dealMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ItemMapper itemMapper;

    // 需要保证传递过来的数量是正整数，由上层检查
    public boolean buyItem(String bookName, String user, int number) {
        if (number <= 0) {
            logger.info("指定的购买数量不是正整数，购买失败");
            return false;
        }
        Book book = bookMapper.findBookByName(bookName);
        if (book.stockNumber < number) {
            // 不能买书
            logger.info("用户尝试买书，但是书的数目不够");
            return false;
        } else {
            // 1. 书籍库存减少
            book.stockNumber -= number;
            bookMapper.updateBook(book);

            // 2. 持有者的清单项目被删除
            itemMapper.deleteOne(user, bookName);

            logger.info("成功购买了" + number + "本" + bookName);

            // 建立交易记录
            dealList = dealMapper.findAllDeals();
            // 依赖数据库设计的id自增
            Deal deal = new Deal(null, user, bookName,
                    Timestamp.valueOf(LocalDateTime.now()), number);
            dealMapper.addDeal(deal);
            return true;
        }
    }

    public Book findBookByName(String name) {
        return bookMapper.findBookByName(name);
    }

    public List<Book> findAllBooks() {
        return bookMapper.findAllBooks();
    }

    public List<Book> findUserRepo(String userId){
        dealList = dealMapper.findDealByUserId(userId);
        List<Book> repoList = new ArrayList<Book>();
        for(Deal deal:dealList){
            repoList.add(findBookByName(deal.bookName));
        }
        return repoList;
    }

    public void updateBook(Book book) {
        bookMapper.updateBook(book);
    }

    @Deprecated
    public void addBook(Book book) {
        // BUG：没有检查是否真的添加了这本书，当已经添加的时候就会报错
        bookMapper.addBook(book);
    }

    public void deleteBook(String bookName){
        bookMapper.deleteBook(bookName);
    }

    // ************************* 使用token的操作 *************************
    // 购买单个记录项的书籍
    public boolean buyItemByToken(String bookName, String token, int number) {
        String id = accountMapper.findIdByToken(token);
        return buyItem(bookName, id, number);
    }

    // 购买多个记录项的书籍
    public void buyItemsByToken(String token, List<Map<String, Object>> mapList) {
        String id = accountMapper.findIdByToken(token);
        boolean flag = true;
        for (Map<String, Object> map: mapList) {
            String bookName = (String) map.get("name");

            // 获取number域
            // 得到的数字可能是String类型的，也可能是Integer类型的
            Object numberObj = map.get("number");
            Integer number;
            if (numberObj instanceof Integer) {
                number = (Integer) numberObj;
            } else if (numberObj instanceof String) {
                number = Integer.parseInt((String) numberObj);
            } else {
                throw new RuntimeException("number域的类型无法推断。");
            }

            // 购买
            flag = buyItem(bookName, id, number);

            // 如果中途有一本书购买失败，则引发异常
            if (!flag) {
                throw new RuntimeException("购买一批书籍的过程中出现错误。");
            }
        }
    }

    // 修改一本书的记录
    public boolean updateBookByToken(String token, String bookName, Integer number, Double price) {
        // 检查输入的合法性
        if (number <= 0 || price <= 0) {
            return false;
        }

        // 检查是否为管理员
        String id = accountMapper.findIdByToken(token);
        boolean isAdmin = "admin".equals(id);
        if (!isAdmin) {
            return false;
        }

        // 更新书籍的价格和库存信息
        Book book = bookMapper.findBookByName(bookName);
        book.setPrice(price);
        book.setStockNumber(number);

        // 调用更新书的mapper接口
        bookMapper.updateBook(book);
        return true;
    }

    // 添加一本新书
    public boolean addBookByToken(String token, Book newBook) {
        // 核查管理员身份
        if (token == null) {
            logger.info("token不能够为空");
            return false;
        }
        String id = accountMapper.findIdByToken(token);
        if (id == null) {
            logger.info("找不到token对应的id");
            return false;
        }
        if (!"admin".equals(id)) {
            logger.info("用户" + id + "不是管理员，不能够添加书籍");
            return false;
        }

        /**
         * 这里最好是检查一下这本书是否名字重复，如果重复还不能够添加这本书
         */

        // 添加书籍的操作
        Book book = bookMapper.findBookByName(newBook.name);
        if (book != null) {
            logger.info("书籍已经存在，将对书籍进行更新");
            bookMapper.updateBook(newBook);
        } else {
            logger.info("书籍不存在，将添加新的书籍");
            bookMapper.addBook(newBook);
        }
        return true;
    }

    // 删除一本书
    public boolean deleteBookByToken(String token, String bookName) {
        // 核查管理员身份
        if (token == null) {
            logger.info("token不能够为空");
            return false;
        }
        String id = accountMapper.findIdByToken(token);
        if (id == null) {
            logger.info("找不到token对应的id");
            return false;
        }
        if (!"admin".equals(id)) {
            logger.info("用户" + id + "不是管理员，不能够删除书籍");
            return false;
        }

        // 删除书籍
        Book book = bookMapper.findBookByName(bookName);
        if (book == null) {
            logger.info("书籍不存在，不能够删除");
            return false;
        }

        // 操作数据库
        bookMapper.deleteBook(bookName);
        return true;
    }
}

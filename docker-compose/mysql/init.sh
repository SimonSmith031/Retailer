# 因为在docker-compose中指定了创建这个表，这里就不创建了（否则报错）
# mysql -uroot -p$MYSQL_ROOT_PASSWORD -e "create database Retailer;"

# -p之后要紧跟密码，不能够有空格
mysql -uroot -p$MYSQL_ROOT_PASSWORD Retailer < /mysql/retailer.sql

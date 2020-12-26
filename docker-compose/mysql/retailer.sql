-- MySQL dump 10.13  Distrib 8.0.19, for osx10.15 (x86_64)
--
-- Host: localhost    Database: Retailer
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Account`
--

DROP TABLE IF EXISTS `Account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Account` (
  `id` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `token` char(48) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Account`
--

LOCK TABLES `Account` WRITE;
/*!40000 ALTER TABLE `Account` DISABLE KEYS */;
INSERT INTO `Account` VALUES ('1','1','d1eb2fad-1b19-4f9c-a869-f48060822c65'),('admin','123','cf5de141-0f90-4ca7-8b40-d8269c591085');
/*!40000 ALTER TABLE `Account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Book`
--

DROP TABLE IF EXISTS `Book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Book` (
  `name` varchar(32) NOT NULL,
  `fullName` varchar(32) NOT NULL,
  `stockNumber` int NOT NULL DEFAULT '0',
  `price` double NOT NULL DEFAULT '0',
  `description` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Book`
--

LOCK TABLES `Book` WRITE;
/*!40000 ALTER TABLE `Book` DISABLE KEYS */;
INSERT INTO `Book` VALUES ('advanced-swift','Swift 进阶',66,79,'在本书中，我们涵盖了 Swift 程序设计的一些进阶话题。如果你已经通读 Apple 的 Swift 编程指南，并且想要深入探索关于这门语言的更多内容，那么这本书正适合你！<br/>Swift 非常适合用来进行系统编程，而同时它也能被用于书写高层级的代码。我们在书中既会研究像是泛型、协议这样的高层级抽象的内容，也会涉足像是封装 C 代码以及字符串内部实现这样的低层级话题。'),('app-architecture','App 架构',54,79,'本书通过使用五种不同的设计模式，完整实现一个 app，并藉此讨论各种 app 架构的实现和优劣。<br/>我们在书中避免枯燥抽象的理论说明，而是着眼于各个架构实际使用时的特点：我们针对架构是如何构建 app 的各个部件的，视图和模型之间的交互到底应当如何进行，怎样优雅地处理非模型状态等话题进行了思考和回答。'),('core-data','Core Data',43,69,'本书将向您介绍使用 Core Data 时需要特别注意的事项，这将帮助您避开使用 Core Data 这个十分灵活且异常强大的框架时的一些陷阱。我们从一个简单的应用例子开始，逐步将其扩展为包含关系，高级数据类型，并发，同步以及其他很多特性的完整例子，并在这个过程中对所有这些主题进行了讲解。<br/>在本书后半部本，我们还会超出这个基本应用所需要涉及的范围，将我们的知识点深入扩展到 Core Data 幕后的工作原理上。我们会学习如何获取高性能，不同 Core Data 设置之间的权衡，以及如何对你的 Core Data 代码进行调试和性能测试。 本书所有的代码都使用 Swift 书写， 我们也展示了如何将 Swift 的语言特性融入到 Core Data 中，并写出优雅和安全的代码。我们希望您在阅读本书的时候有一定的 Swift 和 iOS 开发基础，不过相信不论是新人还是富有经验的开发者，都能从本书中找到实用的信息和设计模式。'),('functional-swift','函数式 Swift',34,69,'Swift 为编程世界打开了一扇新的大门，在本书中我们将带领您遨游这个世界。正如 Swift 的创造者 Chris Lattner 所言：<br/>“没有 C 的 Objective-C” 确实是在做减法，但是 Swift 引入了泛型和函数式编程的概念，这极大扩展了语言设计的空间。<br/>在 Swift 中引入函数式编程为我们的开发带来了无限的乐趣。在本书中我们将阐述函数式编程的理念，并向您展示如何在 Swift 中将它们运用于程序设计，这可以帮助我们写出更加简洁和明确的代码。'),('optimizing-collections','集合类型优化',15,59,'在本书中，我们会介绍如何编写高性能的 Swift 集合类型代码。通观全书，我们创建了很多性能测试，并得到了一些让人惊喜的结果。我们将会探索如何用实现像是有序数组、二叉树、红黑树和 B 树这样的自定义数据结构，并让它们具有值语义和写时复制等特性。<br/>即使你从未实现过自定义的集合类型，本书也将会带领您逐渐深入，并一探 Swift 代码性能的特质。'),('swift-ui','SwiftUI 与 Combine 编程',54,79,'WWDC 2019 上 Apple 公布了声明式全新界面框架 SwiftUI，以及配套的响应式编程框架 Combine。对于 Apple 平台的开发者来说，这是一次全新的转变和挑战。本书通过几个具体的实战例子，由浅入深介绍了 SwiftUI 和 Combine 框架的使用方式及核心思想，帮助您顺利步入令人激动的 Apple 开发新时代。'),('swifter-tips','Swifter - Swift 开发者必备 Tips',42,49,'Swift 是 Apple 于 WWDC 2014 提出的一门全新的开发语言。这门语言从其他很多语言中继承和学习了不少优点，语法非常优美，并且具有轻便灵活等特点，一经推出就备受瞩目。<br/>活跃在 iOS 开发一线的本书作者王巍 (onevcat) 参加了 WWDC 14 开发者大会，亲历了这门语言的诞生，并在第一时间进行了大量的学习和研究。在本书中作者通过总结和分享了 Swift 中 100 条十分实用的小技巧和需要特别注意的地方，希望能让大家更好更快地掌握 Swift。'),('thinking-in-swiftui','SwiftUI 编程思想',103,59,'与 UIKit 这样的传统面向对象的 UI 构建方式相比，SwiftUI 有着天壤之别。声明式的构建方式可以天生避免一系列 app 开发中的最常见错误。不过，这需要我们使用不同以往的思考方式，来处理可能面临的新问题和新需求。<br/>本书详细解释了 SwiftUI 编程中最重要的一些概念，让你能够在很快时间内建立起 SwiftUI 的编程思想，以此快速完成向全新编程范式的转换。');
/*!40000 ALTER TABLE `Book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Deal`
--

DROP TABLE IF EXISTS `Deal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Deal` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `userId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `bookName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `time` timestamp NOT NULL,
  `number` int unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Deal`
--

LOCK TABLES `Deal` WRITE;
/*!40000 ALTER TABLE `Deal` DISABLE KEYS */;
/*!40000 ALTER TABLE `Deal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShoppingList`
--

DROP TABLE IF EXISTS `ShoppingList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ShoppingList` (
  `userId` varchar(32) NOT NULL,
  `bookName` varchar(32) NOT NULL,
  `number` int NOT NULL,
  PRIMARY KEY (`userId`,`bookName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShoppingList`
--

LOCK TABLES `ShoppingList` WRITE;
/*!40000 ALTER TABLE `ShoppingList` DISABLE KEYS */;
INSERT INTO `ShoppingList` VALUES ('3','app-architecture',2),('admin','2',2),('admin','advanced-swift',1),('admin','app-architecture',2);
/*!40000 ALTER TABLE `ShoppingList` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-22 23:18:32

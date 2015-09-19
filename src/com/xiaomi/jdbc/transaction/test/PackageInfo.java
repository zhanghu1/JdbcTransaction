package com.xiaomi.jdbc.transaction.test;

/**
 * @author 		zhanghu1
 * @date   		2015年8月12日
 * @fileName	PackageInfo.java
 * @packageName	com.xiaomi.jdbc.transaction.test
 * @projectName JdbcTransaction
 * @Company		Xiaomi
 */
/**
 * 默认情况下，当我们创建一个数据库连接时，会运行在自动提交模式（Auto-commit）下。
 * 这意味着，任何时候我们执行一条SQL完成之后，事务都会自动提交。所以我们执行的
 * 每一条SQL都是一个事务，并且如果正在运行DML或者DDL语句，这些改变会在每一条SQL语句
 * 结束的时存入数据库。有时候我们想让一组SQL语句成为事务的一部分，那样我们就可以在所有
 * 语句运行成功的时候提交，并且如果出现任何异常，这些语句作为事务的一部分，我们可以选择
 * 将其全部回滚。
 * */
/**
 * 这里使用JDBC的事务管理来支持数据的完整性。假设我们有一个名为UserDB的数据库，
 * 员工的信息分别存储在两张表中。
 * 插入之后数据库中表的情况是插入了employee和address，都变成了小写
 * CREATE TABLE Employee (
  empId int(11) unsigned NOT NULL,
  name varchar(20) DEFAULT NULL,
  PRIMARY KEY (empId)
)
CREATE TABLE Address (
  empId int(11) unsigned NOT NULL,
  address varchar(20) DEFAULT NULL,
  city varchar(5) DEFAULT NULL,
  country varchar(20) DEFAULT NULL,
  PRIMARY KEY (empId)
)
 * */
/**
 * CREATE TABLE Logs (
  id int(3) unsigned NOT NULL AUTO_INCREMENT,
  message varchar(10) DEFAULT NULL,
  PRIMARY KEY (id)
)
 * */


package com.xiaomi.jdbc.transaction.test;

/**
 * @author 		zhanghu1
 * @date   		2015��8��12��
 * @fileName	PackageInfo.java
 * @packageName	com.xiaomi.jdbc.transaction.test
 * @projectName JdbcTransaction
 * @Company		Xiaomi
 */
/**
 * Ĭ������£������Ǵ���һ�����ݿ�����ʱ�����������Զ��ύģʽ��Auto-commit���¡�
 * ����ζ�ţ��κ�ʱ������ִ��һ��SQL���֮�����񶼻��Զ��ύ����������ִ�е�
 * ÿһ��SQL����һ�����񣬲��������������DML����DDL��䣬��Щ�ı����ÿһ��SQL���
 * ������ʱ�������ݿ⡣��ʱ����������һ��SQL����Ϊ�����һ���֣��������ǾͿ���������
 * ������гɹ���ʱ���ύ��������������κ��쳣����Щ�����Ϊ�����һ���֣����ǿ���ѡ��
 * ����ȫ���ع���
 * */
/**
 * ����ʹ��JDBC�����������֧�����ݵ������ԡ�����������һ����ΪUserDB�����ݿ⣬
 * Ա������Ϣ�ֱ�洢�����ű��С�
 * ����֮�����ݿ��б������ǲ�����employee��address���������Сд
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


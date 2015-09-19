package com.xiaomi.jdbc.transaction.test;

/**
 * @author 		zhanghu1
 * @date   		2015��8��12��
 * @fileName	EmployeeJDBCSavePointExample.java
 * @packageName	com.xiaomi.jdbc.transaction.test
 * @projectName JdbcTransaction
 * @Company		Xiaomi
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * ��ʱ��һ�����������һ�鸴�ӵ���䣬��˿�����Ҫ�ع���������ĳ������ĵ㡣
 * JDBC Savepoint�������������д������㣨checkpoint���������Ϳ��Իع���ָ���㡣
 * �������ύ������������ع���Ϊ����������κα���㶼���Զ��ͷŲ���Ϊ��Ч��
 * ������ع���һ������㣬��ʹ�������б�����Զ��ͷŲ���Ϊ��Ч��
 * 
 * ����������һ����־��Logs��������¼Ա����Ϣ����ɹ�����־��������Ϊ��ֻ������־��¼��
 * ��������־�����κ��쳣ʱ�����ǲ�ϣ���ع�����������������һ�������JDBC Savepoint��ʵ�֡�
 * */
/**
 * CREATE TABLE Logs (
  id int(3) unsigned NOT NULL AUTO_INCREMENT,
  message varchar(10) DEFAULT NULL,
  PRIMARY KEY (id)
)
 * */
/**
 * ��γ���ǳ�������⡣�����ݳɹ�����Employee���Address��󣬴�����һ��Savepoint��
 * ����׳�SQLException����SavepointΪ�գ���ζ����ִ�в���Employee����Address��ʱ�������쳣��
 * ������Ҫ�ع���������
 * 
 * ���Savepoint��Ϊ�գ���ζ��SQLException�ɲ�����־��Logs��������������ֻ�ع����񵽱���㣬Ȼ���ύ��
 * */
/**
 * ����鿴���ݿ�����Կ������ݳɹ��ز��뵽��Employee���Address����Ҫע����ǣ�
 * �����и��򵥵�ʵ�ַ�ʽ�������ݳɹ�����Employee���Address��ʱ�ύ����ʹ����һ��
 * ������������־�Ĳ�������ֻ��Ϊ��չʾJava������JDBC Savepoint���÷���
 * */
public class EmployeeJDBCSavePointExample {
    public static final String INSERT_LOGS_QUERY = "insert into Logs (message) values (?)";
 
    public static void main(String[] args) {
    	
        Connection con = null;
        Savepoint savepoint = null;
        try {
            con = DBConnection.getConnection();
            // set auto commit to false
            con.setAutoCommit(false);
            EmployeeJDBCInsertExample.insertEmployeeData(con, 2, "Pankaj");
            EmployeeJDBCInsertExample.insertAddressData(con,
                                                        2,
                                                        "Albany Dr",
                                                        "SFO",
                                                        "USA");
            // if code reached here, means main work is done successfully
            savepoint = con.setSavepoint("EmployeeSavePoint");
            insertLogData(con, 2);
            // now commit transaction
            con.commit();
        }
        catch (SQLException e) {
            e.printStackTrace();
            try {
                if (savepoint == null) {
                    // SQLException occurred in saving into Employee or Address
                    // tables
                    con.rollback();
                    System.out.println("JDBC Transaction rolled back successfully");
                } else {
                    // exception occurred in inserting into Logs table
                    // we can ignore it by rollback to the savepoint
                    con.rollback(savepoint);
                    // lets commit now
                    con.commit();
                }
            }
            catch (SQLException e1) {
                System.out.println("SQLException in rollback" + e.getMessage());
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 
    private static void insertLogData(Connection con, int i)
            throws SQLException {
        PreparedStatement stmt = con.prepareStatement(INSERT_LOGS_QUERY);
        // message is very long, will throw SQLException
        stmt.setString(1, "Employee information saved successfully for ID" + i);
        stmt.executeUpdate();
        System.out.println("Logs Data inserted successfully for ID=" + i);
        stmt.close();
    }
 
}


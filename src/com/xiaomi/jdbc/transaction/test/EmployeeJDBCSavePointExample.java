package com.xiaomi.jdbc.transaction.test;

/**
 * @author 		zhanghu1
 * @date   		2015年8月12日
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
 * 有时候一个事务可能是一组复杂的语句，因此可能想要回滚到事务中某个特殊的点。
 * JDBC Savepoint帮我们在事务中创建检查点（checkpoint），这样就可以回滚到指定点。
 * 当事务提交或者整个事务回滚后，为事务产生的任何保存点都会自动释放并变为无效。
 * 把事务回滚到一个保存点，会使其他所有保存点自动释放并变为无效。
 * 
 * 假设我们有一张日志表Logs，用来记录员工信息保存成功的日志。但是因为它只用于日志记录，
 * 当插入日志表有任何异常时，我们不希望回滚整个事务。我们来看一下如何用JDBC Savepoint来实现。
 * */
/**
 * CREATE TABLE Logs (
  id int(3) unsigned NOT NULL AUTO_INCREMENT,
  message varchar(10) DEFAULT NULL,
  PRIMARY KEY (id)
)
 * */
/**
 * 这段程序非常容易理解。在数据成功插入Employee表和Address表后，创建了一个Savepoint。
 * 如果抛出SQLException，而Savepoint为空，意味着在执行插入Employee或者Address表时发生了异常，
 * 所以需要回滚整个事务。
 * 
 * 如果Savepoint不为空，意味着SQLException由插入日志表Logs操作引发，所以只回滚事务到保存点，然后提交。
 * */
/**
 * 如果查看数据库表，可以看到数据成功地插入到了Employee表和Address表。需要注意的是，
 * 我们有更简单的实现方式。当数据成功插入Employee表和Address表时提交事务，使用另一个
 * 事务管理插入日志的操作。这只是为了展示Java程序中JDBC Savepoint的用法。
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


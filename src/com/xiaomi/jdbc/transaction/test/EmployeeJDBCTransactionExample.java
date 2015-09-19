package com.xiaomi.jdbc.transaction.test;

/**
 * @author 		zhanghu1
 * @date   		2015年8月12日
 * @fileName	EmployeeJDBCTransactionExample.java
 * @packageName	com.xiaomi.jdbc.transaction.test
 * @projectName JdbcTransaction
 * @Company		Xiaomi
 */

/**
 * JDBC API提供了setAutoCommit()方法，通过它我们可以禁用自动提交数据库连接。
 * 自动提交应该被禁用，因为只有这样事务(一条语句就是一个事务)才不会自动提交，除非调用了连接的commit()方法。
 * 数据库服务器使用表锁来实现事务管理，并且它是一种紧张的资源。因此，在操作完成后应该
 * 尽快提交事务。让我们编写另外一个程序，这里我将使用JDBC事务管理特性来保证数据的完整性不被破坏。
 * */
/**
 * 这段输出和前面的程序很像，但是如果你查看数据库表，就会发现数据没有被插入Employee表。
 * 现在我们可以修改城市（city）的值，这样它就可以符合字段要求，重新运行程序就能够把数据
 * 插到两张表中。注意：只有当两个插入操作都执行成功时，连接才会提交。如果其中任何一个抛出
 * 异常，整个事务会回滚。
 * */
import java.sql.Connection;
import java.sql.SQLException;
 
public class EmployeeJDBCTransactionExample {
 
    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            // set auto commit to false
            con.setAutoCommit(false);
            EmployeeJDBCInsertExample.insertEmployeeData(con, 2, "Pankaj");
            EmployeeJDBCInsertExample.insertAddressData(con,
                                                        2,
                                                        "Albany Dr",
                                                        "San Jose",
                                                        "USA");
            // now commit transaction
            con.commit();
        }
        catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
                System.out.println("JDBC Transaction rolled back successfully");
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
}

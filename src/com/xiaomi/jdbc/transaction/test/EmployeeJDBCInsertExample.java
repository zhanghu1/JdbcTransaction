package com.xiaomi.jdbc.transaction.test;

/**
 * @author 		zhanghu1
 * @date   		2015年8月12日
 * @fileName	EmployeeJDBCInsertExample.java
 * @packageName	com.xiaomi.jdbc.transaction.test
 * @projectName JdbcTransaction
 * @Company		Xiaomi
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 在我们试图往Address表中插入数据时，由于输入的值超过了字段的大小，
 * 因此抛出了SQLException异常。
 * */
/**
 * 如果浏览Employee和Address表的内容，你会发现Employee表有数据，Address表却没有。
 * 这是一个严重的问题，因为只有部分数据正确地被插入。并且如果我们再次运行这个程序，
 * 它会再次试图向Employee表插入数据，并且引发下面的异常：
 * */
/**
 * 所以，我们没有办法把Employee对应的Address数据保存到Address表中。这个程序造成了
 * 数据完整性的问题，这也是为什么需要用事务管理来确保两张表都得以成功插入，并且如果
 * 发生任何异常全部回滚。
 * */
public class EmployeeJDBCInsertExample {
	
    public static final String INSERT_EMPLOYEE_QUERY = 
    		"insert into Employee (empId, name) values (?,?)";
    public static final String INSERT_ADDRESS_QUERY = 
    		"insert into Address (empId, address, city, country) values (?,?,?,?)";
 
    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            insertEmployeeData(con, 1, "Pankaj");
            insertAddressData(con, 1, "Albany Dr", "San Jose", "USA");
        }
        catch (SQLException | ClassNotFoundException e) {
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
 
    public static void insertAddressData(Connection con,
                                         int id,
                                         String address,
                                         String city,
                                         String country) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(INSERT_ADDRESS_QUERY);
        stmt.setInt(1, id);
        stmt.setString(2, address);
        stmt.setString(3, city);
        stmt.setString(4, country);
        stmt.executeUpdate();
        System.out.println("Address Data inserted successfully for ID=" + id);
        stmt.close();
    }
 
    public static void insertEmployeeData(Connection con, int id, String name)
            throws SQLException {
        PreparedStatement stmt = con.prepareStatement(INSERT_EMPLOYEE_QUERY);
        stmt.setInt(1, id);
        stmt.setString(2, name);
        stmt.executeUpdate();
        System.out.println("Employee Data inserted successfully for ID=" + id);
        stmt.close();
    }
}


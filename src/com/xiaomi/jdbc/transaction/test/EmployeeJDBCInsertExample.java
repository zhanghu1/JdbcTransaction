package com.xiaomi.jdbc.transaction.test;

/**
 * @author 		zhanghu1
 * @date   		2015��8��12��
 * @fileName	EmployeeJDBCInsertExample.java
 * @packageName	com.xiaomi.jdbc.transaction.test
 * @projectName JdbcTransaction
 * @Company		Xiaomi
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ��������ͼ��Address���в�������ʱ�����������ֵ�������ֶεĴ�С��
 * ����׳���SQLException�쳣��
 * */
/**
 * ������Employee��Address������ݣ���ᷢ��Employee�������ݣ�Address��ȴû�С�
 * ����һ�����ص����⣬��Ϊֻ�в���������ȷ�ر����롣������������ٴ������������
 * �����ٴ���ͼ��Employee��������ݣ���������������쳣��
 * */
/**
 * ���ԣ�����û�а취��Employee��Ӧ��Address���ݱ��浽Address���С�������������
 * ���������Ե����⣬��Ҳ��Ϊʲô��Ҫ�����������ȷ�����ű����Գɹ����룬�������
 * �����κ��쳣ȫ���ع���
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


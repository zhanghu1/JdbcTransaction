package com.xiaomi.jdbc.transaction.test;

/**
 * @author 		zhanghu1
 * @date   		2015��8��12��
 * @fileName	EmployeeJDBCTransactionExample.java
 * @packageName	com.xiaomi.jdbc.transaction.test
 * @projectName JdbcTransaction
 * @Company		Xiaomi
 */

/**
 * JDBC API�ṩ��setAutoCommit()������ͨ�������ǿ��Խ����Զ��ύ���ݿ����ӡ�
 * �Զ��ύӦ�ñ����ã���Ϊֻ����������(һ��������һ������)�Ų����Զ��ύ�����ǵ��������ӵ�commit()������
 * ���ݿ������ʹ�ñ�����ʵ�����������������һ�ֽ��ŵ���Դ����ˣ��ڲ�����ɺ�Ӧ��
 * �����ύ���������Ǳ�д����һ�����������ҽ�ʹ��JDBC���������������֤���ݵ������Բ����ƻ���
 * */
/**
 * ��������ǰ��ĳ�����񣬵��������鿴���ݿ���ͻᷢ������û�б�����Employee��
 * �������ǿ����޸ĳ��У�city����ֵ���������Ϳ��Է����ֶ�Ҫ���������г�����ܹ�������
 * �嵽���ű��С�ע�⣺ֻ�е��������������ִ�гɹ�ʱ�����ӲŻ��ύ����������κ�һ���׳�
 * �쳣�����������ع���
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

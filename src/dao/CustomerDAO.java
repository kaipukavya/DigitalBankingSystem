package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DBConnection;
import model.Customer;

public class CustomerDAO {

    public boolean createAccount(Customer customer) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO CUSTOMER VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, customer.getAccountNo());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getAddress());
            ps.setInt(6, customer.getPin());
            ps.setDouble(7, customer.getBalance());
            ps.setString(8, customer.getAccountType());

            int rows = ps.executeUpdate();

            con.close();

            if (rows > 0)
                return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Customer loginCustomer(int accountNo, int pin) {

        Customer customer = null;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM CUSTOMER WHERE ACCOUNT_NO=? AND PIN=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, accountNo);
            ps.setInt(2, pin);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                customer = new Customer();

                customer.setAccountNo(rs.getInt("ACCOUNT_NO"));
                customer.setName(rs.getString("NAME"));
                customer.setPhone(rs.getString("PHONE"));
                customer.setEmail(rs.getString("EMAIL"));
                customer.setAddress(rs.getString("ADDRESS"));
                customer.setPin(rs.getInt("PIN"));
                customer.setBalance(rs.getDouble("BALANCE"));
                customer.setAccountType(rs.getString("ACCOUNT_TYPE"));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customer;
    }
    public double checkBalance(int accountNo) {

        double balance = 0;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT BALANCE FROM CUSTOMER WHERE ACCOUNT_NO=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, accountNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                balance = rs.getDouble("BALANCE");

            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return balance;
    }
    
    public boolean deposit(int accountNo, double amount) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE CUSTOMER SET BALANCE=BALANCE+? WHERE ACCOUNT_NO=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, amount);
            ps.setInt(2, accountNo);

            int rows = ps.executeUpdate();

            con.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean withdraw(int accountNo, double amount) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE CUSTOMER SET BALANCE=BALANCE-? WHERE ACCOUNT_NO=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, amount);
            ps.setInt(2, accountNo);

            int rows = ps.executeUpdate();

            con.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean accountExists(int accountNo) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM CUSTOMER WHERE ACCOUNT_NO=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, accountNo);

            ResultSet rs = ps.executeQuery();

            boolean status = rs.next();

            con.close();

            return status;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    
    public boolean transfer(int senderAccount,
            int receiverAccount,
            double amount) {

try {

Connection con = DBConnection.getConnection();

con.setAutoCommit(false);

String debit =
"UPDATE CUSTOMER SET BALANCE=BALANCE-? WHERE ACCOUNT_NO=?";

PreparedStatement ps1 = con.prepareStatement(debit);

ps1.setDouble(1, amount);
ps1.setInt(2, senderAccount);

ps1.executeUpdate();

String credit =
"UPDATE CUSTOMER SET BALANCE=BALANCE+? WHERE ACCOUNT_NO=?";

PreparedStatement ps2 = con.prepareStatement(credit);

ps2.setDouble(1, amount);
ps2.setInt(2, receiverAccount);

ps2.executeUpdate();

con.commit();

con.close();

return true;

} catch (Exception e) {

e.printStackTrace();

}

return false;
}
    
    public Customer getCustomer(int accountNo) {

        Customer customer = null;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM CUSTOMER WHERE ACCOUNT_NO=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, accountNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                customer = new Customer();

                customer.setAccountNo(rs.getInt("ACCOUNT_NO"));
                customer.setName(rs.getString("NAME"));
                customer.setPhone(rs.getString("PHONE"));
                customer.setEmail(rs.getString("EMAIL"));
                customer.setAddress(rs.getString("ADDRESS"));
                customer.setPin(rs.getInt("PIN"));
                customer.setBalance(rs.getDouble("BALANCE"));
                customer.setAccountType(rs.getString("ACCOUNT_TYPE"));

            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customer;

    }
    
    public boolean updateProfile(Customer customer) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE CUSTOMER SET NAME=?, PHONE=?, EMAIL=?, ADDRESS=? WHERE ACCOUNT_NO=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getAddress());
            ps.setInt(5, customer.getAccountNo());

            int rows = ps.executeUpdate();

            con.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean changePin(int accountNo, int newPin) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE CUSTOMER SET PIN=? WHERE ACCOUNT_NO=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, newPin);
            ps.setInt(2, accountNo);

            int rows = ps.executeUpdate();

            con.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean deleteAccount(int accountNo) {

        try {

            Connection con = DBConnection.getConnection();

            String sql1 = "DELETE FROM TRANSACTIONS WHERE ACCOUNT_NO=?";

            PreparedStatement ps1 = con.prepareStatement(sql1);

            ps1.setInt(1, accountNo);

            ps1.executeUpdate();

            String sql2 = "DELETE FROM CUSTOMER WHERE ACCOUNT_NO=?";

            PreparedStatement ps2 = con.prepareStatement(sql2);

            ps2.setInt(1, accountNo);

            int rows = ps2.executeUpdate();

            con.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
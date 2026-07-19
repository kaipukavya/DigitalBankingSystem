package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DBConnection;

public class TransactionDAO {

    public void addTransaction(int accountNo, String type, double amount, double balance) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO TRANSACTIONS (ACCOUNT_NO, TRANSACTION_TYPE, AMOUNT, BALANCE) VALUES(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, accountNo);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.setDouble(4, balance);

            ps.executeUpdate();

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void miniStatement(int accountNo) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM TRANSACTIONS WHERE ACCOUNT_NO=? ORDER BY TRANSACTION_DATE DESC";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, accountNo);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n==============================================================");
            System.out.printf("%-12s %-12s %-12s %-15s%n",
                    "TYPE", "AMOUNT", "BALANCE", "DATE");
            System.out.println("==============================================================");

            while (rs.next()) {

                System.out.printf("%-12s %-12.2f %-12.2f %-15s%n",
                        rs.getString("TRANSACTION_TYPE"),
                        rs.getDouble("AMOUNT"),
                        rs.getDouble("BALANCE"),
                        rs.getDate("TRANSACTION_DATE"));

            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DBConnection;

public class AccountGenerator {

    public static int generateAccountNumber() {

        int accountNo = 1001;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT MAX(ACCOUNT_NO) FROM CUSTOMER";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                accountNo = rs.getInt(1);

                if (accountNo == 0) {
                    accountNo = 1001;
                } else {
                    accountNo = accountNo + 1;
                }
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accountNo;

    }

}
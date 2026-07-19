package app;

import java.sql.Connection;
import db.DBConnection;

public class ConnectionTest {

    public static void main(String[] args) {

        try {
            Connection con = DBConnection.getConnection();

            if (con != null) {
                System.out.println("Oracle Connected Successfully");
                con.close();
            } else {
                System.out.println("Connection Failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
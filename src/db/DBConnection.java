package db;

import java.sql.*;
import java.io.FileInputStream;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection() {

        Connection con = null;

        try {
            Properties prop = new Properties();

            FileInputStream fis = new FileInputStream("config.properties");
            prop.load(fis);

            Class.forName(prop.getProperty("driver"));

            con = DriverManager.getConnection(
                    prop.getProperty("url"),
                    prop.getProperty("username"),
                    prop.getProperty("password")
            );

            System.out.println("Database Connected Successfully");

        } catch(Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}
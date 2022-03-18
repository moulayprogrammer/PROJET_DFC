package BddPackage;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectBD {

    public ConnectBD() {

    }

    public Connection connect() {
        Connection conn = null;
        // db parameters
        String url = "jdbc:mysql://localhost:3306/dfc_projet?useSSL=false";
        String user = "root";
        String password = "";
        String unicode= "?useUnicode=yes&characterEncoding=UTF-8";

        try {
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to the database");
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        return conn;
    }
}

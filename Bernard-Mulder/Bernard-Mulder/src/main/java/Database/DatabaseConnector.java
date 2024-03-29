package Database;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.Properties;

public class DatabaseConnector {
    // init database constants
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/nerdygadgets";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String MAX_POOL = "250";

    // init connection object
    private Connection connection;
    // init properties object
    private Properties properties;

    // create properties
    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }

    // connect database
    public Connection connect() {
//        if (connection == null) {
//            try {
//                Class.forName(DATABASE_DRIVER);
//                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
//            } catch (ClassNotFoundException | SQLException e) {
//                e.printStackTrace();
//            }
//        }
        try {
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
            ds.setUrl(DATABASE_URL);
            ds.setUsername(USERNAME);
            ds.setPassword(PASSWORD);
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // disconnect database
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

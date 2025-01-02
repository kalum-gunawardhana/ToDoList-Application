package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection ob;
    private Connection connection;

    private DBConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/to_do_list", "root", "1234");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection(){
        return  connection;
    }

    public static DBConnection getInstance() {
        if(ob==null){
            ob=new DBConnection();
        }
        return ob;
    }
}

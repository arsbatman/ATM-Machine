package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBconnection{
    static Connection conn;

    public static Connection getConnection(){
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "1111";
        Connection connection=null;
        try {
            conn= DriverManager.getConnection(url,user,password);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void closeDbConnection() {

        try {
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static PreparedStatement prepareStatement(String SQL) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(SQL);
        return stmt;
    }
}

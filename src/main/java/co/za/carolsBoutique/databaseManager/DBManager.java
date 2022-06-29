package co.za.carolsBoutique.databaseManager;

import jakarta.activation.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;



public class DBManager {
    
    private static DataSource dataSource;
    private static BasicDataSource dataSource1;
    
    
    //******************************************
    
    private  DBManager(){
        
    }
    
    static {
        dataSource1 = new BasicDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3306/carolsboutique?autoReconnect=true&useSSL=false");
        dataSource1.setUsername("root");
        dataSource1.setPassword("root");
        dataSource1.setMinIdle(10);
        dataSource1.setMaxIdle(10);
        dataSource1.setMaxOpenPreparedStatements(100);
                }
    public static Connection getConnection()throws SQLException{
        return dataSource1.getConnection();
    }
    
}

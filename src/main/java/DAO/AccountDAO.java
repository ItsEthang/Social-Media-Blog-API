package DAO;
import java.sql.Connection;
import java.util.*;
import Util.ConnectionUtil;
import java.sql.*;
import Model.Account;

public class AccountDAO {
    //1: Our API should be able to process new User registrations.
    public Account getAccountByUsername(String username){
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //1: Our API should be able to process new User registrations.
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "INSERT INTO account VALUES (?, ?)" ;
            PreparedStatement ps = connection.prepareStatement(sql);

            //write ps's setString and setInt methods here.
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();
            return account;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //2: Our API should be able to process User logins.
    public Account accountLogin(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM account WHERE username = ? and password = ?" ;
            PreparedStatement ps = connection.prepareStatement(sql);

            //write ps's setString and setInt methods here.
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}

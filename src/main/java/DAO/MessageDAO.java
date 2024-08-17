package DAO;
import java.sql.Connection;
import java.util.*;
import Util.ConnectionUtil;
import java.sql.*;
import Model.Message;

public class MessageDAO {
    //3: Our API should be able to process the creation of new messages.
    public Message insertMessage(Message message){
        Connection conn = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "INSERT INTO message VALUES (?, ?, ?)" ;
            PreparedStatement ps = conn.prepareStatement(sql);

            //write ps's setString and setInt methods here.
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(2, message.getTime_posted_epoch());
            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                //return registered account with its id
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //4: Our API should be able to retrieve all messages.
    public List<Message> getAllMessages(){
        Connection conn = ConnectionUtil.getConnection();
        List<Message> allMessages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getInt("time_posted_epoch"));
                allMessages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return allMessages;
    }

    //5: Our API should be able to retrieve a message by its ID.
    public Message getMessageById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getInt("time_posted_epoch"));
                return message;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //6: Our API should be able to delete a message identified by a message ID.
    public void deleteMessageById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //7: Our API should be able to update a message text identified by a message ID
    public Message updateMessageById(int id, String text) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, text);
            ps.setInt(2, id);
            ps.executeUpdate();
            Message updatedMessage = this.getMessageById(id);
            return updatedMessage;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //8: Our API should be able to retrieve all messages written by a particular user.
    public List<Message> getAllMessagesByUser(int userId){
        Connection conn = ConnectionUtil.getConnection();
        List<Message> allMessages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getInt("time_posted_epoch"));
                allMessages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return allMessages;
    }
}

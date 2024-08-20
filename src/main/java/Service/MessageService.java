package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // create message
    public Message createMessage(Message message) {
        int messageLength = message.getMessage_text().length();
        if (messageLength > 0 && messageLength < 256) {
            return this.messageDAO.insertMessage(message);
        }
        return null;
    }

    // retrieve all messages
    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    // retrieve a message by its ID
    public Message getMessageById(int id) {
        return this.messageDAO.getMessageById(id);
    }

    // delete message by id
    public Message deleteMessageById(int id) {
        Message messageToDelete = this.messageDAO.getMessageById(id);
        if (messageToDelete == null)
            return null;
        this.messageDAO.deleteMessageById(id);
        return messageToDelete;
    }

    // update message by id
    public Message updateMessageById(int id, String text) {
        Message messageToUpdate = this.messageDAO.getMessageById(id);
        int textLength = text.length();

        if (!(messageToUpdate == null) && textLength > 0 && textLength < 256) {
            return this.messageDAO.updateMessageById(id, text);
        }
        return null;
    }

    // retrieve all messages by user
    public List<Message> getAllMessagesByUser(int userId) {
        return this.messageDAO.getAllMessagesByUser(userId);
    }
}

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
    public Message updateMessageById(int id, Message message) {
        Message messageToUpdate = this.messageDAO.getMessageById(id);
        int textLength = message.getMessage_text().length();

        if (!(messageToUpdate == null) && !message.getMessage_text().isBlank() && textLength < 256) {
            this.messageDAO.updateMessageById(id, message);
            return this.messageDAO.getMessageById(id);
        }

        return null;
    }

    // retrieve all messages by user
    public List<Message> getAllMessagesByUser(int userId) {
        return this.messageDAO.getAllMessagesByUser(userId);
    }
}

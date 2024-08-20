package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * 
     * @param context The Javalin Context object manages information about both the
     *                HTTP request and response.
     */
    private void registerHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account newAccount = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.accountRegister(newAccount);
        if (addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account accountToLogin = mapper.readValue(ctx.body(), Account.class);
        Account validLogin = accountService.accountLogin(accountToLogin);
        if (validLogin != null) {
            ctx.json(mapper.writeValueAsString(validLogin));
        } else {
            ctx.status(401);
        }
    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.createMessage(newMessage);
        if (addedMessage != null) {
            ctx.json(mapper.writeValueAsString(addedMessage));

        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> allMessages = messageService.getAllMessages();
        ctx.json(allMessages);
        ctx.status(200);
    }

    private void getMessageByIdHandler(Context ctx) {
        Message messageById = messageService.getMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if (messageById == null) {
            ctx.status(200);
            return;
        }
        ctx.json(messageById);
        ctx.status(200);
    }

    private void deleteMessageByIdHandler(Context ctx) {
        Message deletedMessage = messageService.deleteMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if (deletedMessage == null) {
            ctx.status(200);
            return;
        }
        ctx.json(deletedMessage);
        ctx.status(200);
    }

    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message updatedText = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.updateMessageById(Integer.parseInt(ctx.pathParam("message_id")),
                updatedText);
        if (newMessage != null) {
            ctx.json(mapper.writeValueAsString(newMessage));
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesByUserHandler(Context ctx) {
        List<Message> allUserMessages = messageService
                .getAllMessagesByUser(Integer.parseInt(ctx.pathParam("account_id")));
        ctx.json(allUserMessages);
        ctx.status(200);
    }
}
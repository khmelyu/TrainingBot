package trainingBot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.AddUser;
import trainingBot.controller.PhotoCommandController;
import trainingBot.controller.TextCommandController;


@Component
public class UpdateReceiver {
    private final Logger logger = LoggerFactory.getLogger(UpdateReceiver.class);
    private TextCommandController textCommandController;
    private PhotoCommandController photoCommandController;
    private AddUser addUser;

    @Autowired
    public void setDependencies(TextCommandController textCommandController, PhotoCommandController photoCommandController, AddUser addUser) {
        this.textCommandController = textCommandController;
        this.photoCommandController = photoCommandController;
        this.addUser = addUser;

    }

    public void handle(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            addUser.registerUser(update);
            handleTextMessage(update);
        }
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            handlePhotoMessage(update);
        }
        if (update.hasMessage() && update.getMessage().hasAnimation()) {
            handleAnimationMessage(update);
        }
        if (update.hasMessage() && update.getMessage().hasVoice()) {
            handleVoiceMessage(update);
        }
    }

    private void handleCallbackQuery(Update update) {
        String callbackQuery = update.getCallbackQuery().getData();
        logger.info("User: " + update.getCallbackQuery().getId() + " received callback: {}", callbackQuery);
    }

    private void handleTextMessage(Update update) {
        Message message = update.getMessage();
        String text = message.getText();
        textCommandController.handleTextMessage(update);
        logger.info("User: " + update.getMessage().getChatId() + " received message: {}", text);
    }

    private void handlePhotoMessage(Update update) {
        logger.info("User: " + update.getMessage().getChatId() + " upload picture");
        photoCommandController.handlePhotoMessage(update);
    }

    private void handleAnimationMessage(Update update) {
        logger.info("User: " + update.getMessage().getChatId() + " upload .gif");
    }

    private void handleVoiceMessage(Update update) {
        logger.info("User: " + update.getMessage().getChatId() + " upload voice");
    }
}


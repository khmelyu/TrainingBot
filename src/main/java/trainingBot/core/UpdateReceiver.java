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
        addUser.registerUser(update);
        if (update.getMessage().hasText()) {
            handleTextMessage(update);
        }
        if (update.getMessage().hasPhoto()) {
            handlePhotoMessage(update);
        }
        if (update.getMessage().hasAnimation()) {
            handleAnimationMessage(update);
        }
        if (update.getMessage().hasVoice()) {
            handleVoiceMessage(update);
        }
    }

    private void handleTextMessage(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String text = message.getText();
            logger.info("User: " + update.getMessage().getChatId() + " Received message: {}", text);
            textCommandController.handleTextMessage(update);
        }
    }

    private void handlePhotoMessage(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasPhoto()) {
            String text = "Загрузил картинку";
            logger.info("User: " + update.getMessage().getChatId() + " Received message: {}", text);
            photoCommandController.handlePhotoMessage(update);
        }
    }

    private void handleAnimationMessage(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasAnimation()) {
            String text = "Отправил гифку";
            logger.info("User: " + update.getMessage().getChatId() + " Received message: {}", text);
        }
    }

    private void handleVoiceMessage(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasVoice()) {
            String text = "Отправил войс";
            logger.info("User: " + update.getMessage().getChatId() + " Received message: {}", text);
        }
    }
}


package trainingBot.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.commandController.CallbackCommandController;
import trainingBot.controller.commandController.PhotoCommandController;
import trainingBot.controller.commandController.TextCommandController;
import trainingBot.controller.commandController.VoiceController;
import trainingBot.service.AddUser;


@Component
@RequiredArgsConstructor
public class UpdateReceiver {
    private final Logger logger = LoggerFactory.getLogger(UpdateReceiver.class);
    private final TextCommandController textCommandController;
    private final PhotoCommandController photoCommandController;
    private final CallbackCommandController callBackCommandController;
    private final VoiceController voiceController;
    private final AddUser addUser;


    public void handle(Update update) {
        addUser.registerUser(update);

        if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
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
        callBackCommandController.handleMessage(update);
        logger.info("User: " + update.getCallbackQuery().getMessage().getChatId() + " sent a callback: {}", callbackQuery);
    }

    private void handleTextMessage(Update update) {
        String text = update.getMessage().getText();
        textCommandController.handleMessage(update);
        logger.info("User: " + update.getMessage().getChatId() + " sent a message: {}", text);
    }

    private void handlePhotoMessage(Update update) {
        logger.info("User: " + update.getMessage().getChatId() + " upload picture");
        photoCommandController.handleMessage(update);
    }

    private void handleAnimationMessage(Update update) {
        logger.info("User: " + update.getMessage().getChatId() + " upload .gif");
    }

    private void handleVoiceMessage(Update update) {
        voiceController.handleMessage(update);
        logger.info("User: " + update.getMessage().getChatId() + " upload voice");
    }
}


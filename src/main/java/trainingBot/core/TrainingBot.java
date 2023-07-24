package trainingBot.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.Register;


@Slf4j
@Component
public class TrainingBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var chatId = update.getMessage().getChatId();
        var userName = update.getMessage().getChat().getFirstName();
        var msg = update.getMessage().getText();
        if (msg.equals("/start")) Register.registerUser(update.getMessage());
        log.info(userName + "(" + chatId + "): " + msg);
    }
}

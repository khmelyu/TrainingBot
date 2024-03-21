package trainingBot.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

// НАДО НАХУЙ УБРАТЬ ЭТО ПОЗОРИЩЕ И НАПИСАТЬ ЭКЗЕКЬЮТ В СЭНДЛЕР ИЛИ ОТДЕЛЬНЫМ СЕРВИСОМ!
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
    }

}

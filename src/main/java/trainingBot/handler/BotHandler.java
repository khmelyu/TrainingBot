package trainingBot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotHandler {
    void handle(Update update);
}

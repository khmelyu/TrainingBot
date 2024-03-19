package trainingBot.controller.commandController;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandController {
    void handleMessage(Update update);
}

package trainingBot.controller.messageAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.messageService.TriggerAction;
import trainingBot.controller.Sendler;

@Component
public class StartAction implements TriggerAction {
    private final Sendler sendler;

    @Autowired
    public StartAction(Sendler sendler) {
        this.sendler = sendler;
    }

    @Override
    public void execute(Update update) {
        sendler.sendMainMenu(update.getMessage().getChatId());
    }
}
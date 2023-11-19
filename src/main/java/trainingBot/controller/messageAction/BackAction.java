package trainingBot.controller.messageAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.messageService.TriggerAction;
import trainingBot.controller.Sendler;
import trainingBot.controller.service.redis.UserState;
import trainingBot.controller.service.redis.UserStateService;

@Component
public class BackAction implements TriggerAction {
    private final Sendler sendler;
    private final UserStateService userStateService;

    @Autowired
    public BackAction(Sendler sendler, UserStateService userStateService) {
        this.sendler = sendler;
        this.userStateService = userStateService;
    }

    @Override
    public void execute(Update update) {
        sendler.sendMainMenu(update.getMessage().getChatId());
        userStateService.saveUserState(update.getMessage().getChatId().toString(), UserState.MAIN_MENU);
    }
}
package trainingBot.controller.commandController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.action.BackAction;
import trainingBot.controller.action.CoachAction;
import trainingBot.controller.service.redis.UserState;
import trainingBot.controller.service.redis.UserStateService;
import trainingBot.view.Callback;

@Component
public class CallBackCommandController implements CommandController {

    private UserStateService userStateService;
    private BackAction backAction;
    private CoachAction coachAction;

    @Autowired
    public void setDependencies(UserStateService userStateService, BackAction backAction, CoachAction coachAction) {
        this.userStateService = userStateService;
        this.backAction = backAction;
        this.coachAction = coachAction;
    }

    @Override
    public void handleMessage(Update update) {
        Long id = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        Message currentMessage = update.getCallbackQuery().getMessage();
        UserState userState = userStateService.getUserState(id);
        for (Callback callback : Callback.values()) {
            if (callback.getCallbackData().equals(data)) {
                switch (callback){
                    case BACK -> backAction.backActionInline(id, currentMessage);
                    case COACH_MENU -> coachAction.coachAction(id, currentMessage);
                }
            }
        }
    }
}

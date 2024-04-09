package trainingBot.controller.commandController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.action.BackAction;
import trainingBot.controller.action.CoachAction;
import trainingBot.view.Callback;

@Component
public class CallbackCommandController implements CommandController {

    private BackAction backAction;
    private CoachAction coachAction;

    @Autowired
    public void setDependencies(BackAction backAction, CoachAction coachAction) {
        this.backAction = backAction;
        this.coachAction = coachAction;
    }

    @Override
    public void handleMessage(Update update) {
        Long id = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        Message currentMessage = update.getCallbackQuery().getMessage();
        for (Callback callback : Callback.values()) {
            if (callback.getCallbackData().equals(data)) {
                switch (callback) {
                    case BACK -> backAction.backActionInline(id, currentMessage);
                    case COACH_MENU -> coachAction.coachAction(id, currentMessage);
                    case CREATE_TRAININGS -> coachAction.createTraining(id, currentMessage);
                    case OFFLINE_TRAININGS_CREATE -> coachAction.viewTrainingCity(id, currentMessage);
                    case ONLINE_TRAININGS_CREATE -> coachAction.viewOnlineCategory(id, currentMessage, callback.getCallbackText());
                    case MOSCOW -> coachAction.viewMoscowCategory(id, currentMessage, callback.getCallbackText());
                    case SAINT_PETERSBURG -> coachAction.viewSaintsPetersburgCategory(id, currentMessage, callback.getCallbackText());
                }
            }
        }
    }
}
package trainingBot.controller.commandController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.action.BackAction;
import trainingBot.controller.action.CoachAction;
import trainingBot.model.rep.TrainingsListRepository;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Callback;

@Component
public class CallbackCommandController implements CommandController {

    private BackAction backAction;
    private CoachAction coachAction;
    private TrainingsListRepository trainingsListRepository;
    private UserStateService userStateService;

    @Autowired
    public void setDependencies(UserStateService userStateService, TrainingsListRepository trainingsListRepository, BackAction backAction, CoachAction coachAction) {
        this.backAction = backAction;
        this.coachAction = coachAction;
        this.trainingsListRepository = trainingsListRepository;
        this.userStateService = userStateService;
    }

    @Override
    public void handleMessage(Update update) {
        Long id = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        UserState userState = userStateService.getUserState(id);
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
        if (trainingsListRepository.existsByCategory(data)) {
            switch (userState) {
                case CREATE_SAINT_PETERSBURG_TRAINING, CREATE_MOSCOW_TRAINING, CREATE_ONLINE_TRAINING -> coachAction.viewTrainingsOnCategory(id, currentMessage, data);
            }
        }
    }
}
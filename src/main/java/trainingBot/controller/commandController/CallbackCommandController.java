package trainingBot.controller.commandController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.action.BackAction;
import trainingBot.controller.action.CoachAction;
import trainingBot.controller.action.UsersOnTrainingsAction;
import trainingBot.model.rep.TrainingsListRepository;
import trainingBot.model.rep.TrainingsRepository;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Callback;

@Component
public class CallbackCommandController implements CommandController {

    private final BackAction backAction;
    private final CoachAction coachAction;
    private final UsersOnTrainingsAction usersOnTrainingsAction;
    private final TrainingsListRepository trainingsListRepository;
    private final TrainingsRepository trainingsRepository;
    private final UserStateService userStateService;

    @Autowired
    public CallbackCommandController(UserStateService userStateService, TrainingsListRepository trainingsListRepository, BackAction backAction, CoachAction coachAction, UsersOnTrainingsAction usersOnTrainingsAction, TrainingsRepository trainingsRepository) {
        this.backAction = backAction;
        this.coachAction = coachAction;
        this.usersOnTrainingsAction = usersOnTrainingsAction;
        this.trainingsListRepository = trainingsListRepository;
        this.userStateService = userStateService;
        this.trainingsRepository = trainingsRepository;
    }

    @Override
    public void handleMessage(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        Message currentMessage = update.getCallbackQuery().getMessage();
        UserState userState = userStateService.getUserState(id);
        for (Callback callback : Callback.values()) {
            if (callback.getCallbackData().equals(data)) {
                switch (callback) {
                    case BACK -> backAction.backActionInline(id, currentMessage);
                    case COACH_MENU -> coachAction.coachAction(id, currentMessage);
                    case CREATE_TRAININGS -> coachAction.creatingTraining(id, currentMessage);
                    case OFFLINE_TRAININGS_CREATE -> coachAction.viewTrainingCity(id, currentMessage);
                    case ONLINE_TRAININGS_CREATE ->
                            coachAction.viewOnlineCategory(id, currentMessage, callback.getCallbackText());
                    case MOSCOW -> {
                        if (userState.equals(UserState.CREATE_OFFLINE_TRAINING)) {
                            coachAction.viewMoscowCategory(id, currentMessage, callback.getCallbackText());
                        } else
                            usersOnTrainingsAction.viewMoscowCategory(id, currentMessage, callback.getCallbackText());
                    }
                    case SAINT_PETERSBURG -> {
                        if (userState.equals(UserState.CREATE_OFFLINE_TRAINING)) {
                            coachAction.viewSaintsPetersburgCategory(id, currentMessage, callback.getCallbackText());
                        } else
                            usersOnTrainingsAction.viewSaintsPetersburgCategory(id, currentMessage, callback.getCallbackText());
                    }
                    case NEXT_MONTH, PREV_MONTH -> coachAction.viewChangeCalendar(id, currentMessage, data);
                    case CREATED_TRAININGS -> coachAction.createdTrainings(id, currentMessage);
                    case ONLINE_TRAININGS -> usersOnTrainingsAction.viewOnlineCategory(id, currentMessage);
                    case OFFLINE_TRAININGS -> usersOnTrainingsAction.viewTrainingCity(id, currentMessage);
                    case SIGN_UP -> usersOnTrainingsAction.checkUserData(id, currentMessage);
                    case YES -> usersOnTrainingsAction.signUpOnTraining(update);
                    case MY_TRAININGS -> usersOnTrainingsAction.viewMyTrainings(id, currentMessage);
                    case ABORTING -> usersOnTrainingsAction.abortTrainings(update);
                    case IN_ARCHIVE -> coachAction.archiveTraining(update);
                    case DELETE_TRAINING -> coachAction.deleteTraining(update);
                    case ARCHIVE_TRAININGS -> coachAction.archivedTrainings(id, currentMessage);
                }
            }
        }

        switch (userState) {
            case CREATE_SAINT_PETERSBURG_TRAINING, CREATE_MOSCOW_TRAINING, CREATE_ONLINE_TRAINING -> {
                if (trainingsListRepository.existsByCategory(data)) {
                    coachAction.viewTrainingsOnCategory(id, currentMessage, data);
                }
            }
            case MOSCOW_TRAININGS, SAINT_PETERSBURG_TRAININGS, ONLINE_TRAININGS -> {
                if (trainingsRepository.existsByCategory(data)) {
                    usersOnTrainingsAction.viewTrainingsOnCategory(id, currentMessage, data);
                }
            }
            case TRAININGS_ON_CITY_FOR_CREATE -> coachAction.viewCalendar(id, currentMessage, data);
            case TRAINING_START_TIME -> coachAction.viewTrainingEndTime(id, currentMessage, data);
            case TRAINING_END_TIME -> coachAction.setTrainingEndTime(update);
        }
        if (data.matches("\\d{4}-\\d{2}-\\d{2}")) {
            coachAction.viewTrainingStartTime(id, currentMessage, data);
        }
        if (data.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$")) {
            switch (userState) {
                case TRAININGS_ON_CITY, MY_TRAININGS -> usersOnTrainingsAction.reviewTraining(id, currentMessage, data);
                case CREATED_TRAININGS -> coachAction.reviewTraining(id, currentMessage, data);
            }
        }
    }
}
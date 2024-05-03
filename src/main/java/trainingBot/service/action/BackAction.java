package trainingBot.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import trainingBot.service.redis.TrainingDataService;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Callback;
import trainingBot.view.Sendler;

@Service
@PropertySources({@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8")})
public class BackAction {


    private final Sendler sendler;
    private final UserStateService userStateService;
    private final CoachAction coachAction;
    private final TrainingDataService trainingDataService;
    private final UsersOnTrainingsAction usersOnTrainingsAction;
    @Value("${main.menu.message}")
    private String mainMenuMessage;
    @Value("${training.menu}")
    private String trainingMenu;
    @Value("${training.type}")
    private String trainingType;
    @Value("${coach.menu}")
    private String coachMenu;
    @Value("${training.city}")
    private String trainingCity;

    @Autowired
    public BackAction(
            @Lazy Sendler sendler,
            UserStateService userStateService, CoachAction coachAction, TrainingDataService trainingDataService, UsersOnTrainingsAction usersOnTrainingsAction
    ) {
        this.sendler = sendler;
        this.userStateService = userStateService;
        this.coachAction = coachAction;
        this.trainingDataService = trainingDataService;
        this.usersOnTrainingsAction = usersOnTrainingsAction;
    }

    public void backAction(long id) {
        sendler.sendMainMenu(id, mainMenuMessage);
        userStateService.setUserState(id, UserState.MAIN_MENU);
    }

    public void backActionInline(long id, Message currentMessage) {
        UserState userState = userStateService.getUserState(id);
        if (userState != null) {
            switch (userState) {
                case COACH_MENU, ONLINE_TRAININGS, OFFLINE_TRAININGS, MY_TRAININGS -> {
                    sendler.updateTrainingsMenu(id, trainingMenu, currentMessage);
                    userStateService.setUserState(id, UserState.TRAININGS_MENU);
                }
                case CREATE_TRAINING, CREATED_TRAININGS, ARCHIVE_TRAININGS -> {
                    sendler.sendCoachMenu(id, coachMenu, currentMessage);
                    userStateService.setUserState(id, UserState.COACH_MENU);
                }
                case CREATE_OFFLINE_TRAINING, CREATE_ONLINE_TRAINING, TRAININGS_ON_CITY_FOR_CREATE -> {
                    sendler.sendCreateMenu(id, trainingType, currentMessage);
                    userStateService.setUserState(id, UserState.CREATE_TRAINING);
                }
                case CREATE_MOSCOW_TRAINING, CREATE_SAINT_PETERSBURG_TRAINING -> {
                    sendler.sendCityChoice(id, trainingCity, currentMessage);
                    userStateService.setUserState(id, UserState.CREATE_OFFLINE_TRAINING);
                }
                case MOSCOW_TRAININGS, SAINT_PETERSBURG_TRAININGS -> {
                    sendler.sendCityChoice(id, trainingCity, currentMessage);
                    userStateService.setUserState(id, UserState.OFFLINE_TRAININGS);
                }
                case MARK_USERS -> {
                    userStateService.setUserState(id, UserState.CREATED_TRAININGS);
                    coachAction.reviewTraining(id, currentMessage, trainingDataService.getTrainingId(id));
                }
                case CHECK_USER_DATA -> {
                    userStateService.setUserState(id, UserState.MARK_USERS);
                    coachAction.viewMarkUsersMenu(id, currentMessage);
                }
                case SELECT_MY_TRAINING -> usersOnTrainingsAction.viewMyTrainings(id, currentMessage);
                case SELECT_TRAINING -> {
                    String category = trainingDataService.getCategory(id);
                    usersOnTrainingsAction.viewTrainingsOnCategory(id, currentMessage, category);
                }
                case SELECT_COACH_TRAINING -> {
                    userStateService.setUserState(id, UserState.COACH_MENU);
                    coachAction.createdTrainings(id, currentMessage);
                }
                case SELECT_ARCHIVE_TRAINING -> coachAction.archivedTrainings(id, currentMessage);
                case FEEDBACK_VIEW -> coachAction.viewPresenceList(id, currentMessage);
                case FEEDBACK_USER_LIST -> {
                    userStateService.setUserState(id, UserState.ARCHIVE_TRAININGS);
                    coachAction.reviewTraining(id, currentMessage, trainingDataService.getTrainingId(id));
                }
                case TRAININGS_ON_CITY -> {
                    String city = trainingDataService.getCity(id);

                    if (Callback.MOSCOW.getCallbackText().equals(city)) {
                        usersOnTrainingsAction.viewMoscowCategory(id, currentMessage, city);
                    }
                    if (Callback.SAINT_PETERSBURG.getCallbackText().equals(city)) {
                        usersOnTrainingsAction.viewSaintsPetersburgCategory(id, currentMessage, city);
                    }
                    if (Callback.ONLINE_TRAININGS_CREATE.getCallbackText().equals(city)) {
                        usersOnTrainingsAction.viewOnlineCategory(id, currentMessage);
                    }
                }

            }
        } else {
            userStateService.setUserState(id, UserState.MY_TRAININGS);
            backActionInline(id, currentMessage);
        }
    }
}



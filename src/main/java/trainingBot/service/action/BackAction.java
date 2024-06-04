package trainingBot.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
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
    private final MainMenuAction mainMenuAction;
    private final ContactSearchAction contactSearchAction;
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
            Sendler sendler,
            UserStateService userStateService, CoachAction coachAction, MainMenuAction mainMenuAction, ContactSearchAction contactSearchAction, TrainingDataService trainingDataService, UsersOnTrainingsAction usersOnTrainingsAction
    ) {
        this.sendler = sendler;
        this.userStateService = userStateService;
        this.coachAction = coachAction;
        this.mainMenuAction = mainMenuAction;
        this.contactSearchAction = contactSearchAction;
        this.trainingDataService = trainingDataService;
        this.usersOnTrainingsAction = usersOnTrainingsAction;
    }

    public void backAction(long id) {
        UserState userState = userStateService.getUserState(id);
        if (userState != null) {
            switch (userState) {
                case WORK_NOTES_MENU, CERTIFICATES_MENU, COMPETENCIES_MENU -> mainMenuAction.documents(id);
                case CONTACT_SEARCH_NO, CONTACT_SEARCH_YES -> mainMenuAction.contactSearch(id);
                case CONTACT_SEARCH_STAFFER, CONTACT_SEARCH_GALLERY -> contactSearchAction.yesMenu(id);
                case MARATHON_FEEDBACK -> mainMenuAction.marathonInfo(id);

                default -> {
                    sendler.sendMainMenu(id, mainMenuMessage);
                    userStateService.setUserState(id, UserState.MAIN_MENU);
                }
            }
        } else {
            userStateService.setUserState(id, UserState.DOCUMENTS_MENU);
            backAction(id);
        }
    }


    public void backActionInline(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        Message currentMessage = update.getCallbackQuery().getMessage();
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
                case CREATE_OFFLINE_TRAINING, CREATE_ONLINE_TRAINING -> {
                    sendler.sendCreateMenu(id, trainingType, currentMessage);
                    userStateService.setUserState(id, UserState.CREATE_TRAINING);
                }
                case CREATE_MOSCOW_TRAINING, CREATE_SAINT_PETERSBURG_TRAINING -> {
                    sendler.sendCityChoice(id, trainingCity, currentMessage);
                    userStateService.setUserState(id, UserState.CREATE_OFFLINE_TRAINING);
                }
                case TRAININGS_ON_CITY_FOR_CREATE -> {
                    String city = trainingDataService.getCity(id);
                    if (city.equals(Callback.MOSCOW.getCallbackText())) {
                        userStateService.setUserState(id, UserState.CREATE_OFFLINE_TRAINING);
                        coachAction.viewMoscowCategory(id, currentMessage, city);
                    }
                    if (city.equals(Callback.SAINT_PETERSBURG.getCallbackText())) {
                        userStateService.setUserState(id, UserState.CREATE_OFFLINE_TRAINING);
                        coachAction.viewSaintsPetersburgCategory(id, currentMessage, city);
                    }
                    if (city.equals(Callback.ONLINE_TRAININGS_CREATE.getCallbackText())) {
                        userStateService.setUserState(id, UserState.CREATE_TRAINING);
                        coachAction.viewOnlineCategory(id, currentMessage, city);
                    }

                }
                case CALENDAR -> {
                    String category = trainingDataService.getCategory(id);
                    if (trainingDataService.getCity(id).equals(Callback.MOSCOW.getCallbackText())) {
                        userStateService.setUserState(id, UserState.CREATE_MOSCOW_TRAINING);
                    }
                    if (trainingDataService.getCity(id).equals(Callback.SAINT_PETERSBURG.getCallbackText())) {
                        userStateService.setUserState(id, UserState.CREATE_SAINT_PETERSBURG_TRAINING);
                    }
                    if (trainingDataService.getCity(id).equals(Callback.ONLINE_TRAININGS_CREATE.getCallbackText())) {
                        userStateService.setUserState(id, UserState.CREATE_ONLINE_TRAINING);
                    }
                    coachAction.viewTrainingsOnCategory(id, currentMessage, category);
                }

                case TRAINING_START_TIME -> {
                    String trainingListId = trainingDataService.getTrainingListId(id);
                    coachAction.viewCalendar(id, currentMessage, trainingListId);
                }

                case TRAINING_END_TIME -> {
                    String date = trainingDataService.getTrainingDate(id);
                    coachAction.viewTrainingStartTime(id, currentMessage, date);
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
                case CHECK_MY_DATA -> {
                    userStateService.setUserState(id, UserState.TRAININGS_ON_CITY);
                    usersOnTrainingsAction.reviewTraining(id, currentMessage, trainingDataService.getTrainingId(id));
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
            backActionInline(update);
        }
    }
}



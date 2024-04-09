package trainingBot.controller.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

@Component
@PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8")
public class CoachAction {
    @Value("${coach.menu}")
    private String coachMenu;
    @Value("${training.type}")
    private String trainingType;
    @Value("${training.category}")
    private String trainingCategory;
    @Value("${training.city}")
    private String trainingCity;
    @Value("${training.choice}")
    private String trainingChoice;


    private Sendler sendler;
    private UserStateService userStateService;

    @Autowired
    public void setDependencies(
            @Lazy Sendler sendler,
            UserStateService userStateService
    ) {
        this.sendler = sendler;
        this.userStateService = userStateService;
    }


    public void coachAction(long id, Message currentMessage) {
        sendler.sendCoachMenu(id, coachMenu, currentMessage);
        userStateService.setUserState(id, UserState.COACH_MENU);
    }

    public void createTraining(long id, Message currentMessage) {
        sendler.sendCreateMenu(id, trainingType, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_TRAINING);
    }

    public void viewTrainingCity(long id, Message currentMessage) {
        sendler.sendCityChoice(id, trainingCity, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_OFFLINE_TRAINING);
    }

    public void viewOnlineCategory(long id, Message currentMessage, String data) {
        userStateService.setCity(id, data);
        sendler.sendOnlineCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_ONLINE_TRAINING);
    }

    public void viewMoscowCategory(long id, Message currentMessage, String data) {
        userStateService.setCity(id, data);
        sendler.sendMoscowCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_MOSCOW);
    }

    public void viewSaintsPetersburgCategory(long id, Message currentMessage, String data) {
        userStateService.setCity(id, data);
        sendler.sendSaintPetersburgCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_SAINT_PETERSBURG);
    }
    public void viewTrainingsOnCategory(long id, Message currentMessage, String data) {
        userStateService.setCategory(id, data);
        sendler.sendTrainingsOnCategory(id, trainingChoice, currentMessage, userStateService.getCity(id), data);
        userStateService.setUserState(id, UserState.VIEW_TRAINING_ON_CITY);
    }
}
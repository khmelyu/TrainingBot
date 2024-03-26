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

    public void createOnlineTraining(long id, Message currentMessage) {
        sendler.sendCreateOnlineMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_ONLINE_TRAINING);
    }
    public void createOfflineTraining(long id, Message currentMessage) {
        sendler.sendCreateOfflineMenu(id, trainingCity, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_OFFLINE_TRAINING);
    }

    public void createMoscowTraining(long id, Message currentMessage) {
        sendler.sendCreateMoscowMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.MOSCOW);
    }

    public void createSaintsPetersburgTraining(long id, Message currentMessage) {
        sendler.sendCreateSaintPetersburgMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.SAINT_PETERSBURG);
    }
}


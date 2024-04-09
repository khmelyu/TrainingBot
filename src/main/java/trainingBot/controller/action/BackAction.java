package trainingBot.controller.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

@Component
@PropertySources({@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8")})
public class BackAction {
    private Sendler sendler;
    private UserStateService userStateService;

    @Value("${main.menu.message}")
    private String mainMenuMessage;
    @Value("${training.type}")
    private String trainingType;
    @Value("${coach.menu}")
    private String coachMenu;
    @Value("${training.city}")
    private String trainingCity;


    @Autowired
    public void setDependencies(
            @Lazy Sendler sendler,
            UserStateService userStateService
    ) {
        this.sendler = sendler;
        this.userStateService = userStateService;
    }

    public void backAction(long id) {
        sendler.sendMainMenu(id, mainMenuMessage);
        userStateService.setUserState(id, UserState.MAIN_MENU);
    }

    public void backActionInline(long id, Message curentMessage) {
        UserState state = userStateService.getUserState(id);
        switch (state) {
            case COACH_MENU -> {
                sendler.updateTrainingsMenu(id, trainingType, curentMessage);
                userStateService.setUserState(id, UserState.TRAININGS_MENU);
            }
            case CREATE_TRAINING -> {
                sendler.sendCoachMenu(id, coachMenu, curentMessage);
                userStateService.setUserState(id, UserState.COACH_MENU);
            }
            case CREATE_OFFLINE_TRAINING, CREATE_ONLINE_TRAINING, VIEW_TRAINING_ON_CITY -> {
                sendler.sendCreateMenu(id, trainingType, curentMessage);
                userStateService.setUserState(id, UserState.CREATE_TRAINING);
            }
            case CREATE_MOSCOW_TRAINING, CREATE_SAINT_PETERSBURG_TRAINING -> {
                sendler.sendCityChoice(id, trainingCity, curentMessage);
                userStateService.setUserState(id, UserState.CREATE_OFFLINE_TRAINING);
            }
        }
    }
}

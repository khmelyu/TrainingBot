package trainingBot.controller.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import trainingBot.controller.UpdateReceiver;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

@Component
@PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8")
public class UsersOnTrainingsAction {
    private final Logger logger = LoggerFactory.getLogger(UpdateReceiver.class);
    private final Sendler sendler;
    private final UserStateService userStateService;


    @Value("${training.category}")
    private String trainingCategory;
    @Value("${training.city}")
    private String trainingCity;
    @Value("${training.choice}")
    private String trainingChoice;
    @Value("${online}")
    private String online;

    @Autowired
    public UsersOnTrainingsAction(@Lazy Sendler sendler, UserStateService userStateService) {
        this.sendler = sendler;
        this.userStateService = userStateService;
    }

    public void viewOnlineCategory(long id, Message currentMessage) {
        userStateService.setCity(id, online);
        sendler.sendOnlineCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.ONLINE_TRAININGS);
    }

    public void viewTrainingCity(long id, Message currentMessage) {
        sendler.sendCityChoice(id, trainingCity, currentMessage);
        userStateService.setUserState(id, UserState.OFFLINE_TRAININGS);
    }

    public void viewMoscowCategory(long id, Message currentMessage, String data) {
        userStateService.setCity(id, data);
        sendler.sendMoscowCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.MOSCOW_TRAININGS);
    }

    public void viewSaintsPetersburgCategory(long id, Message currentMessage, String data) {
        userStateService.setCity(id, data);
        sendler.sendSaintPetersburgCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.SAINT_PETERSBURG_TRAININGS);
    }

    public void viewTrainingsOnCategory(long id, Message currentMessage, String data) {
        userStateService.setCategory(id, data);
        sendler.sendTrainingsOnCategory(id, trainingChoice, currentMessage, userStateService.getCity(id), data);
        userStateService.setUserState(id, UserState.TRAININGS_ON_CITY);
    }
}

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
    @Value("${choice.training.type}")
    private String trainingType;




    @Autowired
    public void setDependencies(
            @Lazy Sendler sendler,
            UserStateService userStateService
    ) {
        this.sendler = sendler;
        this.userStateService = userStateService;
    }

    public void backAction(long id){
        sendler.sendMainMenu(id,mainMenuMessage);
        userStateService.setUserState(id, UserState.MAIN_MENU);
    }
    public void backActionInline(long id, Message curentMessage){
        sendler.updateTrainingsMenu(id, trainingType, curentMessage);
        userStateService.setUserState(id, UserState.TRAININGS_MENU);
    }
}

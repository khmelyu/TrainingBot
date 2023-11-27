package trainingBot.controller.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.service.redis.UserState;
import trainingBot.controller.service.redis.UserStateService;
import trainingBot.view.Sendler;

@Component
public class TrainingsAction {
    private Sendler sendler;
    private UserStateService userStateService;

    @Autowired
    public void setDependencies(
            UserStateService userStateService,
            @Lazy Sendler sendler
    ) {
        this.userStateService = userStateService;
        this.sendler = sendler;
    }
    public void trainingsAction(Update update) {
        Long id = update.getMessage().getChatId();
        sendler.sendTrainingsMenu(id);
        userStateService.setUserState(id, UserState.TRAININGS_MENU);
    }
}

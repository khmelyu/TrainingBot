package trainingBot.controller.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import trainingBot.controller.service.redis.UserStateService;
import trainingBot.view.Sendler;

@Component
@PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8")
public class CoachAction {
    @Value("${coach.menu}")
    private String coachMenu;
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

    public void coachAction(Long id, Message currentMessage) {
        sendler.sendCoachMenu(id, coachMenu, currentMessage);
    }
}


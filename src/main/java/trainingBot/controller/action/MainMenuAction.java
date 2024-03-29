package trainingBot.controller.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.model.entity.User;
import trainingBot.model.rep.UserRepository;
import trainingBot.view.Sendler;

@Component
@PropertySources({@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8"), @PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8")})
public class MainMenuAction {
    private Sendler sendler;
    private UserRepository userRepository;
    private StartAction startAction;
    private UserStateService userStateService;


    @Value("${user.data.ok}")
    private String userDataOk;
    @Value("${training.type}")
    private String trainingType;
    @Value("${feedback.message}")
    private String feedbackMessage;
    @Value("${documents.message}")
    private String documentsMessage;

    @Autowired
    public void setDependencies(
            @Lazy Sendler sendler,
            UserRepository userRepository,
            UserStateService userStateService,
            StartAction startAction
    ) {
        this.userRepository = userRepository;
        this.sendler = sendler;
        this.userStateService = userStateService;
        this.startAction = startAction;
    }

    public void userData(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        String msg = user.userData();
        sendler.sendMyDataMenu(id, msg);
        userStateService.setUserState(id, UserState.USER_DATA);
    }

    public void userDataOk(Long id) {
        sendler.sendMainMenu(id, userDataOk);
        userStateService.setUserState(id, UserState.MAIN_MENU);
    }

    public void userDataFail(Long id) {
        startAction.startAction(id);
    }

    public void trainingsAction(Long id) {
        sendler.sendTrainingsMenu(id, trainingType);
        userStateService.setUserState(id, UserState.TRAININGS_MENU);
    }

    public void feedback(Long id) {
        sendler.sendTextMessage(id, feedbackMessage);
    }

    public void documents(Long id) {
        sendler.sendDocumentsMenu(id, documentsMessage);
        userStateService.setUserState(id, UserState.DOCUMENTS_MENU);
    }
}

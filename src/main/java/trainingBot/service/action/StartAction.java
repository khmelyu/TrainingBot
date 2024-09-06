package trainingBot.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.model.entity.User;
import trainingBot.model.rep.UserRepository;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;


@Service
@PropertySources({@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8")})
public class StartAction {

    private final Sendler sendler;
    private final UserStateService userStateService;
    private final UserRepository userRepository;

    @Value("${main.menu.message}")
    private String mainMenuMessage;
    @Value("${start.message}")
    private String startMessage;
    @Value("${start.login.message}")
    private String loginMessage;
    @Value("${start.wrong.login.message}")
    private String wrongLoginMessage;
    @Value("${start.password.message}")
    private String passwordMessage;
    @Value("${start.wrong.password.message}")
    private String wrongPasswordMessage;
    @Value("${start.login}")
    private String login;
    @Value("${start.password}")
    private String password;
    @Value("${user.data.fail}")
    private String userDataFail;

    @Autowired
    public StartAction(UserRepository userRepository, UserStateService userStateService, Sendler sendler) {
        this.userRepository = userRepository;
        this.userStateService = userStateService;
        this.sendler = sendler;
    }

    public void startAction(long id) {
        if (userRepository.findById(id).isEmpty()) {
            sendler.sendTextMessage(id, loginMessage);
            userStateService.setUserState(id, UserState.LOGIN);
        } else {
            restart(id);
        }
    }

    public void inputLogin(Update update) {
        long id = update.getMessage().getChatId();
        if (update.getMessage().getText().equals(login)) {
            sendler.sendTextMessage(id, passwordMessage);
            userStateService.setUserState(id, UserState.PASSWORD);
        } else sendler.sendTextMessage(id, wrongLoginMessage);
    }

    public void inputPassword(Update update) {
        long id = update.getMessage().getChatId();
        if (update.getMessage().getText().equals(password)) {
            restart(id);
        } else sendler.sendTextMessage(id, wrongPasswordMessage);
    }

    public void restart(long id) {
        String msg;
        if (userStateService.getUserState(id).equals(UserState.PASSWORD)) {
            msg = startMessage;
            User user = userRepository.findById(id).orElse(new User());
            user.setId(id);
            userRepository.save(user);
            sendler.sendMyDataMenu(id, msg);
            userStateService.setUserState(id, UserState.REGISTER);
        } else {
            sendler.sendMainMenu(id, mainMenuMessage);
        }

    }
}


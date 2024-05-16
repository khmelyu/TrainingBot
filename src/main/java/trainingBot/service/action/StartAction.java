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
    @Value("${add.name.message}")
    private String addNameMessage;
    @Value("${add.lastname.message}")
    private String addLastNameMessage;
    @Value("${add.phone.message}")
    private String addPhoneMessage;
    @Value("${add.city.message}")
    private String addCityMessage;
    @Value("${add.gallery.message}")
    private String addGalleryMessage;
    @Value("${add.rate.message}")
    private String addRateMessage;
    @Value("${main.menu.message}")
    private String mainMenu;
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
        if (userStateService.getUserState(id).equals(UserState.USER_DATA) || userStateService.getUserState(id).equals(UserState.CHECK_MY_DATA) || userStateService.getUserState(id).equals(UserState.MARATHON_DATA)) {
            msg = userDataFail;
        } else {
            msg = startMessage;
        }
        sendler.sendTextMessage(id, msg);
        sendler.sendTextMessage(id, addNameMessage);
        userStateService.setUserState(id, UserState.START);
    }

    public void inputName(Update update) {
        long id = update.getMessage().getChatId();
        userStateService.setUserState(id, UserState.SET_NAME);
        addName(update);
    }

    public void addName(Update update) {
        long id = update.getMessage().getChatId();
        String userName = update.getMessage().getText();
        User user = userRepository.findById(id).orElse(new User());
        user.setId(id);
        user.setName(userName);
        userRepository.save(user);
        userStateService.setUserState(id, UserState.SET_LASTNAME);
        sendler.sendTextMessage(id, addLastNameMessage);
    }

    public void addLastName(Update update) {
        long id = update.getMessage().getChatId();
        String userLastName = update.getMessage().getText();
        User user = userRepository.findById(id).orElse(new User());
        user.setId(id);
        user.setLastname(userLastName);
        userRepository.save(user);
        userStateService.setUserState(id, UserState.SET_PHONE);
        sendler.sendTextMessage(id, addPhoneMessage);
    }

    public void addPhone(Update update) {
        long id = update.getMessage().getChatId();
        String userPhone = update.getMessage().getText();
        User user = userRepository.findById(id).orElse(new User());
        user.setId(id);
        user.setPhone(userPhone);
        userRepository.save(user);
        userStateService.setUserState(id, UserState.SET_CITY);
        sendler.sendTextMessage(id, addCityMessage);
    }

    public void addCity(Update update) {
        long id = update.getMessage().getChatId();
        String userCity = update.getMessage().getText();
        User user = userRepository.findById(id).orElse(new User());
        user.setId(id);
        user.setCity(userCity);
        userRepository.save(user);
        userStateService.setUserState(id, UserState.SET_GALLERY);
        sendler.sendTextMessage(id, addGalleryMessage);
    }

    public void addGallery(Update update) {
        long id = update.getMessage().getChatId();
        String userGallery = update.getMessage().getText();
        User user = userRepository.findById(id).orElse(new User());
        user.setId(id);
        user.setGallery(userGallery);
        userRepository.save(user);
        userStateService.setUserState(id, UserState.SET_RATE);
        sendler.sendTextMessage(id, addRateMessage);
    }

    public void addRate(Update update) {
        long id = update.getMessage().getChatId();
        String userRate = update.getMessage().getText();
        User user = userRepository.findById(id).orElse(new User());
        user.setId(id);
        user.setRate(userRate);
        userRepository.save(user);
        userStateService.setUserState(id, UserState.MAIN_MENU);
        sendler.sendMainMenu(id, mainMenu);
    }
}


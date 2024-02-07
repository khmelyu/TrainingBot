package trainingBot.controller.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.service.redis.UserState;
import trainingBot.controller.service.redis.UserStateService;
import trainingBot.model.entity.User;
import trainingBot.model.rep.UserRepository;
import trainingBot.view.Sendler;


@Component
@PropertySources({@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8")})
public class StartAction {

    @Value("${start.message}")
    private String startMessage;

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


    private Sendler sendler;
    private UserStateService userStateService;
    private UserRepository userRepository;

    @Autowired
    public void setDependencies(
            UserRepository userRepository,
            UserStateService userStateService,
            @Lazy Sendler sendler
    ) {
        this.userRepository = userRepository;
        this.userStateService = userStateService;
        this.sendler = sendler;
    }

    public void startAction(Update update) {
        Long id = update.getMessage().getChatId();
        String msg = null;
        if (userStateService.getUserState(id).equals(UserState.USER_DATA)) {
            msg = userDataFail;
        } else {
            msg = startMessage;
        }
        sendler.sendTextMessage(id, msg);
        userStateService.setUserState(id, UserState.START);
    }

    public void inputName(Update update) {
        Long id = update.getMessage().getChatId();
        sendler.sendTextMessage(id, addNameMessage);
        userStateService.setUserState(id, UserState.SET_NAME);
    }

    public void addName(Update update) {
        Long id = update.getMessage().getChatId();
        String userName = update.getMessage().getText();
        User user = userRepository.findById(id).orElse(new User());
        user.setId(id);
        user.setName(userName);
        userRepository.save(user);
        userStateService.setUserState(id, UserState.SET_LASTNAME);
        sendler.sendTextMessage(id, addLastNameMessage);
    }

    public void addLastName(Update update) {
        Long id = update.getMessage().getChatId();
        String userLastName = update.getMessage().getText();
        User user = userRepository.findById(id).orElse(new User());
        user.setId(id);
        user.setLastname(userLastName);
        userRepository.save(user);
        userStateService.setUserState(id, UserState.SET_PHONE);
        sendler.sendTextMessage(id, addPhoneMessage);
    }

    public void addPhone(Update update) {
        Long id = update.getMessage().getChatId();
        String userPhone = update.getMessage().getText();
        User user = userRepository.findById(id).orElse(new User());
        user.setId(id);
        user.setPhone(userPhone);
        userRepository.save(user);
        userStateService.setUserState(id, UserState.SET_CITY);
        sendler.sendTextMessage(id, addCityMessage);
    }

    public void addCity(Update update) {
        Long id = update.getMessage().getChatId();
        String userCity = update.getMessage().getText();
        User user = userRepository.findById(id).orElse(new User());
        user.setId(id);
        user.setCity(userCity);
        userRepository.save(user);
        userStateService.setUserState(id, UserState.SET_GALLERY);
        sendler.sendTextMessage(id, addGalleryMessage);
    }

    public void addGallery(Update update) {
        Long id = update.getMessage().getChatId();
        String userGallery = update.getMessage().getText();
        User user = userRepository.findById(id).orElse(new User());
        user.setId(id);
        user.setGallery(userGallery);
        userRepository.save(user);
        userStateService.setUserState(id, UserState.SET_RATE);
        sendler.sendTextMessage(id, addRateMessage);
    }

    public void addRate(Update update) {
        Long id = update.getMessage().getChatId();
        String userRate = update.getMessage().getText();
        User user = userRepository.findById(id).orElse(new User());
        user.setId(id);
        user.setRate(userRate);
        userRepository.save(user);
        userStateService.setUserState(id, UserState.MAIN_MENU);
        sendler.sendMainMenu(id, mainMenu);
    }
}


package trainingBot.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingBot.model.entity.CantataZnaet;
import trainingBot.model.entity.User;
import trainingBot.model.rep.CantataZnaetRepository;
import trainingBot.model.rep.UserRepository;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

import java.util.List;

@Service
@PropertySources({@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8"), @PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8")})
public class MainMenuAction {
    private final Sendler sendler;
    private final UserRepository userRepository;
    private final StartAction startAction;
    private final UserStateService userStateService;
    private final CantataZnaetRepository cantataZnaetRepository;


    @Value("${user.data.ok}")
    private String userDataOk;
    @Value("${training.menu}")
    private String trainingMenu;
    @Value("${feedback.message}")
    private String feedbackMessage;
    @Value("${documents.message}")
    private String documentsMessage;
    @Value("${info.search.message}")
    private String infoSearchMessage;
    @Value("${contact.search.message}")
    private String contactSearchMessage;
    @Value("${cz.search.message}")
    private String czSearchMessage;
    @Value("${cz.search.empty.message}")
    private String czSearchEmptyMessage;

    @Autowired
    public MainMenuAction(
            Sendler sendler,
            UserRepository userRepository,
            UserStateService userStateService,
            StartAction startAction, CantataZnaetRepository cantataZnaetRepository
    ) {
        this.userRepository = userRepository;
        this.sendler = sendler;
        this.userStateService = userStateService;
        this.startAction = startAction;
        this.cantataZnaetRepository = cantataZnaetRepository;
    }

    public void userData(long id) {
        User user = userRepository.findById(id).orElseThrow();
        String msg = user.userData();
        sendler.sendMyDataMenu(id, msg);
        userStateService.setUserState(id, UserState.USER_DATA);
    }

    public void userDataOk(long id) {
        sendler.sendMainMenu(id, userDataOk);
        userStateService.setUserState(id, UserState.MAIN_MENU);
    }

    public void wrongUserData(long id) {
        startAction.startAction(id);
    }

    public void trainingsAction(long id) {
        sendler.sendTrainingsMenu(id, trainingMenu);
        userStateService.setUserState(id, UserState.TRAININGS_MENU);
    }

    public void feedback(long id) {
        sendler.sendTextMessage(id, feedbackMessage);
    }

    public void documents(long id) {
        sendler.sendDocumentsMenu(id, documentsMessage);
        userStateService.setUserState(id, UserState.DOCUMENTS_MENU);
    }

    public void infoSearch(long id) {
        sendler.sendInfoSearchMenu(id, infoSearchMessage);
        userStateService.setUserState(id, UserState.INFO_SEARCH);
    }

    public void contactSearch(long id) {
        sendler.sendContactSearchMenu(id, contactSearchMessage);
        userStateService.setUserState(id, UserState.CONTACT_SEARCH);
    }

    public void czSearchMessage(long id) {
        sendler.sendBack(id, czSearchMessage);
        userStateService.setUserState(id, UserState.CZ_SEARCH);
    }

    public void czSearchAction(long id, String text) {
        int maxResults = 25;
        Pageable pageable = PageRequest.of(0, maxResults);
        List<CantataZnaet> cantataZnaet = cantataZnaetRepository.findByTitleLike("%" + text.toLowerCase() + "%", pageable);

        if (cantataZnaet.isEmpty()) {
            sendler.sendTextMessage(id, czSearchEmptyMessage);
        } else {
            StringBuilder resultBuilder = new StringBuilder();
            for (CantataZnaet cz : cantataZnaet) {
                resultBuilder.append(cz.getTitle()).append("\n");
                resultBuilder.append(cz.getLink()).append("\n");
                resultBuilder.append("\n");
            }
            sendler.sendMainMenu(id, String.valueOf(resultBuilder));
            userStateService.setUserState(id, UserState.MAIN_MENU);
        }
    }

}
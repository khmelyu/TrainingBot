package trainingBot.service.action;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingBot.model.entity.*;
import trainingBot.model.rep.*;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@PropertySources({@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8"), @PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8"), @PropertySource(value = "classpath:jumanji.txt", encoding = "UTF-8")})
public class MainMenuAction {
    private final Sendler sendler;
    private final UserRepository userRepository;
    private final StartAction startAction;
    private final UserStateService userStateService;
    private final CantataZnaetRepository cantataZnaetRepository;
    private final MarathonRepository marathonRepository;
    private final JumanjiRepository jumanjiRepository;
    private final Ambassador2024Repository ambassador2024Repository;


    @Value("${main.menu.message}")
    private String mainMenuMessage;
    @Value("${user.data.ok}")
    private String userDataOk;
    @Value("${user.data}")
    private String userData;
    @Value("${user.new.data}")
    private String userNewData;
    @Value("${need.update.data}")
    private String needUpdateData;
    @Value("${need.update.data.message}")
    private String needUpdateDataMessage;
    @Value("${training.menu}")
    private String trainingMenu;
    @Value("${feedback.message}")
    private String feedbackMessage;
    @Value("${documents.message}")
    private String documentsMessage;
    @Value("${info.search.message}")
    private String infoSearchMessage;
    @Value("${contact.search.message}")
    private String telephonyMessage;
    @Value("${cz.search.message}")
    private String czSearchMessage;
    @Value("${cz.search.empty.message}")
    private String czSearchEmptyMessage;
    @Value("${marathon.info}")
    private String marathonInfo;
    @Value("${jumanji.message}")
    private String jumanjiMessage;
    @Value("${jumanji.repeat}")
    private String jumanjiRepeat;
    @Value("${jumanji.pic.main}")
    private String jumanjiPicMain;
    @Value("${gift.menu.start.message}")
    private String giftMenuStartMessage;
    @Value("${ambassador.start.message}")
    private String ambassadorStartMessage;
    @Value("${ambassador.reply.reg}")
    private String ambassadorReplyReg;
    @Value("${ambassador.pic}")
    private String ambassadorPic;



    public void userData(long id) {
        if (!userRepository.departmentIsNull(id)) {
            Users users = userRepository.findById(id).orElseThrow();
            String msg = users.userData();
            sendler.sendMyDataMenu(id, msg, userData);
        } else {
            needUpdateUserData(id);
        }
    }

    public void updateUserData(long id) {
        Users users = userRepository.findById(id).orElseThrow();
        String msg = users.userData();
        if (userRepository.departmentIsNull(id)) {
            needUpdateUserData(id);
        } else if (!userRepository.departmentIsNull(id) && userStateService.getUserState(id).equals(UserState.REGISTER)) {
            sendler.sendMyDataMenu(id, msg, userNewData);
            sendler.sendMainMenu(id, mainMenuMessage);
            userStateService.setUserState(id, UserState.MAIN_MENU);
        } else {
            sendler.sendMyDataMenu(id, msg, userNewData);
        }
    }

    public void needUpdateUserData(long id) {
        sendler.sendMyDataMenu(id, needUpdateDataMessage, needUpdateData);
    }

    public void userDataOk(long id) {
        sendler.sendMainMenu(id, userDataOk);
        userStateService.setUserState(id, UserState.MAIN_MENU);
    }

    public void wrongUserData(long id) {
        startAction.startAction(id);
    }

    public void trainingsAction(long id) {
        if (!userRepository.departmentIsNull(id)) {
            sendler.sendTrainingsMenu(id, trainingMenu);
            userStateService.setUserState(id, UserState.TRAININGS_MENU);
        } else {
            needUpdateUserData(id);
        }
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

    public void telephony(long id) {
        sendler.sendTelephonyMenu(id, telephonyMessage);
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

    public void ambassador(long id) {
        Optional<Ambassador2024> optionalAmbassador2024 = ambassador2024Repository.findById(id);

        if (optionalAmbassador2024.isPresent()) {
            Ambassador2024 ambassador2024 = optionalAmbassador2024.get();
            String team = ambassador2024.getTeam();
            sendler.sendTextMessage(id, ambassadorReplyReg + team +"'");
        } else {
            sendler.sendAmbassadorMenu(id, ambassadorStartMessage, ambassadorPic);
            userStateService.setUserState(id, UserState.AMBASSADOR);
        }
    }


    public void jumanji(long id) {
        Optional<Jumanji> optionalJumanji = jumanjiRepository.findById(id);
        if (optionalJumanji.isPresent()) {
            sendler.sendTextMessage(id, jumanjiRepeat);
        } else {
            sendler.sendJumanji(id, jumanjiMessage, jumanjiPicMain);
        }
    }

    public void marathonInfo(long id) {
        Optional<Marathon> optionalMarathon = marathonRepository.findById(id);
        if (optionalMarathon.isPresent()) {
            sendler.sendMarathonMenu(id, marathonInfo);
            userStateService.setUserState(id, UserState.MARATHON);
        }
    }

    public void orderGift(long id) {
        sendler.sendGiftMenu(id, giftMenuStartMessage);
    }

}
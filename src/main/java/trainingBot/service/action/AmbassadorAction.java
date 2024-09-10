package trainingBot.service.action;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.model.entity.Ambassador2024;
import trainingBot.model.rep.Ambassador2024Repository;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySources({@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8"), @PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8")})
public class AmbassadorAction {

    private final Sendler sendler;
    private final UserStateService userStateService;
    private final Ambassador2024Repository ambassador2024Repository;

    @Value("${ambassador.pic}")
    private String ambassadorPic;
    @Value("${ambassador.yes.message}")
    private String ambassadorYesMessage;
    @Value("${ambassador.excellent.message}")
    private String ambassadorExcellentMessage;
    @Value("${ambassador.ready.message}")
    private String ambassadorReadyMessage;
    @Value("${ambassador.create.team.message}")
    private String ambassadorCreateTeamMessage;
    @Value("${ambassador.final.message}")
    private String ambassadorFinalMessage;
    @Value("${ambassador.final.message.join}")
    private String ambassadorFinalMessageJoin;
    @Value("${ambassador.empty}")
    private String ambassadorEmpty;


    public void ambassadorYes(long id, Message currentMessage) {
        sendler.sendAmbassadorYesMenu(id, ambassadorPic, ambassadorYesMessage, currentMessage);
    }

    public void ambassadorExcellent(long id, Message currentMessage) {
        sendler.sendAmbassadorExcellentMenu(id, ambassadorPic, ambassadorExcellentMessage, currentMessage);
    }

    public void ambassadorReady(long id, Message currentMessage) {
        sendler.sendAmbassadorReadyMenu(id, ambassadorPic, ambassadorReadyMessage, currentMessage);
    }

    public void createTeam(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        userStateService.setUserState(id, UserState.AMBASSADOR_CREATE_TEAM);
        sendler.callbackAnswer(update);
        sendler.sendAbort(id, ambassadorCreateTeamMessage);
    }

    public void saveNewTeam(long id, String text) {
        Ambassador2024 ambassador = ambassador2024Repository.findById(id).orElse(new Ambassador2024());
        ambassador.setId(id);
        ambassador.setTeam(text);
        ambassador2024Repository.save(ambassador);
        sendler.sendMainMenu(id, ambassadorFinalMessage);
        userStateService.setUserState(id, UserState.MAIN_MENU);
    }


    public void joinTeam(long id, Message currentMessage, int page) {
        List<Ambassador2024> ambassadors = ambassador2024Repository.findAll();
        if (ambassadors.isEmpty()){
            sendler.sendTextMessage(id, ambassadorEmpty);
        } else {
            sendler.sendAmbassadorTeamListMenu(id, ambassadorPic, currentMessage, page);
            userStateService.setUserState(id, UserState.AMBASSADOR_JOIN_TEAM);
        }
    }

    public void saveJoinTeam(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        String text =  update.getCallbackQuery().getData();
        Ambassador2024 ambassador = ambassador2024Repository.findById(id).orElse(new Ambassador2024());
        ambassador.setId(id);
        ambassador.setTeam(text);
        ambassador2024Repository.save(ambassador);
        sendler.sendMainMenu(id, ambassadorFinalMessageJoin);
        sendler.callbackAnswer(update);
        userStateService.setUserState(id, UserState.MAIN_MENU);
    }

}

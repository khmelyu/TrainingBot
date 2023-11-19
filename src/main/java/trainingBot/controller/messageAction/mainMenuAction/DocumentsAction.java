package trainingBot.controller.messageAction.mainMenuAction;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.Sendler;
import trainingBot.controller.messageService.TriggerAction;
import trainingBot.controller.service.redis.UserState;
import trainingBot.controller.service.redis.UserStateService;

@Component
public class DocumentsAction implements TriggerAction {
    private final Sendler sendler;
    private final UserStateService userStateService;

    @Autowired
    public DocumentsAction(Sendler sendler, UserStateService userStateService) {
        this.sendler = sendler;
        this.userStateService = userStateService;
    }

    @Override
    public void execute(Update update) {
        UserState userState = userStateService.getUserState(update.getMessage().getChatId().toString());
        if (userState == UserState.MAIN_MENU) {
            sendler.sendDocumentsMenu(update.getMessage().getChatId());
            userStateService.saveUserState(update.getMessage().getChatId().toString(), UserState.DOCUMENTS_MENU);
        }
    }
}


package trainingBot.controller.messageAction.mainMenuAction;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.Sendler;
import trainingBot.controller.messageService.TriggerAction;
@Component
public class DocumentsAction implements TriggerAction {
    private final Sendler sendler;


    public DocumentsAction(Sendler sendler) {
        this.sendler = sendler;
    }

    @Override
    public void execute(Update update) {
        sendler.sendDocumentsMenu(update.getMessage().getChatId());
    }
}


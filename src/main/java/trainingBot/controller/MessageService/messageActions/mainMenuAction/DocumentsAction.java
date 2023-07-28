package trainingBot.controller.MessageService.messageActions.mainMenuAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.MessageService.TriggerAction;
import trainingBot.core.TrainingBot;
import trainingBot.view.Sendler;

@Component
public class DocumentsAction implements TriggerAction {
    private final Sendler sendler;

    @Autowired
    public DocumentsAction(Sendler sendler) {
        this.sendler = sendler;
    }

    @Override
    public void execute(Update update, TrainingBot trainingBot) {
        sendler.sendDocumentsMenu(update.getMessage().getChatId());
    }
}
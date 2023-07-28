package trainingBot.controller.MessageService.messageActions.documentsActions;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.MessageService.TriggerAction;
import trainingBot.core.TrainingBot;
import trainingBot.view.Sendler;
@Component
public class WorkNoteAction implements TriggerAction {
    private final Sendler sendler;

    public WorkNoteAction(Sendler sendler) {
        this.sendler = sendler;
    }

    @Override
    public void execute(Update update, TrainingBot trainingBot) {
        sendler.sendWorkNotesMenu(update.getMessage().getChatId());
    }
}

package trainingBot.controller.messageAction.mainMenuAction.documentsAction;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.Sendler;
import trainingBot.controller.messageService.TriggerAction;

@Component
public class WorkNoteAction implements TriggerAction {
    private final Sendler sendler;


    public WorkNoteAction(Sendler sendler) {
        this.sendler = sendler;
    }

    @Override
    public void execute(Update update) {
        sendler.sendWorkNotesMenu(update.getMessage().getChatId());
    }
}


package trainingBot.controller.MessageService.messageActions.documentsActions.workNoteActions;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.MessageService.TriggerAction;
import trainingBot.core.TrainingBot;
import trainingBot.core.patterns.LinkPatterns;
import trainingBot.core.patterns.TextPatterns;
import trainingBot.view.Sendler;
@Component
public class InternAction implements TriggerAction {
    private final Sendler sendler;

    public InternAction(Sendler sendler) {
        this.sendler = sendler;
    }

    @Override
    public void execute(Update update, TrainingBot trainingBot) {
        sendler.sendLink(update.getMessage().getChatId(), TextPatterns.INTERN.getTriggerText(), LinkPatterns.INTERN.getTriggerText());
    }
}

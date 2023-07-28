package trainingBot.controller.MessageService.messageActions.mainMenuAction;

import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.MessageService.TriggerAction;
import trainingBot.core.patterns.TextPatterns;
import trainingBot.core.TrainingBot;
import trainingBot.view.Sendler;

public class FeedbackAction implements TriggerAction {

    private final Sendler sendler;

    public FeedbackAction(Sendler sendler) {
        this.sendler = sendler;
    }

    @Override
    public void execute(Update update, TrainingBot trainingBot) {
        sendler.sendTextMessage(update.getMessage().getChatId(), TextPatterns.FEEDBACK.getTriggerText());
    }
}

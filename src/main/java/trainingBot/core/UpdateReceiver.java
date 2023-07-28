package trainingBot.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.MessageService.TriggerAction;
import trainingBot.controller.MessageService.messageActions.documentsActions.WorkNoteAction;
import trainingBot.controller.MessageService.messageActions.documentsActions.workNoteActions.InternAction;
import trainingBot.controller.MessageService.messageActions.mainMenuAction.DocumentsAction;
import trainingBot.controller.MessageService.messageActions.mainMenuAction.FeedbackAction;
import trainingBot.controller.MessageService.messageActions.mainMenuAction.StartAction;
import trainingBot.core.triggers.TextTriggers;
import trainingBot.view.Sendler;

import java.util.HashMap;
import java.util.Map;

@Component
public class UpdateReceiver {
    private final Map<String, TriggerAction> triggerActions = new HashMap<>();

    @Autowired
    public UpdateReceiver(@Lazy Sendler sendler) {
        triggerActions.put(TextTriggers.START.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.DOCUMENTS.getTriggerText(), new DocumentsAction(sendler));
        triggerActions.put(TextTriggers.BACK.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.FEEDBACK.getTriggerText(), new FeedbackAction(sendler));
        triggerActions.put(TextTriggers.WORK_NOTE.getTriggerText(), new WorkNoteAction(sendler));
        triggerActions.put(TextTriggers.INTERN.getTriggerText(), new InternAction(sendler));
    }


    public void handle(Update update, TrainingBot trainingBot) {
        if (update.hasMessage()) {
            handleTextMessage(update, trainingBot);
        } else if (update.hasCallbackQuery()) {
            handleCallbackMessage(update, trainingBot);
        }
    }

    private void handleTextMessage(Update update, TrainingBot trainingBot) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String text = message.getText();
            TriggerAction action = triggerActions.get(text);
            if (action != null) {
                action.execute(update, trainingBot);
            }
        }
    }


    private void handleCallbackMessage(Update update, TrainingBot trainingBot) {

    }

}



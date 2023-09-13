package trainingBot.core;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.Sendler;
import trainingBot.controller.messageAction.StartAction;
import trainingBot.controller.messageAction.mainMenuAction.DocumentsAction;
import trainingBot.controller.messageAction.mainMenuAction.documentsAction.WorkNoteAction;
import trainingBot.controller.messageService.TriggerAction;
import trainingBot.core.triggers.TextTriggers;

import java.util.HashMap;
import java.util.Map;

import static trainingBot.controller.RegisterUser.registerUser;

@Component
public class UpdateReceiver {
    private final Logger logger = Logger.getLogger(UpdateReceiver.class);

    private final Map<String, TriggerAction> triggerActions = new HashMap<>();


    @Autowired
    @Lazy
    public UpdateReceiver(Sendler sendler) {
        triggerActions.put(TextTriggers.START.getTriggerText(), new StartAction(sendler));

        //Main menu//

        triggerActions.put(TextTriggers.FEEDBACK.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.SEARCH_CZ.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.SEARCH_C.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.SEARCH_INFO.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.SEARCH_CONTACTS.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.DOCUMENTS.getTriggerText(), new DocumentsAction(sendler));
        triggerActions.put(TextTriggers.TRAININGS.getTriggerText(), new StartAction(sendler));

        //Documents menu//

        triggerActions.put(TextTriggers.WORK_NOTE.getTriggerText(), new WorkNoteAction(sendler));
        triggerActions.put(TextTriggers.PATTERNS.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.CERTIFICATES.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.COMPETENCIES.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.BACK.getTriggerText(), new StartAction(sendler));
    }

    public void handle(Update update) {
        registerUser(update);
        if (update.hasMessage()) {
            handleTextMessage(update);
            logger.info("User: " + update.getMessage().getChatId() + " - Обработка текстового сообщения:  " + update.getMessage().getText());
        }
        if (update.hasCallbackQuery()) {

            logger.info("User: " + update.getCallbackQuery().getId() + " - Обработка callback-запроса:  " + update.getCallbackQuery());
        }
    }

    private void handleTextMessage(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String text = message.getText();
            TriggerAction action = triggerActions.get(text);
            if (action != null) {
                action.execute(update);
            }
        }
    }
}
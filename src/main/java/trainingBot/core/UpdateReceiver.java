package trainingBot.core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.Sendler;
import trainingBot.controller.messageAction.BackAction;
import trainingBot.controller.messageAction.StartAction;
import trainingBot.controller.messageAction.mainMenuAction.BotFeedBackAction;
import trainingBot.controller.messageAction.mainMenuAction.DocumentsAction;
import trainingBot.controller.messageAction.mainMenuAction.documentsAction.CertificatesAction;
import trainingBot.controller.messageAction.mainMenuAction.documentsAction.CompetenciesAction;
import trainingBot.controller.messageAction.mainMenuAction.documentsAction.PatternsAction;
import trainingBot.controller.messageAction.mainMenuAction.documentsAction.WorkNoteAction;
import trainingBot.controller.messageService.TriggerAction;
import trainingBot.core.triggers.TextTriggers;

import java.util.HashMap;
import java.util.Map;


import static trainingBot.controller.RegisterUser.registerUser;

@Component
public class UpdateReceiver {
    private final Map<String, TriggerAction> triggerActions = new HashMap<>();
    @Autowired
    @Lazy
    public UpdateReceiver(Sendler sendler) {
        triggerActions.put(TextTriggers.START.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.BACK.getTriggerText(), new BackAction(sendler));


        //Main menu//

        triggerActions.put(TextTriggers.FEEDBACK.getTriggerText(), new BotFeedBackAction(sendler));
        triggerActions.put(TextTriggers.SEARCH_CZ.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.SEARCH_C.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.SEARCH_INFO.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.SEARCH_CONTACTS.getTriggerText(), new StartAction(sendler));
        triggerActions.put(TextTriggers.DOCUMENTS.getTriggerText(), new DocumentsAction(sendler));
        triggerActions.put(TextTriggers.TRAININGS.getTriggerText(), new StartAction(sendler));

        //Documents menu//

        triggerActions.put(TextTriggers.WORK_NOTE.getTriggerText(), new WorkNoteAction(sendler));
        triggerActions.put(TextTriggers.PATTERNS.getTriggerText(), new PatternsAction(sendler));
        triggerActions.put(TextTriggers.CERTIFICATES.getTriggerText(), new CertificatesAction(sendler));
        triggerActions.put(TextTriggers.COMPETENCIES.getTriggerText(), new CompetenciesAction(sendler));

        //WorkNote menu//

        triggerActions.put(TextTriggers.INTERN.getTriggerText(), new WorkNoteAction(sendler));
        triggerActions.put(TextTriggers.SECOND_LEVEL.getTriggerText(), new WorkNoteAction(sendler));
        triggerActions.put(TextTriggers.THIRD_LEVEL.getTriggerText(), new WorkNoteAction(sendler));
        triggerActions.put(TextTriggers.FOURTH_LEVEL.getTriggerText(), new WorkNoteAction(sendler));
        triggerActions.put(TextTriggers.FIFTH_LEVEL.getTriggerText(), new WorkNoteAction(sendler));

        //Pattern menu//

        triggerActions.put(TextTriggers.ACCOUNTIGS.getTriggerText(), new PatternsAction(sendler));
        triggerActions.put(TextTriggers.SCHEDULE.getTriggerText(), new PatternsAction(sendler));
        triggerActions.put(TextTriggers.DECLARATION.getTriggerText(), new PatternsAction(sendler));

        //Certificates menu//

        triggerActions.put(TextTriggers.TEA.getTriggerText(), new CertificatesAction(sendler));
        triggerActions.put(TextTriggers.COFFEE.getTriggerText(), new CertificatesAction(sendler));
        triggerActions.put(TextTriggers.WRAP.getTriggerText(), new CertificatesAction(sendler));
        triggerActions.put(TextTriggers.CANDY.getTriggerText(), new CertificatesAction(sendler));
        triggerActions.put(TextTriggers.ACCESSORIES.getTriggerText(), new CertificatesAction(sendler));
        triggerActions.put(TextTriggers.HOUSEHOLD_GOODS.getTriggerText(), new CertificatesAction(sendler));
        triggerActions.put(TextTriggers.CHOKOSTYLE.getTriggerText(), new CertificatesAction(sendler));

        //Competencies menu//

        triggerActions.put(TextTriggers.CORPORATE.getTriggerText(), new CompetenciesAction(sendler));
        triggerActions.put(TextTriggers.CONSULTANT.getTriggerText(), new CompetenciesAction(sendler));
        triggerActions.put(TextTriggers.MANAGER.getTriggerText(), new CompetenciesAction(sendler));
        triggerActions.put(TextTriggers.CURATOR.getTriggerText(), new CompetenciesAction(sendler));
    }

    public void handle(Update update) {
        registerUser(update);

        if (update.hasMessage()) {
            handleTextMessage(update);
        }
        if (update.getMessage().hasPhoto()){
        }
        if (update.hasCallbackQuery()) {
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
//    private void handlePhotoMessage(Update update) {
//        Message message = update.getMessage();
//        PhotoSize photoSize = message.getPhoto().get(0);
//
//    }
}
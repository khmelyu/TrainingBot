package trainingBot.controller.messageAction.mainMenuAction.documentsAction;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.Sendler;
import trainingBot.controller.messageService.TriggerAction;
@Component
public class CertificatesAction implements TriggerAction {
    private final Sendler sendler;


    public CertificatesAction(Sendler sendler) {
        this.sendler = sendler;
    }

    @Override
    public void execute(Update update) {
        sendler.sendCertificatesMenu(update.getMessage().getChatId());
    }
}

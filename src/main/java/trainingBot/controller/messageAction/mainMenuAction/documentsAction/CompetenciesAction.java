package trainingBot.controller.messageAction.mainMenuAction.documentsAction;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.Sendler;
import trainingBot.controller.messageService.TriggerAction;
import trainingBot.core.triggers.TextTriggers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
public class CompetenciesAction implements TriggerAction {
    private final Sendler sendler;
    private final Properties pattern = new Properties();

    public CompetenciesAction(Sendler sendler) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("pattern.properties")) {
            assert inputStream != null;
            pattern.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.sendler = sendler;
    }

    @Override
    public void execute(Update update) {
        String corporate = pattern.getProperty("competencies.corporate.link");
        String consultant = pattern.getProperty("competencies.consultant.link");
        String manager = pattern.getProperty("competencies.manager.link");
        String curator = pattern.getProperty("competencies.curator.link");

        if (update.getMessage().getText().equals(TextTriggers.COMPETENCIES.getTriggerText()))
            sendler.sendCompetenciesMenu(update.getMessage().getChatId());
        if (update.getMessage().getText().equals(TextTriggers.CORPORATE.getTriggerText()))
            sendler.sendLink(update.getMessage().getChatId(), TextTriggers.CORPORATE.getTriggerText(), corporate);
        if (update.getMessage().getText().equals(TextTriggers.MANAGER.getTriggerText()))
            sendler.sendLink(update.getMessage().getChatId(), TextTriggers.MANAGER.getTriggerText(), manager);
        if (update.getMessage().getText().equals(TextTriggers.CONSULTANT.getTriggerText()))
            sendler.sendLink(update.getMessage().getChatId(), TextTriggers.CONSULTANT.getTriggerText(), consultant);
        if (update.getMessage().getText().equals(TextTriggers.CURATOR.getTriggerText()))
            sendler.sendLink(update.getMessage().getChatId(), TextTriggers.CURATOR.getTriggerText(), curator);

    }
}

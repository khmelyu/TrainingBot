package trainingBot.controller.messageAction.mainMenuAction.documentsAction;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.Sendler;
import trainingBot.controller.messageService.TriggerAction;
import trainingBot.controller.service.redis.UserState;
import trainingBot.controller.service.redis.UserStateService;
import trainingBot.core.triggers.TextTriggers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
public class PatternsAction implements TriggerAction {
    private final Sendler sendler;
    private final UserStateService userStateService;
    private final Properties pattern = new Properties();

    public PatternsAction(Sendler sendler, UserStateService userStateService) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("pattern.properties")) {
            assert inputStream != null;
            pattern.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.userStateService = userStateService;
        this.sendler = sendler;
    }

    @Override
    public void execute(Update update) {
        String accounings = pattern.getProperty("patterns.accounings.link");
        String schedule = pattern.getProperty("patterns.scheduling.link");
        String declaration = pattern.getProperty("patterns.declaration.link");

        UserState userState = userStateService.getUserState(update.getMessage().getChatId().toString());
        if (userState == UserState.DOCUMENTS_MENU) {
            if (update.getMessage().getText().equals(TextTriggers.PATTERNS.getTriggerText()))
                sendler.sendPatternsMenu(update.getMessage().getChatId());
            userStateService.saveUserState(update.getMessage().getChatId().toString(), UserState.PATTERNS_MENU);
        }
        if (userState == UserState.PATTERNS_MENU) {
            if (update.getMessage().getText().equals(TextTriggers.ACCOUNTIGS.getTriggerText()))
                sendler.sendLink(update.getMessage().getChatId(), "Учеты", accounings);
            if (update.getMessage().getText().equals(TextTriggers.SCHEDULE.getTriggerText()))
                sendler.sendLink(update.getMessage().getChatId(), "РСП для ЗП", schedule);
            if (update.getMessage().getText().equals(TextTriggers.DECLARATION.getTriggerText()))
                sendler.sendLink(update.getMessage().getChatId(), "Заявления", declaration);
        }
    }
}


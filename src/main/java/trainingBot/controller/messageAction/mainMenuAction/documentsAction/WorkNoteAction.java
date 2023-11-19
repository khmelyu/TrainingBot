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
public class WorkNoteAction implements TriggerAction {
    private final Sendler sendler;
    private final UserStateService userStateService;
    private final Properties pattern = new Properties();

    public WorkNoteAction(Sendler sendler, UserStateService userStateService) {
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
        String intern = pattern.getProperty("work.note.intern.link");
        String secondLevel = pattern.getProperty("work.note.level.2.link");
        String thirdLevel = pattern.getProperty("work.note.level.3.link");
        String fourthLevel = pattern.getProperty("work.note.level.4.link");
        String fifthLevel = pattern.getProperty("work.note.level.5.link");

        UserState userState = userStateService.getUserState(update.getMessage().getChatId().toString());
        if (userState == UserState.DOCUMENTS_MENU) {
            if (update.getMessage().getText().equals(TextTriggers.WORK_NOTE.getTriggerText()))
                sendler.sendWorkNotesMenu(update.getMessage().getChatId());
            userStateService.saveUserState(update.getMessage().getChatId().toString(), UserState.WORK_NOTES_MENU);
        }
        if (userState == UserState.WORK_NOTES_MENU) {
            if (update.getMessage().getText().equals(TextTriggers.INTERN.getTriggerText()))
                sendler.sendLink(update.getMessage().getChatId(), "Стажерская рабочая тетрадь", intern);
            if (update.getMessage().getText().equals(TextTriggers.SECOND_LEVEL.getTriggerText()))
                sendler.sendLink(update.getMessage().getChatId(), "Рабочая тетрадь 2 уровня", secondLevel);
            if (update.getMessage().getText().equals(TextTriggers.THIRD_LEVEL.getTriggerText()))
                sendler.sendLink(update.getMessage().getChatId(), "Рабочая тетрадь 3 уровня", thirdLevel);
            if (update.getMessage().getText().equals(TextTriggers.FOURTH_LEVEL.getTriggerText()))
                sendler.sendLink(update.getMessage().getChatId(), "Рабочая тетрадь 4 уровня", fourthLevel);
            if (update.getMessage().getText().equals(TextTriggers.FIFTH_LEVEL.getTriggerText()))
                sendler.sendLink(update.getMessage().getChatId(), "Рабочая тетрадь 5 уровня", fifthLevel);
        }
    }
}


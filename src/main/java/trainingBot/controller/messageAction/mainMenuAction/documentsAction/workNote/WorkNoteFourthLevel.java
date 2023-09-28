package trainingBot.controller.messageAction.mainMenuAction.documentsAction.workNote;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.Sendler;
import trainingBot.controller.messageService.TriggerAction;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
public class WorkNoteFourthLevel implements TriggerAction {
    private final Sendler sendler;
    private final Properties pattern = new Properties();


    public WorkNoteFourthLevel(Sendler sendler) {
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
        String link = pattern.getProperty("work.note.level.4.link");
        sendler.sendLink(update.getMessage().getChatId(), "Рабочая тетрадь 4 уровня", link);
    }
}

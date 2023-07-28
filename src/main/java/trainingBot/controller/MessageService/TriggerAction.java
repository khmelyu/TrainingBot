package trainingBot.controller.MessageService;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.core.TrainingBot;
@Component
public interface TriggerAction {
    void execute(Update update, TrainingBot trainingBot);
}

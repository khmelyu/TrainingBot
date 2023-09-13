package trainingBot.controller.messageService;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public interface TriggerAction {
     void execute(Update update);
}
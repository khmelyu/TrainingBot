package trainingBot.controller.commandController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.service.action.AmbassadorAction;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;

@Component
@RequiredArgsConstructor
public class VoiceController implements CommandController{

    private final UserStateService userStateService;
    private final AmbassadorAction ambassadorAction;
    @Override
    public void handleMessage(Update update) {
        Long id = update.getMessage().getChatId();
        UserState userState = userStateService.getUserState(id);
        String voice = update.getMessage().getVoice().getFileId();

        switch (userState) {
            case AMBASSADOR_MEDIA -> ambassadorAction.ambassadorMediaAction(id, voice);
        }
    }
}

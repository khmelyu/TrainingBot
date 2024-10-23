package trainingBot.service.action;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.model.rep.UserRepository;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;


@Service
@RequiredArgsConstructor
public class PhotoAction {
    private final Logger logger = LoggerFactory.getLogger(PhotoAction.class);
    private final Sendler sendler;
    private final UserRepository userRepository;
    private final UserStateService userStateService;
    private final AmbassadorAction ambassadorAction;

    public void photoAction(Update update) {
        Long id = update.getMessage().getChatId();
        UserState userState = userStateService.getUserState(id);
        PhotoSize photoSize = update.getMessage().getPhoto().get(0);

        if (userState.equals(UserState.AMBASSADOR_MEDIA)) {
            ambassadorAction.ambassadorMediaAction(id, photoSize.getFileId());
        } else {
            userRepository.findById(id).ifPresentOrElse(user -> {
                if (user.isAdmin()) {

                    sendler.sendTextMessage(id, photoSize.getFileId());
                    logger.info("User: " + id + " Got the picture id: " + photoSize.getFileId());
                } else {
                    sendler.sendTextMessage(id, "Бот пока не поддерживает загрузку изображений");
                }
            }, () -> sendler.sendTextMessage(id, "Пользователь не найден или не является администратором."));
        }
    }
}

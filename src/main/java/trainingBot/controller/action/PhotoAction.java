package trainingBot.controller.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.UpdateReceiver;
import trainingBot.model.rep.UserRepository;
import trainingBot.view.Sendler;


@Component
public class PhotoAction {
    private final Logger logger = LoggerFactory.getLogger(UpdateReceiver.class);
    private final Sendler sendler;
    private final UserRepository userRepository;

    @Autowired
    public PhotoAction(@Lazy Sendler sendler, UserRepository userRepository) {
        this.sendler = sendler;
        this.userRepository = userRepository;
    }

    public void photoAction(Update update) {
        Long id = update.getMessage().getChatId();
        userRepository.findById(id).ifPresentOrElse(user -> {
            if (user.isAdmin()) {
                PhotoSize photoSize = update.getMessage().getPhoto().get(0);
                sendler.sendTextMessage(id, photoSize.getFileId());
                logger.info("User: " + id + " Got the picture id: " + photoSize.getFileId());
            } else {
                sendler.sendTextMessage(id, "Бот пока не поддерживает загрузку изображений");
            }
        }, () -> sendler.sendTextMessage(id, "Пользователь не найден или не является администратором."));
    }
}

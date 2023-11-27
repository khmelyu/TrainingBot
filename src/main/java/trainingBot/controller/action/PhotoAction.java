package trainingBot.controller.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.model.rep.UserRepository;
import trainingBot.view.Sendler;

@Component
public class PhotoAction {
    private Sendler sendler;
    private UserRepository userRepository;

    @Autowired
    public void setDependencies(@Lazy Sendler sendler, UserRepository userRepository) {
        this.sendler = sendler;
        this.userRepository = userRepository;
    }

    public void photoAction(Update update) {
        Long id = update.getMessage().getChatId();
        boolean user = userRepository.findById(id).get().isAdmin();
        if (user) {
            PhotoSize photoSize = update.getMessage().getPhoto().get(0);
            sendler.sendTextMessage(id, photoSize.getFileId());
        } else {
            sendler.sendTextMessage(id, "Бот пока не поддерживает загрузку изображений");
        }
    }
}

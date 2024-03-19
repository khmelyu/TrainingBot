package trainingBot.controller.commandController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.action.PhotoAction;

@Component
public class PhotoCommandController implements CommandController {
    private PhotoAction photoAction;

    @Autowired
    public void setPhotoAction(PhotoAction photoAction) {
        this.photoAction = photoAction;
    }

    @Override
    public void handleMessage(Update update) {
        photoAction.photoAction(update);

    }
}

package trainingBot.controller.commandController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.service.action.PhotoAction;

@Component
public class PhotoCommandController implements CommandController {
    private final PhotoAction photoAction;

    @Autowired
    public PhotoCommandController(PhotoAction photoAction) {
        this.photoAction = photoAction;
    }

    @Override
    public void handleMessage(Update update ) {
        photoAction.photoAction(update);

    }
}

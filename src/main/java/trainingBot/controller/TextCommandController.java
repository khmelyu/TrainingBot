package trainingBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.service.redis.UserState;
import trainingBot.controller.service.redis.UserStateService;

@Component
public class TextCommandController {

    private StartAction startAction;
    private UserStateService userStateService;

    @Autowired
    public void setUserStateService(UserStateService userStateService) {
        this.userStateService = userStateService;
    }

    @Autowired
    public void setStartAction(StartAction startAction) {
        this.startAction = startAction;
    }

    public void handleTextMessage(Update update) {
        Long id = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        switch (text) {
            case "/start" -> startAction.startAction(update);

        }
        UserState userState = userStateService.getUserState(id);
        switch (userState) {
            case START -> startAction.inputName(update);
            case SET_NAME -> startAction.addName(update);
            case SET_LASTNAME -> startAction.addLastName(update);
            case SET_PHONE -> startAction.addPhone(update);
            case SET_CITY -> startAction.addCity(update);
            case SET_GALLERY -> startAction.addGallery(update);
            case SET_RATE -> startAction.addRate(update);
        }
    }

}

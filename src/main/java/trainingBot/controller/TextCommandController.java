package trainingBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.action.StartAction;
import trainingBot.controller.action.TrainingsAction;
import trainingBot.controller.service.redis.UserState;
import trainingBot.controller.service.redis.UserStateService;

@Component
public class TextCommandController {

    private StartAction startAction;
    private TrainingsAction trainingsAction;
    private UserStateService userStateService;

    @Autowired
    public void setDependencies(UserStateService userStateService, StartAction startAction, TrainingsAction trainingsAction) {
        this.userStateService = userStateService;
        this.startAction = startAction;
        this.trainingsAction = trainingsAction;
    }

    public void handleTextMessage(Update update) {
        Long id = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        switch (text) {
            case "/start" -> startAction.startAction(update);
            case "Тренинги" -> trainingsAction.trainingsAction(update);

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

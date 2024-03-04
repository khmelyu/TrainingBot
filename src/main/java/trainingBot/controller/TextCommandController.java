package trainingBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.action.MainMenuAction;
import trainingBot.controller.action.StartAction;
import trainingBot.controller.service.redis.UserState;
import trainingBot.controller.service.redis.UserStateService;

@Component
public class TextCommandController implements CommandController {

    private StartAction startAction;
    private UserStateService userStateService;
    private MainMenuAction mainMenuAction;

    @Autowired
    public void setDependencies(UserStateService userStateService, StartAction startAction, MainMenuAction mainMenuAction) {
        this.userStateService = userStateService;
        this.startAction = startAction;
        this.mainMenuAction = mainMenuAction;
    }

    @Override
    public void handleMessage(Update update) {
        Long id = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        UserState userState = userStateService.getUserState(id);
        switch (text) {
            case "/start" -> startAction.startAction(update);
            case "Тренинги" -> mainMenuAction.trainingsAction(id);
            case "Мои данные" -> mainMenuAction.userData(id);
            case "Все верно" -> mainMenuAction.userDataOk(id);
            case "Изменить" -> mainMenuAction.userDataFail(update);
            case "Обратная связь" -> mainMenuAction.feedback(id);
        }
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

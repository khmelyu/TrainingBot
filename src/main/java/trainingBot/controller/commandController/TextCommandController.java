package trainingBot.controller.commandController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.action.*;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Button;

@Component
public class TextCommandController implements CommandController {

    private final UserStateService userStateService;
    private final StartAction startAction;
    private final AdminAction adminAction;
    private final BackAction backAction;
    private final MainMenuAction mainMenuAction;
    private final DocumentsAction documentsAction;
    private final CoachAction coachAction;

    @Autowired
    public TextCommandController(UserStateService userStateService, StartAction startAction, AdminAction adminAction, BackAction backAction, MainMenuAction mainMenuAction, DocumentsAction documentsAction, CoachAction coachAction) {
        this.userStateService = userStateService;
        this.startAction = startAction;
        this.adminAction = adminAction;
        this.backAction = backAction;
        this.mainMenuAction = mainMenuAction;
        this.documentsAction = documentsAction;
        this.coachAction = coachAction;
    }

    @Override
    public void handleMessage(Update update) {
        long id = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        UserState userState = userStateService.getUserState(id);
        if (userState != null) {
            if (text.equals("/start")) {
                startAction.startAction(id);
            }
            for (Button button : Button.values()) {
                if (button.getText().equals(text)) {
                    switch (button) {
                        case BACK -> backAction.backAction(id);
                        //MainMenu
                        case TRAININGS -> mainMenuAction.trainingsAction(id);
                        case DOCUMENTS -> mainMenuAction.documents(id);
                        case MY_DATA -> mainMenuAction.userData(id);
                        case FEEDBACK -> mainMenuAction.feedback(id);
                        //MyData
                        case ITS_OK -> mainMenuAction.userDataOk(id);
                        case CHANGE -> mainMenuAction.userDataFail(id);
                        //Admin
                        case ADMIN -> adminAction.adminAction(id);
                        case UPDATE_STAT -> adminAction.updateStat(id);
                        //Documents
                        case WORKNOTE -> documentsAction.worknote(id);
                        case WORKNOTE_TRAINEE -> documentsAction.traineeNote(id);
                        case WORKNOTE_SECOND -> documentsAction.secondLevelNote(id);
                        case WORKNOTE_THIRD -> documentsAction.thirdLevelNote(id);
                        case WORKNOTE_FOURTH -> documentsAction.fourthLevelNote(id);
                        case WORKNOTE_FIFTH -> documentsAction.fifthLevelNote(id);

                        case PATTERNS -> documentsAction.patterns(id);
                        case SALARY -> documentsAction.salary(id);
                        case STATEMENTS -> documentsAction.statements(id);
                        case INVENTORY -> documentsAction.inventory(id);

                        case CERTIFICATES -> documentsAction.certificates(id);
                        case TEA -> documentsAction.tea(id);
                        case COFFEE -> documentsAction.coffee(id);
                        case ACCESSORIES -> documentsAction.accessories(id);
                        case SWEETS -> documentsAction.sweets(id);
                        case PACKAGE -> documentsAction.packages(id);
                        case HOUSEHOLD -> documentsAction.household(id);
                        case CHOCOSTYLE -> documentsAction.chocostyle(id);

                        case COMPETITIONS -> documentsAction.competitions(id);
                        case CORPORATE -> documentsAction.corporate(id);
                        case CONSULTANT -> documentsAction.consultant(id);
                        case CURATOR -> documentsAction.curator(id);
                        case MANAGER -> documentsAction.manager(id);
                    }
                }
            }
            switch (userState) {
                case START -> startAction.inputName(update);

                case LOGIN -> startAction.inputLogin(update);
                case PASSWORD -> startAction.inputPassword(update);

                case SET_NAME -> startAction.addName(update);
                case SET_LASTNAME -> startAction.addLastName(update);
                case SET_PHONE -> startAction.addPhone(update);
                case SET_CITY -> startAction.addCity(update);
                case SET_GALLERY -> startAction.addGallery(update);
                case SET_RATE -> startAction.addRate(update);
                case TRAINING_LINK -> coachAction.setTrainingLink(update);
            }
        } else {
            userStateService.setUserState(id, UserState.MAIN_MENU);
            handleMessage(update);
        }
    }
}
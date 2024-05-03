package trainingBot.controller.commandController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.service.action.*;
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
    private final InfoSearchAction infoSearchAction;
    private final ContactSearchAction contactSearchAction;
    private final CoachAction coachAction;
    private final UsersOnTrainingsAction usersOnTrainingsAction;

    @Autowired
    public TextCommandController(UserStateService userStateService, StartAction startAction, AdminAction adminAction, BackAction backAction, MainMenuAction mainMenuAction, DocumentsAction documentsAction, InfoSearchAction infoSearchAction, ContactSearchAction contactSearchAction, CoachAction coachAction, UsersOnTrainingsAction usersOnTrainingsAction) {
        this.userStateService = userStateService;
        this.startAction = startAction;
        this.adminAction = adminAction;
        this.backAction = backAction;
        this.mainMenuAction = mainMenuAction;
        this.documentsAction = documentsAction;
        this.infoSearchAction = infoSearchAction;
        this.contactSearchAction = contactSearchAction;
        this.coachAction = coachAction;
        this.usersOnTrainingsAction = usersOnTrainingsAction;
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
                        case BACK, ABORT -> backAction.backAction(id);
                        //MainMenu
                        case TRAININGS -> mainMenuAction.trainingsAction(id);
                        case CZ_SEARCH -> mainMenuAction.czSearchMessage(id);
                        case DOCUMENTS -> mainMenuAction.documents(id);
                        case INFO_SEARCH -> mainMenuAction.infoSearch(id);
                        case CONTACTS_SEARCH -> mainMenuAction.contactSearch(id);
                        case MY_DATA -> mainMenuAction.userData(id);
                        case FEEDBACK -> mainMenuAction.feedback(id);
                        //MyData
                        case ITS_OK -> mainMenuAction.userDataOk(id);
                        case CHANGE -> mainMenuAction.wrongUserData(id);
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
                        //InfoSearch
                        case PRODUCT_AND_SERVICE -> infoSearchAction.productAndService(id);
                        case MANAGEMENT -> infoSearchAction.management(id);
                        case DESIGN -> infoSearchAction.design(id);
                        case IT -> infoSearchAction.it(id);
                        case CORPORATE_CULTURE -> infoSearchAction.corporateCulture(id);
                        //ContactSearch
                        case NO -> contactSearchAction.noMenu(id);
                        case PRODUCT_QUALITY -> contactSearchAction.productQuality(id);
                        case PROMOTION -> contactSearchAction.promotion(id);
                        case TECH -> contactSearchAction.tech(id);
                        case CHAIN_MEETING -> contactSearchAction.chainMeeting(id);
                        case TAKEAWAY -> contactSearchAction.takeAway(id);
                        case ORDER -> contactSearchAction.order(id);
                        case SICK_LEAVE_REQUEST -> contactSearchAction.sickLeaveRequest(id);
                        case CONTRACT -> contactSearchAction.contract(id);

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
                case CZ_SEARCH -> mainMenuAction.czSearchAction(id, text);
                case TRAINING_LINK -> coachAction.setTrainingLink(update);
                case FEEDBACK_ANSWER -> {
                    if (!text.equals(Button.BACK.getText())) {
                        usersOnTrainingsAction.sendingFeedback(id, text);
                    }
                }
            }
        } else {
            userStateService.setUserState(id, UserState.MAIN_MENU);
            handleMessage(update);
        }
    }
}
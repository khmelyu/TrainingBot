package trainingBot.controller.commandController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.service.action.*;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Button;

@Component
@RequiredArgsConstructor
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
    private final MarathonAction marathonAction;
    private final JumanjiAction jumanjiAction;
    private final GiftAction giftAction;
    private final AmbassadorAction ambassadorAction;

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
                        case BACK, ABORT, MARATHON_NO, GIFT_NO -> backAction.backAction(id);
                        //MainMenu
                        case TRAININGS -> mainMenuAction.trainingsAction(id);
                        case CZ_SEARCH -> mainMenuAction.czSearchMessage(id);
                        case DOCUMENTS -> mainMenuAction.documents(id);
                        case INFO_SEARCH -> mainMenuAction.infoSearch(id);
                        case TELEPHONY -> mainMenuAction.telephony(id);
                        case MY_DATA -> mainMenuAction.userData(id);
                        case FEEDBACK -> mainMenuAction.feedback(id);
                        case AMBASSADOR -> mainMenuAction.ambassador(id);
//                         case JUMANJI -> mainMenuAction.jumanji(id);
//                         case READY -> jumanjiAction.jumanjiUserData(id);
//                         case MARATHON -> mainMenuAction.marathonInfo(id);

                        ///GiftMenu
                        case GIFT_YES -> giftAction.orderGiftName(id);

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
                        case GALLERY_SEARCH -> contactSearchAction.gallerySearchMessage(id);
                        case OTHER_DIVISION_SEARCH -> contactSearchAction.otherDivisionSearchMessage(id);

                        case PRODUCT_QUALITY -> contactSearchAction.productQuality(id);
                        case PROMOTION -> contactSearchAction.promotion(id);
                        case TECH -> contactSearchAction.tech(id);
                        case CHAIN_MEETING -> contactSearchAction.chainMeeting(id);
                        case TAKEAWAY -> contactSearchAction.takeAway(id);
                        case ORDER -> contactSearchAction.order(id);
                        case SICK_LEAVE_REQUEST -> contactSearchAction.sickLeaveRequest(id);
                        case CONTRACT -> contactSearchAction.contract(id);
                        case STAFFER -> contactSearchAction.stafferSearchMessage(id);
                        case GALLERY -> contactSearchAction.gallerySearchMessage(id);

                        //Jumanji
//
//                        case DATA_OK -> jumanjiAction.jumanjiOk(id);
//                        case FIND_TEAM -> jumanjiAction.jumanjiChoiceTeam(id);
//                        case JOIN_TEAM -> jumanjiAction.jumanjiEnd(id);
                        //Marathon
//                        case SIGNUP -> marathonAction.marathonUserData(id);
//                        case DATA_OK -> marathonAction.sexChoice(id);
//                        case WARM_UP -> marathonAction.warmUpMessage(id);
//                        case OKAY -> marathonAction.coachMessage(id);
//                        case NICE_TO_MEET_YOU -> marathonAction.nutritionistMessage(id);
//                        case HELLO_ALEX -> marathonAction.instructionMessage(id);
//                        case OF_COURSE -> marathonAction.instruction2Message(id);
//                        case DEAL -> marathonAction.warmUpTimeMessage(id);
//                        case GOOD -> marathonAction.checkCanal(id);
//                        case DONE -> marathonAction.helper(id);
//                        case UNDERSTAND -> marathonAction.finish(id);
//                        case BY_MONDAY -> marathonAction.exit(id);
//                        case MY_POINTS -> marathonAction.points(id);
//                        case MARATHON_ABORT -> marathonAction.abort(id);
//                        case MARATHON_YES -> marathonAction.abortYes(id);
//                        case MEMBERS -> marathonAction.membersCount(id);
//                        case MARATHON_FEEDBACK -> marathonAction.marathonFeedbackMessage(id);
                    }
                }
            }
            switch (userState) {
                case LOGIN -> startAction.inputLogin(update);
                case PASSWORD -> startAction.inputPassword(update);
                case TRAINING_LINK -> coachAction.setTrainingLink(update);
                case CONTACT_SEARCH_OTHER_DIVISION -> {
                    if (!text.equals(Button.BACK.getText())) {
                        contactSearchAction.cityChoiceMessage(id, text);
                    }
                }
                case CONTACT_SEARCH_DIVISION -> {
                    if (!text.equals(Button.BACK.getText())) {
                        contactSearchAction.contactViewMessage(id, text);
                    }
                }

                case CZ_SEARCH -> {
                    if (!text.equals(Button.BACK.getText())) {
                        mainMenuAction.czSearchAction(id, text);
                    }
                }
                case CONTACT_SEARCH_STAFFER -> {
                    if (!text.equals(Button.BACK.getText())) {
                        contactSearchAction.stafferSearchAction(id, text);
                    }
                }
                case CONTACT_SEARCH_GALLERY -> {
                    if (!text.equals(Button.BACK.getText())) {
                        contactSearchAction.gallerySearchAction(id, text);
                    }
                }
                case FEEDBACK_ANSWER -> {
                    if (!text.equals(Button.BACK.getText())) {
                        usersOnTrainingsAction.sendingFeedback(id, text);
                    }
                }
                case AMBASSADOR_CREATE_TEAM -> {
                    if (!text.equals(Button.ABORT.getText())) {
                        ambassadorAction.saveNewTeam(id, text);
                    }
                }
                case SEX_CHOICE -> {
                    if (!text.equals(Button.ABORT.getText())) {
                        marathonAction.helloMessage(id, text);
                    }
                }
                case MARATHON_FEEDBACK -> {
                    if (!text.equals(Button.BACK.getText())) {
                        marathonAction.marathonSendingFeedback(id, text);
                    }
                }
                case MARATHON_TIME_CHOICE -> marathonAction.timeZoneMessage(id, text);
                case MARATHON_TIMEZONE_CHOICE -> marathonAction.signUp(id, text);
                case GIFT_MENU -> giftAction.orderGift(id, text);

            }
        } else {
            userStateService.setUserState(id, UserState.MAIN_MENU);
            handleMessage(update);
        }
    }
}
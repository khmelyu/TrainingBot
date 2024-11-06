package trainingBot.view;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import trainingBot.model.entity.Ambassador2024;
import trainingBot.model.entity.Marathon;
import trainingBot.model.entity.Users;
import trainingBot.model.rep.Ambassador2024Repository;
import trainingBot.model.rep.MarathonRepository;
import trainingBot.model.rep.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
public class ButtonMenu {

    private static UserRepository userRepository;
    private static MarathonRepository marathonRepository;
    private static Ambassador2024Repository ambassador2024Repository;


    public ButtonMenu(UserRepository userRepository, MarathonRepository marathonRepository, Ambassador2024Repository ambassador2024Repository) {
        ButtonMenu.userRepository = userRepository;
        ButtonMenu.marathonRepository = marathonRepository;
        ButtonMenu.ambassador2024Repository = ambassador2024Repository;
    }

    public static ReplyKeyboardMarkup createKeyboardMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }

    public static KeyboardRow createKeyboardRow(Button... buttons) {
        KeyboardRow keyboardRow = new KeyboardRow();
        for (Button button : buttons) {
            keyboardRow.add(new KeyboardButton(button.getText()));
        }
        return keyboardRow;
    }

    public static ReplyKeyboardMarkup back() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(createKeyboardRow(Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup abort() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(createKeyboardRow(Button.ABORT));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup mainMenu(long id) {
        Users users = userRepository.findById(id).orElse(null);
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(createKeyboardRow(Button.CZ_SEARCH, Button.INFO_SEARCH, Button.TELEPHONY));
        keyboard.add(createKeyboardRow(Button.TRAININGS, Button.DOCUMENTS));
        keyboard.add(createKeyboardRow(Button.MY_DATA, Button.FEEDBACK));

        Optional<Ambassador2024> ambassador = ambassador2024Repository.findById(id);
        if (ambassador.isPresent()) {
            keyboard.add(createKeyboardRow(Button.AMBASSADOR));
        }
//        keyboard.add(createKeyboardRow(Button.JUMANJI));

//        Optional<Marathon> optionalMarathon = marathonRepository.findById(id);
//        if (optionalMarathon.isPresent() && optionalMarathon.get().isActual()) {
//                keyboard.add(createKeyboardRow(Button.MARATHON));
//            }
        if (users != null && users.isAdmin()) {
            keyboard.add(createKeyboardRow(Button.ADMIN));
        }

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup ambassadorMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.MY_TEAM));
        keyboard.add(createKeyboardRow(Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

//    public static ReplyKeyboardMarkup ambassadorTaskMenu(Long id) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
//        List<KeyboardRow> keyboard = new ArrayList<>();
//        KeyboardRow keyboardRow = new KeyboardRow();
//        KeyboardRow keyboardRow2 = new KeyboardRow();
//        Optional<Ambassador2024> optionalAmbassador2024 = ambassador2024Repository.findById(id);
//        if (optionalAmbassador2024.isPresent()) {
//            Ambassador2024 ambassador2024 = optionalAmbassador2024.get();
//            List<Users> users = ambassador2024Repository.findMembersByTeam(ambassador2024.getTeam());
//            boolean test = false;
//            boolean oneWord = false;
//            boolean letter = false;
//            boolean media = false;
//            for (Users user : users) {
//                Optional<String> teamAnswerTest = Optional.ofNullable(ambassador2024Repository.teamAnswerTest(user.getId()));
//                Optional<String> teamAnswerWord = Optional.ofNullable(ambassador2024Repository.teamAnswerOneWord(user.getId()));
//                Optional<String> teamAnswerLetter = Optional.ofNullable(ambassador2024Repository.teamAnswerLetter(user.getId()));
//                Optional<String> teamAnswerMedia = Optional.ofNullable(ambassador2024Repository.teamAnswerMedia(user.getId()));
//
//                if (teamAnswerTest.isPresent()) {
//                    test = true;
//                }
//                if (teamAnswerWord.isPresent()) {
//                    oneWord = true;
//                }
//                if (teamAnswerLetter.isPresent()) {
//                    letter = true;
//                }
//                if (teamAnswerMedia.isPresent()) {
//                    media = true;
//                }
//            }
//            if (!test) {
//                keyboardRow.add(new KeyboardButton(Button.TEST.getText()));
//            }
//            if (!oneWord) {
//                keyboardRow.add(new KeyboardButton(Button.ONE_WORD.getText()));
//            }
//            if (!letter) {
//                keyboardRow2.add(new KeyboardButton(Button.LETTER.getText()));
//            }
//            if (!media) {
//                keyboardRow2.add(new KeyboardButton(Button.MEDIA.getText()));
//            }
//            keyboard.add(keyboardRow);
//            keyboard.add(keyboardRow2);
//            keyboard.add(createKeyboardRow(Button.BACK));
//
//            replyKeyboardMarkup.setKeyboard(keyboard);
//        }
//        return replyKeyboardMarkup;
//    }

    public static ReplyKeyboardMarkup ambassadorTestMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.TEST_1, Button.TEST_2));
        keyboard.add(createKeyboardRow(Button.TEST_3));
        keyboard.add(createKeyboardRow(Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup telephonyMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.GALLERY_SEARCH, Button.OTHER_DIVISION_SEARCH));
        keyboard.add(createKeyboardRow(Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup otherDivisionMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        List<String> cities = userRepository.findCitiesOffice().stream()
                .sorted((city1, city2) -> {
                    if (city1.equals("Москва и МО")) return -1;
                    if (city2.equals("Москва и МО")) return 1;
                    if (city1.equals("Санкт-Петербург")) return -1;
                    if (city2.equals("Санкт-Петербург")) return 1;
                    return city1.compareTo(city2);
                })
                .toList();

        for (String city : cities) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(city));
            keyboard.add(keyboardRow);
        }

        keyboard.add(createKeyboardRow(Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup divisionMenu(String city) {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        List<String> divisions = userRepository.findDivisionsOnCity(city);

        for (String division : divisions) {
            if (!Objects.isNull(division)) {
                KeyboardRow keyboardRow = new KeyboardRow();
                keyboardRow.add(new KeyboardButton(division));
                keyboard.add(keyboardRow);
            }
        }

        keyboard.add(createKeyboardRow(Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup giftMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.GIFT_YES, Button.GIFT_NO));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup jumanjiMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.READY, Button.BACK));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup jumanjiOkMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.FIND_TEAM));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup jumanjiJoinTeam() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.JOIN_TEAM));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }


    public static ReplyKeyboardMarkup marathonMenu(long id) {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        Optional<Marathon> optionalMarathon = marathonRepository.findById(id);
        if (optionalMarathon.isPresent()) {
            Marathon marathon = optionalMarathon.get();
            if (marathon.isActual()) {
                keyboard.add(createKeyboardRow(Button.MY_POINTS));
                keyboard.add(createKeyboardRow(Button.MARATHON_FEEDBACK));
            } else {
                keyboard.add(createKeyboardRow(Button.SIGNUP));
            }
            keyboard.add(createKeyboardRow(Button.BACK));
        } else {
            keyboard.add(createKeyboardRow(Button.SIGNUP, Button.BACK));
        }

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup marathonData() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.DATA_OK, Button.CHANGE));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup sexMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.MALE, Button.FEMALE));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup warmUpMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.WARM_UP));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup okayMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.OKAY));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup coachLinkMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.NICE_TO_MEET_YOU));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup nutritionistLinkMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.HELLO_ALEX));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup instructionMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.OF_COURSE));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup instruction2Menu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.DEAL));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup marathonTime() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("6:00"));
        keyboardRow.add(new KeyboardButton("7:00"));
        keyboardRow2.add(new KeyboardButton("8:00"));
        keyboardRow2.add(new KeyboardButton("9:00"));
        keyboard.add(keyboardRow);
        keyboard.add(keyboardRow2);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup timeZone() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();

        keyboardRow.add(new KeyboardButton("Москва (0)"));
        keyboardRow.add(new KeyboardButton("Екатеринбург (+2)"));
        keyboardRow2.add(new KeyboardButton("Красноярск (+4)"));
        keyboardRow2.add(new KeyboardButton("Владивосток (+7)"));

        keyboard.add(keyboardRow);
        keyboard.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup goodButton() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.GOOD));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup doneButton() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.DONE));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup understandButton() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.UNDERSTAND));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup byMondayButton() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.BY_MONDAY));
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup yesNoMarathonMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.MARATHON_NO, Button.MARATHON_YES));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup myData() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.ITS_OK, Button.CHANGE));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup adminMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.UPDATE_STAT));
        keyboard.add(createKeyboardRow(Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup documentsMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.WORKNOTE, Button.CERTIFICATES, Button.COMPETITIONS));
        keyboard.add(createKeyboardRow(/*Button.PATTERNS,*/ Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup worknoteMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.WORKNOTE_TRAINEE, Button.WORKNOTE_SECOND, Button.WORKNOTE_THIRD));
        keyboard.add(createKeyboardRow(Button.WORKNOTE_FOURTH, Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup patternsMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.INVENTORY, Button.SALARY));
        keyboard.add(createKeyboardRow(Button.STATEMENTS, Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup certificatesMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.TEA, Button.PACKAGE, Button.ACCESSORIES, Button.CHOCOSTYLE));
        keyboard.add(createKeyboardRow(Button.COFFEE, Button.SWEETS, Button.HOUSEHOLD, Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup competitionsMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.CORPORATE, Button.CONSULTANT, Button.MANAGER));
        keyboard.add(createKeyboardRow(Button.CURATOR, Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup infoSearchMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.PRODUCT_AND_SERVICE, Button.MANAGEMENT, Button.DESIGN));
        keyboard.add(createKeyboardRow(Button.IT, Button.CORPORATE_CULTURE));
        keyboard.add(createKeyboardRow(Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup contactSearchMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.YES, Button.NO));
        keyboard.add(createKeyboardRow(Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup contactSearchNoMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.PRODUCT_QUALITY, Button.PROMOTION, Button.TECH));
        keyboard.add(createKeyboardRow(Button.CHAIN_MEETING, Button.TAKEAWAY, Button.ORDER));
        keyboard.add(createKeyboardRow(Button.SICK_LEAVE_REQUEST, Button.CONTRACT, Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup contactSearchYesMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.GALLERY, Button.STAFFER));
        keyboard.add(createKeyboardRow(Button.BACK));

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

}
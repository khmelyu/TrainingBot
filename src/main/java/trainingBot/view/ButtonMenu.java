package trainingBot.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import trainingBot.model.entity.User;
import trainingBot.model.rep.UserRepository;


import java.util.ArrayList;
import java.util.List;


@Component
public class ButtonMenu {

    private static UserRepository userRepository;

    @Autowired
    public ButtonMenu(UserRepository userRepository) {
        ButtonMenu.userRepository = userRepository;
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
        User user = userRepository.findById(id).orElse(null);
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(createKeyboardRow(Button.CZ_SEARCH, Button.INFO_SEARCH, Button.CONTACTS_SEARCH));
        keyboard.add(createKeyboardRow(Button.TRAININGS, Button.DOCUMENTS));
        keyboard.add(createKeyboardRow(Button.MY_DATA, Button.FEEDBACK));
        if (user != null && user.isAdmin()) {
            keyboard.add(createKeyboardRow(Button.ADMIN));
        }

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
        keyboard.add(createKeyboardRow(Button.WORKNOTE_FOURTH, Button.WORKNOTE_FIFTH, Button.BACK));

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
        keyboard.add(createKeyboardRow(Button.COFFEE,Button.SWEETS, Button.HOUSEHOLD, Button.BACK));

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

}
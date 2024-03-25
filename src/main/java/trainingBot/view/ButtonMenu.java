package trainingBot.view;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;


@Component
public class ButtonMenu {

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

    public static ReplyKeyboardMarkup mainMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        keyboard.add(createKeyboardRow(Button.TRAININGS, Button.MY_DATA, Button.FEEDBACK));
        keyboard.add(createKeyboardRow(Button.DOCUMENTS));

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

}
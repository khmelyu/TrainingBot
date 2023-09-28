package trainingBot.view.menu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import trainingBot.core.triggers.TextTriggers;

import java.util.ArrayList;
import java.util.List;


@Component
public class ButtonMenu {

    public static ReplyKeyboardMarkup mainMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.SEARCH_CZ.getTriggerText()));
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.SEARCH_INFO.getTriggerText()));
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.SEARCH_CONTACTS.getTriggerText()));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.SEARCH_C.getTriggerText()));
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.TRAININGS.getTriggerText()));
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.DOCUMENTS.getTriggerText()));

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton(TextTriggers.FEEDBACK.getTriggerText()));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup documentsMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.WORK_NOTE.getTriggerText()));
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.CERTIFICATES.getTriggerText()));
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.COMPETENCIES.getTriggerText()));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.PATTERNS.getTriggerText()));
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.BACK.getTriggerText()));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup workNotesMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.INTERN.getTriggerText()));
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.SECOND_LEVEL.getTriggerText()));
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.THIRD_LEVEL.getTriggerText()));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.FOURTH_LEVEL.getTriggerText()));
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.FIFTH_LEVEL.getTriggerText()));
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.BACK.getTriggerText()));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup patternsMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.ACCOUNTIGS.getTriggerText()));
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.SCHEDULE.getTriggerText()));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.DECLARATION.getTriggerText()));
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.BACK.getTriggerText()));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
    public static ReplyKeyboardMarkup certificatesMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.TEA.getTriggerText()));
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.COFFEE.getTriggerText()));
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.CANDY.getTriggerText()));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.WRAP.getTriggerText()));
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.ACCESSORIES.getTriggerText()));
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.CHOKOSTYLE.getTriggerText()));

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton(TextTriggers.HOUSEHOLD_GOODS.getTriggerText()));
        keyboardThirdRow.add(new KeyboardButton(TextTriggers.BACK.getTriggerText()));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
    public static ReplyKeyboardMarkup competenciesMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.CORPORATE.getTriggerText()));
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.CONSULTANT.getTriggerText()));


        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.CURATOR.getTriggerText()));
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.MANAGER.getTriggerText()));


        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton(TextTriggers.BACK.getTriggerText()));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

}

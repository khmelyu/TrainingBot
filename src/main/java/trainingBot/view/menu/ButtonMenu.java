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

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.SEARCH_C.getTriggerText()));

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.SEARCH_INFO.getTriggerText()));

        KeyboardRow keyboardFourthRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.TRAININGS.getTriggerText()));

        KeyboardRow keyboardFifthRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.SEARCH_CONTACTS.getTriggerText()));

        KeyboardRow keyboardSixthRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.DOCUMENTS.getTriggerText()));

        KeyboardRow keyboardSeventhRow = new KeyboardRow();
        keyboardSeventhRow.add(new KeyboardButton(TextTriggers.FEEDBACK.getTriggerText()));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        keyboard.add(keyboardFourthRow);
        keyboard.add(keyboardFifthRow);
        keyboard.add(keyboardSixthRow);
        keyboard.add(keyboardSeventhRow);

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

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.PATTERNS.getTriggerText()));

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.CERTIFICATES.getTriggerText()));

        KeyboardRow keyboardFourthRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(TextTriggers.COMPETENCIES.getTriggerText()));

        KeyboardRow keyboardFifthRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(TextTriggers.BACK.getTriggerText()));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        keyboard.add(keyboardFourthRow);
        keyboard.add(keyboardFifthRow);

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
        keyboardFirstRow.add(new KeyboardButton("Стажерская"));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("2 уровень"));

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("3 уровень"));

        KeyboardRow keyboardFourthRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("4 уровень"));

        KeyboardRow keyboardFifthRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("5 уровень"));

        KeyboardRow keyboardSixthRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Назад"));


        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        keyboard.add(keyboardFourthRow);
        keyboard.add(keyboardFifthRow);
        keyboard.add(keyboardSixthRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

}

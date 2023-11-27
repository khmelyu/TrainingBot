package trainingBot.view;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineMenu {
    public static InlineKeyboardMarkup trainingsMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();

        row.add(InlineKeyboardButton.builder().text("Оффлайн тренинги").callbackData("OfflineTrainings").build());
        row.add(InlineKeyboardButton.builder().text("Онлайн тренинги").callbackData("OnlineTrainings").build());
        row2.add(InlineKeyboardButton.builder().text("Мои тренинги").callbackData("MyTrainings").build());

        keyboard.add(row);
        keyboard.add(row2);


        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }
}

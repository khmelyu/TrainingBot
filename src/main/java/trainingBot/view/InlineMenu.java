package trainingBot.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import trainingBot.model.entity.User;
import trainingBot.model.rep.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class InlineMenu {
    private static UserRepository userRepository;

    @Autowired
    public InlineMenu(UserRepository userRepository) {
        InlineMenu.userRepository = userRepository;
    }


    public static InlineKeyboardButton createButton(Callback callback) {
        return InlineKeyboardButton.builder().text(callback.getCallbackText()).callbackData(callback.getCallbackData()).build();
    }

    public static List<InlineKeyboardButton> createRow(InlineKeyboardButton... buttons) {
        return new ArrayList<>(Arrays.asList(buttons));
    }

    public static InlineKeyboardMarkup trainingsMenu(long id) {
        User user = userRepository.findById(id).orElse(null);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(createRow(
                createButton(Callback.OFFLINE_TRAININGS),
                createButton(Callback.ONLINE_TRAININGS)));

        keyboard.add(createRow(
                createButton(Callback.MY_TRAININGS),
                user != null && user.isCoach() ? createButton(Callback.COACH_MENU) : null));

        keyboard.add(createRow(
                user != null && user.isAdmin() ? createButton(Callback.COACH_MENU) : null));

        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup coachMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(createRow(
                createButton(Callback.CREATE_TRAININGS),
                createButton(Callback.CREATED_TRAININGS)));

        keyboard.add(createRow(
                createButton(Callback.ARCHIVE_TRAININGS),
                createButton(Callback.BACK)));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
}
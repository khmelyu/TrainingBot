package trainingBot.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import trainingBot.model.entity.User;
import trainingBot.model.rep.TrainingsListRepository;
import trainingBot.model.rep.UserRepository;

import java.util.*;


@Component
public class CallbackMenu {
    private static UserRepository userRepository;
    private static TrainingsListRepository trainingsListRepository;

    @Autowired
    public CallbackMenu(UserRepository userRepository, TrainingsListRepository trainingsListRepository) {
        CallbackMenu.userRepository = userRepository;
        CallbackMenu.trainingsListRepository = trainingsListRepository;
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

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(createButton(Callback.MY_TRAININGS));
        if (user != null && user.isCoach()) {
            row.add(createButton(Callback.COACH_MENU));
        }
        keyboard.add(row);

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

    public static InlineKeyboardMarkup createTrainingsMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(createRow(
                createButton(Callback.OFFLINE_TRAININGS_CREATE),
                createButton(Callback.ONLINE_TRAININGS_CREATE)));

        keyboard.add(createRow(
                createButton(Callback.BACK)));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup trainingCategoryMenu(String city) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (String training : trainingsListRepository.findByCity(city)) {
            keyboard.add(createRow(
                    InlineKeyboardButton.builder().text(training).callbackData(training).build()));
        }
        keyboard.add(createRow(
                createButton(Callback.BACK)));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup createOnlineTrainingsMenu() {
        return trainingCategoryMenu("Онлайн");
    }


    public static InlineKeyboardMarkup createMoscowTrainingsMenu() {
        return trainingCategoryMenu("Мск");
    }

    public static InlineKeyboardMarkup createSaintPetersburgTrainingsMenu() {
        return trainingCategoryMenu("СПб");
    }

    public static InlineKeyboardMarkup cityChoiceMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(createRow(
                createButton(Callback.MOSCOW),
                createButton(Callback.SAINT_PETERSBURG)));

        keyboard.add(createRow(
                createButton(Callback.BACK)));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
}
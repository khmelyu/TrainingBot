package trainingBot.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import trainingBot.model.entity.Trainings;
import trainingBot.model.entity.TrainingsList;
import trainingBot.model.entity.User;
import trainingBot.model.rep.TrainingsListRepository;
import trainingBot.model.rep.TrainingsRepository;
import trainingBot.model.rep.UserRepository;
import trainingBot.model.rep.UsersToTrainingsRepository;
import trainingBot.service.UserListService;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Component
@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8")
public class CallbackMenu {
    private final UserRepository userRepository;
    private final TrainingsListRepository trainingsListRepository;
    private final TrainingsRepository trainingsRepository;
    private final UsersToTrainingsRepository usersToTrainingsRepository;
    private final UserStateService userStateService;
    private final UserListService userListService;

    @Value("${calendar.name}")
    private String calendarName;
    @Value("${calendar.link}")
    private String calendarLink;

    @Autowired
    public CallbackMenu(UserRepository userRepository, TrainingsListRepository trainingsListRepository, TrainingsRepository trainingsRepository, UsersToTrainingsRepository usersToTrainingsRepository, UserStateService userStateService, UserListService userListService) {
        this.userRepository = userRepository;
        this.trainingsListRepository = trainingsListRepository;
        this.trainingsRepository = trainingsRepository;
        this.usersToTrainingsRepository = usersToTrainingsRepository;
        this.userStateService = userStateService;
        this.userListService = userListService;
    }

    public InlineKeyboardButton createButton(Callback callback) {
        return InlineKeyboardButton.builder().text(callback.getCallbackText()).callbackData(callback.getCallbackData()).build();

    }

    private InlineKeyboardButton createButton(String text, String url) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setUrl(url);
        return button;
    }


    public List<InlineKeyboardButton> createRow(InlineKeyboardButton... buttons) {
        return new ArrayList<>(Arrays.asList(buttons));
    }

    public InlineKeyboardMarkup backMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(createRow(createButton(Callback.BACK)));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup trainingsMenu(long id) {
        User user = userRepository.findById(id).orElse(null);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(createRow(createButton(Callback.OFFLINE_TRAININGS), createButton(Callback.ONLINE_TRAININGS)));

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(createButton(Callback.MY_TRAININGS));
        if (user != null && user.isCoach()) {
            row.add(createButton(Callback.COACH_MENU));
        }
        keyboard.add(row);
        keyboard.add(createRow(createButton(calendarName, calendarLink)));
        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup trainingInfoMenu(long id) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        if (userStateService.getUserState(id).equals(UserState.MY_TRAININGS)) {
            keyboard.add(createRow(createButton(Callback.ABORTING), createButton(Callback.BACK)));
        } else if (userStateService.getUserState(id).equals(UserState.CREATED_TRAININGS)) {
            keyboard.add(createRow(createButton(Callback.DELETE_TRAINING), createButton(Callback.USERS_LIST)));
            keyboard.add(createRow(createButton(Callback.MARK_USERS), createButton(Callback.FEEDBACK_REQUEST)));
            keyboard.add(createRow(createButton(Callback.IN_ARCHIVE)));
            keyboard.add(createRow(createButton(Callback.BACK)));
        } else if (userStateService.getUserState(id).equals(UserState.TRAININGS_ON_CITY)) {
            keyboard.add(createRow(createButton(Callback.SIGN_UP), createButton(Callback.BACK)));
        } else {
            keyboard.add(createRow(createButton(Callback.SIGN_UP)));
        }
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup checkDataMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(createRow(createButton(Callback.YES), createButton(Callback.NO)));
        keyboard.add(createRow(createButton(Callback.ABORT_SIGNUP)));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup coachMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(createRow(createButton(Callback.CREATE_TRAININGS), createButton(Callback.CREATED_TRAININGS)));

        keyboard.add(createRow(createButton(Callback.ARCHIVE_TRAININGS), createButton(Callback.BACK)));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup myTrainingsMenu(long id) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        if (userStateService.getUserState(id).equals(UserState.COACH_MENU)) {
            for (Trainings training : trainingsRepository.findByCreator(id)) {
                keyboard.add(createRow(InlineKeyboardButton.builder().text(training.getName()).callbackData(String.valueOf(training.getId())).build()));
            }
        } else {
            for (Trainings training : usersToTrainingsRepository.findByUserId(id)) {
                keyboard.add(createRow(InlineKeyboardButton.builder().text(training.getName()).callbackData(String.valueOf(training.getId())).build()));
            }
        }
        keyboard.add(createRow(createButton(Callback.BACK)));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup archivedTrainingsMenu(long id) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (Trainings training : trainingsRepository.findByCreatorArchived(id)) {
            keyboard.add(createRow(InlineKeyboardButton.builder().text(training.getName()).callbackData(String.valueOf(training.getId())).build()));
        }
        keyboard.add(createRow(createButton(Callback.BACK)));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup markUserMenu(String trainingId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        Map<String, String> trainingMap = userListService.markUserList(trainingId);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (Map.Entry<String, String> entry : trainingMap.entrySet()) {

            String userId = entry.getKey();
            String userName = entry.getValue();
            keyboard.add(createRow(InlineKeyboardButton.builder().text(userName).callbackData(Callback.SELECT_USER.getCallbackData() + userId).build(),
                    InlineKeyboardButton.builder().text(Callback.USER_MARK.getCallbackText()).callbackData(Callback.USER_MARK.getCallbackData() + userId).build()));
        }
        keyboard.add(createRow(createButton(Callback.BACK)));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createTrainingsMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(createRow(createButton(Callback.OFFLINE_TRAININGS_CREATE), createButton(Callback.ONLINE_TRAININGS_CREATE)));

        keyboard.add(createRow(createButton(Callback.BACK)));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup cityChoiceMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(createRow(createButton(Callback.MOSCOW), createButton(Callback.SAINT_PETERSBURG)));

        keyboard.add(createRow(createButton(Callback.BACK)));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup onlineCategory(long id) {
        return categoryMenu(Callback.ONLINE_TRAININGS_CREATE.getCallbackText(), id);
    }

    public InlineKeyboardMarkup moscowCategory(long id) {
        return categoryMenu(Callback.MOSCOW.getCallbackText(), id);
    }

    public InlineKeyboardMarkup saintPetersburgCategory(long id) {
        return categoryMenu(Callback.SAINT_PETERSBURG.getCallbackText(), id);
    }

    public InlineKeyboardMarkup categoryMenu(String city, long id) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        if (userStateService.getUserState(id).equals(UserState.CREATE_TRAINING) || userStateService.getUserState(id).equals(UserState.CREATE_OFFLINE_TRAINING)) {
            for (String training : trainingsListRepository.findByCity(city)) {
                keyboard.add(createRow(InlineKeyboardButton.builder().text(training).callbackData(training).build()));
            }
        } else {
            for (String training : trainingsRepository.findByCity(city)) {
                keyboard.add(createRow(InlineKeyboardButton.builder().text(training).callbackData(training).build()));
            }
        }
        keyboard.add(createRow(createButton(Callback.BACK)));
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup trainingsOnCategoryMenu(String city, String category, long id) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        if (userStateService.getUserState(id).equals(UserState.CREATE_MOSCOW_TRAINING) || userStateService.getUserState(id).equals(UserState.CREATE_SAINT_PETERSBURG_TRAINING) || userStateService.getUserState(id).equals(UserState.CREATE_ONLINE_TRAINING)) {
            for (TrainingsList training : trainingsListRepository.findByCityAndCategory(city, category)) {
                keyboard.add(createRow(InlineKeyboardButton.builder().text(training.getName()).callbackData(String.valueOf(training.getId())).build()));
            }
        } else {
            for (Trainings training : trainingsRepository.findByCityAndCategory(city, category)) {
                keyboard.add(createRow(InlineKeyboardButton.builder().text(training.getName()).callbackData(String.valueOf(training.getId())).build()));
            }
        }
        keyboard.add(createRow(createButton(Callback.BACK)));
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup startTimeMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        List<InlineKeyboardButton> row5 = new ArrayList<>();


        row1.add(InlineKeyboardButton.builder().text("10:00").callbackData("10:00").build());
        row1.add(InlineKeyboardButton.builder().text("10:30").callbackData("10:30").build());
        row1.add(InlineKeyboardButton.builder().text("11:00").callbackData("11:00").build());
        row2.add(InlineKeyboardButton.builder().text("11:30").callbackData("11:30").build());
        row2.add(InlineKeyboardButton.builder().text("12:00").callbackData("12:00").build());
        row2.add(InlineKeyboardButton.builder().text("12:30").callbackData("12:30").build());
        row3.add(InlineKeyboardButton.builder().text("13:00").callbackData("13:00").build());
        row3.add(InlineKeyboardButton.builder().text("13:30").callbackData("13:30").build());
        row3.add(InlineKeyboardButton.builder().text("14:00").callbackData("14:00").build());
        row4.add(InlineKeyboardButton.builder().text("14:30").callbackData("14:30").build());
        row4.add(InlineKeyboardButton.builder().text("15:00").callbackData("15:00").build());
        row4.add(InlineKeyboardButton.builder().text("15:30").callbackData("15:30").build());
        row5.add(InlineKeyboardButton.builder().text("16:00").callbackData("16:00").build());
        row5.add(InlineKeyboardButton.builder().text("16:30").callbackData("16:30").build());
        row5.add(InlineKeyboardButton.builder().text("17:00").callbackData("17:00").build());

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        keyboard.add(row5);

        keyboard.add(createRow(createButton(Callback.BACK)));

        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup endTimeMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        List<InlineKeyboardButton> row5 = new ArrayList<>();


        row1.add(InlineKeyboardButton.builder().text("13:00").callbackData("13:00").build());
        row1.add(InlineKeyboardButton.builder().text("13:30").callbackData("13:30").build());
        row1.add(InlineKeyboardButton.builder().text("14:00").callbackData("14:00").build());
        row2.add(InlineKeyboardButton.builder().text("14:30").callbackData("14:30").build());
        row2.add(InlineKeyboardButton.builder().text("15:00").callbackData("15:00").build());
        row2.add(InlineKeyboardButton.builder().text("15:30").callbackData("15:30").build());
        row3.add(InlineKeyboardButton.builder().text("16:00").callbackData("16:00").build());
        row3.add(InlineKeyboardButton.builder().text("16:30").callbackData("16:30").build());
        row3.add(InlineKeyboardButton.builder().text("17:00").callbackData("17:00").build());
        row4.add(InlineKeyboardButton.builder().text("17:30").callbackData("17:30").build());
        row4.add(InlineKeyboardButton.builder().text("18:00").callbackData("18:00").build());
        row4.add(InlineKeyboardButton.builder().text("18:30").callbackData("18:30").build());
        row5.add(InlineKeyboardButton.builder().text("19:00").callbackData("19:00").build());
        row5.add(InlineKeyboardButton.builder().text("19:30").callbackData("19:30").build());
        row5.add(InlineKeyboardButton.builder().text("20:00").callbackData("20:00").build());

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        keyboard.add(row5);


        keyboard.add(createRow(createButton(Callback.BACK)));

        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }
}
package trainingBot.controller.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import trainingBot.core.TrainingBot;
import trainingBot.model.entity.Trainings;
import trainingBot.model.entity.User;
import trainingBot.model.entity.UsersToTrainings;
import trainingBot.model.rep.TrainingsRepository;
import trainingBot.model.rep.UserRepository;
import trainingBot.model.rep.UsersToTrainingsRepository;
import trainingBot.service.NotificationUser;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

import java.util.Optional;
import java.util.UUID;

@Component
@PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8")
public class UsersOnTrainingsAction {
    private final Logger logger = LoggerFactory.getLogger(UsersOnTrainingsAction.class);
    private final Sendler sendler;
    private final UserStateService userStateService;
    private final TrainingsRepository trainingsRepository;
    private final UserRepository userRepository;
    private final UsersToTrainingsRepository usersToTrainingsRepository;
    private final NotificationUser notificationUser;
    private final TrainingBot trainingBot;


    @Value("${training.category}")
    private String trainingCategory;
    @Value("${training.city}")
    private String trainingCity;
    @Value("${training.choice}")
    private String trainingChoice;
    @Value("${my.trainings}")
    private String myTrainings;
    @Value("${user.info}")
    private String userInfo;
    @Value("${online}")
    private String online;
    @Value("${signup.message}")
    private String signUpMessage;
    @Value("${waiting.list.message}")
    private String waitingListMessage;
    @Value("${repeat.signup.message}")
    private String repeatSignupMessage;
    @Value("${training.abort.message}")
    private String trainingAbortMessage;


    @Autowired
    public UsersOnTrainingsAction(@Lazy Sendler sendler, UserStateService userStateService, TrainingsRepository trainingsRepository, UserRepository userRepository, UsersToTrainingsRepository usersToTrainingsRepository, NotificationUser notificationUser, TrainingBot trainingBot) {
        this.sendler = sendler;
        this.userStateService = userStateService;
        this.trainingsRepository = trainingsRepository;
        this.userRepository = userRepository;
        this.usersToTrainingsRepository = usersToTrainingsRepository;
        this.notificationUser = notificationUser;
        this.trainingBot = trainingBot;
    }

    public void viewOnlineCategory(long id, Message currentMessage) {
        userStateService.setCity(id, online);
        sendler.sendOnlineCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.ONLINE_TRAININGS);
    }

    public void viewTrainingCity(long id, Message currentMessage) {
        sendler.sendCityChoice(id, trainingCity, currentMessage);
        userStateService.setUserState(id, UserState.OFFLINE_TRAININGS);
    }

    public void viewMoscowCategory(long id, Message currentMessage, String data) {
        userStateService.setCity(id, data);
        sendler.sendMoscowCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.MOSCOW_TRAININGS);
    }

    public void viewSaintsPetersburgCategory(long id, Message currentMessage, String data) {
        userStateService.setCity(id, data);
        sendler.sendSaintPetersburgCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.SAINT_PETERSBURG_TRAININGS);
    }

    public void viewTrainingsOnCategory(long id, Message currentMessage, String data) {
        userStateService.setCategory(id, data);
        sendler.sendTrainingsOnCategory(id, trainingChoice, currentMessage, userStateService.getCity(id), data);
        userStateService.setUserState(id, UserState.TRAININGS_ON_CITY);
    }

    public void reviewTraining(long id, Message currentMessage, String data) {
        userStateService.setTrainingId(id, data);

        Optional<Trainings> trainingOptional = trainingsRepository.findById(UUID.fromString(data));
        if (trainingOptional.isPresent()) {
            Trainings training = trainingOptional.get();
            String description = training.getDescription();
            String pic = training.getPic();
            String shortDescription;
            if (description.length() <= 1024) {
                shortDescription = description;
            } else {
                shortDescription = description.substring(0, 1024);
            }
            sendler.sendTrainingInfo(id, pic, currentMessage, shortDescription);
            userStateService.setUserState(id, UserState.SELECT_TRAINING);
        }
    }

    public void reviewTraining(long id, String data) {
        userStateService.setTrainingId(id, data);

        Optional<Trainings> trainingOptional = trainingsRepository.findById(UUID.fromString(data));
        if (trainingOptional.isPresent()) {
            Trainings training = trainingOptional.get();
            String description = training.getDescription();
            String pic = training.getPic();
            String shortDescription;
            if (description.length() <= 1024) {
                shortDescription = description;
            } else {
                shortDescription = description.substring(0, 1024);
            }
            sendler.sendTrainingInfoFromCalendar(id, pic, shortDescription);
            userStateService.setUserState(id, UserState.SELECT_TRAINING);
        }
    }

    public void checkUserData(long id, Message currentMessage) {
        User user = userRepository.findById(id).orElseThrow();
        String data = user.userData();
        sendler.sendCheckMyData(id, userInfo, currentMessage, data);
        userStateService.setUserState(id, UserState.CHECK_DATA);
    }

    @Transactional
    public void signUpOnTraining(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Trainings training = trainingsRepository.findById(UUID.fromString(userStateService.getTrainingId(id))).orElseThrow(() -> new RuntimeException("Training not found"));
        long currentUserCount = usersToTrainingsRepository.countByTrainings(training, false, true);
        boolean exists = usersToTrainingsRepository.existsByUserAndTrainings(user, training);
        boolean fullTraining = currentUserCount >= training.getMax_users();
        Boolean actual = usersToTrainingsRepository.isUserOnActual(user, training);
        Boolean waitingList = usersToTrainingsRepository.isUserOnWaitingList(user, training);

        if (!exists) {
            UsersToTrainings usersToTrainings = new UsersToTrainings();

            usersToTrainings.setUser(user);
            usersToTrainings.setTrainings(training);
            usersToTrainings.setActual(true);
            usersToTrainings.setPresence(true);
            usersToTrainings.setWaiting_list(fullTraining);
            usersToTrainingsRepository.save(usersToTrainings);

            if (fullTraining) {
                sendler.sendTextMessage(id, waitingListMessage);
                logger.info("User: {} signup waiting list on training. Training id: {}", id, training.getId());
            } else {
                sendler.sendTextMessage(id, signUpMessage);
                logger.info("User: {} signup on training. Training id: {}", id, training.getId());
            }
        }
        if (exists && fullTraining) {
            if (!waitingList && actual) {
                sendler.sendTextMessage(id, repeatSignupMessage);
                logger.info("User: {} tried to re-signup on training. Training id: {}", id, training.getId());
                System.out.println(currentUserCount);
            } else {
                usersToTrainingsRepository.addUserFromWaitingList(user, training);
                sendler.sendTextMessage(id, waitingListMessage);
                logger.info("User: {} tried to re-signup on training. Training id: {}", id, training.getId());
            }
        }
        if (exists && !fullTraining) {
            if (waitingList && actual) {
                usersToTrainingsRepository.removeUserFromWaitingList(user, training);
                sendler.sendTextMessage(id, signUpMessage);
                logger.info("User: {} signup on training. Training id: {}", id, training.getId());

            } else if (!waitingList && !actual) {
                usersToTrainingsRepository.reSignupUserFromTraining(user, training);
                sendler.sendTextMessage(id, signUpMessage);
                logger.info("User: {} signup on training. Training id: {}", id, training.getId());
            } else {
                usersToTrainingsRepository.reSignupUserFromTraining(user, training);
                sendler.sendTextMessage(id, repeatSignupMessage);
                logger.info("User: {} to re-signup on training . Training id: {}", id, training.getId());
            }
        }
        userStateService.setUserState(id, UserState.MAIN_MENU);
        String callbackQueryId = update.getCallbackQuery().getId();
        AnswerCallbackQuery answer = AnswerCallbackQuery.builder().callbackQueryId(callbackQueryId).text("").showAlert(false).build();
        try {
            trainingBot.execute(answer);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void viewMyTrainings(long id, Message currentMessage) {
        sendler.sendMyTrainings(id, myTrainings, currentMessage);
        userStateService.setUserState(id, UserState.MY_TRAININGS);
    }

    @Transactional
    public void abortTrainings(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Trainings training = trainingsRepository.findById(UUID.fromString(userStateService.getTrainingId(id))).orElseThrow(() -> new RuntimeException("Training not found"));
        usersToTrainingsRepository.abortUserFromTraining(user, training);
        sendler.sendTextMessage(id, trainingAbortMessage);
        if (notificationUser.canCall(id)) {
            notificationUser.notificationWaitingList(userStateService.getTrainingId(id));
        }
        userStateService.setUserState(id, UserState.MAIN_MENU);
        String callbackQueryId = update.getCallbackQuery().getId();
        AnswerCallbackQuery answer = AnswerCallbackQuery.builder().callbackQueryId(callbackQueryId).text("").showAlert(false).build();
        try {
            trainingBot.execute(answer);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
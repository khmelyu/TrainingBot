package trainingBot.service.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.model.entity.Trainings;
import trainingBot.model.entity.Users;
import trainingBot.model.entity.UsersToTrainings;
import trainingBot.model.rep.TrainingsRepository;
import trainingBot.model.rep.UserRepository;
import trainingBot.model.rep.UsersToTrainingsRepository;
import trainingBot.service.NotificationUser;
import trainingBot.service.redis.TrainingDataService;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
@PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8")
public class UsersOnTrainingsAction {
    private final Logger logger = LoggerFactory.getLogger(UsersOnTrainingsAction.class);
    private final Sendler sendler;
    private final UserStateService userStateService;
    private final TrainingDataService trainingDataService;
    private final TrainingsRepository trainingsRepository;
    private final UserRepository userRepository;
    private final UsersToTrainingsRepository usersToTrainingsRepository;
    private final NotificationUser notificationUser;


    @Value("${training.category}")
    private String trainingCategory;
    @Value("${training.city}")
    private String trainingCity;
    @Value("${training.choice}")
    private String trainingChoice;
    @Value("${my.trainings}")
    private String myTrainings;
    @Value("${actual.data}")
    private String actualData;
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
    @Value("${training.feedback.questions}")
    private String trainingFeedbackQuestions;
    @Value("${training.feedback.end}")
    private String trainingFeedbackEnd;

    @Autowired
    public UsersOnTrainingsAction(Sendler sendler, UserStateService userStateService, TrainingDataService trainingDataService, TrainingsRepository trainingsRepository, UserRepository userRepository, UsersToTrainingsRepository usersToTrainingsRepository, NotificationUser notificationUser) {
        this.sendler = sendler;
        this.userStateService = userStateService;
        this.trainingDataService = trainingDataService;
        this.trainingsRepository = trainingsRepository;
        this.userRepository = userRepository;
        this.usersToTrainingsRepository = usersToTrainingsRepository;
        this.notificationUser = notificationUser;
    }

    public void viewOnlineCategory(long id, Message currentMessage) {
        trainingDataService.setCity(id, online);
        sendler.sendOnlineCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.ONLINE_TRAININGS);
    }

    public void viewTrainingCity(long id, Message currentMessage) {
        sendler.sendCityChoice(id, trainingCity, currentMessage);
        userStateService.setUserState(id, UserState.OFFLINE_TRAININGS);
    }

    public void viewMoscowCategory(long id, Message currentMessage, String data) {
        trainingDataService.setCity(id, data);
        sendler.sendMoscowCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.MOSCOW_TRAININGS);
    }

    public void viewSaintsPetersburgCategory(long id, Message currentMessage, String data) {
        trainingDataService.setCity(id, data);
        sendler.sendSaintPetersburgCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.SAINT_PETERSBURG_TRAININGS);
    }

    public void viewTrainingsOnCategory(long id, Message currentMessage, String data) {
        trainingDataService.setCategory(id, data);
        sendler.sendTrainingsOnCategory(id, trainingChoice, currentMessage, trainingDataService.getCity(id), data);
        userStateService.setUserState(id, UserState.TRAININGS_ON_CITY);
    }

    public void reviewMyTraining(long id, Message currentMessage, String data) {
        trainingDataService.setTrainingId(id, data);

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
            userStateService.setUserState(id, UserState.SELECT_MY_TRAINING);
        }
    }

    public void reviewTraining(long id, Message currentMessage, String data) {
        trainingDataService.setTrainingId(id, data);

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
        trainingDataService.setTrainingId(id, data);

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
        Users users = userRepository.findById(id).orElseThrow();
        String data = users.userData();
        sendler.sendCheckMyData(id, actualData, currentMessage, data);
        userStateService.setUserState(id, UserState.CHECK_MY_DATA);
    }

    @Transactional
    public void signUpOnTraining(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        Users users = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Trainings training = trainingsRepository.findById(UUID.fromString(trainingDataService.getTrainingId(id))).orElseThrow(() -> new RuntimeException("Training not found"));
        long currentUserCount = usersToTrainingsRepository.countByTrainings(training, false, true);
        boolean exists = usersToTrainingsRepository.existsByUserAndTrainings(users, training);
        boolean fullTraining = currentUserCount >= training.getMax_users();
        Boolean actual = usersToTrainingsRepository.isUserOnActual(users, training);
        Boolean waitingList = usersToTrainingsRepository.isUserOnWaitingList(users, training);

        if (!exists) {
            UsersToTrainings usersToTrainings = new UsersToTrainings();
            usersToTrainings.setUser(users);
            usersToTrainings.setTrainings(training);
            usersToTrainings.setActual(true);
            usersToTrainings.setSignup_time(new Timestamp(System.currentTimeMillis()));
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
                usersToTrainingsRepository.addUserFromWaitingList(users, training);
                sendler.sendTextMessage(id, waitingListMessage);
                logger.info("User: {} tried to re-signup on training. Training id: {}", id, training.getId());
            }
        }
        if (exists && !fullTraining) {
            if (waitingList && actual) {
                usersToTrainingsRepository.removeUserFromWaitingList(users, training);
                sendler.sendTextMessage(id, signUpMessage);
                logger.info("User: {} signup on training. Training id: {}", id, training.getId());

            } else if (!waitingList && !actual) {
                usersToTrainingsRepository.reSignupUserFromTraining(users, training);
                sendler.sendTextMessage(id, signUpMessage);
                logger.info("User: {} signup on training. Training id: {}", id, training.getId());
            } else {
                usersToTrainingsRepository.reSignupUserFromTraining(users, training);
                sendler.sendTextMessage(id, repeatSignupMessage);
                logger.info("User: {} to re-signup on training . Training id: {}", id, training.getId());
            }
        }
        userStateService.setUserState(id, UserState.MAIN_MENU);
        sendler.callbackAnswer(update);
    }

    public void viewMyTrainings(long id, Message currentMessage) {
        sendler.sendMyTrainings(id, myTrainings, currentMessage);
        userStateService.setUserState(id, UserState.MY_TRAININGS);
    }

    @Transactional
    public void abortTrainings(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        Users users = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Trainings training = trainingsRepository.findById(UUID.fromString(trainingDataService.getTrainingId(id))).orElseThrow(() -> new RuntimeException("Training not found"));
        usersToTrainingsRepository.abortUserFromTraining(users, training, new Timestamp(System.currentTimeMillis()));
        sendler.sendTextMessage(id, trainingAbortMessage);
        logger.info("User: {} aborting on training. Training id: {}", id, training.getId());
        if (notificationUser.canCall(id)) {
            notificationUser.notificationWaitingList(trainingDataService.getTrainingId(id));
        }
        userStateService.setUserState(id, UserState.MAIN_MENU);
        sendler.callbackAnswer(update);
    }

    public void feedbackAnswer(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        trainingDataService.setTrainingId(id, data.substring(16));
        userStateService.setUserState(id, UserState.FEEDBACK_ANSWER);
        sendler.sendBack(id, trainingFeedbackQuestions);
        sendler.callbackAnswer(update);
    }

    @Transactional
    public void sendingFeedback(long id, String feedback) {
        Users users = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Trainings training = trainingsRepository.findById(UUID.fromString(trainingDataService.getTrainingId(id))).orElseThrow(() -> new RuntimeException("Training not found"));
        usersToTrainingsRepository.saveFeedback(feedback, users, training);
        logger.info("User: {} send feedback {} for training {}", id, feedback, training.getId());
        sendler.sendMainMenu(id, trainingFeedbackEnd);
        userStateService.setUserState(id, UserState.MAIN_MENU);
    }
}
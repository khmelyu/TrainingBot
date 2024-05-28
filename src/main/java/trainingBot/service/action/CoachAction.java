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
import trainingBot.model.entity.TrainingsList;
import trainingBot.model.entity.User;
import trainingBot.model.entity.UsersToTrainings;
import trainingBot.model.rep.TrainingsListRepository;
import trainingBot.model.rep.TrainingsRepository;
import trainingBot.model.rep.UserRepository;
import trainingBot.model.rep.UsersToTrainingsRepository;
import trainingBot.service.NotificationUser;
import trainingBot.service.redis.TrainingDataService;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Button;
import trainingBot.view.Sendler;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8")
public class CoachAction {
    private final Logger logger = LoggerFactory.getLogger(CoachAction.class);
    private final Sendler sendler;
    private final UserStateService userStateService;
    private final TrainingDataService trainingDataService;
    private final TrainingsListRepository trainingsListRepository;
    private final TrainingsRepository trainingsRepository;
    private final UserRepository userRepository;
    private final UsersToTrainingsRepository usersToTrainingsRepository;
    private final NotificationUser notificationUser;

    @Value("${coach.menu}")
    private String coachMenu;
    @Value("${training.type}")
    private String trainingType;
    @Value("${training.category}")
    private String trainingCategory;
    @Value("${training.city}")
    private String trainingCity;
    @Value("${training.choice}")
    private String trainingChoice;
    @Value("${training.date}")
    private String trainingDate;
    @Value("${training.start.time}")
    private String trainingStartTime;
    @Value("${training.end.time}")
    private String trainingEndTime;
    @Value("${training.create.complete}")
    private String trainingCreateComplete;
    @Value("${training.create.error}")
    private String trainingCreateError;
    @Value("${created.trainings}")
    private String createdTrainings;
    @Value("${training.link}")
    private String trainingLink;
    @Value("${calendar.text}")
    private String calendarText;
    @Value("${online}")
    private String online;
    @Value("${offline}")
    private String offline;
    @Value("${archive.training}")
    private String trainingArchiveMessage;
    @Value("${un.archive.training}")
    private String unArchiveTrainingsMessage;
    @Value("${training.delete.message}")
    private String trainingDeleteMessage;
    @Value("${archive.trainings}")
    private String archiveTrainings;
    @Value("${mark.user}")
    private String markUser;
    @Value("${user.info}")
    private String userInfo;
    @Value("${waiting.list.users}")
    private String waitingListUsers;
    @Value("${user.list.empty}")
    private String userListEmpty;
    @Value("${user.list}")
    private String userList;
    @Value("${training.feedback.coach.message}")
    private String trainingFeedbackCoachMessage;
    @Value("${training.feedback.user.message}")
    private String trainingFeedbackUserMessage;
    @Value("${info.and.feedback}")
    private String infoAndFeedback;
    @Value("${feedback.is.empty}")
    private String feedbackIsEmpty;


    @Autowired
    public CoachAction(Sendler sendler, UserStateService userStateService, TrainingDataService trainingDataService, TrainingsListRepository trainingsListRepository, TrainingsRepository trainingsRepository, UserRepository userRepository, UsersToTrainingsRepository usersToTrainingsRepository, NotificationUser notificationUser) {
        this.sendler = sendler;
        this.userStateService = userStateService;
        this.trainingDataService = trainingDataService;
        this.trainingsListRepository = trainingsListRepository;
        this.trainingsRepository = trainingsRepository;
        this.userRepository = userRepository;
        this.usersToTrainingsRepository = usersToTrainingsRepository;
        this.notificationUser = notificationUser;
    }

    public void coachAction(long id, Message currentMessage) {
        sendler.sendCoachMenu(id, coachMenu, currentMessage);
        userStateService.setUserState(id, UserState.COACH_MENU);
    }

    public void createdTrainings(long id, Message currentMessage) {
        sendler.sendMyTrainings(id, createdTrainings, currentMessage);
        userStateService.setUserState(id, UserState.CREATED_TRAININGS);
    }

    public void creatingTraining(long id, Message currentMessage) {
        sendler.sendCreateMenu(id, trainingType, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_TRAINING);
    }

    public void viewTrainingCity(long id, Message currentMessage) {
        sendler.sendCityChoice(id, trainingCity, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_OFFLINE_TRAINING);
    }

    public void viewOnlineCategory(long id, Message currentMessage, String data) {
        trainingDataService.setCity(id, data);
        sendler.sendOnlineCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_ONLINE_TRAINING);
    }

    public void viewMoscowCategory(long id, Message currentMessage, String data) {
        trainingDataService.setCity(id, data);
        sendler.sendMoscowCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_MOSCOW_TRAINING);
    }

    public void viewSaintsPetersburgCategory(long id, Message currentMessage, String data) {
        trainingDataService.setCity(id, data);
        sendler.sendSaintPetersburgCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_SAINT_PETERSBURG_TRAINING);
    }

    public void viewTrainingsOnCategory(long id, Message currentMessage, String data) {
        trainingDataService.setCategory(id, data);
        sendler.sendTrainingsOnCategory(id, trainingChoice, currentMessage, trainingDataService.getCity(id), data);
        userStateService.setUserState(id, UserState.TRAININGS_ON_CITY_FOR_CREATE);
    }

    public void viewCalendar(long id, Message currentMessage, String data) {

        Optional<TrainingsList> trainingOptional = trainingsListRepository.findById(Long.valueOf(data));

        if (trainingOptional.isPresent()) {
            TrainingsList training = trainingOptional.get();
            trainingDataService.setTrainingName(id, training.getName());
            trainingDataService.setTrainingDescription(id, training.getDescription());
            trainingDataService.setPic(id, training.getPic());
            trainingDataService.setMaxUsers(id, String.valueOf(training.getMax_users()));
            trainingDataService.setTrainingListId(id, data);
        }

        LocalDate currentDate = LocalDate.now();
        sendler.sendCalendar(id, trainingDate, currentMessage, currentDate, calendarText);
        userStateService.setUserState(id, UserState.CALENDAR);
    }

    public void viewChangeCalendar(long id, Message currentMessage, String data) {
        sendler.sendChangeCalendar(id, trainingDate, currentMessage, data, calendarText);
    }

    public void viewTrainingStartTime(long id, Message currentMessage, String data) {

        trainingDataService.setTrainingDate(id, data);
        sendler.sendStartTimeMenu(id, trainingStartTime, currentMessage);
        userStateService.setUserState(id, UserState.TRAINING_START_TIME);
    }

    public void viewTrainingEndTime(long id, Message currentMessage, String data) {
        String formattedStartTime = LocalTime.parse(data).format(DateTimeFormatter.ISO_LOCAL_TIME);

        trainingDataService.setStartTime(id, formattedStartTime);
        sendler.sendEndTimeMenu(id, trainingEndTime, currentMessage);
        userStateService.setUserState(id, UserState.TRAINING_END_TIME);
    }

    public void setTrainingEndTime(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        String formattedEndTime = LocalTime.parse(data).format(DateTimeFormatter.ISO_LOCAL_TIME);
        trainingDataService.setEndTime(id, formattedEndTime);
        if (trainingDataService.getCity(id).equals(online)) {
            sendler.sendAbort(id, trainingLink);
            userStateService.setUserState(id, UserState.TRAINING_LINK);
            sendler.callbackAnswer(update);
        } else createTraining(update);
    }

    public void setTrainingLink(Update update) {
        long id = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        if (!text.equals(Button.ABORT.getText())) {
            trainingDataService.setLink(id, text);
            createTraining(update);
        }
    }

    public void createTraining(Update update) {
        long id;
        if (update.hasCallbackQuery()) {
            id = update.getCallbackQuery().getMessage().getChatId();
        } else {
            id = update.getMessage().getChatId();
        }
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM");
        LocalDate localDate = LocalDate.parse(trainingDataService.getTrainingDate(id), inputFormatter);
        String date = outputFormatter.format(localDate);
        LocalTime startTime = LocalTime.parse(trainingDataService.getStartTime(id));
        LocalTime endTime = LocalTime.parse(trainingDataService.getEndTime(id));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedStartTime = startTime.format(timeFormatter);
        String formattedEndTime = endTime.format(timeFormatter);
        String description = trainingDataService.getTrainingDescription(id);
        User user = userRepository.findById(id).orElse(new User());
        String trainingName = trainingDataService.getTrainingName(id);
        String formattedTrainingName = date + " - " + trainingName;
        String coachName = user.getName();
        String coachLastname = user.getLastname();
        String finalDescription = date + "\n" + formattedStartTime + " - " + formattedEndTime + "\n" + "\n" + description + "\n" + "\n" + "Тренер:" + "\n" + coachLastname + " " + coachName;
        Trainings training = new Trainings();

        training.setName(formattedTrainingName);
        training.setDescription(finalDescription);
        training.setCategory(trainingDataService.getCategory(id));
        training.setCity(trainingDataService.getCity(id));
        training.setCreator(String.valueOf(id));
        training.setDate(Date.valueOf(trainingDataService.getTrainingDate(id)));
        training.setStart_time(Time.valueOf(trainingDataService.getStartTime(id)));
        training.setEnd_time(Time.valueOf(trainingDataService.getEndTime(id)));
        training.setMax_users(Integer.parseInt(trainingDataService.getMaxUsers(id)));
        training.setPic(trainingDataService.getPic(id));
        training.setActual(true);
        training.setArchive(false);
        if (trainingDataService.getCity(id).equals(online)) {
            training.setType(online);
            training.setLink(trainingDataService.getLink(id));
        } else {
            training.setType(offline);
        }
        trainingsRepository.save(training);
        logger.info("User: {} created new training. Training id: {}", id, training.getId());
        trainingDataService.clearTemplate(id);

        sendler.sendMainMenu(id, trainingCreateComplete);
        userStateService.setUserState(id, UserState.MAIN_MENU);
        if (update.hasCallbackQuery()) {
            sendler.callbackAnswer(update);
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
            if (userStateService.getUserState(id).equals(UserState.CREATED_TRAININGS)) {
                userStateService.setUserState(id, UserState.SELECT_COACH_TRAINING);
            } else {
                userStateService.setUserState(id, UserState.SELECT_ARCHIVE_TRAINING);
            }
        }
    }

    @Transactional
    public void archiveTraining(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        String trainingIdString = trainingDataService.getTrainingId(id);
        UUID trainingId = UUID.fromString(trainingIdString);
        trainingsRepository.archiveTraining(trainingId);
        logger.info("User: {} move training {} in archive", id, trainingId);
        sendler.sendTextMessage(id, trainingArchiveMessage);
        sendler.callbackAnswer(update);
    }

    @Transactional
    public void unArchiveTraining(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        String trainingIdString = trainingDataService.getTrainingId(id);
        UUID trainingId = UUID.fromString(trainingIdString);
        trainingsRepository.UnArchiveTraining(trainingId);
        logger.info("User: {} move training {} out archive", id, trainingId);
        sendler.sendTextMessage(id, unArchiveTrainingsMessage);
        sendler.callbackAnswer(update);
    }

    @Transactional
    public void deleteTraining(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        String trainingIdString = trainingDataService.getTrainingId(id);
        UUID trainingId = UUID.fromString(trainingIdString);
        trainingsRepository.deleteTraining(trainingId);
        sendler.sendTextMessage(id, trainingDeleteMessage);
        logger.info("User: {} deleting training {}", id, trainingId);
        if (notificationUser.canCall(id)) {
            notificationUser.notificationAbortTraining(trainingDataService.getTrainingId(id));
        }
        sendler.callbackAnswer(update);
    }

    public void archivedTrainings(long id, Message currentMessage) {
        sendler.sendArchivedTrainings(id, archiveTrainings, currentMessage);
        userStateService.setUserState(id, UserState.ARCHIVE_TRAININGS);
    }

    public void viewMarkUsersMenu(long id, Message currentMessage) {
        sendler.sendMarkUserList(id, markUser, currentMessage, trainingDataService.getTrainingId(id));
        userStateService.setUserState(id, UserState.MARK_USERS);
    }

    @Transactional
    public void markUsers(long id, Message currentMessage, String data) {
        UUID trainingUUID = UUID.fromString(trainingDataService.getTrainingId(id));
        usersToTrainingsRepository.markUserOnTraining(trainingUUID, Long.parseLong(data.substring(10)));
        sendler.sendMarkUserList(id, markUser, currentMessage, trainingDataService.getTrainingId(id));
        userStateService.setUserState(id, UserState.MARK_USERS);
    }

    public void checkUserData(long id, Message currentMessage, String data) {
        long userId = Long.parseLong(data.substring(12));
        User user = userRepository.findById(userId).orElseThrow();
        String userData = user.userData();
        sendler.sendCheckUserData(id, userInfo, currentMessage, userData);
        userStateService.setUserState(id, UserState.CHECK_USER_DATA);
    }

    public void feedbackRequest(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        String trainingId = trainingDataService.getTrainingId(id);
        List<Long> usersId = usersToTrainingsRepository.findPresenceUsers(UUID.fromString(trainingId));
        Trainings training = trainingsRepository.findById(UUID.fromString(trainingId)).orElseThrow(() -> new RuntimeException("Training not found"));
        String trainingName = training.getName();
        String message = String.format(trainingFeedbackUserMessage, trainingName);
        for (long userId : usersId) {
            try {
                sendler.sendFeedbackAnswer(userId, message, trainingId);
                logger.info("User: {} received a message about a feedback for training {}", userId, trainingId);
                Thread.sleep(400);
            } catch (InterruptedException e) {
                logger.warn("User: {} didn't get a request for feedback for training {}", userId, trainingId);
            }
        }
        sendler.sendTextMessage(id, trainingFeedbackCoachMessage);
        sendler.callbackAnswer(update);
    }

    public void viewUserList(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        StringBuilder userList = new StringBuilder();
        UUID trainingUUID = UUID.fromString(trainingDataService.getTrainingId(id));
        List<UsersToTrainings> userTrainingsList = usersToTrainingsRepository.findByTrainingsIdNoWaitingList(trainingUUID, true);
        List<UsersToTrainings> userWaitingList = usersToTrainingsRepository.findByTrainingsIdAndWaitingList(trainingUUID, true);
        userTrainingsList.sort(Comparator.comparing(ut -> ut.getUser().getLastname()));
        userWaitingList.sort(Comparator.comparing(ut -> ut.getUser().getLastname()));
        int i = 0;
        if (!userTrainingsList.isEmpty()) {
            for (UsersToTrainings ut : userTrainingsList) {
                i++;
                User user = ut.getUser();
                String name = user.getName();
                String lastname = user.getLastname();
                String userMessage = i + ". " + lastname + " " + name + "\n";
                userList.append(userMessage);
            }
            if (!userWaitingList.isEmpty()) {
                userList.append("\n").append(waitingListUsers).append("\n");
                i = 0;
                for (UsersToTrainings ut : userWaitingList) {
                    i++;
                    User user = ut.getUser();
                    String name = user.getName();
                    String lastname = user.getLastname();
                    String userMessage = i + ". " + lastname + " " + name + "\n";
                    userList.append(userMessage);
                }
            }
            sendler.sendTextMessage(id, String.valueOf(userList));

        } else {
            sendler.sendTextMessage(id, userListEmpty);
        }
        sendler.callbackAnswer(update);
    }

    public void viewPresenceList(long id, Message currentMessage) {
        sendler.sendUserListMenu(id, userList, currentMessage, trainingDataService.getTrainingId(id));
        userStateService.setUserState(id, UserState.FEEDBACK_USER_LIST);
    }

    public void viewFeedback(Update update) {
        long id =  update.getCallbackQuery().getMessage().getChatId();
        Message currentMessage = update.getCallbackQuery().getMessage();
        String data = update.getCallbackQuery().getData();
        long userId = Long.parseLong(data.substring(16));
        User user = userRepository.findById(userId).orElseThrow();
        String userData = user.userData();
        String feedback = usersToTrainingsRepository.viewFeedback(UUID.fromString(trainingDataService.getTrainingId(id)), userId);

        StringBuilder message = new StringBuilder();
        message.append(userData);
        message.append("\n");
        if (feedback != null && !feedback.isEmpty()) {
            message.append(feedback);
        } else {
            message.append(feedbackIsEmpty);
        }
        if (message.length() < 1024) {
            sendler.sendUserFeedback(id, infoAndFeedback, currentMessage, String.valueOf(message));
            userStateService.setUserState(id, UserState.FEEDBACK_VIEW);
        } else {
            sendler.sendTextMessage(id, String.valueOf(message));
            sendler.callbackAnswer(update);
        }

    }
}
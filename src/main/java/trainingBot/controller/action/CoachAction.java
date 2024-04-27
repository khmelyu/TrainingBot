package trainingBot.controller.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.model.entity.Trainings;
import trainingBot.model.entity.TrainingsList;
import trainingBot.model.entity.User;
import trainingBot.model.rep.TrainingsListRepository;
import trainingBot.model.rep.TrainingsRepository;
import trainingBot.model.rep.UserRepository;
import trainingBot.service.NotificationUser;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Component
@PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8")
public class CoachAction {
    private final Logger logger = LoggerFactory.getLogger(CoachAction.class);
    private final Sendler sendler;
    private final UserStateService userStateService;
    private final TrainingsListRepository trainingsListRepository;
    private final TrainingsRepository trainingsRepository;
    private final UserRepository userRepository;
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
    @Value("${online}")
    private String online;
    @Value("${offline}")
    private String offline;
    @Value("${archive.training}")
    private String trainingArchiveMessage;
    @Value("${training.delete.message}")
    private String trainingDeleteMessage;
    @Value("${archive.trainings}")
    private String archiveTrainings;


    @Autowired
    public CoachAction(@Lazy Sendler sendler, UserStateService userStateService, TrainingsListRepository trainingsListRepository, TrainingsRepository trainingsRepository, UserRepository userRepository, NotificationUser notificationUser) {
        this.sendler = sendler;
        this.userStateService = userStateService;
        this.trainingsListRepository = trainingsListRepository;
        this.trainingsRepository = trainingsRepository;
        this.userRepository = userRepository;
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
        userStateService.setCity(id, data);
        sendler.sendOnlineCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_ONLINE_TRAINING);
    }

    public void viewMoscowCategory(long id, Message currentMessage, String data) {
        userStateService.setCity(id, data);
        sendler.sendMoscowCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_MOSCOW_TRAINING);
    }

    public void viewSaintsPetersburgCategory(long id, Message currentMessage, String data) {
        userStateService.setCity(id, data);
        sendler.sendSaintPetersburgCategoryMenu(id, trainingCategory, currentMessage);
        userStateService.setUserState(id, UserState.CREATE_SAINT_PETERSBURG_TRAINING);
    }

    public void viewTrainingsOnCategory(long id, Message currentMessage, String data) {

        userStateService.setCategory(id, data);
        sendler.sendTrainingsOnCategory(id, trainingChoice, currentMessage, userStateService.getCity(id), data);
        userStateService.setUserState(id, UserState.TRAININGS_ON_CITY_FOR_CREATE);
    }

    public void viewCalendar(long id, Message currentMessage, String data) {

        Optional<TrainingsList> trainingOptional = trainingsListRepository.findById(Long.valueOf(data));

        if (trainingOptional.isPresent()) {
            TrainingsList training = trainingOptional.get();
            userStateService.setTrainingName(id, training.getName());
            userStateService.setTrainingDescription(id, training.getDescription());
            userStateService.setPic(id, training.getPic());
            userStateService.setMaxUsers(id, String.valueOf(training.getMax_users()));
        }

        LocalDate currentDate = LocalDate.now();
        sendler.sendCalendar(id, trainingDate, currentMessage, currentDate);
        userStateService.setUserState(id, UserState.CALENDAR);
    }

    public void viewChangeCalendar(long id, Message currentMessage, String data) {
        sendler.sendChangeCalendar(id, trainingDate, currentMessage, data);
    }

    public void viewTrainingStartTime(long id, Message currentMessage, String data) {

        userStateService.setTrainingDate(id, data);
        sendler.sendStartTimeMenu(id, trainingStartTime, currentMessage);
        userStateService.setUserState(id, UserState.TRAINING_START_TIME);
    }

    public void viewTrainingEndTime(long id, Message currentMessage, String data) {
        String formattedStartTime = LocalTime.parse(data).format(DateTimeFormatter.ISO_LOCAL_TIME);

        userStateService.setStartTime(id, formattedStartTime);
        sendler.sendEndTimeMenu(id, trainingEndTime, currentMessage);
        userStateService.setUserState(id, UserState.TRAINING_END_TIME);
    }

    public void setTrainingEndTime(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        String formattedEndTime = LocalTime.parse(data).format(DateTimeFormatter.ISO_LOCAL_TIME);
        userStateService.setEndTime(id, formattedEndTime);
        if (userStateService.getCity(id).equals(online)) {
            sendler.sendTextMessage(id, trainingLink);
            userStateService.setUserState(id, UserState.TRAINING_LINK);
            sendler.callbackAnswer(update);
        } else createTraining(update);
    }

    public void setTrainingLink(Update update) {
        long id = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        userStateService.setLink(id, text);
        createTraining(update);
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
        LocalDate localDate = LocalDate.parse(userStateService.getTrainingDate(id), inputFormatter);
        String date = outputFormatter.format(localDate);
        LocalTime startTime = LocalTime.parse(userStateService.getStartTime(id));
        LocalTime endTime = LocalTime.parse(userStateService.getEndTime(id));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedStartTime = startTime.format(timeFormatter);
        String formattedEndTime = endTime.format(timeFormatter);
        String description = userStateService.getTrainingDescription(id);
        User user = userRepository.findById(id).orElse(new User());
        String trainingName = userStateService.getTrainingName(id);
        String formattedTrainingName = date + " - " + trainingName;
        String coachName = user.getName();
        String coachLastname = user.getLastname();
        String finalDescription = date + "\n" + formattedStartTime + " - " + formattedEndTime + "\n" + "\n" + description + "\n" + "\n" + "Тренер:" + "\n" + coachLastname + " " + coachName;
        Trainings training = new Trainings();

        training.setName(formattedTrainingName);
        training.setDescription(finalDescription);
        training.setCategory(userStateService.getCategory(id));
        training.setCity(userStateService.getCity(id));
        training.setCreator(String.valueOf(id));
        training.setDate(Date.valueOf(userStateService.getTrainingDate(id)));
        training.setStart_time(Time.valueOf(userStateService.getStartTime(id)));
        training.setEnd_time(Time.valueOf(userStateService.getEndTime(id)));
        training.setMax_users(Integer.parseInt(userStateService.getMaxUsers(id)));
        training.setPic(userStateService.getPic(id));
        training.setActual(true);
        training.setArchive(false);
        if (userStateService.getCity(id).equals(online)) {
            training.setType(online);
            training.setLink(userStateService.getLink(id));
        } else {
            training.setType(offline);
        }
        trainingsRepository.save(training);
        logger.info("User: {} created new training. Training id: {}", id, training.getId());
        userStateService.clearTemplate(id);

        sendler.sendTextMessage(id, trainingCreateComplete);
        userStateService.setUserState(id, UserState.MAIN_MENU);
        if (update.hasCallbackQuery()) {
            sendler.callbackAnswer(update);
        }
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

    @Transactional
    public void archiveTraining(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        String trainingIdString = userStateService.getTrainingId(chatId);
        UUID trainingId = UUID.fromString(trainingIdString);
        trainingsRepository.archiveTraining(trainingId);
        sendler.sendTextMessage(chatId, trainingArchiveMessage);
        userStateService.setUserState(chatId, UserState.MAIN_MENU);
        sendler.callbackAnswer(update);
    }

    @Transactional
    public void deleteTraining(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        String trainingIdString = userStateService.getTrainingId(id);
        UUID trainingId = UUID.fromString(trainingIdString);
        trainingsRepository.deleteTraining(trainingId);
        sendler.sendTextMessage(id, trainingDeleteMessage);
        logger.info("User: {} deleting training {}", id, trainingId);
        if (notificationUser.canCall(id)) {
            notificationUser.notificationAbortTraining(userStateService.getTrainingId(id));
        }
        userStateService.setUserState(id, UserState.MAIN_MENU);
        sendler.callbackAnswer(update);
    }

    public void archivedTrainings(long id, Message currentMessage) {
        sendler.sendArchivedTrainings(id, archiveTrainings, currentMessage);
        userStateService.setUserState(id, UserState.ARCHIVE_TRAININGS);
    }
}
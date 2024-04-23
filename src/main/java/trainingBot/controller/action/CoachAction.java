package trainingBot.controller.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import trainingBot.controller.UpdateReceiver;
import trainingBot.core.TrainingBot;
import trainingBot.model.entity.Trainings;
import trainingBot.model.entity.TrainingsList;
import trainingBot.model.rep.TrainingsListRepository;
import trainingBot.model.rep.TrainingsRepository;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8")
public class CoachAction {
    private final Logger logger = LoggerFactory.getLogger(UpdateReceiver.class);
    private final TrainingBot trainingBot;
    private final Sendler sendler;
    private final UserStateService userStateService;
    private final TrainingsListRepository trainingsListRepository;
    private final TrainingsRepository trainingsRepository;

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


    @Autowired
    public CoachAction(
            TrainingBot trainingBot, @Lazy Sendler sendler,
            UserStateService userStateService,
            TrainingsListRepository trainingsListRepository,
            TrainingsRepository trainingsRepository
    ) {
        this.trainingBot = trainingBot;
        this.sendler = sendler;
        this.userStateService = userStateService;
        this.trainingsListRepository = trainingsListRepository;
        this.trainingsRepository = trainingsRepository;
    }

    public void coachAction(long id, Message currentMessage) {
        sendler.sendCoachMenu(id, coachMenu, currentMessage);
        userStateService.setUserState(id, UserState.COACH_MENU);
    }

    public void createdTrainings(long id, Message currentMessage) {
        sendler.sendCreatedTrainingsMenu(id, createdTrainings, currentMessage);
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
        } else createTraining(update);
    }

    public void setTrainingLink(Update update) {
        long id = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        userStateService.setLink(id, text);
        createTraining(update);
    }

    public void createTraining(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        Trainings training = new Trainings();
        training.setName(userStateService.getTrainingName(id));
        training.setDescription(userStateService.getTrainingDescription(id));
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

        String callbackQueryId = update.getCallbackQuery().getId();
        AnswerCallbackQuery answer = AnswerCallbackQuery.builder().callbackQueryId(callbackQueryId).text("").showAlert(false).build();
        try {
            trainingBot.execute(answer);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void reviewMyTraining(long id, Message currentMessage) {

        userStateService.setUserState(id, UserState.COACH_TRAINING_REVIEW);
    }
}
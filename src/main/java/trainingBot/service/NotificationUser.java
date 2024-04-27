package trainingBot.service;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import trainingBot.model.entity.Trainings;
import trainingBot.model.rep.TrainingsRepository;
import trainingBot.model.rep.UsersToTrainingsRepository;
import trainingBot.view.Sendler;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationUser {
    private final Logger logger = LoggerFactory.getLogger(NotificationUser.class);
    private final Sendler sendler;
    private final UsersToTrainingsRepository usersToTrainingsRepository;
    private final TrainingsRepository trainingsRepository;
    private final ConcurrentHashMap<Long, Long> lastCallTimes = new ConcurrentHashMap<>();

    @Value("${training.vacancy.notification.message}")
    private String trainingVacancy;
    @Value("${training.delete.notification.message}")
    private String trainingDelete;

    @Autowired
    public NotificationUser(Sendler sendler, UsersToTrainingsRepository usersToTrainingsRepository, TrainingsRepository trainingsRepository) {
        this.sendler = sendler;
        this.usersToTrainingsRepository = usersToTrainingsRepository;
        this.trainingsRepository = trainingsRepository;
    }


    public boolean canCall(long id) {
        long now = System.currentTimeMillis();
        Long lastCallTime = lastCallTimes.get(id);
        if (lastCallTime == null || now - lastCallTime > TimeUnit.SECONDS.toMillis(10)) {
            lastCallTimes.put(id, now);
            return true;
        }
        long secondsLeft = TimeUnit.MILLISECONDS.toSeconds(lastCallTime + TimeUnit.SECONDS.toMillis(10) - now);
        logger.info("Delay active. {} seconds left.", secondsLeft);
        return false;
    }


    @SneakyThrows
    public void notificationWaitingList(String trainingId) {
        List<Long> userId = usersToTrainingsRepository.findUserWaitingForTraining(UUID.fromString(trainingId));

        Trainings training = trainingsRepository.findById(UUID.fromString(trainingId)).orElseThrow(() -> new RuntimeException("Training not found"));
        String trainingName = training.getName();

        String message = String.format(trainingVacancy, trainingName);
        for (long id : userId) {
            sendler.sendTextMessage(id, message);
            logger.info("User: {} received a message about a vacancy for training {}", id, trainingId);
            Thread.sleep(400);
        }
    }

    @SneakyThrows
    public void notificationAbortTraining(String trainingId) {
        List<Long> userId = usersToTrainingsRepository.findUserSignedTraining(UUID.fromString(trainingId));

        Trainings training = trainingsRepository.findById(UUID.fromString(trainingId)).orElseThrow(() -> new RuntimeException("Training not found"));
        String trainingName = training.getName();

        String message = String.format(trainingDelete, trainingName);
        for (long id : userId) {
            sendler.sendTextMessage(id, message);
            logger.info("User: {} received a message deleting training {}", id, trainingId);
            Thread.sleep(400);
        }
    }

}
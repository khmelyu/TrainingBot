package trainingBot.service.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import trainingBot.model.entity.Marathon;
import trainingBot.model.entity.User;
import trainingBot.model.rep.MarathonRepository;
import trainingBot.model.rep.UserRepository;
import trainingBot.service.redis.MarathonDataService;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Button;
import trainingBot.view.Sendler;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8")
public class MarathonAction {

    private final Logger logger = LoggerFactory.getLogger(MarathonAction.class);

    private final UserRepository userRepository;
    private final Sendler sendler;
    private final UserStateService userStateService;
    private final MarathonRepository marathonRepository;
    private final MarathonDataService marathonDataService;

    @Value("${marathon.sex}")
    private String marathonSex;
    @Value("${marathon.hello.male}")
    private String marathonHelloMale;
    @Value("${marathon.hello.female}")
    private String marathonHelloFemale;
    @Value("${marathon.warmup.male}")
    private String marathonWarmUpMale;
    @Value("${marathon.warmup.female}")
    private String marathonWarmUpFemale;
    @Value("${marathon.coach}")
    private String marathonCoach;
    @Value("${marathon.nutritionist.text}")
    private String marathonNutritionistText;
    @Value("${marathon.nutritionist.link}")
    private String marathonNutritionistLink;
    @Value("${marathon.instruction.male}")
    private String marathonInstructionMale;
    @Value("${marathon.instruction.female}")
    private String marathonInstructionFemale;
    @Value("${marathon.instruction}")
    private String marathonInstruction;
    @Value("${marathon.warmup.time}")
    private String marathonWarmUpTime;
    @Value("${marathon.timezone}")
    private String marathonTimezone;
    @Value("${marathon.change.time}")
    private String marathonChangeTime;
    @Value("${marathon.helper}")
    private String marathonHelper;
    @Value("${marathon.finish}")
    private String marathonFinish;
    @Value("${marathon.check.male}")
    private String marathonCheckMale;
    @Value("${marathon.check.female}")
    private String marathonCheckFemale;
    @Value("${main.menu.message}")
    private String mainMenu;
    @Value("${marathon.abort}")
    private String marathonAbort;
    @Value("${marathon.abort.yes}")
    private String marathonAbortYes;
    @Value("${marathon.one.point}")
    private String marathonOnePoint;
    @Value("${marathon.two.points}")
    private String marathonTwoPoints;
    @Value("${marathon.three.points}")
    private String marathonThreePoints;


    @Autowired
    public MarathonAction(UserRepository userRepository, Sendler sendler, UserStateService userStateService, MarathonRepository marathonRepository, MarathonDataService marathonDataService) {
        this.userRepository = userRepository;
        this.sendler = sendler;
        this.userStateService = userStateService;
        this.marathonRepository = marathonRepository;
        this.marathonDataService = marathonDataService;
    }

    public void marathonUserData(long id) {
        userStateService.setUserState(id, UserState.MARATHON_DATA);
        User user = userRepository.findById(id).orElseThrow();
        String msg = user.userData();
        sendler.sendMarathonDataMenu(id, msg);
    }

    public void sexChoice(long id) {
        sendler.sendSexMenu(id, marathonSex);
        userStateService.setUserState(id, UserState.SEX_CHOICE);
    }

    public void helloMessage(long id, String text) {
        String sex;
        String msg;
        if (text.equals(Button.FEMALE.getText())) {
            sex = "female";
            msg = marathonHelloFemale;
        } else {
            sex = "male";
            msg = marathonHelloMale;
        }
        marathonDataService.setSex(id, sex);
        sendler.sendWarmUpMenu(id, msg);
        userStateService.setUserState(id, UserState.MARATHON);
    }

    public void warmUpMessage(long id) {
        String msg;
        if (marathonDataService.getSex(id).equals("female")) {
            msg = marathonWarmUpFemale;
        } else {
            msg = marathonWarmUpMale;
        }
        sendler.sendOkayMenu(id, msg);
    }

    public void coachMessage(long id) {
        sendler.sendCoach(id, marathonCoach);
    }

    public void nutritionistMessage(long id) {
        sendler.sendNutritionistLink(id, marathonNutritionistText, marathonNutritionistLink);
    }

    public void instructionMessage(long id) {
        String msg;
        if (marathonDataService.getSex(id).equals("female")) {
            msg = marathonInstructionFemale;
        } else {
            msg = marathonInstructionMale;
        }
        sendler.sendInstructionMenu(id, msg);
    }

    public void instruction2Message(long id) {
        sendler.sendInstruction2Menu(id, marathonInstruction);
    }

    public void warmUpTimeMessage(long id) {
        sendler.sendMarathonTime(id, marathonWarmUpTime);
        userStateService.setUserState(id, UserState.MARATHON_TIME_CHOICE);
    }

    public void timeZoneMessage(long id, String text) {
        marathonDataService.setTime(id, text);
        sendler.sendTimeZone(id, marathonTimezone);
        userStateService.setUserState(id, UserState.MARATHON_TIMEZONE_CHOICE);
    }

    public void signUp(long id, String text) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            int timeZone = Integer.parseInt(matcher.group());
            Marathon marathon = new Marathon();
            marathon.setId(id);
            marathon.setSex(marathonDataService.getSex(id));
            marathon.setTraining_time(marathonDataService.getTime(id));
            marathon.setTime_zone(timeZone);
            marathon.setPoints(0);
            marathon.setActual(true);
            marathonRepository.save(marathon);
            userStateService.setUserState(id, UserState.MARATHON);
            sendler.sendGoodButton(id, marathonChangeTime);
            logger.info("User: {} join on marathon", id);
        }
    }

    public void checkCanal(long id) {
        String msg;
        if (marathonDataService.getSex(id).equals("female")) {
            msg = marathonCheckFemale;
        } else {
            msg = marathonCheckMale;
        }
        sendler.sendDoneButton(id, msg);
    }

    public void helper(long id) {
        sendler.sendUnderstandButton(id, marathonHelper);
    }

    public void finish(long id) {
        sendler.sendByMondayButton(id, marathonFinish);
    }

    public void exit(long id) {
        sendler.sendMainMenu(id, mainMenu);
        marathonDataService.clearTemplate(id);
        userStateService.setUserState(id, UserState.MAIN_MENU);
    }

    public void points(long id) {
        String point = "";
        Optional<Marathon> optionalMarathon = marathonRepository.findById(id);
        if (optionalMarathon.isPresent()) {
            Marathon marathon = optionalMarathon.get();
            point = String.valueOf(marathon.getPoints());
        }
        sendler.sendTextMessage(id, point + " - \uD83C\uDF4D");
    }

    public void abort(long id) {
        sendler.sendAbortMarathon(id, marathonAbort);
    }

    @Transactional
    public void abortYes(long id) {
        marathonRepository.abort(id);
        sendler.sendMainMenu(id, marathonAbortYes);
    }

    @Transactional
    public void onePointPlus(long id, Message currentMessage) {
        sendler.deleteMessage(id, currentMessage);
        Optional<Marathon> optionalMarathon = marathonRepository.findById(id);
        if (optionalMarathon.isPresent()) {
            Marathon marathon = optionalMarathon.get();
            marathon.setPoints(marathon.getPoints() + 1);
            marathonRepository.save(marathon);
            sendler.sendTextMessage(id, marathonOnePoint);
        }
    }

    @Transactional
    public void twoPointsPlus(long id, Message currentMessage) {
        sendler.deleteMessage(id, currentMessage);
        Optional<Marathon> optionalMarathon = marathonRepository.findById(id);
        if (optionalMarathon.isPresent()) {
            Marathon marathon = optionalMarathon.get();
            marathon.setPoints(marathon.getPoints() + 2);
            marathonRepository.save(marathon);
            sendler.sendTextMessage(id, marathonTwoPoints);
        }
    }

    @Transactional
    public void threePointsPlus(long id, Message currentMessage) {
        sendler.deleteMessage(id, currentMessage);
        Optional<Marathon> optionalMarathon = marathonRepository.findById(id);
        if (optionalMarathon.isPresent()) {
            Marathon marathon = optionalMarathon.get();
            marathon.setPoints(marathon.getPoints() + 3);
            marathonRepository.save(marathon);
            sendler.sendTextMessage(id, marathonThreePoints);
        }
    }



    public void membersCount(long id) {
        int MESSAGE_LIMIT = 1000;
        List<Marathon> marathons = marathonRepository.findAll();

        StringBuilder message = new StringBuilder("Записано участников на марафон: " + marathons.size() + "\n\n");

        for (Marathon marathon : marathons) {
            Optional<User> userOptional = userRepository.findById(marathon.getId());

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                message.append("Имя: ").append(user.getName()).append("\n")
                        .append("Фамилия: ").append(user.getLastname()).append("\n")
                        .append("Часовой пояс: +").append(marathon.getTime_zone()).append("\n")
                        .append("Время тренировки: ").append(marathon.getTraining_time()).append("\n")
                        .append("Очки: ").append(marathon.getPoints()).append("\n")
                        .append("__________________").append("\n\n");

                if (message.length() > MESSAGE_LIMIT) {
                    sendler.sendTextMessage(id, message.toString());
                    message.setLength(0); // Очистка StringBuilder
                }
            }
        }

        if (message.length() > 0) {
            sendler.sendTextMessage(id, message.toString());
        }
    }


}

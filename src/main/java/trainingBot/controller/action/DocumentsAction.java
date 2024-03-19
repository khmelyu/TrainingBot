package trainingBot.controller.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

@Component
@PropertySources({@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8"), @PropertySource(value = "classpath:files.txt", encoding = "UTF-8")})
public class DocumentsAction {
    private Sendler sendler;

    private UserStateService userStateService;

    @Value("${worknote.message}")
    private String worknoteMessage;
    @Value("${worknote.trainee.link}")
    private String worknoteTrainee;
    @Value("${worknote.trainee.message}")
    private String worknoteTraineeText;
    @Value("${worknote.second.link}")
    private String worknoteSecond;
    @Value("${worknote.second.message}")
    private String worknoteSecondText;
    @Value("${worknote.third.link}")
    private String worknoteThird;
    @Value("${worknote.third.message}")
    private String worknoteThirdText;
    @Value("${worknote.fourth.link}")
    private String worknoteFourth;
    @Value("${worknote.fourth.message}")
    private String worknoteFourthText;
    @Value("${worknote.fifth.link}")
    private String worknoteFifth;
    @Value("${worknote.fifth.message}")
    private String worknoteFifthText;
    @Value("${pattern.salary.message}")
    private String patternSalaryText;
    @Value("${pattern.salary.link}")
    private String patternSalary;
    @Value("${pattern.inventory.message}")
    private String patternInventoryText;
    @Value("${pattern.inventory.link}")
    private String patternInventory;
    @Value("${pattern.statements.message}")
    private String patternStatementsText;
    @Value("${pattern.statements.link}")
    private String patternStatements;

    @Autowired
    public void setDependencies(@Lazy Sendler sendler, UserStateService userStateService) {
        this.sendler = sendler;
        this.userStateService = userStateService;
    }

    public void worknote(Long id) {
        sendler.sendWorkNotesMenu(id, worknoteMessage);
        userStateService.setUserState(id, UserState.WORK_NOTES_MENU);
    }

    public void traineeNote(Long id) {
        sendler.sendLink(id, worknoteTraineeText, worknoteTrainee);
    }

    public void secondLevelNote(Long id) {
        sendler.sendLink(id, worknoteSecondText, worknoteSecond);
    }

    public void thirdLevelNote(Long id) {
        sendler.sendLink(id, worknoteThirdText, worknoteThird);
    }

    public void fourthLevelNote(Long id) {
        sendler.sendLink(id, worknoteFourthText, worknoteFourth);
    }

    public void fifthLevelNote(Long id) {
        sendler.sendLink(id, worknoteFifthText, worknoteFifth);
    }

    public void patterns(Long id) {
        sendler.sendPatternsMenu(id, worknoteMessage);
        userStateService.setUserState(id, UserState.PATTERNS_MENU);
    }

    public void salary(Long id) {
        sendler.sendLink(id, patternSalaryText, patternSalary);
    }

    public void statements(Long id) {
        sendler.sendLink(id, patternStatementsText, patternStatements);
    }

    public void inventory(Long id) {
        sendler.sendLink(id, patternInventoryText, patternInventory);
    }
}




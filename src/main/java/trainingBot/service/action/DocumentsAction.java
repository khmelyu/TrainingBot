package trainingBot.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

@Service
@PropertySources({@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8"), @PropertySource(value = "classpath:files.txt", encoding = "UTF-8")})
public class DocumentsAction {
    private final Sendler sendler;

    private final UserStateService userStateService;

    @Value("${worknote.message}")
    private String worknoteMessage;
    @Value("${worknote.trainee.message}")
    private String worknoteTraineeText;
    @Value("${worknote.trainee.link}")
    private String worknoteTraineeLink;
    @Value("${worknote.second.message}")
    private String worknoteSecondText;
    @Value("${worknote.second.link}")
    private String worknoteSecondLink;
    @Value("${worknote.third.message}")
    private String worknoteThirdText;
    @Value("${worknote.third.link}")
    private String worknoteThirdLink;
    @Value("${worknote.fourth.message}")
    private String worknoteFourthText;
    @Value("${worknote.fourth.link}")
    private String worknoteFourthLink;
    @Value("${worknote.fifth.message}")
    private String worknoteFifthText;
    @Value("${worknote.fifth.link}")
    private String worknoteFifthLink;


    @Value("${pattern.message}")
    private String patternMessage;
    @Value("${pattern.salary.message}")
    private String patternSalaryText;
    @Value("${pattern.salary.link}")
    private String patternSalaryLink;
    @Value("${pattern.inventory.message}")
    private String patternInventoryText;
    @Value("${pattern.inventory.link}")
    private String patternInventoryLink;
    @Value("${pattern.statements.message}")
    private String patternStatementsText;
    @Value("${pattern.statements.link}")
    private String patternStatementsLink;

    @Value("${certificate.message}")
    private String certificateMessage;
    @Value("${certificate.tea.message}")
    private String certificateTeaText;
    @Value("${certificate.tea.link}")
    private String certificateTeaLink;
    @Value("${certificate.coffee.message}")
    private String certificateCoffeeText;
    @Value("${certificate.coffee.link}")
    private String certificateCoffeeLink;
    @Value("${certificate.accessories.message}")
    private String certificateAccessoriesText;
    @Value("${certificate.accessories.link}")
    private String certificateAccessoriesLink;
    @Value("${certificate.sweets.message}")
    private String certificateSweetsText;
    @Value("${certificate.sweets.link}")
    private String certificateSweetsLink;
    @Value("${certificate.package.message}")
    private String certificatePackageText;
    @Value("${certificate.package.link}")
    private String certificatePackageLink;
    @Value("${certificate.household.message}")
    private String certificateHouseholdText;
    @Value("${certificate.household.link}")
    private String certificateHouseholdLink;
    @Value("${certificate.chocostyle.message}")
    private String certificateChocostyleText;
    @Value("${certificate.chocostyle.link}")
    private String certificateChocostyleLink;

    @Value("${competitions.message}")
    private String competitionsMessage;
    @Value("${competitions.corporate.message}")
    private String competitionsCorporateText;
    @Value("${competitions.corporate.link}")
    private String competitionsCorporateLink;
    @Value("${competitions.consultant.message}")
    private String competitionsConsultantText;
    @Value("${competitions.consultant.link}")
    private String competitionsConsultantLink;
    @Value("${competitions.curator.message}")
    private String competitionsCuratorText;
    @Value("${competitions.curator.link}")
    private String competitionsCuratorLink;
    @Value("${competitions.manager.message}")
    private String competitionsManagerText;
    @Value("${competitions.manager.link}")
    private String competitionsManagerLink;


    @Autowired
    public DocumentsAction(@Lazy Sendler sendler, UserStateService userStateService) {
        this.sendler = sendler;
        this.userStateService = userStateService;
    }

    public void worknote(long id) {
        sendler.sendWorkNotesMenu(id, worknoteMessage);
        userStateService.setUserState(id, UserState.WORK_NOTES_MENU);
    }

    public void traineeNote(long id) {
        sendler.sendLink(id, worknoteTraineeText, worknoteTraineeLink);
    }

    public void secondLevelNote(long id) {
        sendler.sendLink(id, worknoteSecondText, worknoteSecondLink);
    }

    public void thirdLevelNote(long id) {
        sendler.sendLink(id, worknoteThirdText, worknoteThirdLink);
    }

    public void fourthLevelNote(long id) {
        sendler.sendLink(id, worknoteFourthText, worknoteFourthLink);
    }

    public void fifthLevelNote(long id) {
        sendler.sendLink(id, worknoteFifthText, worknoteFifthLink);
    }


    public void patterns(long id) {
        sendler.sendPatternsMenu(id, patternMessage);
        userStateService.setUserState(id, UserState.PATTERNS_MENU);
    }

    public void salary(long id) {
        sendler.sendLink(id, patternSalaryText, patternSalaryLink);
    }

    public void statements(long id) {
        sendler.sendLink(id, patternStatementsText, patternStatementsLink);
    }

    public void inventory(long id) {
        sendler.sendLink(id, patternInventoryText, patternInventoryLink);
    }


    public void certificates(long id) {
        sendler.sendCertificatesMenu(id, certificateMessage);
        userStateService.setUserState(id, UserState.CERTIFICATES_MENU);
    }

    public void tea(long id) {
        sendler.sendLink(id, certificateTeaText, certificateTeaLink);
    }

    public void coffee(long id) {
        sendler.sendLink(id, certificateCoffeeText, certificateCoffeeLink);
    }

    public void accessories(long id) {
        sendler.sendLink(id, certificateAccessoriesText, certificateAccessoriesLink);
    }

    public void sweets(long id) {
        sendler.sendLink(id, certificateSweetsText, certificateSweetsLink);
    }

    public void packages(long id) {
        sendler.sendLink(id, certificatePackageText, certificatePackageLink);
    }

    public void household(long id) {
        sendler.sendLink(id, certificateHouseholdText, certificateHouseholdLink);
    }

    public void chocostyle(long id) {
        sendler.sendLink(id, certificateChocostyleText, certificateChocostyleLink);
    }


    public void competitions(long id) {
        sendler.sendCompetitionsMenu(id, competitionsMessage);
        userStateService.setUserState(id, UserState.COMPETENCIES_MENU);
    }

    public void corporate(long id) {
        sendler.sendLink(id, competitionsCorporateText, competitionsCorporateLink);
    }

    public void consultant(long id) {
        sendler.sendLink(id, competitionsConsultantText, competitionsConsultantLink);
    }

    public void curator(long id) {
        sendler.sendLink(id, competitionsCuratorText, competitionsCuratorLink);
    }

    public void manager(long id) {
        sendler.sendLink(id, competitionsManagerText, competitionsManagerLink);
    }
}




package trainingBot.service.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import trainingBot.model.entity.Jumanji;
import trainingBot.model.entity.User;
import trainingBot.model.rep.JumanjiRepository;
import trainingBot.model.rep.UserRepository;
import trainingBot.view.Sendler;

@Service
@PropertySource(value = "classpath:jumanji.txt", encoding = "UTF-8")
public class JumanjiAction {

    @Value("${jumanji.second.message}")
    private String jumanjiSecondMessage;
    @Value("${jumanji.third.message}")
    private String jumanjiThirdMessage;
    @Value("${jumanji.team}")
    private String jumanjiTeam;
    @Value("${alligator}")
    private String alligator;
    @Value("${gorilla}")
    private String gorilla;
    @Value("${zebra}")
    private String zebra;
    @Value("${colibri}")
    private String colibri;
    @Value("${leopard}")
    private String leopard;
    @Value("${medved}")
    private String medved;
    @Value("${nosorog}")
    private String nosorog;
    @Value("${panda}")
    private String panda;
    @Value("${anaconda}")
    private String anaconda;
    @Value("${pantera}")
    private String pantera;

    private final Logger logger = LoggerFactory.getLogger(JumanjiAction.class);
    private final UserRepository userRepository;
    private final Sendler sendler;
    private final JumanjiRepository jumanjiRepository;

    private static int count = 0;

    public JumanjiAction(UserRepository userRepository, Sendler sendler, JumanjiRepository jumanjiRepository) {
        this.userRepository = userRepository;
        this.sendler = sendler;
        this.jumanjiRepository = jumanjiRepository;
    }

    public void jumanjiUserData(long id) {
        User user = userRepository.findById(id).orElseThrow();
        String msg = user.userData();
        sendler.sendMarathonDataMenu(id, msg);
    }

    public void jumanjiOk(long id) {
        sendler.sendJumanjiOk(id, jumanjiSecondMessage);
    }

    public void jumanjiChoiceTeam(long id) {


        String pic = null;
        String team = null;

        if (count > 9){
            count = 0;
        }

        if (count == 0){
            pic = alligator;
            team = "alligator";
        }
        if (count == 1){
            pic = gorilla;
            team = "gorilla";
        }
        if (count == 2){
            pic = zebra;
            team = "zebra";
        }
        if (count == 3){
            pic = colibri;
            team = "colibri";
        }
        if (count == 4){
            pic = leopard;
            team = "leopard";
        }
        if (count == 5){
            pic = medved;
            team = "medved";
        }
        if (count == 6){
            pic = nosorog;
            team = "nosorog";
        }
        if (count == 7){
            pic = panda;
            team = "panda";
        }
        if (count == 8){
            pic = anaconda;
            team = "anaconda";
        }
        if (count == 9){
            pic = pantera;
            team = "pantera";
        }

        Jumanji jumanji = jumanjiRepository.findById(id).orElse(new Jumanji());
        jumanji.setId(id);
        jumanji.setTeam(team);
        jumanjiRepository.save(jumanji);

        logger.info("User: {} join {} team", id, count);
        count++;

        sendler.sendJumanjiJoin(id, jumanjiTeam, pic);

    }
    public void jumanjiEnd(long id) {
        sendler.sendMainMenu(id, jumanjiThirdMessage);
    }

}

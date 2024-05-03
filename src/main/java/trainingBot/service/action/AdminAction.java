package trainingBot.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import trainingBot.model.rep.UserInfoRepository;
import trainingBot.view.Sendler;

@Service
@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8")
public class AdminAction {

    private final Sendler sendler;
    private final UserInfoRepository userInfoRepository;
    @Value("${admin.menu.message}")
    private String adminMenu;
    @Value("${admin.update.processed}")
    private String adminUpdateProcessed;

    @Autowired
    public AdminAction(Sendler sendler, UserInfoRepository userInfoRepository) {
        this.sendler = sendler;
        this.userInfoRepository = userInfoRepository;
    }

    public void adminAction(long id) {
        sendler.sendAdminMenu(id, adminMenu);
    }

    public void updateStat(long id) {
        long totalUpdateCount = userInfoRepository.sumUpdateCount();
        sendler.sendTextMessage(id, adminUpdateProcessed + "\n" + totalUpdateCount);
    }

}

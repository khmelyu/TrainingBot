package trainingBot.controller.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import trainingBot.model.rep.UserInfoRepository;
import trainingBot.view.Sendler;

@Component
@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8")
public class AdminAction {

    @Value("${admin.menu.message}")
    private String adminMenu;
    private Sendler sendler;
    private UserInfoRepository userInfoRepository;

    @Autowired
    public void setDependencies(@Lazy Sendler sendler, UserInfoRepository userInfoRepository) {
        this.sendler = sendler;
        this.userInfoRepository = userInfoRepository;
    }
    public void adminAction(long id){
        sendler.sendAdminMenu(id, adminMenu);
    }
    public void updateStat(long id){
        long totalUpdateCount = userInfoRepository.sumUpdateCount();
        sendler.sendTextMessage(id, "Всего было обработано запросов: " + totalUpdateCount);
    }

}

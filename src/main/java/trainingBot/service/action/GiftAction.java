package trainingBot.service.action;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import trainingBot.controller.UpdateReceiver;
import trainingBot.model.entity.User;
import trainingBot.model.rep.UserRepository;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

@Service
@PropertySources({@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8"), @PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8"), @PropertySource(value = "classpath:jumanji.txt", encoding = "UTF-8")})
@RequiredArgsConstructor
public class GiftAction {

    private final UserStateService userStateService;
    private final UserRepository userRepository;
    private final Sendler sendler;

    private final Logger logger = LoggerFactory.getLogger(GiftAction.class);

    @Value("${gift.name}")
    private String giftName;
    @Value("${gift.send}")
    private String giftSend;
    @Value("${gift.admin.id}")
    private String giftAdminId;

    public void orderGiftName(long id) {
        sendler.sendBack(id, giftName);
        userStateService.setUserState(id, UserState.GIFT_MENU);
    }

    public void orderGift(long id, String text) {
        userStateService.setUserState(id, UserState.MAIN_MENU);
        User user = userRepository.findById(id).orElse(new User());
        StringBuilder sb = new StringBuilder();

        sb.append("Заявка на подарок")
                .append("\n")
                .append(id)
                .append("\n")
                .append(user.getName())
                .append(" ")
                .append(user.getLastname())
                .append("\n")
                .append("\n")
                .append(text);
        sendler.sendTextMessage(Long.valueOf(giftAdminId), sb.toString());
        logger.info("User: {} send order. Gift: {}", id, text);
        sendler.sendMainMenu(id, giftSend);
    }
}
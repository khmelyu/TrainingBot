package trainingBot.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

@Service
@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8")
public class ContactSearchAction {
    private final Sendler sendler;
    private final UserStateService userStateService;

    @Value("${contact.search.no.message}")
    private String contactSearchNoMessage;
    @Value("${contact.search.product.quality}")
    private String contactSearchProductQuality;
    @Value("${contact.search.promotion}")
    private String contactSearchPromotion;
    @Value("${contact.search.tech}")
    private String contactSearchTech;
    @Value("${contact.search.chain.meeting}")
    private String contactSearchChainMeeting;
    @Value("${contact.search.takeaway}")
    private String contactSearchTakeaway;
    @Value("${contact.search.order}")
    private String contactSearchOrder;
    @Value("${contact.search.sick.leave.request}")
    private String contactSearchSickLeaveRequest;
    @Value("${contact.search.contract}")
    private String contactSearchContract;


    @Autowired
    public ContactSearchAction(Sendler sendler, UserStateService userStateService) {
        this.sendler = sendler;
        this.userStateService = userStateService;
    }

    public void noMenu(long id) {
        sendler.sendContactSearchNoMenu(id, contactSearchNoMessage);
        userStateService.setUserState(id, UserState.CONTACT_SEARCH_NO);
    }

    public void productQuality(long id) {
        sendler.sendTextMessage(id, contactSearchProductQuality);
    }

    public void promotion(long id) {
        sendler.sendTextMessage(id, contactSearchPromotion);
    }

    public void tech(long id) {
        sendler.sendTextMessage(id, contactSearchTech);
    }

    public void chainMeeting(long id) {
        sendler.sendTextMessage(id, contactSearchChainMeeting);
    }

    public void takeAway(long id) {
        sendler.sendTextMessage(id, contactSearchTakeaway);
    }

    public void sickLeaveRequest(long id) {
        sendler.sendTextMessage(id, contactSearchSickLeaveRequest);
    }

    public void order(long id) {
        sendler.sendTextMessage(id, contactSearchOrder);
    }

    public void contract(long id) {
        sendler.sendTextMessage(id, contactSearchContract);
    }
}

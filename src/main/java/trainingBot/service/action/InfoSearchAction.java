package trainingBot.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import trainingBot.view.Sendler;

@Service
@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8")
public class InfoSearchAction {
    private final Sendler sendler;

    @Value("${info.search.product.and.service}")
    private String infoSearchProductAndService;
    @Value("${info.search.management}")
    private String infoSearchManagement;
    @Value("${info.search.design}")
    private String infoSearchDesign;
    @Value("${info.search.it}")
    private String infoSearchIt;
    @Value("${info.search.corporate.culture}")
    private String infoSearchCorporateCulture;

    @Autowired
    public InfoSearchAction(Sendler sendler) {
        this.sendler = sendler;

    }

    public void productAndService(long id) {
        sendler.sendTextMessage(id, infoSearchProductAndService);
    }

    public void management(long id) {
        sendler.sendTextMessage(id, infoSearchManagement);
    }

    public void design(long id) {
        sendler.sendTextMessage(id, infoSearchDesign);
    }

    public void it(long id) {
        sendler.sendTextMessage(id, infoSearchIt);
    }

    public void corporateCulture(long id) {
        sendler.sendTextMessage(id, infoSearchCorporateCulture);
    }

}

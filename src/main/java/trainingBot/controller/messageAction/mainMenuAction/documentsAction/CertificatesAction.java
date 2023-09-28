package trainingBot.controller.messageAction.mainMenuAction.documentsAction;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.Sendler;
import trainingBot.controller.messageService.TriggerAction;
import trainingBot.core.triggers.TextTriggers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
public class CertificatesAction implements TriggerAction {
    private final Sendler sendler;
    private final Properties pattern = new Properties();


    public CertificatesAction(Sendler sendler) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("pattern.properties")) {
            assert inputStream != null;
            pattern.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.sendler = sendler;
    }

    @Override
    public void execute(Update update) {
        String tea = pattern.getProperty("certificates.tea.link");
        String coffee = pattern.getProperty("certificates.coffee.link");
        String wrap = pattern.getProperty("certificates.wrap.link");
        String candy = pattern.getProperty("certificates.candy.link");
        String accessories = pattern.getProperty("certificates.accessories.link");
        String householdGoods = pattern.getProperty("certificates.household.goods.link");
        String chokostyle = pattern.getProperty("certificates.chokostyle.link");

        if (update.getMessage().getText().equals(TextTriggers.CERTIFICATES.getTriggerText()))
            sendler.sendCertificatesMenu(update.getMessage().getChatId());
        if (update.getMessage().getText().equals(TextTriggers.TEA.getTriggerText()))
            sendler.sendLink(update.getMessage().getChatId(), "Сертификаты на чай",tea);
        if (update.getMessage().getText().equals(TextTriggers.COFFEE.getTriggerText()))
            sendler.sendLink(update.getMessage().getChatId(), "Сертификаты на кофе",coffee);
        if (update.getMessage().getText().equals(TextTriggers.WRAP.getTriggerText()))
            sendler.sendLink(update.getMessage().getChatId(), "Сертификаты на подарочную упаковку",wrap);
        if (update.getMessage().getText().equals(TextTriggers.CANDY.getTriggerText()))
            sendler.sendLink(update.getMessage().getChatId(), "Сертификаты на сладости",candy);
        if (update.getMessage().getText().equals(TextTriggers.ACCESSORIES.getTriggerText()))
            sendler.sendLink(update.getMessage().getChatId(), "Сертификаты на аксессуары",accessories);
        if (update.getMessage().getText().equals(TextTriggers.HOUSEHOLD_GOODS.getTriggerText()))
            sendler.sendLink(update.getMessage().getChatId(), "Сертификаты на хозтовары",householdGoods);
        if (update.getMessage().getText().equals(TextTriggers.CHOKOSTYLE.getTriggerText()))
            sendler.sendLink(update.getMessage().getChatId(), "Сертификаты Шокостайл",chokostyle);

    }
}

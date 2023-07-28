package trainingBot.view;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import trainingBot.core.TrainingBot;
import trainingBot.view.menu.ButtonMenu;

@Component
public class Sendler {

    private final TrainingBot trainingBot;

    public Sendler(TrainingBot trainingBot) {
        this.trainingBot = trainingBot;
    }

    public void sendTextMessage(Long who, String what) {
        SendMessage sm = SendMessage.builder().chatId(who.toString()).text(what).build();
        try {
            trainingBot.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendLink(Long who, String what, String url) {
        String messageText = "<a href=\"" + url + "\">" + what + "</a>";
        SendMessage sm = SendMessage.builder().chatId(who.toString()).text(messageText).parseMode(ParseMode.HTML).disableWebPagePreview(true).build();
        try {
            trainingBot.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessageWithKeyboard(Long who, String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sm = SendMessage.builder().chatId(who.toString()).text(text).replyMarkup(replyKeyboardMarkup).build();
        try {
            trainingBot.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMainMenu(Long who) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.mainMenu();
        sendMessageWithKeyboard(who, "Главное меню", replyKeyboardMarkup);
    }

    public void sendDocumentsMenu(Long who) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.documentsMenu();
        sendMessageWithKeyboard(who, "Выбери тип документа", replyKeyboardMarkup);
    }

    public void sendWorkNotesMenu(Long who) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.workNotesMenu();
        sendMessageWithKeyboard(who, "Выбери рабочую тетрадь", replyKeyboardMarkup);
    }
}

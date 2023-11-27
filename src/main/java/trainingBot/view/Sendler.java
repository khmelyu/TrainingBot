package trainingBot.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import trainingBot.core.TrainingBot;

@Component
public class Sendler {

    private TrainingBot trainingBot;


    @Autowired
    public void setTrainingBot(TrainingBot trainingBot) {
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


    private void sendMessageWithButton(Long who, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sm = SendMessage.builder().chatId(who.toString()).text("Главное меню").replyMarkup(replyKeyboardMarkup).build();
        try {
            trainingBot.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessageWithCallBack(Long who, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sm = SendMessage.builder().chatId(who.toString()).text("Главное меню").replyMarkup(inlineKeyboardMarkup).build();
        try {
            trainingBot.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMainMenu(Long who) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.mainMenu();
        sendMessageWithButton(who, replyKeyboardMarkup);
    }

    public void sendTrainingsMenu(Long who) {
        InlineKeyboardMarkup inlineKeyboardMarkup = InlineMenu.trainingsMenu();
        sendMessageWithCallBack(who, inlineKeyboardMarkup);
    }
}








//
//    public void sendDocumentsMenu(Long who) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.documentsMenu();
//        sendMessageWithKeyboard(who, "Выбери тип документа", replyKeyboardMarkup);
//    }
//
//    public void sendWorkNotesMenu(Long who) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.workNotesMenu();
//        sendMessageWithKeyboard(who, "Выбери уровень", replyKeyboardMarkup);
//    }
//
//    public void sendPatternsMenu(Long who) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.patternsMenu();
//        sendMessageWithKeyboard(who, "Выбери шаблон или заявление", replyKeyboardMarkup);
//    }
//
//    public void sendCertificatesMenu(Long who) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.certificatesMenu();
//        sendMessageWithKeyboard(who, "Выбери группу сертификатов", replyKeyboardMarkup);
//    }
//    public void sendCompetenciesMenu(Long who) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.competenciesMenu();
//        sendMessageWithKeyboard(who, "Выбери компетенции", replyKeyboardMarkup);
//    }


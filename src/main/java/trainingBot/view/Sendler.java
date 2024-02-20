package trainingBot.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
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


    private void sendMessageWithButton(Long who, String what, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sm = SendMessage.builder().chatId(who.toString()).text(what).replyMarkup(replyKeyboardMarkup).build();
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

    public void sendMainMenu(Long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.mainMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }
    public void sendMyDataMenu(Long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.myData();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendTrainingsMenu(Long who, String pic) {
        InlineKeyboardMarkup inlineKeyboardMarkup = InlineMenu.trainingsMenu();
        String photoFileId = pic;

        InputFile inputFile = new InputFile(photoFileId);

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(who.toString());
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        try {
            trainingBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}


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


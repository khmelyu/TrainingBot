package trainingBot.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import trainingBot.core.TrainingBot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

    public void sendFile(Long who, String what, String filePath) {
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);

            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(String.valueOf(who));
            sendDocument.setDocument(new InputFile(fileInputStream, file.getName()));
            sendDocument.setCaption(what);

            trainingBot.execute(sendDocument);

            fileInputStream.close();
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
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

    public void sendMainMenu(Long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.mainMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendMyDataMenu(Long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.myData();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendDocumentsMenu(Long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.documentsMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendWorkNotesMenu(Long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.worknoteMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendPatternsMenu(Long who, String what){
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.patternsMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }
    public void sendCertificatesMenu(Long who, String what){
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.certificatesMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }
    public void sendCompetitionsMenu(Long who, String what){
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.competitionsMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }


    public void sendTrainingsMenu(Long who, String pic) {
        InlineKeyboardMarkup inlineKeyboardMarkup = InlineMenu.trainingsMenu(who);

        InputFile inputFile = new InputFile(pic);

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
    public void updateTrainingsMenu(Long who, String pic, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = InlineMenu.trainingsMenu(who);
        try {
            EditMessageMedia editMessageMedia = EditMessageMedia.builder().chatId(who.toString()).messageId(currentMessage.getMessageId()).media(InputMediaPhoto.builder().media(pic).build()).replyMarkup(updatedKeyboard).build();
            trainingBot.execute(editMessageMedia);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCoachMenu(Long who, String pic, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = InlineMenu.coachMenu();
        try {
            EditMessageMedia editMessageMedia = EditMessageMedia.builder().chatId(who.toString()).messageId(currentMessage.getMessageId()).media(InputMediaPhoto.builder().media(pic).build()).replyMarkup(updatedKeyboard).build();
            trainingBot.execute(editMessageMedia);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}


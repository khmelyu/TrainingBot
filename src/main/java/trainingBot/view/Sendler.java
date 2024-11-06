package trainingBot.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import trainingBot.core.TrainingBot;
import trainingBot.service.Calendar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Sendler {
    private final TrainingBot trainingBot;
    private final Calendar calendar;
    private final CallbackMenu callbackMenu;

    @Autowired
    public Sendler(TrainingBot trainingBot, Calendar calendar, CallbackMenu callbackMenu) {
        this.trainingBot = trainingBot;
        this.calendar = calendar;
        this.callbackMenu = callbackMenu;
    }

    public void callbackAnswer(Update update) {
        String callbackQueryId = update.getCallbackQuery().getId();
        AnswerCallbackQuery answer = AnswerCallbackQuery.builder().callbackQueryId(callbackQueryId).text("").showAlert(false).build();
        try {
            trainingBot.execute(answer);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMessage(long chatId, Message currentMessage) {
        String chatIdString = String.valueOf(chatId);
        Integer messageId = currentMessage.getMessageId();
        try {
            trainingBot.execute(new DeleteMessage(chatIdString, messageId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendTextMessage(Long who, String what) {
        SendMessage sm = SendMessage.builder().chatId(who.toString()).text(what).disableWebPagePreview(true).build();
        try {
            trainingBot.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPhotoMessage(Long who, String text, String photo) {
        InputFile inputFile = new InputFile(photo);
        SendPhoto sendPhoto = new SendPhoto(who.toString(), inputFile);
        sendPhoto.setCaption(text);
        try {
            trainingBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendTextAndImage(Long who, String what, String photoId) {
        InputFile inputFile = new InputFile(photoId);
        SendPhoto sendPhoto = new SendPhoto(who.toString(), inputFile);

        sendPhoto.setCaption(what);
        try {
            trainingBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendTextAndImageAndButton(Long who, String what, String photoId, ReplyKeyboardMarkup replyKeyboardMarkup) {
        InputFile inputFile = new InputFile(photoId);
        SendPhoto sendPhoto = new SendPhoto(who.toString(), inputFile);
        sendPhoto.setCaption(what);
        sendPhoto.setReplyMarkup(replyKeyboardMarkup);
        try {
            sendPhoto.setCaption(what);
            trainingBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendJumanji(long who, String what, String pic) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.jumanjiMenu();
        sendTextAndImageAndButton(who, what, pic, replyKeyboardMarkup);
    }

    public void ambassadorMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.ambassadorMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }
    public void ambassadorTasksMenu(long who, String what) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.ambassadorTaskMenu(who);
//        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }
    public void ambassadorTestMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.ambassadorTestMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendTelephonyMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.telephonyMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendOtherDivisionMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.otherDivisionMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendDivisionMenu(long who, String what, String city) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.divisionMenu(city);
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }


    public void sendJumanjiOk(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.jumanjiOkMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendJumanjiJoin(long who, String what, String pic) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.jumanjiJoinTeam();
        sendTextAndImageAndButton(who, what, pic, replyKeyboardMarkup);
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

    public void sendLinkWithButton(Long who, String what, String url, ReplyKeyboardMarkup replyKeyboardMarkup) {
        String[] words = what.split("\\s+");
        List<String> wordsWithLink = new ArrayList<>();
        for (String word : words) {
            if (word.equals(words[words.length - 1])) {
                wordsWithLink.add("<a href=\"" + url + "\">" + word + "</a>");
            } else {
                wordsWithLink.add(word);
            }
        }
        String messageText = String.join(" ", wordsWithLink);
        SendMessage sm = SendMessage.builder().chatId(who.toString()).text(messageText).parseMode(ParseMode.HTML).disableWebPagePreview(true).replyMarkup(replyKeyboardMarkup).build();
        try {
            trainingBot.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessageWithButton(Long who, String what, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sm = SendMessage.builder().chatId(who.toString()).text(what).disableWebPagePreview(true).replyMarkup(replyKeyboardMarkup).build();
        try {
            trainingBot.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendBack(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.back();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendAbort(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.abort();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendMainMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.mainMenu(who);
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendGiftMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.giftMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendMarathonMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.marathonMenu(who);
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendMarathonDataMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.marathonData();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendSexMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.sexMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendWarmUpMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.warmUpMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendOkayMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.okayMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendCoach(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.coachLinkMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendNutritionistLink(long who, String what, String url) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.nutritionistLinkMenu();
        sendLinkWithButton(who, what, url, replyKeyboardMarkup);
    }

    public void sendInstructionMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.instructionMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendInstruction2Menu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.instruction2Menu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendMarathonTime(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.marathonTime();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendTimeZone(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.timeZone();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendGoodButton(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.goodButton();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendDoneButton(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.doneButton();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendUnderstandButton(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.understandButton();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendByMondayButton(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.byMondayButton();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendAbortMarathon(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.yesNoMarathonMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }


    public void sendAdminMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.adminMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendDocumentsMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.documentsMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendWorkNotesMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.worknoteMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendPatternsMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.patternsMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendCertificatesMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.certificatesMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendCompetitionsMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.competitionsMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendInfoSearchMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.infoSearchMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendContactSearchMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.contactSearchMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendContactSearchNoMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.contactSearchNoMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }

    public void sendContactSearchYesMenu(long who, String what) {
        ReplyKeyboardMarkup replyKeyboardMarkup = ButtonMenu.contactSearchYesMenu();
        sendMessageWithButton(who, what, replyKeyboardMarkup);
    }


    public void sendTrainingsMenu(Long who, String pic) {
        InlineKeyboardMarkup inlineKeyboardMarkup = callbackMenu.trainingsMenu(who);

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

    public void updateMenu(Long who, String pic, Message currentMessage, InlineKeyboardMarkup updatedKeyboard) {
        try {
            EditMessageMedia editMessageMedia = EditMessageMedia.builder().chatId(who.toString()).messageId(currentMessage.getMessageId()).media(InputMediaPhoto.builder().media(pic).build()).replyMarkup(updatedKeyboard).build();
            trainingBot.execute(editMessageMedia);

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateMenu(Long who, String pic, Message currentMessage, InlineKeyboardMarkup updatedKeyboard, String caption) {
        try {

            EditMessageMedia editMessageMedia = EditMessageMedia.builder().chatId(who.toString()).messageId(currentMessage.getMessageId()).media(InputMediaPhoto.builder().media(pic).caption(caption).build()).replyMarkup(updatedKeyboard).build();
            trainingBot.execute(editMessageMedia);


        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendAmbassadorMenu(Long who, String what, String pic) {
        InlineKeyboardMarkup inlineKeyboardMarkup = callbackMenu.ambassadorYes();

        InputFile inputFile = new InputFile(pic);

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(who.toString());
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setCaption(what);
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        try {
            trainingBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendAmbassadorYesMenu(long who, String pic, String caption, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.ambassadorExcellent();
        updateMenu(who, pic, currentMessage, updatedKeyboard, caption);
    }

    public void sendAmbassadorExcellentMenu(long who, String pic, String caption, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.ambassadorReady();
        updateMenu(who, pic, currentMessage, updatedKeyboard, caption);
    }

    public void sendAmbassadorReadyMenu(long who, String pic, String caption, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.ambassadorCreateOrJoin();
        updateMenu(who, pic, currentMessage, updatedKeyboard, caption);
    }

    public void sendAmbassadorTeamListMenu(long who, String pic, Message currentMessage, int page) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.ambassadorChoseTeamMenu(page);
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }



    public void sendMyDataMenu(Long who, String what, String pic) {
        InlineKeyboardMarkup inlineKeyboardMarkup = callbackMenu.myDataMenu();

        InputFile inputFile = new InputFile(pic);

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(who.toString());
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setCaption(what);
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        try {
            trainingBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMyDataMenu(Long who, String what) {
        InlineKeyboardMarkup inlineKeyboardMarkup = callbackMenu.myDataMenu();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(who.toString());
        sendMessage.setText(what);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            trainingBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTrainingsMenu(long who, String pic, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.trainingsMenu(who);
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendCoachMenu(long who, String pic, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.coachMenu();
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendCreateMenu(long who, String pic, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.createTrainingsMenu();
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendCityChoice(long who, String pic, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.cityChoiceMenu();
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendOnlineCategoryMenu(long who, String pic, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.onlineCategory(who);
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendMoscowCategoryMenu(long who, String pic, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.moscowCategory(who);
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendSaintPetersburgCategoryMenu(long who, String pic, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.saintPetersburgCategory(who);
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendTrainingsOnCategory(long who, String pic, Message currentMessage, String city, String category) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.trainingsOnCategoryMenu(city, category, who);
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendCalendar(long who, String pic, Message currentMessage, LocalDate currentDate, String caption) {
        InlineKeyboardMarkup updatedKeyboard = calendar.createCalendar(currentDate, who);
        updateMenu(who, pic, currentMessage, updatedKeyboard, caption);
    }

    public void sendChangeCalendar(long who, String pic, Message currentMessage, String data, String caption) {
        InlineKeyboardMarkup updatedKeyboard = calendar.changeMonth(data, who);
        updateMenu(who, pic, currentMessage, updatedKeyboard, caption);
    }

    public void sendStartTimeMenu(long who, String pic, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.startTimeMenu();
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendEndTimeMenu(long who, String pic, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.endTimeMenu();
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendTrainingInfo(long who, String pic, Message currentMessage, String caption) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.trainingInfoMenu(who);
        updateMenu(who, pic, currentMessage, updatedKeyboard, caption);
    }

    public void sendTrainingInfoFromCalendar(Long who, String pic, String caption) {
        InlineKeyboardMarkup inlineKeyboardMarkup = callbackMenu.trainingInfoMenu(who);
        InputFile inputFile = new InputFile(pic);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(who.toString());
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        sendPhoto.setCaption(caption);
        try {
            trainingBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCheckMyData(long who, String pic, Message currentMessage, String caption) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.checkDataMenu();
        updateMenu(who, pic, currentMessage, updatedKeyboard, caption);
    }

    public void sendCheckUserData(long who, String pic, Message currentMessage, String caption) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.backMenu();
        updateMenu(who, pic, currentMessage, updatedKeyboard, caption);
    }

    public void sendMyTrainings(long who, String pic, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.myTrainingsMenu(who);
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendArchivedTrainings(long who, String pic, Message currentMessage) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.archivedTrainingsMenu(who);
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendMarkUserList(long who, String pic, Message currentMessage, String trainingId) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.markUserMenu(trainingId);
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendFeedbackAnswer(Long who, String what, String trainingId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = callbackMenu.feedbackMenu(trainingId);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(who.toString());
        sendMessage.setText(what);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            trainingBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendUserListMenu(long who, String pic, Message currentMessage, String trainingId) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.userListMenu(trainingId);
        updateMenu(who, pic, currentMessage, updatedKeyboard);
    }

    public void sendUserFeedback(long who, String pic, Message currentMessage, String caption) {
        InlineKeyboardMarkup updatedKeyboard = callbackMenu.backMenu();
        updateMenu(who, pic, currentMessage, updatedKeyboard, caption);
    }
}
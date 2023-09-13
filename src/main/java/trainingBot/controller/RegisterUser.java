package trainingBot.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.model.entity.UserInfo;
import trainingBot.model.rep.UserInfoRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Component
public class RegisterUser {
    private static final Logger logger = LogManager.getLogger(RegisterUser.class);
    private static final LocalDateTime currentTime = LocalDateTime.now();
    private static UserInfoRepository userInfoRepository;

    public RegisterUser(UserInfoRepository userInfoRepository) {
        RegisterUser.userInfoRepository = userInfoRepository;
    }

    public static void registerUser(Update update) {
        var chatId = update.getMessage().getChatId();
        var chat = update.getMessage().getChat();

        Optional<UserInfo> existingUserOptional = userInfoRepository.findById(chatId);
        if (existingUserOptional.isEmpty()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(chatId);
            userInfo.setName(chat.getFirstName());
            userInfo.setLastname(chat.getLastName());
            userInfo.setUsername(chat.getUserName());
            userInfo.setLast_update(Timestamp.valueOf(currentTime));
            userInfoRepository.save(userInfo);
            logger.info("User: " + chatId + " добавлен в базу");
        } else {
            UserInfo existingUser = existingUserOptional.get();
            String oldName = existingUser.getName();
            String oldLastname = existingUser.getLastname();
            String oldUsername = existingUser.getUsername();

            if (!existingUser.equalsUserInfo(chat.getFirstName(), chat.getLastName(), chat.getUserName())) {
                if (!Objects.equals(existingUser.getName(), chat.getFirstName())) {
                    logger.info("User: " + chatId + " Изменил имя. Старое имя: " + oldName + "; Новое имя: " + chat.getFirstName());
                    existingUser.setName(chat.getFirstName());
                }

                if (!Objects.equals(existingUser.getLastname(), chat.getLastName())) {
                    logger.info("User: " + chatId + " Изменил фамилию. Старая фамилия: " + oldLastname + "; Новая фамилия: " + chat.getLastName());
                    existingUser.setLastname(chat.getLastName());
                }

                if (!Objects.equals(existingUser.getUsername(), chat.getUserName())) {
                    logger.info("User: " + chatId + ". Изменил юзернейм. Старый юзернейм: " + oldUsername + "; Новый юзернейм: " + chat.getUserName());
                    existingUser.setUsername(chat.getUserName());
                }

                existingUser.setLast_update(Timestamp.valueOf(currentTime));
                userInfoRepository.save(existingUser);
            }
        }
    }
}
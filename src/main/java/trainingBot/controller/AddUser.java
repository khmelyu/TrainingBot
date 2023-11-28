package trainingBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.model.entity.UserInfo;
import trainingBot.model.rep.UserInfoRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Component
public class AddUser {
    private UserInfoRepository userInfoRepository;

    @Autowired
    public void setUserInfoRepository(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public void registerUser(Update update) {
        LocalDateTime currentTime = LocalDateTime.now();
        Long id = update.getMessage().getChatId();
        Chat chat = update.getMessage().getChat();

        Optional<UserInfo> existingUserOptional = userInfoRepository.findById(id);

        if (existingUserOptional.isEmpty()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(id);
            userInfo.setName(chat.getFirstName());
            userInfo.setLastname(chat.getLastName());
            userInfo.setUsername(chat.getUserName());
            userInfo.setLast_update(Timestamp.valueOf(currentTime));
            userInfoRepository.save(userInfo);
        } else {
            UserInfo existingUser = existingUserOptional.get();
            existingUser.setLast_update(Timestamp.valueOf(currentTime));

            if (!existingUser.equalsUserInfo(chat.getFirstName(), chat.getLastName(), chat.getUserName())) {
                if (!Objects.equals(existingUser.getName(), chat.getFirstName())) {
                    existingUser.setName(chat.getFirstName());
                }

                if (!Objects.equals(existingUser.getLastname(), chat.getLastName())) {
                    existingUser.setLastname(chat.getLastName());
                }

                if (!Objects.equals(existingUser.getUsername(), chat.getUserName())) {
                    existingUser.setUsername(chat.getUserName());
                }
            }
            userInfoRepository.save(existingUser);
        }
    }
}

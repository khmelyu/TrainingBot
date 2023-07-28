package trainingBot.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import trainingBot.model.entity.User;
import trainingBot.model.rep.UserRepository;

@Component
public class Register {
    private static UserRepository userRepository;
    public Register(UserRepository userRepository) {
        Register.userRepository = userRepository;
    }
    public static void registerUser(Message message) {

        if (userRepository.findById(message.getChatId()).isEmpty()) {
            var chatId = message.getChatId();
            var chat = message.getChat();

            User user = new User();

            user.setId(chatId);
            user.setName(chat.getUserName());

            userRepository.save(user);
        }
    }
}

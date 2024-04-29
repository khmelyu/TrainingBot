package trainingBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainingBot.model.entity.User;
import trainingBot.model.entity.UsersToTrainings;
import trainingBot.model.rep.UsersToTrainingsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserListService {

    private final UsersToTrainingsRepository usersToTrainingsRepository;

    @Autowired
    public UserListService(UsersToTrainingsRepository usersToTrainingsRepository) {
        this.usersToTrainingsRepository = usersToTrainingsRepository;
    }


    public Map<String, String> markUserList(String trainingId) {
        UUID trainingUUID = UUID.fromString(trainingId);
        List<UsersToTrainings> userTrainingsList = usersToTrainingsRepository.findByTrainingsIdAndActual(trainingUUID, true);

        Map<String, String> userMap = new HashMap<>();

        for (UsersToTrainings ut : userTrainingsList) {
            User user = ut.getUser();
            String userId = String.valueOf(user.getId());
            String name = user.getName();
            String lastName = user.getLastname();
            boolean presence = ut.isPresence();

            String userInfo = lastName + " " + name.charAt(0) + ".";

            if (!presence) {
                StringBuilder sb = new StringBuilder();
                for (char c : userInfo.toCharArray()) {
                    sb.append(c).append('̶');
                }
                userInfo = sb.toString();
            }

            userMap.put(userId, userInfo);
        }

        return userMap;
    }
}

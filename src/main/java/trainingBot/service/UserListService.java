package trainingBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trainingBot.model.entity.Users;
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
            Users users = ut.getUser();
            String userId = String.valueOf(users.getId());
            String name = users.getName();
            String lastName = users.getLastname();
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

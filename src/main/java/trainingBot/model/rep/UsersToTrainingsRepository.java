package trainingBot.model.rep;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.Trainings;
import trainingBot.model.entity.User;
import trainingBot.model.entity.UsersToTrainings;

import java.util.List;
import java.util.UUID;

@Repository
public interface UsersToTrainingsRepository extends JpaRepository<UsersToTrainings, Long> {
    @Query("SELECT COUNT(u) FROM users_to_trainings u WHERE u.trainings = :trainings AND u.waiting_list = :waitingList AND u.actual = :actual")
    long countByTrainings(@Param("trainings") Trainings trainings, @Param("waitingList") boolean waitingList, @Param("actual") boolean actual);

    boolean existsByUserAndTrainings(User user, Trainings training);

    @Query("SELECT u.waiting_list FROM users_to_trainings u WHERE u.user = :user AND u.trainings = :trainings")
    Boolean isUserOnWaitingList(@Param("user") User user, @Param("trainings") Trainings trainings);

    @Query("SELECT u.actual FROM users_to_trainings u WHERE u.user = :user AND u.trainings = :trainings")
    Boolean isUserOnActual(@Param("user") User user, @Param("trainings") Trainings trainings);

    @Modifying
    @Query("update users_to_trainings u set u.waiting_list = false where u.user = :user and u.trainings = :trainings")
    void removeUserFromWaitingList(@Param("user") User user, @Param("trainings") Trainings trainings);

    @Modifying
    @Query("update users_to_trainings u set u.waiting_list = true, u.actual = true where u.user = :user and u.trainings = :trainings")
    void addUserFromWaitingList(@Param("user") User user, @Param("trainings") Trainings trainings);

    @Modifying
    @Query("update users_to_trainings u set u.actual = true where u.user = :user and u.trainings = :trainings")
    void reSignupUserFromTraining(@Param("user") User user, @Param("trainings") Trainings trainings);

    @Modifying
    @Query("update users_to_trainings u set u.actual = false where u.user = :user and u.trainings = :trainings")
    void abortUserFromTraining(@Param("user") User user, @Param("trainings") Trainings trainings);

    @Query("SELECT t FROM users_to_trainings u JOIN u.trainings t WHERE u.user.id = :userId AND u.actual = true AND t.actual = true AND t.archive = false AND u.waiting_list = false")
    List<Trainings> findByUserId(@Param("userId") Long userId);

    @Query("SELECT u.user.id FROM users_to_trainings u WHERE u.trainings.id = :trainingId AND u.waiting_list = true")
    List<Long> findUserWaitingForTraining(@Param("trainingId") UUID trainingId);

    @Query("SELECT u.user.id FROM users_to_trainings u WHERE u.trainings.id = :trainingId AND u.waiting_list = false AND u.actual = true")
    List<Long> findUserSignedTraining(@Param("trainingId") UUID trainingId);

}






package trainingBot.model.rep;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.Trainings;
import trainingBot.model.entity.User;
import trainingBot.model.entity.UsersToTrainings;

import java.sql.Timestamp;
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
    @Query("update users_to_trainings u set u.actual = false, u.abort_time = :abort_time where u.user = :user and u.trainings = :trainings")
    void abortUserFromTraining(@Param("user") User user, @Param("trainings") Trainings trainings, @Param("abort_time")Timestamp timestamp);


    @Query("SELECT t FROM users_to_trainings u JOIN u.trainings t WHERE u.user.id = :userId AND u.actual = true AND t.actual = true AND t.archive = false AND u.waiting_list = false AND (t.date > current_date OR (t.date = current_date AND t.start_time > current_time))")
    List<Trainings> findByUserId(@Param("userId") Long userId);

    @Query("SELECT u.user.id FROM users_to_trainings u WHERE u.trainings.id = :trainingId AND u.waiting_list = true")
    List<Long> findUserWaitingForTraining(@Param("trainingId") UUID trainingId);

    @Query("SELECT u.user.id FROM users_to_trainings u WHERE u.trainings.id = :trainingId AND u.waiting_list = false AND u.actual = true")
    List<Long> findUserSignedTraining(@Param("trainingId") UUID trainingId);

    @Query("SELECT ut FROM users_to_trainings ut WHERE ut.trainings.id = :trainingId AND ut.actual = :actual AND ut.waiting_list = false")
    List<UsersToTrainings> findByTrainingsIdAndActual(@Param("trainingId") UUID trainingId, @Param("actual") boolean actual);

    @Modifying
    @Query("update users_to_trainings u set u.presence = CASE WHEN u.presence = true THEN false ELSE true END where u.user.id = :userId and u.trainings.id = :trainingId")
    void markUserOnTraining(@Param("trainingId") UUID trainingId, @Param("userId") Long userId);

    @Query("SELECT ut FROM users_to_trainings ut WHERE ut.trainings.id = :trainingId AND ut.actual = :actual AND ut.waiting_list = false")
    List<UsersToTrainings> findByTrainingsIdNoWaitingList(@Param("trainingId") UUID trainingId, @Param("actual") boolean actual);

    @Query("SELECT ut FROM users_to_trainings ut WHERE ut.trainings.id = :trainingId AND ut.actual = :actual AND ut.waiting_list = true")
    List<UsersToTrainings> findByTrainingsIdAndWaitingList(@Param("trainingId") UUID trainingId, @Param("actual") boolean actual);

    @Query("SELECT u.user.id FROM users_to_trainings u WHERE u.trainings.id = :trainingId AND u.waiting_list = false AND u.actual = true AND u.presence = true")
    List<Long> findPresenceUsers(@Param("trainingId") UUID trainingId);
    @Modifying
    @Query("update users_to_trainings u set u.feedback = :feedback where u.user = :user and u.trainings = :trainings")
    void saveFeedback(@Param("feedback") String feedback, @Param("user") User user, @Param("trainings") Trainings trainings);
}








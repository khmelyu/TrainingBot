package trainingBot.view;

import lombok.Getter;
@Getter
public enum Callback {
    BACK("Назад", "Back"),
    ONLINE_TRAININGS("Онлайн тренинги", "OnlineTrainings"),
    OFFLINE_TRAININGS("Офлайн тренинги", "OfflineTrainings"),
    MY_TRAININGS("Мои тренинги", "MyTrainings"),
    COACH_MENU("Меню тренера", "CoachMenu"),
    CREATE_TRAININGS("Создать тренинг", "CreateTrainings"),
    ONLINE_TRAININGS_CREATE("Онлайн","OnlineTrainingsCreate"),
    OFFLINE_TRAININGS_CREATE("Офлайн","OfflineTrainingsCreate"),
    MOSCOW("Мск","Moscow"),
    SAINT_PETERSBURG("СПб","SaintPetersburg"),

    CREATED_TRAININGS("Созданные тренинги", "CreatedTrainings"),
    ARCHIVE_TRAININGS("Архив тренингов", "ArchiveTrainings");





    @Getter
    private final String callbackText;
    private final String callbackData;

    Callback(String callbackText, String callbackData) {
        this.callbackText = callbackText;
        this.callbackData = callbackData;
    }
}

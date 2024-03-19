package trainingBot.view;

import lombok.Getter;
@Getter
public enum Callback {
    BACK("Назад", "Back"),
    OFFLINE_TRAININGS("Оффлайн тренинги", "OfflineTrainings"),
    ONLINE_TRAININGS("Онлайн тренинги", "OnlineTrainings"),
    MY_TRAININGS("Мои тренинги", "MyTrainings"),
    COACH_MENU("Меню тренера", "CoachMenu"),
    ADMIN_MENU("Меню админа", "AdminMenu"),
    CREATE_TRAININGS("Создать тренинг", "CreateTrainings"),
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

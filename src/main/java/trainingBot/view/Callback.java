package trainingBot.view;

import lombok.Getter;

@Getter
public enum Callback {
    BACK("Назад", "BACK"),
    ONLINE_TRAININGS("Онлайн тренинги", "ONLINE_TRAININGS"),
    OFFLINE_TRAININGS("Офлайн тренинги", "OFFLINE_TRAININGS"),
    MY_TRAININGS("Мои тренинги", "MY_TRAININGS"),
    COACH_MENU("Меню тренера", "COACH_MENU"),
    CREATE_TRAININGS("Создать тренинг", "CREATE_TRAININGS"),
    ONLINE_TRAININGS_CREATE("Онлайн", "ONLINE_TRAININGS_CREATE"),
    OFFLINE_TRAININGS_CREATE("Офлайн", "OFFLINE_TRAININGS_CREATE"),
    MOSCOW("Мск", "MOSCOW"),
    SAINT_PETERSBURG("СПб", "SAINT_PETERSBURG"),
    CALENDAR("Календарь", "https://t.me/ReefCareBot/calendar"),

    CREATED_TRAININGS("Созданные тренинги", "CREATED_TRAININGS"),
    ARCHIVE_TRAININGS("Архив тренингов", "ARCHIVE_TRAININGS"),
    NEXT_MONTH("▶️", "NEXT_MONTH"),
    PREV_MONTH("◀️", "PREV_MONTH"),

    SIGN_UP("Записаться", "SIGN_UP"),
    YES("Да", "YES"),
    NO("Нет", "NO"),
    ABORT_SIGNUP("Отмена", "ABORT"),
    ABORTING("Отменить запись", "ABORTING"),

    DELETE_TRAINING("Удалить тренинг", "DELETE_TRAINING"),
    USERS_LIST("Список записавшихся", "USERS_LIST"),
    MARK_USERS("Отметить участников", "MARK_USERS"),
    FEEDBACK_REQUEST("Запросить ОС", "FEEDBACK_REQUEST"),
    IN_ARCHIVE("В архив", "IN_ARCHIVE"),
    OUT_ARCHIVE("Убрать из архива", "OUT_ARCHIVE"),
    FEEDBACK_VIEW("Участники и ОС", "FEEDBACK_VIEW"),
    SELECT_USER("SELECT_USER","SELECT_USER:"),
    USER_MARK("✅ / ❌", "USER_MARK:"),
    FEEDBACK_ANSWER("Оставить ОС", "FEEDBACK_ANSWER:"),
    SELECT_FEEDBACK("SELECT_FEEDBACK", "SELECT_FEEDBACK:");



    @Getter
    private final String callbackText;
    private final String callbackData;

    Callback(String callbackText, String callbackData) {
        this.callbackText = callbackText;
        this.callbackData = callbackData;
    }
}

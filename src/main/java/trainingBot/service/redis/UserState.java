package trainingBot.service.redis;

import lombok.Getter;

@Getter
public enum UserState {
    START,
    LOGIN,
    PASSWORD,

    SET_NAME,
    SET_LASTNAME,
    SET_PHONE,
    SET_CITY,
    SET_GALLERY,
    SET_RATE,

    MAIN_MENU,
    USER_DATA,
    DOCUMENTS_MENU,

    WORK_NOTES_MENU,
    PATTERNS_MENU,
    CERTIFICATES_MENU,
    COMPETENCIES_MENU,

    TRAININGS_MENU,

    COACH_MENU,
    CREATED_TRAININGS,
    CREATE_TRAINING,
    CREATE_ONLINE_TRAINING,
    CREATE_OFFLINE_TRAINING,
    CREATE_MOSCOW_TRAINING,
    CREATE_SAINT_PETERSBURG_TRAINING,
    TRAININGS_ON_CITY_FOR_CREATE,
    CALENDAR,
    TRAINING_START_TIME,
    TRAINING_END_TIME,
    TRAINING_LINK,

    ONLINE_TRAININGS,
    OFFLINE_TRAININGS,
    MOSCOW_TRAININGS,
    SAINT_PETERSBURG_TRAININGS,
    TRAININGS_ON_CITY
}

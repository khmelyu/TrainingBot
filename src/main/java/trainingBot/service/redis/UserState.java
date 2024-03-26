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
    CREATE_TRAINING,
    CREATE_ONLINE_TRAINING,
    CREATE_OFFLINE_TRAINING,
    MOSCOW,
    SAINT_PETERSBURG
}

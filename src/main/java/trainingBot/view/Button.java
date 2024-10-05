package trainingBot.view;

import lombok.Getter;

@Getter
public enum Button {
    BACK("Назад"),
    ABORT("Отмена"),
    MY_DATA("Мои данные"),
    FEEDBACK("Обратная связь"),
    TRAININGS("Тренинги"),
    DOCUMENTS("Документы"),
    CZ_SEARCH("Поиск на cantata-znaet.com"),
    TELEPHONY("Телефония"),
    INFO_SEARCH("Где искать информацию?"),
    ADMIN("Админ панель"),
    AMBASSADOR("Амбассадор"),

    MARATHON("За день до лета"),

    JUMANJI("Джуманджи"),

    MY_POINTS("Мои ананасики \uD83C\uDF4D"),

    MEMBERS("!участники"),

    SIGNUP("Участвовать"),
    MARATHON_ABORT("Отписаться"),
    MARATHON_FEEDBACK("Оставить ОС"),
    DATA_OK("Да, это я!"),
    MALE("МУЖ"),
    FEMALE("ЖЕН"),
    WARM_UP("Что за разминка?"),
    OKAY("Окей!!"),
    NICE_TO_MEET_YOU("Очень приятно"),
    HELLO_ALEX("Привет, Лёша!"),
    OF_COURSE("Конечно!"),
    DEAL("Договорились!"),
    GOOD("Хорошо!!!"),
    DONE("Готово!"),
    UNDERSTAND("Понятно!"),
    BY_MONDAY("До понедельника!"),
    MARATHON_YES("Да, я сдаюсь"),
    MARATHON_NO("Буду продолжать!"),

    GALLERY_SEARCH("Галерея"),
    OTHER_DIVISION_SEARCH("Другое подразделение"),

    READY("Я готов!"),
    FIND_TEAM("Найти своих"),
    JOIN_TEAM("Присоединиться к стае"),


    YES("Да"),
    NO("Нет"),

    GIFT_YES("Да!"),
    GIFT_NO("Я еще подумаю"),

    ITS_OK("Все верно"),
    CHANGE("Изменить"),

    UPDATE_STAT("Статистика запросов"),

    WORKNOTE("Рабочие тетради"),
    WORKNOTE_TRAINEE("Стажерская"),
    WORKNOTE_SECOND("2 уровень"),
    WORKNOTE_THIRD("3 уровень"),
    WORKNOTE_FOURTH("4 уровень"),

    CERTIFICATES("Сертификаты"),
    TEA("Чай"),
    COFFEE("Кофе"),
    ACCESSORIES("Аксессуары"),
    SWEETS("Сладости"),
    PACKAGE("ПУ"),
    HOUSEHOLD("Хоз товары"),
    CHOCOSTYLE("Шокостайл"),

    COMPETITIONS("Компетенции"),
    CORPORATE("Корпоративные компетенции"),
    CONSULTANT("Компетенции консультанта"),
    CURATOR("Компетенции куратора"),
    MANAGER("Компетенции управляющего"),

    PATTERNS("Шаблоны/заявления"),
    INVENTORY("Учеты"),
    SALARY("РСП для ЗП"),
    STATEMENTS("Заявления"),

    PRODUCT_AND_SERVICE("Продукт и сервис\uD83C\uDF75"),
    MANAGEMENT("Управление\uD83D\uDCC8"),
    DESIGN("Дизайн\uD83C\uDF80"),
    IT("IT\uD83D\uDCBB"),
    CORPORATE_CULTURE("Корпоративная культура\uD83D\uDC6B"),

    GALLERY("Галереи"),
    STAFFER("Сотрудника офиса"),

    PRODUCT_QUALITY("Качество продукта"),
    PROMOTION("Акции"),
    TECH("Техника"),
    CHAIN_MEETING("Собрание сети"),
    TAKEAWAY("Напитки с собой"),
    ORDER("Заказы/остатки"),
    SICK_LEAVE_REQUEST("Заявления на отпуск и больничный"),
    CONTRACT("Договоры/проверки");




    private final String text;

    Button(String text) {
        this.text = text;
    }

}

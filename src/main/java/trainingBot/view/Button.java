package trainingBot.view;

import lombok.Getter;

@Getter
public enum Button {
    BACK("Назад"),
    MY_DATA("Мои данные"),
    FEEDBACK("Обратная связь"),
    TRAININGS("Тренинги"),
    DOCUMENTS("Документы"),
    ADMIN("Админ панель"),

    ITS_OK("Все верно"),
    CHANGE("Изменить"),

    UPDATE_STAT("Статистика запросов"),

    WORKNOTE("Рабочие тетради"),
    WORKNOTE_TRAINEE("Стажерская"),
    WORKNOTE_SECOND("2 уровень"),
    WORKNOTE_THIRD("3 уровень"),
    WORKNOTE_FOURTH("4 уровень"),
    WORKNOTE_FIFTH("5 уровень"),

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
    STATEMENTS("Заявления");



    private final String text;

    Button(String text) {
        this.text = text;
    }

}

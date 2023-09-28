package trainingBot.core.triggers;


public enum TextTriggers {
    START("/start"),
    BACK("Назад"),

    //Main menu//

    SEARCH_CZ("Поиск на cantata-znaet"),
    SEARCH_C("Поиск на cantata.ru"),
    SEARCH_INFO("Где искать информацию?"),
    SEARCH_CONTACTS("Поиск контактов"),
    TRAININGS("Тренинги"),
    DOCUMENTS("Документы"),
    FEEDBACK("Обратная связь"),

    //Documents menu//

    WORK_NOTE("Рабочие тетради"),
    PATTERNS("Шаблоны/заявления"),
    CERTIFICATES("Сертификаты"),
    COMPETENCIES("Компетенции"),

    //WorkNote menu//

    INTERN("Стажерская"),
    SECOND_LEVEL("2 уровень"),
    THIRD_LEVEL("3 уровень"),
    FOURTH_LEVEL("4 уровень"),
    FIFTH_LEVEL("5 уровень"),

    //Patterns menu//

    ACCOUNTIGS("Учеты"),
    SCHEDULE("РСП для ЗП"),
    DECLARATION("Заявления"),

    //Certificates menu//

    TEA("Чай"),
    COFFEE("Кофе"),
    PACKAGE("ПУ"),
    CANDY("Cладости"),
    ACCESSORIES("Аксессуары"),
    HOUSEHOLD_GOODS("Хозтовары"),
    CHOKOSTYLE("Шокостайл");






    private final String triggerText;

    TextTriggers(String triggerText) {
        this.triggerText = triggerText;
    }

    public String getTriggerText() {
        return triggerText;
    }
}
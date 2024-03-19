package trainingBot.view;

public enum Button {
    BACK("Назад"),
    MY_DATA("Мои данные"),
    FEEDBACK("Обратная связь"),
    TRAININGS("Треннинги"),
    DOCUMENTS("Документы"),
    ITS_OK("Все верно"),
    CHANGE("Изменить"),
    WORKNOTE("Рабочие тетради"),
    WORKNOTE_TRAINEE("Стажерская"),
    WORKNOTE_SECOND("2 уровень"),
    WORKNOTE_THIRD("3 уровень"),
    WORKNOTE_FOURTH("4 уровень"),
    WORKNOTE_FIFTH("5 уровень"),
    CERTIFICATES("Сертификаты"),
    COMPETITIONS("Компетенции"),
    PATTERNS("Шаблоны/заявления"),
    INVENTORY("Учеты"),
    SALARY("РСП для ЗП"),
    STATEMENTS("Заявления");




    private final String text;

    Button(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }

}

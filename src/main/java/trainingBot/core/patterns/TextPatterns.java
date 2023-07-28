package trainingBot.core.patterns;

public enum TextPatterns {

    FEEDBACK("""
            Привет!\uD83D\uDE09
            Я Катя Хмелинина, менеджер по обучению и автор этого бота.\s
            Если у тебя есть вопросы или пожелания по его работе и наполнению, напиши мне!\s
            @katerina_khmelinina"""),
    INTERN("Стажерская рабочая тетрадь");

    private final String textPattern;

    TextPatterns(String textPattern) {
        this.textPattern = textPattern;
    }

    public String getTriggerText() {
        return textPattern;
    }
}
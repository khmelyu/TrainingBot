package trainingBot.core.patterns;

public enum LinkPatterns {

    INTERN("https://disk.yandex.ru/i/4teWvsVGjP01nQ");


    private final String linkPattern;

    LinkPatterns(String linkPattern) {
        this.linkPattern = linkPattern;
    }

    public String getTriggerText() {
        return linkPattern;
    }
}
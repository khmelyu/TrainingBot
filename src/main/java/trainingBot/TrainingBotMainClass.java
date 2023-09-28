package trainingBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrainingBotMainClass {
    public static void main(String[] args) {
        System.setProperty("user.timezone", "Europe/Moscow");
        SpringApplication.run(TrainingBotMainClass.class, args);
    }
}

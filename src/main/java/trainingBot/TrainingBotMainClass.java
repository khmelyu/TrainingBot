package trainingBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "trainingBot.model.entity")
public class TrainingBotMainClass {
    public static void main(String[] args) {
        System.setProperty("user.timezone", "Europe/London");
        SpringApplication.run(TrainingBotMainClass.class, args);
    }

}
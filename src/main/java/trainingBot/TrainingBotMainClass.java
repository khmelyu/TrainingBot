package trainingBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class TrainingBotMainClass {
    public static void main(String[] args) {
        System.setProperty("user.timezone", "Europe/Moscow");
        System.setProperty("current.date", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        SpringApplication.run(TrainingBotMainClass.class, args);
    }
}

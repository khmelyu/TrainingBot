package trainingBot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebhookController {
    private final Logger logger = LoggerFactory.getLogger(UpdateReceiver.class);
    private final UpdateReceiver updateReceiver;

    @Autowired
    public WebhookController(UpdateReceiver updateReceiver) {
        this.updateReceiver = updateReceiver;
    }

    @PostMapping
    public void onUpdateReceived(@RequestBody Update update) {
        updateReceiver.handle(update);
    }

    @PostMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestBody CallbackRequest request) {
        logger.info("Sent a callback: {} from calendar", request.trainingId);
        return ResponseEntity.ok("Success baby");
    }

    public record CallbackRequest(String trainingId) {
    }
}
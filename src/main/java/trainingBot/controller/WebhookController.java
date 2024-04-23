package trainingBot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebhookController {
    private final UpdateReceiver updateReceiver;

    public WebhookController(UpdateReceiver updateReceiver) {
        this.updateReceiver = updateReceiver;
    }

    @PostMapping
    public void onUpdateReceived(@RequestBody Update update ) {
        updateReceiver.handle(update);
    }
}
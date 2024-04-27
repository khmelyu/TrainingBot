package trainingBot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.controller.commandController.CallbackCommandController;

@RestController
public class WebhookController {
    private final Logger logger = LoggerFactory.getLogger(WebhookController.class);
    private final UpdateReceiver updateReceiver;
    private final CallbackCommandController callBackCommandController;

    @Autowired
    public WebhookController(UpdateReceiver updateReceiver, CallbackCommandController callBackCommandController) {
        this.updateReceiver = updateReceiver;
        this.callBackCommandController = callBackCommandController;
    }

    @PostMapping
    public void onUpdateReceived(@RequestBody Update update) {
        updateReceiver.handle(update);
    }

    @PostMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestBody CallbackRequest request) {
        logger.info("User: {} sent callback {} from calendar", request.userId, request.trainingId);
        callBackCommandController.handleCallbackRequest(request);
        return ResponseEntity.ok("Success baby");
    }

    public record CallbackRequest(String trainingId, long userId) {
    }
}
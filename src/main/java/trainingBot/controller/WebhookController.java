package trainingBot.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
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
    private final Counter webhookRequestsCounter;

    @Autowired
    public WebhookController(UpdateReceiver updateReceiver, CallbackCommandController callBackCommandController, MeterRegistry meterRegistry) {
        this.updateReceiver = updateReceiver;
        this.callBackCommandController = callBackCommandController;
        this.webhookRequestsCounter = meterRegistry.counter("webhook_requests");
    }

    @PostMapping
    public void onUpdateReceived(@RequestBody Update update) {
        try {
            updateReceiver.handle(update);

            webhookRequestsCounter.increment();
        } catch (Exception e) {
            logger.error("Error processing update: {}", update, e);
        }
    }


    @PostMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestBody CallbackRequest request) {
        logger.info("User: {} sent callback {} from calendar", request.userId, request.trainingId);
        callBackCommandController.handleCallbackRequest(request);

        webhookRequestsCounter.increment();
        return ResponseEntity.ok("Success baby");
    }

    public record CallbackRequest(String trainingId, long userId) {
    }
}

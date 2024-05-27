package trainingBot.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    private final Counter webhookRequestsCounter;

    public MetricsController(MeterRegistry meterRegistry) {
        this.webhookRequestsCounter = Counter.builder("webhook_requests")
                .description("Counter for webhook requests")
                .register(meterRegistry);
    }

    @GetMapping("/metrics")
    public String getMetrics() {

        return "# HELP webhook_requests Total count of webhook requests\n" +
                "# TYPE webhook_requests counter\n" +
                "webhook_requests " + webhookRequestsCounter.count() + "\n" +
                "# EOF";
    }
}
package events.sourcing.example.events;

import org.springframework.context.ApplicationEvent;

public class MoneyCreditedEvent extends ApplicationEvent {

    public final String id;

    public final double creditAmount;

    public final String currency;

    public MoneyCreditedEvent(String source, String id, double creditAmount, String currency) {
        super(source);
        this.id = id;
        this.creditAmount = creditAmount;
        this.currency = currency;
    }
}


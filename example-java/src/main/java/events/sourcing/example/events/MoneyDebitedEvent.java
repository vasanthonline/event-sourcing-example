package events.sourcing.example.events;

import org.springframework.context.ApplicationEvent;

public class MoneyDebitedEvent extends ApplicationEvent {

    public final String id;

    public final double debitAmount;

    public final String currency;

    public MoneyDebitedEvent(String source, String id, double debitAmount, String currency) {
        super(source);
        this.id = id;
        this.debitAmount = debitAmount;
        this.currency = currency;
    }
}



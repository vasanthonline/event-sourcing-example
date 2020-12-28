package events.sourcing.example.events;

import org.springframework.context.ApplicationEvent;

public class AccountCreatedEvent extends ApplicationEvent {

    public final String id;

    public final double accountBalance;

    public final String currency;

    public AccountCreatedEvent(String source, String id, double accountBalance, String currency) {
        super(source);
        this.id = id;
        this.accountBalance = accountBalance;
        this.currency = currency;
    }
}

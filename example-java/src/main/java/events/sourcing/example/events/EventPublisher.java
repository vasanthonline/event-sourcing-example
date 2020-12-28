package events.sourcing.example.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
     
    public void publish(AccountCreatedEvent accountCreatedEvent){    
        publisher.publishEvent(accountCreatedEvent);
    }

    public void publish(MoneyCreditedEvent moneyCreditedEvent){    
        publisher.publishEvent(moneyCreditedEvent);
    }

    public void publish(MoneyDebitedEvent moneyDebitedEvent){    
        publisher.publishEvent(moneyDebitedEvent);
    }
}

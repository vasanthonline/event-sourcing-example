package events.sourcing.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

import events.sourcing.example.dto.AccountCreateDTO;
import events.sourcing.example.dto.AccountDTO;
import events.sourcing.example.dto.MoneyCreditDTO;
import events.sourcing.example.dto.MoneyDebitDTO;
import events.sourcing.example.events.AccountCreatedEvent;
import events.sourcing.example.events.EventPublisher;
import events.sourcing.example.events.MoneyCreditedEvent;
import events.sourcing.example.events.MoneyDebitedEvent;

@Service
public class AccountsService {

    @Autowired
    private EventPublisher eventPublisher;

    private Map<String, List<ApplicationEvent>> eventStore = new HashMap<String, List<ApplicationEvent>>();

    public enum Status {
        CREATED, ACTIVATED, HOLD
    }

    public enum EVENT_NAME {
        ACCOUNT_CREATION, MONEY_CREDIT, MONEY_DEBIT
    }

    public String createAccount(AccountCreateDTO accountCreateDTO) {
        String accountId = UUID.randomUUID().toString();
        AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent(String.valueOf(EVENT_NAME.ACCOUNT_CREATION), accountId, accountCreateDTO.getStartingBalance(), accountCreateDTO.getCurrency());
        appendToEvents(accountId, accountCreatedEvent);
        eventPublisher.publish(accountCreatedEvent);
        return accountId;
    }

    public Boolean creditMoneyToAccount(String accountId, MoneyCreditDTO moneyCreditDTO) {
        MoneyCreditedEvent moneyCreditedEvent = new MoneyCreditedEvent(String.valueOf(EVENT_NAME.MONEY_CREDIT), accountId, moneyCreditDTO.getCreditAmount(), moneyCreditDTO.getCurrency());
        appendToEvents(accountId, moneyCreditedEvent);
        eventPublisher.publish(moneyCreditedEvent);
        return true;
    }

    public Boolean debitMoneyFromAccount(String accountId, MoneyDebitDTO moneyDebitDTO) {
        MoneyDebitedEvent moneyDebitedEvent = new MoneyDebitedEvent(String.valueOf(EVENT_NAME.MONEY_DEBIT), accountId, moneyDebitDTO.getDebitAmount(), moneyDebitDTO.getCurrency());
        appendToEvents(accountId, moneyDebitedEvent);
        eventPublisher.publish(moneyDebitedEvent);
        return true;
    }

    public void appendToEvents(String accountId, ApplicationEvent event) {
        List<ApplicationEvent> eventsForAccount = eventStore.getOrDefault(accountId, new ArrayList<ApplicationEvent>());
        eventsForAccount.add(event);
        eventStore.put(accountId, eventsForAccount);
    }

    public AccountDTO getAccount(String accountId) {
        List<ApplicationEvent> eventsForAccount = eventStore.getOrDefault(accountId, new ArrayList<ApplicationEvent>());
        AccountDTO accountDTO = new AccountDTO();
        return eventsForAccount.stream().reduce(accountDTO, (AccountDTO accountDTOAcc, ApplicationEvent event) -> {
            if(event instanceof AccountCreatedEvent) {
                AccountCreatedEvent accountCreatedEvent = (AccountCreatedEvent) event;
                accountDTOAcc.setId(accountCreatedEvent.id);
                accountDTOAcc.setAccountBalance(accountCreatedEvent.accountBalance);
                accountDTOAcc.setCurrency(accountCreatedEvent.currency);
                accountDTOAcc.setStatus(String.valueOf(Status.ACTIVATED));
                return accountDTOAcc;
            }else if(event instanceof MoneyCreditedEvent) {
                MoneyCreditedEvent moneyCreditedEvent = (MoneyCreditedEvent) event;
                accountDTOAcc.setAccountBalance(accountDTOAcc.getAccountBalance() + moneyCreditedEvent.creditAmount);
                return accountDTOAcc;
            }else if(event instanceof MoneyDebitedEvent) {
                MoneyDebitedEvent moneyDebitedEvent = (MoneyDebitedEvent) event;
                accountDTOAcc.setAccountBalance(accountDTOAcc.getAccountBalance() - moneyDebitedEvent.debitAmount);
                return accountDTOAcc;
            }
            return accountDTOAcc;
        }, (AccountDTO accountDTO1, AccountDTO accountDTO2) -> {
            return accountDTO1;
        });
    }

    public List<ApplicationEvent> listEventsForAccount(String accountId) {
        List<ApplicationEvent> eventsForAccount = eventStore.getOrDefault(accountId, new ArrayList<ApplicationEvent>());
        return eventsForAccount;
    }

}

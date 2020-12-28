package events.sourcing.example.controller;

import java.util.List;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.*;

import events.sourcing.example.dto.AccountCreateDTO;
import events.sourcing.example.dto.AccountDTO;
import events.sourcing.example.dto.MoneyCreditDTO;
import events.sourcing.example.dto.MoneyDebitDTO;
import events.sourcing.example.service.AccountsService;

@RestController
@RequestMapping(value = "/bank-accounts")
public class AccountsController {

    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @PostMapping
    public String createAccount(@RequestBody AccountCreateDTO accountCreateDTO){
        return accountsService.createAccount(accountCreateDTO);
    }

    @PutMapping(value = "/credits/{accountNumber}")
    public Boolean creditMoneyToAccount(@PathVariable(value = "accountNumber") String accountNumber,
                                                          @RequestBody MoneyCreditDTO moneyCreditDTO){
        return accountsService.creditMoneyToAccount(accountNumber, moneyCreditDTO);
    }

    @PutMapping(value = "/debits/{accountNumber}")
    public Boolean debitMoneyFromAccount(@PathVariable(value = "accountNumber") String accountNumber,
                                                           @RequestBody MoneyDebitDTO moneyDebitDTO){
        return accountsService.debitMoneyFromAccount(accountNumber, moneyDebitDTO);
    }

    @GetMapping("/{accountNumber}")
    public AccountDTO getAccount(@PathVariable(value = "accountNumber") String accountNumber){
        return accountsService.getAccount(accountNumber);
    }

    @GetMapping("/{accountNumber}/events")
    public List<ApplicationEvent> listEventsForAccount(@PathVariable(value = "accountNumber") String accountNumber){
        return accountsService.listEventsForAccount(accountNumber);
    }
    
}

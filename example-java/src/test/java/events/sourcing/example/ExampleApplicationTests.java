package events.sourcing.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import events.sourcing.example.controller.AccountsController;
import events.sourcing.example.dto.AccountCreateDTO;
import events.sourcing.example.dto.AccountDTO;
import events.sourcing.example.dto.MoneyCreditDTO;
import events.sourcing.example.dto.MoneyDebitDTO;
import events.sourcing.example.service.AccountsService;

@SpringBootTest
class ExampleApplicationTests {

	@Autowired
	private AccountsController accountsController;

	@Autowired
	private AccountsService accountsService;

	@Test
	void shouldCreateAccount() {
		AccountCreateDTO accountCreateDTO = new AccountCreateDTO();
		accountCreateDTO.setCurrency("USD");
		accountCreateDTO.setStartingBalance(25.0);
		String accountId = accountsController.createAccount(accountCreateDTO);

		assertNotNull(accountId);

		AccountDTO accountDTO = accountsService.getAccount(accountId);
		assertEquals(25.0, accountDTO.getAccountBalance());
		assertEquals(accountId, accountDTO.getId());
		assertEquals("USD", accountDTO.getCurrency());
		assertEquals("ACTIVATED", accountDTO.getStatus());
	}

	@Test
	void shouldCreditIntoAccount() {
		AccountCreateDTO accountCreateDTO = new AccountCreateDTO();
		accountCreateDTO.setCurrency("USD");
		accountCreateDTO.setStartingBalance(25.0);
		String accountId = accountsController.createAccount(accountCreateDTO);
		MoneyCreditDTO moneyCreditDTO = new MoneyCreditDTO();
		moneyCreditDTO.setCreditAmount(20.0);
		moneyCreditDTO.setCurrency("USD");
		boolean status = accountsController.creditMoneyToAccount(accountId, moneyCreditDTO);
		assertTrue(status);

		AccountDTO accountDTO = accountsService.getAccount(accountId);
		assertEquals(45.0, accountDTO.getAccountBalance());
		assertEquals(accountId, accountDTO.getId());
		assertEquals("USD", accountDTO.getCurrency());
		assertEquals("ACTIVATED", accountDTO.getStatus());

	}


	@Test
	void shouldDebitFromAccount() {
		AccountCreateDTO accountCreateDTO = new AccountCreateDTO();
		accountCreateDTO.setCurrency("USD");
		accountCreateDTO.setStartingBalance(25.0);
		String accountId = accountsController.createAccount(accountCreateDTO);
		MoneyDebitDTO moneyDebitDTO = new MoneyDebitDTO();
		moneyDebitDTO.setDebitAmount(20.0);
		moneyDebitDTO.setCurrency("USD");
		boolean status = accountsController.debitMoneyFromAccount(accountId, moneyDebitDTO);
		assertTrue(status);

		AccountDTO accountDTO = accountsService.getAccount(accountId);
		assertEquals(5.0, accountDTO.getAccountBalance());
		assertEquals(accountId, accountDTO.getId());
		assertEquals("USD", accountDTO.getCurrency());
		assertEquals("ACTIVATED", accountDTO.getStatus());

	}

}

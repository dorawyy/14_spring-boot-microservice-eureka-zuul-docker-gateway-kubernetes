package com.tanerdiler.microservice.accounts.resource;

import com.tanerdiler.microservice.accounts.model.Account;
import com.tanerdiler.microservice.accounts.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
@AllArgsConstructor
public class AccountResource
{
	private final AccountRepository repository;

	@GetMapping("/{id}")
	public ResponseEntity<Account> get(@PathVariable("id") Integer id)
	{
		final var account = repository.findById(id).get(); // call
		log.info("Account {} detail fetched {}", id, account);
		return ResponseEntity.ok(account);
	}

	@GetMapping()
	public ResponseEntity<Collection<Account>> getAll()
	{
		final var accounts = (repository.findAll().get()); // call
		log.info("Executing fetching all accounts {}", accounts);
		return ResponseEntity.ok(accounts);
	}
}

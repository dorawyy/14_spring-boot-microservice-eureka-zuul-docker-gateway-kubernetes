package com.tanerdiler.microservice.main.resource;

import com.tanerdiler.microservice.main.dto.OrderDTO;
import com.tanerdiler.microservice.main.model.Account;
import com.tanerdiler.microservice.main.model.Order;
import com.tanerdiler.microservice.main.model.Product;
import com.tanerdiler.microservice.main.repository.AccountServiceClient;
import com.tanerdiler.microservice.main.repository.OrderServiceClient;
import com.tanerdiler.microservice.main.repository.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/backoffice/api/v1/backoffice")
@RequiredArgsConstructor
public class BackofficeController
{
	private final ProductServiceClient productService;
	private final OrderServiceClient orderService;
	private final AccountServiceClient accountService;

	@GetMapping("/orders")
	public ResponseEntity<List<OrderDTO>> getOrders()
	{

		log.warn("Fetching all orders...");
		List<Order> orders = orderService.findAll(); // call, missing
		Map<Integer, Account> accounts = new HashMap<>();
		Map<Integer, Product> products = new HashMap<>();

		log.warn("Fetching accounts of orders...");
		orders.stream()
				.filter(o->!accounts.containsKey(o.getAccountId())) // call
				.map(o->accountService.findById(o.getAccountId())) // call, missing // call
				.forEach(a->accounts.put(a.getId(), a)); // call

		log.warn("Fetching products of orders...");
		orders.stream()
				.filter(o->!products.containsKey(o.getProductId())) // call
				.map(o->productService.findById(o.getProductId())) // call, missing  // call
				.forEach(a->products.put(a.getId(), a)); // call

		log.warn("Generating composite of orders...");
		List<OrderDTO> orderDTOList = new ArrayList<>();
		orders.forEach(o->{
			orderDTOList.add(new OrderDTO( // call
					o.getId(), // call
					o.getCount(), // call
					o.getPrice(), // call 
					o.getDiscountedPrice(), // call
					accounts.get(o.getAccountId()).getFullname(), // call  // call
					products.get(o.getProductId()).getName() // call  // call
			));
		});

		return ResponseEntity.ok(orderDTOList);

	}
}

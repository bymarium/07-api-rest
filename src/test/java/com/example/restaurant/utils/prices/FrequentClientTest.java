package com.example.restaurant.utils.prices;

import com.example.restaurant.constants.Type;
import com.example.restaurant.models.Client;
import com.example.restaurant.models.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrequentClientTest {

	@Test
	void handlerRequest() {
		Order order = new Order(1L, 120f);
		Client client = new Client(1L, "Mariana", "Usuga", "bymar@gmail.com");
		client.setUserType(Type.COMMON.getName());
		order.setClient(client);

		FrequentClient frequentClient = new FrequentClient();
		frequentClient.handlerRequest(order);

		assertEquals(Type.COMMON.getName(), order.getClient().getUserType());
	}
}
package com.example.restaurant.utils.prices;

import com.example.restaurant.constants.Type;
import com.example.restaurant.models.Client;
import com.example.restaurant.models.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SilverClientTest {

	@Test
	void handlerRequest() {
		Order order = new Order(1L, 120f);
		Client client = new Client(1L, "Mariana", "Usuga", "bymar@gmail.com");
		client.setUserType(Type.BRONZE.getName());
		order.setClient(client);

		SilverClient silverClient = new SilverClient();
		silverClient.handlerRequest(order);

		assertEquals(Type.BRONZE.getName(), order.getClient().getUserType());
	}
}
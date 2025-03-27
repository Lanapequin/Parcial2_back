package edu.eci.cvds.parcial2.test;

import edu.eci.cvds.parcial2.model.Item;
import edu.eci.cvds.parcial2.model.Payment;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ModelTests {

    @Test
    void itemConstructorAndGettersTest() {
        Item item = new Item("Laptop", 1200.0, 2);
        assertEquals("Laptop", item.getName());
        assertEquals(1200.0, item.getUnitPrice());
        assertEquals(2, item.getQuantity());
    }

    @Test
    void itemSettersTest() {
        Item item = new Item("Laptop", 1200.0, 2);
        item.setName("Mouse");
        item.setUnitPrice(25.0);
        item.setQuantity(1);
        assertEquals("Mouse", item.getName());
        assertEquals(25.0, item.getUnitPrice());
        assertEquals(1, item.getQuantity());
    }

    @Test
    void paymentConstructorAndGettersTest() {
        List<Item> items = Arrays.asList(new Item("Laptop", 1200.0, 2), new Item("Mouse", 25.0, 1));
        Date purchaseDate = new Date();
        Payment payment = new Payment("user123", items, 2425.0, purchaseDate, "COMPLETED", "Payment successful");
        assertEquals("user123", payment.getUserId());
        assertEquals(items, payment.getItems());
        assertEquals(2425.0, payment.getTotalAmount());
        assertEquals(purchaseDate, payment.getPurchaseDate());
        assertEquals("COMPLETED", payment.getStatus());
        assertEquals("Payment successful", payment.getResponseMessage());
        assertNull(payment.getId());
    }

    @Test
    void paymentSettersTest() {
        List<Item> items = Arrays.asList(new Item("Laptop", 1200.0, 2), new Item("Mouse", 25.0, 1));
        Date purchaseDate = new Date();
        Payment payment = new Payment("user123", items, 2425.0, purchaseDate, "COMPLETED", "Payment successful");

        List<Item> newItems = Arrays.asList(new Item("Keyboard", 50.0, 1));
        Date newPurchaseDate = new Date(purchaseDate.getTime() + 10000);
        payment.setUserId("user456");
        payment.setItems(newItems);
        payment.setTotalAmount(50.0);
        payment.setPurchaseDate(newPurchaseDate);
        payment.setStatus("PENDING");
        payment.setResponseMessage("Payment initiated");
        payment.setId("someId");

        assertEquals("user456", payment.getUserId());
        assertEquals(newItems, payment.getItems());
        assertEquals(50.0, payment.getTotalAmount());
        assertEquals(newPurchaseDate, payment.getPurchaseDate());
        assertEquals("PENDING", payment.getStatus());
        assertEquals("Payment initiated", payment.getResponseMessage());
        assertEquals("someId", payment.getId());
    }
}

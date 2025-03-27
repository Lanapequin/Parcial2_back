package edu.eci.cvds.parcial2.test;

import edu.eci.cvds.parcial2.controller.PaymentController;
import edu.eci.cvds.parcial2.model.Item;
import edu.eci.cvds.parcial2.model.Payment;
import edu.eci.cvds.parcial2.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void makePayment_shouldReturnProcessedPayment() {
        List<Item> items = Arrays.asList(new Item("Item1", 10.0, 1), new Item("Item2", 20.0, 1));
        Payment payment = new Payment("user123", items, 30.0, new Date(), "PENDING", "Payment initiated");
        when(paymentService.processPayment(any(Payment.class))).thenReturn(payment);

        Payment result = paymentController.makePayment(payment);

        assertEquals(payment, result);
        verify(paymentService, times(1)).processPayment(payment);
    }

    @Test
    void makePayment_shouldHandleException() {
        List<Item> items = Arrays.asList(new Item("Item1", 10.0, 1), new Item("Item2", 20.0, 1));
        Payment payment = new Payment("user123", items, 30.0, new Date(), "PENDING", "Payment initiated");
        when(paymentService.processPayment(any(Payment.class))).thenThrow(new RuntimeException("Payment processing failed"));

        try {
            paymentController.makePayment(payment);
        } catch (RuntimeException e) {
            assertEquals("Payment processing failed", e.getMessage());
        }

        verify(paymentService, times(1)).processPayment(payment);
    }

    @Test
    void getPayments_shouldReturnListOfPayments() {
        List<Item> items = Arrays.asList(new Item("Item1", 10.0, 1), new Item("Item2", 20.0, 1));
        List<Payment> payments = Arrays.asList(
                new Payment("user123", items, 30.0, new Date(), "COMPLETED", "Payment successful"),
                new Payment("user123", items, 30.0, new Date(), "FAILED", "Payment failed")
        );
        when(paymentService.getPaymentsByUser("user123")).thenReturn(payments);

        List<Payment> result = paymentController.getPayments("user123");

        assertEquals(payments, result);
        verify(paymentService, times(1)).getPaymentsByUser("user123");
    }

    @Test
    void getPayments_shouldReturnEmptyList_whenNoPaymentsFound() {
        when(paymentService.getPaymentsByUser("user456")).thenReturn(Arrays.asList());

        List<Payment> result = paymentController.getPayments("user456");

        assertEquals(0, result.size());
        verify(paymentService, times(1)).getPaymentsByUser("user456");
    }

    @Test
    void getPayments_shouldHandleException() {
        when(paymentService.getPaymentsByUser("user123")).thenThrow(new RuntimeException("Database error"));

        try {
            paymentController.getPayments("user123");
        } catch (RuntimeException e) {
            assertEquals("Database error", e.getMessage());
        }

        verify(paymentService, times(1)).getPaymentsByUser("user123");
    }
}

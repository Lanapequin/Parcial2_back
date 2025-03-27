package edu.eci.cvds.parcial2.test;

import edu.eci.cvds.parcial2.model.Item;
import edu.eci.cvds.parcial2.model.Payment;
import edu.eci.cvds.parcial2.repository.PaymentRepository;
import edu.eci.cvds.parcial2.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeclinePaymentWhenUserIdIsNull() {
        Payment payment = new Payment(null, Arrays.asList(new Item("item1", 10.0, 2)), 10.0, new Date(), "", "");
        paymentService.processPayment(payment);
        assertEquals("DECLINED", payment.getStatus());
        assertEquals("Error: El monto total no coincide con la suma de los artículos.", payment.getResponseMessage());
    }

    @Test
    void shouldDeclinePaymentWhenItemsAreEmpty() {
        Payment payment = new Payment("user1", Collections.emptyList(), 20.0, new Date(), "", "");
        paymentService.processPayment(payment);
        assertEquals("DECLINED", payment.getStatus());
        assertEquals("Error: El monto total no coincide con la suma de los artículos.", payment.getResponseMessage());
    }

    @Test
    void shouldDeclinePaymentWhenTotalAmountIsZero() {
        Payment payment = new Payment("user1", Arrays.asList(new Item("item1", 10.0, 2)), 0.0, new Date(), "", "");
        paymentService.processPayment(payment);
        assertEquals("DECLINED", payment.getStatus());
        assertEquals("Error: El monto total no coincide con la suma de los artículos.", payment.getResponseMessage());
    }

    @Test
    void shouldDeclinePaymentWhenTotalAmountDoesNotMatch() {
        Payment payment = new Payment("user1", Arrays.asList(new Item("item1", 10.0, 2)), 25.0, new Date(), "", "");
        paymentService.processPayment(payment);
        assertEquals("DECLINED", payment.getStatus());
        assertEquals("Error: El monto total no coincide con la suma de los artículos.", payment.getResponseMessage());
    }

    @Test
    void shouldApprovePaymentWhenDataIsCorrect() {
        Payment payment = new Payment("user1", Arrays.asList(new Item("item1", 10.0, 2)), 20.0, new Date(), "", "");
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        Payment result = paymentService.processPayment(payment);
        assertEquals("APPROVED", result.getStatus());
        assertEquals("Pago aprobado exitosamente.", result.getResponseMessage());
    }

    @Test
    void shouldRetrievePaymentsByUser() {
        Payment payment = new Payment("user1", Arrays.asList(new Item("item1", 10.0, 2)), 20.0, new Date(), "APPROVED", "Pago aprobado exitosamente.");
        List<Payment> payments = Arrays.asList(payment);
        when(paymentRepository.findByUserId("user1")).thenReturn(payments);
        List<Payment> result = paymentService.getPaymentsByUser("user1");
        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUserId());
    }
}
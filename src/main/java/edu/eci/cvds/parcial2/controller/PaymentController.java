package edu.eci.cvds.parcial2.controller;

import edu.eci.cvds.parcial2.model.Payment;
import edu.eci.cvds.parcial2.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public Payment makePayment(@RequestBody Payment payment) {
        return paymentService.processPayment(payment);
    }

    @GetMapping("/user/{userId}")
    public List<Payment> getPayments(@PathVariable String userId) {
        return paymentService.getPaymentsByUser(userId);
    }
}
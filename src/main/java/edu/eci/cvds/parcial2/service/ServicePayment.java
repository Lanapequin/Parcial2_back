package edu.eci.cvds.parcial2.service;


import edu.eci.cvds.parcial2.model.Payment;

import java.util.List;

public interface ServicePayment {
    Payment processPayment(Payment payment);
    List<Payment> getPaymentsByUser(String userId);
}
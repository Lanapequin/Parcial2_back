package edu.eci.cvds.parcial2.service;

import edu.eci.cvds.parcial2.model.Payment;
import edu.eci.cvds.parcial2.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService implements ServicePayment {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment processPayment(Payment payment) {
        double calculatedTotal = payment.getItems().stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();

        if (calculatedTotal != payment.getTotalAmount()) {
            payment.setStatus("DECLINED");
            payment.setResponseMessage("Error: El monto total no coincide con la suma de los artículos.");
        } else {
            payment.setStatus("APPROVED");
            payment.setResponseMessage("Pago aprobado exitosamente.");
        }

        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPaymentsByUser(String userId) {
        return paymentRepository.findByUserId(userId);
    }
}

package com.vti.service;

import com.vti.entity.Payment;
import com.vti.entity.User;
import com.vti.repository.IPaymentRepository;
import com.vti.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {



    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPaymentRepository paymentRepository;


    @Override
    public void addBankPaymentToUser(int userId) {
        Payment bankPayment = paymentRepository.findByType("Ngân hàng");
        if (bankPayment == null) {
            bankPayment = new Payment();
            bankPayment.setType("Ngân hàng");
            paymentRepository.save(bankPayment);
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.getPayments().add(bankPayment);
            bankPayment.getUsers().add(user);
            userRepository.save(user);
        }
    }
}

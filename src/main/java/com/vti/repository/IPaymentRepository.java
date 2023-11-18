package com.vti.repository;

import com.vti.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentRepository extends JpaRepository<Payment,Integer> {
    Payment findByType(String type);
}

package com.fiap.techchallenge.infrastructure.repository.postgres;

import com.fiap.techchallenge.domain.entity.Payment;
import com.fiap.techchallenge.domain.repository.IPaymentRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Getter
@Repository("PGPaymentRepository")
public class PaymentRepository implements IPaymentRepository {

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public int create(Payment payment) {
    String sqlCreate = "INSERT INTO public.payment (id, order_id, amount, payed_at) VALUES (?, ?, ?, ?)";

    return jdbcTemplate.update(
        sqlCreate,
        payment.getPaymentId(),
        payment.getOrderId(),
        payment.getAmount(),
        payment.getPayedAt()
    );

  }

}

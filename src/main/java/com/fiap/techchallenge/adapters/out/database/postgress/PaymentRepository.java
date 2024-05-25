package com.fiap.techchallenge.adapters.out.database.postgress;

import com.fiap.techchallenge.domain.entity.Payment;
import com.fiap.techchallenge.domain.repository.PaymentRepositoryPort;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Getter
@Repository("PGPaymentRepository")
public class PaymentRepository implements PaymentRepositoryPort {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public PaymentRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
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

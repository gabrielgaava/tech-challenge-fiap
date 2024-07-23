package com.fiap.techchallenge.drivers.postgresql;

import com.fiap.techchallenge.domain.payment.Payment;
import com.fiap.techchallenge.domain.payment.PaymentRepositoryPort;
import com.fiap.techchallenge.drivers.postgresql.mapper.PaymentMapper;
import com.fiap.techchallenge.utils.ParseUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Getter
@Repository("PGPaymentRepository")
public class PaymentRepository implements PaymentRepositoryPort {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public PaymentRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public int create(Payment payment) {
    String sqlCreate = "INSERT INTO public.payment (id, external_id, order_id, status, gateway, amount, transaction_data, payed_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    return jdbcTemplate.update(
        sqlCreate,
        payment.getId(),
        payment.getExternalId(),
        payment.getOrderId(),
        payment.getStatus(),
        payment.getGateway(),
        payment.getAmount(),
        ParseUtils.toJson(payment.getTransactionData()),
        payment.getPayedAt()
    );

  }

  @Override
  public Payment getByExternalId(String externalId) {
    String sqlSelect = "SELECT * FROM public.payment WHERE external_id = ?";
    List<Payment> data = jdbcTemplate.query(sqlSelect, PaymentMapper.map, externalId);

    if (data.isEmpty()) { return null; }

    return data.getFirst();
  }

  @Override
  public Payment get(UUID id) {
    String sqlSelect = "SELECT * FROM public.payment WHERE id = ?";
    List<Payment> data = jdbcTemplate.query(sqlSelect, PaymentMapper.map, id);

    if (data.size() == 1) { return null; }

    return data.getFirst();
  }

  @Override
  public boolean update(Payment payment) {
    String updateStatusSQL = "UPDATE public.payment SET payed_at = ?, status = ? WHERE id = ?";

    int updated = jdbcTemplate.update(
        updateStatusSQL,
        payment.getPayedAt(),
        payment.getStatus(),
        payment.getId()
    );

    return updated == 1;
  }


}

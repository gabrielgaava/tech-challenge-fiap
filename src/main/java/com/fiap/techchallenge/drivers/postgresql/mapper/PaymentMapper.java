package com.fiap.techchallenge.drivers.postgresql.mapper;

import com.fiap.techchallenge.domain.payment.Payment;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class PaymentMapper {

  public static RowMapper<Payment> map = new RowMapper<Payment>(){

    @Override
    public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
      Payment payment = new Payment();
      payment.setId(UUID.fromString(rs.getString("id")));
      payment.setExternalId(rs.getString("external_id"));
      payment.setOrderId(UUID.fromString(rs.getString("order_id")));
      payment.setStatus(rs.getString("status"));
      payment.setGateway(rs.getString("gateway"));
      payment.setAmount(BigDecimal.valueOf(rs.getFloat("amount")));
      payment.setTransactionData(rs.getString("transaction_data"));
      payment.setPayedAt(this.getLocalDateTime(rs.getTimestamp("payed_at")));

      return payment;
    }

    private LocalDateTime getLocalDateTime(Timestamp timestamp) {
      if (timestamp != null) {
        return timestamp.toLocalDateTime();
      }

      return null;
    }

  };



}

CREATE TABLE payment (
  id varchar PRIMARY KEY,
  order_id uuid,
  gateway varchar,
  amount float,
  transaction_data varchar,
  payed_at timestamp,
  FOREIGN KEY (order_id) REFERENCES "order" (id)
);
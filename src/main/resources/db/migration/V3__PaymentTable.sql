CREATE TABLE payment (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  order_id uuid,
  amount float,
  payed_at timestamp,
  FOREIGN KEY (order_id) REFERENCES "order" (id)
);
CREATE TYPE PRODUCT_CATEGORY AS ENUM (
  'SANDWICH',
  'SIDE_DISH',
  'DRINK',
  'DESSERT'
);

CREATE TYPE ORDER_STATUS AS ENUM (
  'RECEIVED',
  'IN_PREPARATION',
  'READY_TO_DELIVERY',
  'FINISHED'
);

CREATE TABLE costumer (
    id uuid PRIMARY KEY,
    cpf varchar(11) UNIQUE,
    nome varchar(256),
    email varchar(256) UNIQUE
);

CREATE TABLE product (
   id uuid PRIMARY KEY,
   name varchar,
   description varchar,
   image_url varchar,
   price float,
   category PRODUCT_CATEGORY
);

CREATE TABLE "order" (
  id uuid PRIMARY KEY,
  order_number varchar,
  costumer_id uuid ,
  created_at timestamp,
  amount float,
  status ORDER_STATUS,
  FOREIGN KEY (costumer_id) REFERENCES costumer (id)
);

CREATE TABLE "order_history" (
  order_id uuid,
  previous_status ORDER_STATUS,
  new_status ORDER_STATUS,
  moment timestamp,
  FOREIGN KEY (order_id) REFERENCES "order" (id),
  PRIMARY KEY (order_id, "previous_status", "new_status")
);

CREATE TABLE "order_products" (
  order_id uuid,
  product_id uuid,
  quantity int,
  FOREIGN KEY ("order_id") REFERENCES "order" (id),
  FOREIGN KEY ("product_id") REFERENCES "product" (id),
  PRIMARY KEY ("order_id", "product_id")
);
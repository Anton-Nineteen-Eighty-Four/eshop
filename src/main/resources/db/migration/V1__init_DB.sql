CREATE TABLE IF NOT EXISTS users (
    id bigint not null auto_increment,
    username varchar(255),
    email varchar(255),
    role enum ('ADMIN','CLIENT','MANAGER'),
    archive bit not null,
    password varchar(255),
    bucked_id bigint,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS buckets (
     id bigint not null auto_increment,
     user_id bigint,
     primary key (id)
);

CREATE TABLE IF NOT EXISTS buckets_products (
     bucket_id bigint not null,
     product_id bigint not null
);

CREATE TABLE IF NOT EXISTS categories (
     id bigint not null auto_increment,
     title varchar(255),
     primary key (id)
);

CREATE TABLE IF NOT EXISTS orders (
    id bigint not null auto_increment,
    status enum ('APPROVED','CANCELED','CLOSES','NEW','PAID'),
    sum decimal(38,2),
    created datetime(6),
    updated datetime(6),
    user_id bigint,
    address varchar(255),
    primary key (id)
);

CREATE TABLE IF NOT EXISTS orders_details (
    id bigint not null auto_increment,
    price decimal(38,2),
    amount decimal(38,2),
    details_id bigint not null,
    order_id bigint,
    product_id bigint,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS products (
    id bigint not null auto_increment,
    title varchar(255),
    price decimal(38,2),
    primary key (id)
);

CREATE TABLE IF NOT EXISTS products_categories (
    category_id bigint not null,
    product_id bigint not null
);

alter table buckets
add constraint UKpu50kur9wrjayubjfco2ye9o7 unique (user_id);

alter table orders_details
add constraint UKkk6y3pyhjt6kajomtjbhsoajo unique (details_id);

alter table users
add constraint UK7tbtso73yoavvfyr5s63xyt4q unique (bucked_id);

alter table buckets
add constraint FKnl0ltaj67xhydcrfbq8401nvj foreign key (user_id) references users (id);

alter table buckets_products
add constraint FKloyxdc1uy11tayedf3dpu9lci foreign key (product_id) references products (id);

alter table buckets_products
add constraint FKc49ah45o66gy2f2f4c3os3149 foreign key (bucket_id) references buckets (id);

alter table orders
add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (user_id) references users (id);

alter table orders_details
add constraint FK5o977kj2vptwo70fu7w7so9fe foreign key (order_id) references orders (id);

alter table orders_details
add constraint FKs0r9x49croribb4j6tah648gt foreign key (product_id) references products (id);

alter table orders_details
add constraint FKgvp1k7a3ubdboj3yhnawd5m1p foreign key (details_id) references orders_details (id);

alter table products_categories
add constraint FKqt6m2o5dly3luqcm00f5t4h2p foreign key (category_id) references categories (id);

alter table products_categories
add constraint FKtj1vdea8qwerbjqie4xldl1el foreign key (product_id) references products (id);

alter table users
add constraint FKrmqt5lvsvls48dc2wsr98iwah foreign key (bucked_id) references buckets (id);
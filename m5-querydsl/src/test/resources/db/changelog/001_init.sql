--liquibase formatted sql
--changeset test:001_init.sql

create table account (
    id     uuid default gen_random_uuid() primary key,
    name   varchar(255) not null
);

create table product (
    id           uuid default gen_random_uuid() primary key,
    code         varchar(10) unique
);

create table category (
    id           uuid default gen_random_uuid() primary key,
    code         int unique
);

create table category_product (
    id            uuid default gen_random_uuid() primary key,
    product_id    uuid not null references product(id),
    category_id   uuid not null references category(id)
);

create table "order" (
    id           uuid default gen_random_uuid() primary key,
    account_id   uuid not null references account(id),
    ordered_at   timestamp not null default now()
);

create table order_item (
    id           uuid default gen_random_uuid() primary key,
    product_id   uuid not null references product(id),
    order_id     uuid not null references "order"(id)
);

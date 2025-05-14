INSERT INTO category (code) VALUES
(1), (2), (3), (4), (5);

INSERT INTO product (code) VALUES
('P1001'), ('P1002'), ('P1003'), ('P1004'), ('P1005'),
('P2001'), ('P2002'), ('P2003'), ('P2004'), ('P2005'),
('P3001'), ('P3002'), ('P3003'), ('P3004'), ('P3005');

INSERT INTO category_product (product_id, category_id) VALUES
((SELECT id FROM product WHERE code = 'P1001'), (SELECT id FROM category WHERE code = 1)),
((SELECT id FROM product WHERE code = 'P1001'), (SELECT id FROM category WHERE code = 2)),
((SELECT id FROM product WHERE code = 'P1001'), (SELECT id FROM category WHERE code = 3)),
((SELECT id FROM product WHERE code = 'P2001'), (SELECT id FROM category WHERE code = 2)),
((SELECT id FROM product WHERE code = 'P2001'), (SELECT id FROM category WHERE code = 3)),
((SELECT id FROM product WHERE code = 'P2001'), (SELECT id FROM category WHERE code = 4)),
((SELECT id FROM product WHERE code = 'P3001'), (SELECT id FROM category WHERE code = 3)),
((SELECT id FROM product WHERE code = 'P3001'), (SELECT id FROM category WHERE code = 4)),
((SELECT id FROM product WHERE code = 'P3001'), (SELECT id FROM category WHERE code = 5));

INSERT INTO category_product (product_id, category_id) VALUES
((SELECT id FROM product WHERE code = 'P1002'), (SELECT id FROM category WHERE code = 1)),
((SELECT id FROM product WHERE code = 'P1002'), (SELECT id FROM category WHERE code = 2)),
((SELECT id FROM product WHERE code = 'P2002'), (SELECT id FROM category WHERE code = 2)),
((SELECT id FROM product WHERE code = 'P2002'), (SELECT id FROM category WHERE code = 3)),
((SELECT id FROM product WHERE code = 'P3002'), (SELECT id FROM category WHERE code = 3)),
((SELECT id FROM product WHERE code = 'P3002'), (SELECT id FROM category WHERE code = 4)),
((SELECT id FROM product WHERE code = 'P1003'), (SELECT id FROM category WHERE code = 4)),
((SELECT id FROM product WHERE code = 'P1003'), (SELECT id FROM category WHERE code = 5)),
((SELECT id FROM product WHERE code = 'P2003'), (SELECT id FROM category WHERE code = 1)),
((SELECT id FROM product WHERE code = 'P2003'), (SELECT id FROM category WHERE code = 5));

INSERT INTO category_product (product_id, category_id) VALUES
((SELECT id FROM product WHERE code = 'P1004'), (SELECT id FROM category WHERE code = 1)),
((SELECT id FROM product WHERE code = 'P1005'), (SELECT id FROM category WHERE code = 2)),
((SELECT id FROM product WHERE code = 'P2004'), (SELECT id FROM category WHERE code = 3)),
((SELECT id FROM product WHERE code = 'P2005'), (SELECT id FROM category WHERE code = 4)),
((SELECT id FROM product WHERE code = 'P3003'), (SELECT id FROM category WHERE code = 5)),
((SELECT id FROM product WHERE code = 'P3004'), (SELECT id FROM category WHERE code = 1)),
((SELECT id FROM product WHERE code = 'P3005'), (SELECT id FROM category WHERE code = 2));

INSERT INTO account (name) VALUES
('Alice Johnson'), ('Bob Smith'), ('Carol Williams'),
('David Brown'), ('Eve Davis');

INSERT INTO "order" (account_id, ordered_at) VALUES
((SELECT id FROM account WHERE name = '${USER_1_NAME}'), '2023-01-10 09:15:00'),
((SELECT id FROM account WHERE name = '${USER_1_NAME}'), '2023-02-05 14:30:00'),
((SELECT id FROM account WHERE name = '${USER_1_NAME}'), '2023-03-20 11:45:00');

INSERT INTO "order" (account_id, ordered_at) VALUES
((SELECT id FROM account WHERE name = '${USER_2_NAME}'), '2023-01-15 10:00:00'),
((SELECT id FROM account WHERE name = '${USER_2_NAME}'), '2023-02-18 16:20:00');

INSERT INTO "order" (account_id, ordered_at) VALUES
((SELECT id FROM account WHERE name = '${USER_3_NAME}'), '2023-01-05 08:30:00'),
((SELECT id FROM account WHERE name = '${USER_3_NAME}'), '2023-01-25 13:15:00'),
((SELECT id FROM account WHERE name = '${USER_3_NAME}'), '2023-02-10 10:45:00'),
((SELECT id FROM account WHERE name = '${USER_3_NAME}'), '2023-03-05 15:00:00');

INSERT INTO "order" (account_id, ordered_at) VALUES
((SELECT id FROM account WHERE name = '${USER_4_NAME}'), '2023-01-20 11:00:00'),
((SELECT id FROM account WHERE name = '${USER_4_NAME}'), '2023-02-15 09:30:00'),
((SELECT id FROM account WHERE name = '${USER_4_NAME}'), '2023-03-10 14:00:00');

INSERT INTO "order" (account_id, ordered_at) VALUES
((SELECT id FROM account WHERE name = '${USER_5_NAME}'), '2023-01-25 12:45:00'),
((SELECT id FROM account WHERE name = '${USER_5_NAME}'), '2023-03-15 10:15:00');

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-01-10 09:15:00'), (SELECT id FROM product WHERE code = 'P1001')),
((SELECT id FROM "order" WHERE ordered_at = '2023-01-10 09:15:00'), (SELECT id FROM product WHERE code = 'P2002'));

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-02-05 14:30:00'), (SELECT id FROM product WHERE code = 'P3001'));

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-03-20 11:45:00'), (SELECT id FROM product WHERE code = 'P1002')),
((SELECT id FROM "order" WHERE ordered_at = '2023-03-20 11:45:00'), (SELECT id FROM product WHERE code = 'P2003')),
((SELECT id FROM "order" WHERE ordered_at = '2023-03-20 11:45:00'), (SELECT id FROM product WHERE code = 'P3005'));

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-01-15 10:00:00'), (SELECT id FROM product WHERE code = 'P1004'));

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-02-18 16:20:00'), (SELECT id FROM product WHERE code = 'P2001')),
((SELECT id FROM "order" WHERE ordered_at = '2023-02-18 16:20:00'), (SELECT id FROM product WHERE code = 'P3002'));

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-01-05 08:30:00'), (SELECT id FROM product WHERE code = 'P1005')),
((SELECT id FROM "order" WHERE ordered_at = '2023-01-05 08:30:00'), (SELECT id FROM product WHERE code = 'P2004')),
((SELECT id FROM "order" WHERE ordered_at = '2023-01-05 08:30:00'), (SELECT id FROM product WHERE code = 'P3003'));

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-01-25 13:15:00'), (SELECT id FROM product WHERE code = 'P1001'));

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-02-10 10:45:00'), (SELECT id FROM product WHERE code = 'P2005')),
((SELECT id FROM "order" WHERE ordered_at = '2023-02-10 10:45:00'), (SELECT id FROM product WHERE code = 'P3004'));

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-03-05 15:00:00'), (SELECT id FROM product WHERE code = 'P1003'));

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-01-20 11:00:00'), (SELECT id FROM product WHERE code = 'P2002')),
((SELECT id FROM "order" WHERE ordered_at = '2023-01-20 11:00:00'), (SELECT id FROM product WHERE code = 'P3005'));

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-02-15 09:30:00'), (SELECT id FROM product WHERE code = 'P1004'));

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-03-10 14:00:00'), (SELECT id FROM product WHERE code = 'P2001')),
((SELECT id FROM "order" WHERE ordered_at = '2023-03-10 14:00:00'), (SELECT id FROM product WHERE code = 'P3001')),
((SELECT id FROM "order" WHERE ordered_at = '2023-03-10 14:00:00'), (SELECT id FROM product WHERE code = 'P1002'));

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-01-25 12:45:00'), (SELECT id FROM product WHERE code = 'P2003')),
((SELECT id FROM "order" WHERE ordered_at = '2023-01-25 12:45:00'), (SELECT id FROM product WHERE code = 'P3002'));

INSERT INTO order_item (order_id, product_id) VALUES
((SELECT id FROM "order" WHERE ordered_at = '2023-03-15 10:15:00'), (SELECT id FROM product WHERE code = 'P1005'));
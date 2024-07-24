INSERT INTO member(email, password, name, phone_number, address, detail_address, email_verified, role) VALUES ('user1@email.com',
                                                                                                         '$2a$10$ZJN6q89.OM4Ir55aK4dIxuLOO4yxmzbl5qs68j0VATnyXXrDVt7My',
                                                                                                         'ryan',
                                                                                                         '010-1234-5678',
                                                                                                         'addr',
                                                                                                         'detail_addr',
                                                                                                         true,
                                                                                                           'ADMIN');
INSERT INTO member(email, password, name, phone_number, address, detail_address, email_verified, role) VALUES ('user2@email.com',
                                                                                                         '$2a$10$ZJN6q89.OM4Ir55aK4dIxuLOO4yxmzbl5qs68j0VATnyXXrDVt7My',
                                                                                                         'neo',
                                                                                                         '010-1234-5678',
                                                                                                         'addr',
                                                                                                         'detail_addr',
                                                                                                         true,
                                                                                                               'SELLER');
INSERT INTO member(email, password, name, phone_number, address, detail_address, email_verified, role) VALUES ('user3@email.com',
                                                                                                         '$2a$10$ZJN6q89.OM4Ir55aK4dIxuLOO4yxmzbl5qs68j0VATnyXXrDVt7My',
                                                                                                         'muzi',
                                                                                                         '010-1234-5678',
                                                                                                         'addr',
                                                                                                         'detail_addr',
                                                                                                         true,
                                                                                                               'USER');

INSERT INTO cart(product_id, user_id, quantity) VALUES (1, (SELECT id FROM member WHERE email='user3@email.com'), 1);
INSERT INTO cart(product_id, user_id, quantity) VALUES (2, (SELECT id FROM member WHERE email='user3@email.com'), 1);

INSERT INTO shipping_address(user_id, address, detail_address, is_primary)
VALUES ((SELECT id FROM member WHERE email='user1@email.com'), 'Address 1-1', 'Detail Address 1-1', true);
INSERT INTO shipping_address(user_id, address, detail_address, is_primary)
VALUES ((SELECT id FROM member WHERE email='user1@email.com'), 'Address 1-2', 'Detail Address 1-2', false);
INSERT INTO shipping_address(user_id, address, detail_address, is_primary)
VALUES ((SELECT id FROM member WHERE email='user1@email.com'), 'Address 1-3', 'Detail Address 1-3', false);

INSERT INTO shipping_address(user_id, address, detail_address, is_primary)
VALUES ((SELECT id FROM member WHERE email='user2@email.com'), 'Address 2-1', 'Detail Address 2-1', true);
INSERT INTO shipping_address(user_id, address, detail_address, is_primary)
VALUES ((SELECT id FROM member WHERE email='user2@email.com'), 'Address 2-2', 'Detail Address 2-2', false);
INSERT INTO shipping_address(user_id, address, detail_address, is_primary)
VALUES ((SELECT id FROM member WHERE email='user2@email.com'), 'Address 2-3', 'Detail Address 2-3', false);

INSERT INTO shipping_address(user_id, address, detail_address, is_primary)
VALUES ((SELECT id FROM member WHERE email='user3@email.com'), 'Address 3-1', 'Detail Address 3-1', true);
INSERT INTO shipping_address(user_id, address, detail_address, is_primary)
VALUES ((SELECT id FROM member WHERE email='user3@email.com'), 'Address 3-2', 'Detail Address 3-2', false);
INSERT INTO shipping_address(user_id, address, detail_address, is_primary)
VALUES ((SELECT id FROM member WHERE email='user3@email.com'), 'Address 3-3', 'Detail Address 3-3', false);
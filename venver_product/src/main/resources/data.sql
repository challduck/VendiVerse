-- product 테이블에 값 삽입
INSERT INTO product (name, description, price) VALUES
    ('사과', '신선한 빨간 사과', 3500);

INSERT INTO product (name, description, price) VALUES
    ('바나나', '잘 익은 바나나', 2800);

-- product_stock 테이블에 값 삽입
INSERT INTO product_stock (product_id, stock) VALUES
    ((SELECT id FROM product WHERE name = '사과'), 2);

INSERT INTO product_stock (product_id, stock) VALUES
    ((SELECT id FROM product WHERE name = '바나나'), 2);

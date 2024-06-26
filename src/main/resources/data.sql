INSERT INTO member(email, password, name, phone_number, address, email_verified) VALUES ('user1@email.com', '$2a$10$ZJN6q89.OM4Ir55aK4dIxuLOO4yxmzbl5qs68j0VATnyXXrDVt7My', 'ryan', '010-1234-5678', 'addr', true);
INSERT INTO member(email, password, name, phone_number, address, email_verified) VALUES ('user2@email.com', '$2a$10$ZJN6q89.OM4Ir55aK4dIxuLOO4yxmzbl5qs68j0VATnyXXrDVt7My', 'ryan', '010-1234-5678', 'addr', true);
INSERT INTO user_roles(user_id, role) VALUES
                                          ((SELECT id FROM member WHERE email='user1@email.com'), 'USER'),
                                          ((SELECT id FROM member WHERE email='user1@email.com'), 'SELLER'),
                                          ((SELECT id FROM member WHERE email='user2@email.com'), 'USER');

INSERT INTO product (name, description, price, stock) VALUES
                                                          ('사과', '신선한 빨간 사과', 1200, 50),
                                                          ('바나나', '잘 익은 바나나', 800, 30),
                                                          ('오렌지', '비타민C가 풍부한 오렌지', 1500, 25),
                                                          ('포도', '달콤한 포도', 3000, 40),
                                                          ('딸기', '신선한 딸기', 4000, 20),
                                                          ('수박', '여름철 필수 수박', 18000, 10),
                                                          ('참외', '달콤한 참외', 2500, 15),
                                                          ('블루베리', '영양가 높은 블루베리', 5000, 35),
                                                          ('키위', '비타민C가 풍부한 키위', 1000, 50),
                                                          ('파인애플', '신선한 파인애플', 3500, 20),
                                                          ('망고', '달콤한 망고', 2000, 25),
                                                          ('복숭아', '향긋한 복숭아', 1500, 30),
                                                          ('레몬', '신선한 레몬', 1000, 40),
                                                          ('자몽', '상큼한 자몽', 2000, 20),
                                                          ('멜론', '달콤한 멜론', 3000, 15),
                                                          ('체리', '신선한 체리', 6000, 10),
                                                          ('라임', '새콤한 라임', 1200, 30),
                                                          ('아보카도', '영양가 높은 아보카도', 4500, 20),
                                                          ('파파야', '달콤한 파파야', 3000, 15),
                                                          ('코코넛', '신선한 코코넛', 4000, 10),
                                                          ('감', '달콤한 감', 2000, 35),
                                                          ('무화과', '신선한 무화과', 5000, 25),
                                                          ('석류', '영양가 높은 석류', 3500, 30),
                                                          ('망고스틴', '달콤한 망고스틴', 7000, 10),
                                                          ('두리안', '특이한 향을 가진 두리안', 8000, 5),
                                                          ('리치', '달콤한 리치', 2500, 20),
                                                          ('야자수', '신선한 야자수', 3000, 15),
                                                          ('구아바', '영양가 높은 구아바', 2000, 25),
                                                          ('드래곤 프룻', '이국적인 드래곤 프룻', 5000, 10),
                                                          ('카람볼라', '별 모양 카람볼라', 3000, 20),
                                                          ('패션 프룻', '열대 과일 패션 프룻', 3500, 15),
                                                          ('람부탄', '독특한 람부탄', 4000, 10),
                                                          ('산딸기', '신선한 산딸기', 2500, 30),
                                                          ('블랙베리', '영양가 높은 블랙베리', 4500, 20),
                                                          ('구스베리', '상큼한 구스베리', 5000, 15),
                                                          ('라즈베리', '달콤한 라즈베리', 6000, 25),
                                                          ('크랜베리', '신선한 크랜베리', 3500, 30),
                                                          ('뱀부', '이국적인 뱀부', 7000, 5),
                                                          ('체리모야', '달콤한 체리모야', 6500, 10),
                                                          ('페피노', '특이한 페피노', 5000, 15),
                                                          ('피타야', '이국적인 피타야', 3000, 20),
                                                          ('피스타치오', '영양가 높은 피스타치오', 8000, 10),
                                                          ('아몬드', '건강한 아몬드', 6000, 25),
                                                          ('호두', '영양가 높은 호두', 4500, 30),
                                                          ('캐슈넛', '달콤한 캐슈넛', 5000, 20),
                                                          ('피칸', '고소한 피칸', 7000, 15),
                                                          ('브라질넛', '영양가 높은 브라질넛', 8000, 10),
                                                          ('마카다미아', '달콤한 마카다미아', 9000, 5),
                                                          ('해바라기씨', '영양가 높은 해바라기씨', 3500, 30),
                                                          ('호박씨', '건강한 호박씨', 4000, 25),
                                                          ('참깨', '고소한 참깨', 2000, 40),
                                                          ('아마씨', '영양가 높은 아마씨', 4500, 20),
                                                          ('치아씨드', '건강한 치아씨드', 5000, 15),
                                                          ('햄프씨드', '영양가 높은 햄프씨드', 6000, 10),
                                                          ('들깨', '고소한 들깨', 2500, 30),
                                                          ('들깨가루', '영양가 높은 들깨가루', 3000, 20),
                                                          ('참깨가루', '고소한 참깨가루', 3500, 25),
                                                          ('콩가루', '신선한 콩가루', 2000, 40),
                                                          ('녹두가루', '영양가 높은 녹두가루', 4500, 20),
                                                          ('팥가루', '고소한 팥가루', 5000, 15),
                                                          ('쌀가루', '신선한 쌀가루', 3000, 30),
                                                          ('보리가루', '영양가 높은 보리가루', 4000, 25),
                                                          ('밀가루', '신선한 밀가루', 2000, 50),
                                                          ('귀리가루', '건강한 귀리가루', 4500, 20),
                                                          ('퀴노아', '영양가 높은 퀴노아', 5000, 15),
                                                          ('타피오카', '신선한 타피오카', 3000, 20),
                                                          ('아마란스', '건강한 아마란스', 3500, 25);

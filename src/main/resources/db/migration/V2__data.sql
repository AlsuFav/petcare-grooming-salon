INSERT INTO breed (name, breed_type) VALUES
                                         ('Другая маленькая порода собак', 'SMALL'),
                                         ('Другая средняя порода собак', 'MEDIUM'),
                                         ('Другая большая порода собак', 'LARGE'),
                                         ('Бигль', 'MEDIUM'),
                                         ('Бульдог', 'MEDIUM'),
                                         ('Доберман', 'LARGE'),
                                         ('Золотистый ретривер', 'LARGE'),
                                         ('Йоркширский терьер', 'SMALL'),
                                         ('Кокер-спаниель', 'MEDIUM'),
                                         ('Лабрадор', 'LARGE'),
                                         ('Мопс', 'SMALL'),
                                         ('Немецкая овчарка', 'LARGE'),
                                         ('Померанский шпиц', 'SMALL'),
                                         ('Ротвейлер', 'LARGE'),
                                         ('Сенбернар', 'LARGE'),
                                         ('Стаффордширский терьер', 'MEDIUM'),
                                         ('Такса', 'SMALL'),
                                         ('Чихуахуа', 'SMALL'),
                                         ('Ши-тцу', 'SMALL'),
                                         ('Шарпей', 'MEDIUM');

INSERT INTO service (name, description) VALUES
                                            ('Hygiene', 'Услуга включает стрижку когтей, вычесывание и бережное купание с использованием профессиональных средств для чистоты и здоровья вашего питомца. Идеально для регулярного поддержания гигиены!'),
                                            ('Grooming', 'Полный комплекс груминга: стрижка когтей, вычесывание, купание и профессиональная стрижка. Ваша собака или кошка получат уход, подчеркивающий их естественную красоту и поддерживающий здоровье шерсти и кожи.'),
                                            ('Grooming + Coloring', 'Полный комплекс груминга со стрижкой и окрашиванием. Безопасное временное окрашивание добавит яркости и индивидуальности вашему питомцу. Прекрасный вариант для особых случаев и фотосессий!');

INSERT INTO service_price (service_id, species, breed_type, price) VALUES
-- Hygiene
(1, 'собака', 'SMALL', 1100),
(1, 'собака', 'MEDIUM', 1300),
(1, 'собака', 'LARGE', 1500),
(1, 'кошка', NULL, 1200),

-- Grooming
(2, 'собака', 'SMALL', 1300),
(2, 'собака', 'MEDIUM', 1500),
(2, 'собака', 'LARGE', 1700),
(2, 'кошка', NULL, 1400),

-- Grooming + Coloring
(3, 'собака', 'SMALL', 1700),
(3, 'собака', 'MEDIUM', 1900),
(3, 'собака', 'LARGE', 2100),
(3, 'кошка', NULL, 1800);

INSERT INTO client (first_name, last_name, phone, password) VALUES
                                                                       ('Анна', 'Сидорова', '+7 (917) 299-64-49', '$2a$10$IALDVRSNwEjD1B/BdY2WpuHTnm5oPRR4T3IRJzCYPDtDprlFPQvSa'), --qwerty
                                                                       ('Иван', 'Петров', '+7 (917) 555-65-49', '$2a$10$IALDVRSNwEjD1B/BdY2WpuHTnm5oPRR4T3IRJzCYPDtDprlFPQvSa'), --qwerty
                                                                       ('Мария', 'Иванова', '+7 (917) 444-44-49', '$2a$10$IALDVRSNwEjD1B/BdY2WpuHTnm5oPRR4T3IRJzCYPDtDprlFPQvSa'), --qwerty
                                                                       ('Павел', 'Иванов', '+7 (917) 999-63-49', '$2a$10$IALDVRSNwEjD1B/BdY2WpuHTnm5oPRR4T3IRJzCYPDtDprlFPQvSa'); --qwerty
INSERT INTO groomer (first_name, last_name, career_start, email, phone) VALUES
                                                                            ('Иван', 'Иванов', '2020-01-01', 'ivan.groomer@example.com', '1112223333'),
                                                                            ('Мария', 'Петрова', '2019-05-15', 'maria.groomer@example.com', '4445556666');

INSERT INTO pet (name, species, breed_id, birth_date, owner_id) VALUES
                                                                    ('Бобик', 'собака', 4, '2018-06-15', 1),
                                                                    ('Хани', 'собака', 13, '2020-03-10', 2),
                                                                    ('Шарик', 'собака', 10, '2019-11-21', 3),
                                                                    ('Пух', 'кошка', NULL, '2021-05-05', 1);

INSERT INTO time_slot (groomer_id, start_time, end_time, taken) VALUES
                                                                    (1, '2025-04-01 09:00:00', '2025-04-01 11:00:00', false),
                                                                    (1, '2025-04-01 11:00:00', '2025-04-01 13:00:00', false),
                                                                    (1, '2025-04-01 13:00:00', '2025-04-01 15:00:00', false),
                                                                    (2, '2025-04-01 10:00:00', '2025-04-01 12:00:00', false),
                                                                    (2, '2025-04-01 12:00:00', '2025-04-01 14:00:00', false),
                                                                    (2, '2025-04-01 14:00:00', '2025-04-01 16:00:00', false);


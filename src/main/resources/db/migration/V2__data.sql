
-- Вставка видов животных
INSERT INTO species (name) VALUES
                               ('Кошка'),
                               ('Собака');

INSERT INTO breed_type (name) VALUES
                                  ('Декоративные породы'),
                                  ('Средние породы'),
                                  ('Крупные породы'),
                                  ('Триммингуемые породы');

-- Вставка пород собак с корректным breed_type_id
INSERT INTO breed (name, species_id, breed_type_id) VALUES
-- Декоративные породы
('Йоркширский терьер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Бивер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Бишон фризе', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Китайская хохлатая', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Мальтийская болонка', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Мопс', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Пекинес', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Пудель той', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Пудель карликовый', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Такса кроличья', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Такса карликовая', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Такса стандартная', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Чихуахуа', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Той-терьер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Ши-тцу', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),
('Шпиц', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Декоративные породы')),

-- Средние породы
('Бульдог английский', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Средние породы')),
('Бульдог французский', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Средние породы')),
('Бигль', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Средние породы')),
('Кавалер-кинг-чарльз-спаниель', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Средние породы')),
('Кокер-спаниель американский', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Средние породы')),
('Кокер-спаниель английский', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Средние породы')),
('Корги', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Средние породы')),
('Пудель малый', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Средние породы')),
('Русский охотничий спаниель', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Средние породы')),
('Сиба-ину', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Средние породы')),
('Стаффордширский терьер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Средние породы')),
('Шарпей', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Средние породы')),

-- Крупные породы
('Акита-ину', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Американская акита', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Боксер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Бордосский дог', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Доберман', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Золотистый ретривер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Кане-корсо', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Лабрадор', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Ньюфаундленд', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Овчарка немецкая', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Овчарка кавказская', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Овчарка среднеазиатская (алабай)', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Овчарка австралийская (аусси)', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Пудель королевский', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Ротвейлер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Самоед', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Сенбернар', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Хаски', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Чау-чау', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),
('Черный терьер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Крупные породы')),

-- Триммингуемые породы
('Вест-хайленд-уайт-терьер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Триммингуемые породы')),
('Гриффон', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Триммингуемые породы')),
('Джек-рассел терьер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Триммингуемые породы')),
('Миттельшнауцер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Триммингуемые породы')),
('Ризеншнауцер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Триммингуемые породы')),
('Такса жесткошерстная', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Триммингуемые породы')),
('Фокстерьер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Триммингуемые породы')),
('Цвергшнауцер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Триммингуемые породы')),
('Эрдельтерьер', (SELECT id FROM species WHERE name = 'Собака'), (SELECT id FROM breed_type WHERE name = 'Триммингуемые породы'));

INSERT INTO client (first_name, last_name, phone, password) VALUES
                                                                       ('Анна', 'Сидорова', '+7 (917) 299-64-49', '$2a$10$IALDVRSNwEjD1B/BdY2WpuHTnm5oPRR4T3IRJzCYPDtDprlFPQvSa'), --qwerty
                                                                       ('Иван', 'Петров', '+7 (917) 555-65-49', '$2a$10$IALDVRSNwEjD1B/BdY2WpuHTnm5oPRR4T3IRJzCYPDtDprlFPQvSa'), --qwerty
                                                                       ('Мария', 'Иванова', '+7 (917) 444-44-49', '$2a$10$IALDVRSNwEjD1B/BdY2WpuHTnm5oPRR4T3IRJzCYPDtDprlFPQvSa'), --qwerty
                                                                       ('Павел', 'Иванов', '+7 (917) 999-63-49', '$2a$10$IALDVRSNwEjD1B/BdY2WpuHTnm5oPRR4T3IRJzCYPDtDprlFPQvSa'); --qwerty

INSERT INTO groomer (first_name, last_name, career_start, email, phone) VALUES
                                                                            ('Иван', 'Иванов', '2020-01-01', 'ivan.groomer@example.com', '1112223333'),
                                                                            ('Мария', 'Петрова', '2019-05-15', 'maria.groomer@example.com', '4445556666');

INSERT INTO time_slot (groomer_id, start_time, end_time, taken) VALUES
                                                                    (1, '2025-04-01 09:00:00', '2025-04-01 11:00:00', false),
                                                                    (1, '2025-04-01 11:00:00', '2025-04-01 13:00:00', false),
                                                                    (1, '2025-04-01 13:00:00', '2025-04-01 15:00:00', false),
                                                                    (2, '2025-04-01 10:00:00', '2025-04-01 12:00:00', false),
                                                                    (2, '2025-04-01 12:00:00', '2025-04-01 14:00:00', false),
                                                                    (2, '2025-04-01 14:00:00', '2025-04-01 16:00:00', false);


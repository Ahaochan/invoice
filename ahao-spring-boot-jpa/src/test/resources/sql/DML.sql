TRUNCATE TABLE user;

INSERT INTO user (id, username, email, password, salt, sex, create_by, update_by, create_time, update_time)
VALUES
(1, 'user1', '1@qq.com', 'pw1', 'salt1', 1, -1, -1, '2019-11-11 00:00:00', now()),
(2, 'user2', '2@qq.com', 'pw2', 'salt2', 1, -1, -1, '2019-11-12 00:00:00', now()),
(3, 'user3', '3@qq.com', 'pw3', 'salt3', 2, -1, -1, '2019-11-13 00:00:00', now()),
(4, 'user4', '4@qq.com', 'pw4', 'salt4', 2, -1, -1, '2019-11-14 00:00:00', now()),
(5, 'user5', '5@qq.com', 'pw5', 'salt5', 2, -1, -1, '2019-11-15 00:00:00', now());

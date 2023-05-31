INSERT INTO `users` (`id`, `age`, `password`, `firstname`, `lastname`, `username`)
VALUES ('101', '18', '$2a$10$IMX8ArS1lYpEaiWLVG4z2uXUelP9BHHgQklcq8aLerJKmk9qn6HS.', 'admin_test', '-', 'admin@mail.ru');
--login admin@mail.ru
--password admin

INSERT INTO `users` (`id`, `age`, `password`, `firstname`, `lastname`, `username`)
VALUES ('102', '20', '$2a$12$nFrjTD/jzyNROVIXhCrUKOoGEk5b3WgKVVfkSV709z4XFVfAtjjKu', 'user_test', '-', 'user@mail.ru');
--login user@mail.ru
--password user

INSERT INTO `roles` (`id`, `name`)
VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `roles` (`id`, `name`)
VALUES ('2', 'ROLE_USER');

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES ('101', '1');
INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES ('102', '2');
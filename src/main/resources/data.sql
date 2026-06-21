INSERT INTO user_roles (name, description)
VALUES ('ADMIN', 'can do everything');

INSERT INTO user_roles (name, description)
VALUES ('MEMBER', 'has limited privileges')

INSERT INTO users (username, password, email, roles)
VALUES ('admin', '{bcrypt}$2a$12$ITZPVKc7LJnxlgaZ2p63TOsCiP.MEyknSlvrTZ6HekZoDqnHXRyGe', 'admin@example.com');

INSERT INTO users_roles_mapping (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles_mapping (user_id, role_id) VALUES (1, 2);

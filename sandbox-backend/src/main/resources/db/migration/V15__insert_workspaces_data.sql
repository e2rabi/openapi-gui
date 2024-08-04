INSERT INTO
    workspace (name, description, enabled, visibility, created, last_modified, created_by, last_modified_by, version)
VALUES
    ('Workspace Alpha', 'Description for Workspace Alpha', true, true, NOW(), NOW(), 'admin', 'admin', 1),
    ('Workspace Beta', 'Description for Workspace Beta', false, true, NOW(), NOW(), 'admin', 'admin', 2),
    ('Workspace Gamma', 'Description for Workspace Gamma', true, false, NOW(), NOW(), 'admin', 'admin', 3),
    ('Workspace Delta', 'Description for Workspace Delta', false, false, NOW(), NOW(), 'admin', 'admin', 4),
    ('Workspace Epsilon', 'Description for Workspace Epsilon', true, true, NOW(), NOW(), 'admin', 'admin', 5);
create table employee
(
    employee_id serial,
    first_name  varchar(255) not null,
    last_name   varchar(255) not null,
    middle_name varchar(255),
    position    varchar(255),
    account     varchar(255),
    email       varchar(255),
    status      varchar(255) not null,
    primary key (employee_id)
);

CREATE UNIQUE INDEX employee_unique_account
    ON employee (account)
    WHERE status = 'ACTIVE';

create table project
(
    project_id  serial,
    code        varchar(255) unique not null,
    name        varchar(255)        not null,
    description text,
    status      varchar(255)        not null,
    primary key (project_id)
);


create table project_member
(
    project_member_id serial,
    project_id        int          not null,
    employee_id       int          not null,
    role              varchar(255) not null,
    primary key (project_member_id),
    foreign key (project_id) references project (project_id) on delete cascade,
    foreign key (employee_id) references employee (employee_id) on delete cascade
);

create table task
(
    task_id          serial,
    name             varchar(255) not null,
    description      text,
    performer_id     int          not null,
    estimated_hours  int          not null,
    deadline         timestamp    not null,
    status           varchar(255) not null,
    author_id        int          not null,
    creation_date    timestamp default current_timestamp,
    last_update_date timestamp default current_timestamp,
    primary key (task_id),
    foreign key (performer_id) references employee (employee_id) on delete cascade,
    foreign key (author_id) references project_member (project_member_id) on delete cascade
);

create table task_file
(
    task_file_id      serial,
    task_id           int unique   not null,
    file              bytea        not null,
    file_content_type varchar(255) not null,
    primary key (task_file_id),
    foreign key (task_id) references task (task_id) on delete cascade
);

create table project_file
(
    project_file_id   serial,
    project_id        int unique   not null,
    file              bytea        not null,
    file_content_type varchar(255) not null,
    primary key (project_file_id),
    foreign key (project_id) references project (project_id) on delete cascade
);

create table task_to_related_task
(
    relationship_id serial,
    task_id         int not null,
    related_task_id int not null,
    primary key (relationship_id),
    foreign key (task_id) references task (task_id) on delete cascade,
    foreign key (related_task_id) references task (task_id) on delete cascade
);



INSERT INTO employee (first_name, last_name, middle_name, position, account, email, status)
VALUES ('John', 'Doe', 'Michael', 'Manager', 'jdoe123', 'jdoe@example.com', 'ACTIVE'),
       ('Jane', 'Smith', NULL, 'Assistant', 'jsmith456', 'jsmith@example.com', 'ACTIVE'),
       ('Robert', 'Johnson', 'William', 'Clerk', 'rjohnson789', 'rjohnson@example.com', 'DELETED'),
       ('dmitriy', 'medvedev', 'anatol''evich', 'smth', null, 'ttl1de@ya.ru', 'ACTIVE'),
       ('David', 'Wilson', 'Thomas', 'Clerk', 'dwilson123', 'dwilson@example.com', 'DELETED'),
       ('Emily', 'Taylor', 'Grace', 'Manager', 'etaylor456', 'etaylor@example.com', 'DELETED'),
       ('Sarah', 'Anderson', 'Elizabeth', 'Supervisor', 'sanders456', 'sanders@example.com', 'ACTIVE');

INSERT INTO project (code, name, description, status)
VALUES ('P001', 'Project 1', 'This is project 1', 'DRAFT'),
       ('P002', 'Project 2', null, 'IN_PROGRESS'),
       ('P003', 'Project 3', 'This is project 3', 'IN_TESTING'),
       ('P004', 'Project 4', 'This project has a very long description...', 'COMPLETED'),
       ('P005', 'Project 5', 'This is project 5', 'IN_PROGRESS');

INSERT INTO project_member (project_id, employee_id, role)
VALUES (1, 1, 'DEVELOPER'),
       (1, 2, 'TESTER'),
       (2, 3, 'LEAD'),
       (3, 4, 'ANALYST'),
       (4, 5, 'DEVELOPER'),
       (5, 6, 'TESTER');

INSERT INTO task(name, description, performer_id, estimated_hours, deadline, status, author_id)
VALUES ('Task 1', 'Description for Task 1', 1, 8, '2024-06-30 12:00:00', 'NEW', 2),
       ('Task 2', 'A short description', 3, 4, '2024-07-15 14:30:00', 'IN_PROGRESS', 4),
       ('Task 3',
        'This is a longer description for Task 3. It provides more details about the task requirements and expectations.',
        5, 10, '2023-07-31 09:00:00', 'CLOSED', 1),
       ('Task 4', 'descdldlldakd[osdjgfopfdsj', 4, 12, '2025-07-31 09:00:00', 'NEW', 3);

INSERT INTO task_to_related_task(task_id, related_task_id)
VALUES (1, 2),
       (1, 3),
       (1, 4),
       (3, 2);
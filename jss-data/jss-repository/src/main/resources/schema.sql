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
    task_to_related_task_id serial,
    task_id                 int not null,
    related_task_id         int not null,
    primary key (task_to_related_task_id),
    foreign key (task_id) references task (task_id) on delete cascade,
    foreign key (related_task_id) references task (task_id) on delete cascade
);

CREATE OR REPLACE FUNCTION update_last_modified()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.last_update_date = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER task_last_modified
    BEFORE UPDATE
    ON task
    FOR EACH ROW
EXECUTE FUNCTION update_last_modified();

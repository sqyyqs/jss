create table employee
(
    employee_id serial,
    first_name  varchar(255) not null,
    last_name   varchar(255) not null,
    middle_name varchar(255),
    position    varchar(255),
    account     json,
    email       varchar(255),
    status      varchar(50)  not null,
    primary key (employee_id)
);

create table project
(
    project_id  serial,
    code        varchar(255) unique not null,
    name        varchar(255)        not null,
    description text,
    status      varchar(50)         not null,
    primary key (project_id)
);

create table project_member
(
    project_member_id serial,
    project_id        int         not null,
    employee_id       int         not null,
    role              varchar(50) not null,
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
    status           varchar(50)  not null,
    author_id        int          not null,
    creation_date    timestamp    not null,
    last_update_date timestamp    not null,
    primary key (task_id),
    foreign key (performer_id) references employee (employee_id) on delete set null,
    foreign key (author_id) references project_member (project_member_id) on delete set null
);
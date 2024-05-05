create table if not exists test_table (
    id serial primary key,
    name text not null
);

create table if not exists users (
    user_id serial primary key,
    username text unique not null,
    email text unique not null,
    password text unique not null,
    roles text unique not null
);

create table if not exists professors (
    professor_id bigint primary key,
    first_name text not null,
    last_name text not null,
    position text not null,
--     email text not null,
    foreign key (professor_id)
        references users (user_id) on delete cascade
--     foreign key (email)
--         references users (email) on delete cascade,
);

create table if not exists subjects (
    subject_id serial primary key,
    name text not null
);

create table if not exists study_groups (
    study_group_id serial primary key,
    grade int not null check ( grade > 0 and grade <= 6 ), -- ili 11
    name text not null
);

create table if not exists students (
    student_id bigint primary key,
    study_group_id bigint,
    first_name text not null,
    last_name text not null,
--     email text not null,
    foreign key (study_group_id)
        references study_groups (study_group_id),

--     foreign key (email)
--         references users (email) on delete cascade,

    foreign key (student_id)
        references users (user_id) on delete cascade
);

-- CREATE TABLE IF NOT EXISTS study_group_students (
--     study_group_id bigint,
--     student_id bigint,
--     FOREIGN KEY (study_group_id) REFERENCES study_groups (study_group_id) ON DELETE CASCADE,
--     FOREIGN KEY (student_id) REFERENCES students (student_id) ON DELETE CASCADE
-- );


-- test  test test test  test testtest  test testtest  test testtest  test testtest  test testtest  test test
-- create table if not exists courses ( -- какой год какой семестр и тд
--     course_id serial primary key,
--     course_name text not null,
--     start_date date not null,
--     end_date date not null
-- );

create table if not exists semesters (
    semester_id serial primary key,
--     course_id bigint not null,
    semester_name text not null,
    start_date date not null,
    end_date date not null
--     foreign key (course_id) references courses (course_id) on delete cascade
);

create table if not exists subject_details (
    subject_details_id serial primary key,
    study_group_id bigint not null,
    professor_id bigint,
    subject_id bigint not null,
    semester_id bigint not null,

    constraint subject_details_unique unique (study_group_id, subject_id, semester_id),

    foreign key (study_group_id)
        references study_groups (study_group_id) on delete cascade,
    foreign key (subject_id)
        references subjects (subject_id) on delete cascade,
    foreign key (semester_id)
        references semesters (semester_id) on delete cascade,
    foreign key (professor_id)
        references professors (professor_id) on delete cascade
);

-- create table if not exists topics (
--     topic_id serial primary key,
--     subject_details_id bigint not null,
--     topic_name text not null,
--     foreign key (subject_details_id)
--         references subject_details (subject_details_id) on delete cascade
-- );

create table if not exists lessons (
    lesson_id serial primary key,
    lesson_date date not null,
    topic text not null,

    subject_id BIGINT,
    study_group_id BIGINT,
    professor_id BIGINT,
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id),
    FOREIGN KEY (study_group_id) REFERENCES study_groups(study_group_id),
    FOREIGN KEY (professor_id) REFERENCES professors(professor_id)
--     foreign key (topic_id)
--        references topics (topic_id) on delete cascade
);

create table if not exists marks (
    mark_id serial primary key,
    student_id bigint not null,
    lesson_id bigint not null,
    mark integer not null check ( mark > 0 and mark <= 10 ),

    constraint mark_unique unique (student_id, lesson_id),

    foreign key (student_id)
        references students (student_id) on delete cascade,
    foreign key (lesson_id)
        references lessons (lesson_id) on delete cascade
);

-- create table if not exists absents (
--     absent_id serial primary key,
--     student_id bigint not null,
--     lesson_id bigint not null,
--
--     constraint absent_unique unique (student_id, lesson_id),
--
--     foreign key (student_id)
--         references students (student_id) on delete cascade,
--     foreign key (lesson_id)
--         references lessons (lesson_id) on delete cascade
-- );

CREATE TABLE IF NOT EXISTS attendances (
    attendance_id serial PRIMARY KEY,
    student_id BIGINT NOT NULL,
    lesson_id BIGINT NOT NULL,
    status text NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students (student_id),
    FOREIGN KEY (lesson_id) REFERENCES lessons (lesson_id)
);



-- INSERT INTO test_table (name) VALUES ('testkazuha');

CREATE TABLE course_1
(
    cid     BIGINT PRIMARY KEY,
    cname   VARCHAR(50) NOT NULL,
    user_id BIGINT      NOT NULL,
    cstatus VARCHAR(10) NOT NULL
);

CREATE TABLE course_2
(
    cid     BIGINT PRIMARY KEY,
    cname   VARCHAR(50) NOT NULL,
    user_id BIGINT      NOT NULL,
    cstatus VARCHAR(10) NOT NULL
);
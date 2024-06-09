CREATE TABLE exhibition
(
    exhibition_id int          NOT NULL AUTO_INCREMENT,
    title         varchar(100) NOT NULL,
    subHeading    varchar(200) NOT NULL,
    location      varchar(100) NOT NULL,
    start_date    date         NOT NULL,
    end_date      date         NOT NULL,
    status        varchar(100) NOT NULL,
    poster        varchar(100) NOT NULL,
    is_display    int          NOT NULL,
    PRIMARY KEY (exhibition_id)
);

CREATE TABLE artist
(
    name               varchar(50)   DEFAULT NULL,
    profile            varchar(50)   DEFAULT NULL,
    introduce          varchar(50)   DEFAULT NULL,
    description        varchar(1024) DEFAULT NULL,
    instagram_id       varchar(50)   DEFAULT NULL,
    is_display         int           DEFAULT NULL,
    view               int           DEFAULT NULL,
    is_about           int           DEFAULT NULL,
    collaboration_date varchar(50)   DEFAULT NULL,
    PRIMARY KEY (instagram_id)
);

CREATE TABLE product
(
    product_id    int NOT NULL auto_increment,
    picture       varchar(50)   DEFAULT NULL,
    title         varchar(50)   DEFAULT NULL,
    artist_name   varchar(50)   DEFAULT NULL,
    description   varchar(1024) DEFAULT NULL,
    price         int           DEFAULT NULL,
    basic_view    int           DEFAULT NULL,
    qr_view       int           DEFAULT NULL,
    like_count    int           DEFAULT NULL,
    order_count   int           DEFAULT NULL,
    PRIMARY KEY (product_id)
);

CREATE TABLE product_with_tag
(
    product_id int DEFAULT NULL,
    tag_id     int DEFAULT NULL
);

CREATE TABLE tag
(
    tag_id int         DEFAULT NULL,
    name   varchar(50) DEFAULT NULL
);


CREATE TABLE product_exhibition
(
    product_id    int NOT NULL,
    exhibition_id int NOT NULL,
    created_at datetime not null default CURRENT_TIMESTAMP,
    updated_at datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table `picture_info`
(
    product_id int not null
        primary key,
    `type`       varchar(100) null,
    `size`       varchar(100) null,
    `year`       int null
);
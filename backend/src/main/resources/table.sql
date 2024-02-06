#member
    create table member (
        id int auto_increment not null,
        nickname varchar(100),
        email varchar(100) not null,
        profile_image_url varchar(1000),
        primary key(id)
    )
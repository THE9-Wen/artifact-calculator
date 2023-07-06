create table if not exists Artifact
(
    id       int  not null primary key auto_increment ,
    position int  not null,
    suit     int  not null,
    subs     text not null,
    main     text not null
);

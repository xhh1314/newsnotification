create database notification default charset utf-8;
create table users(
  id int(11) auto_increment,
  name varchar(255) not null,
  password varchar(255) not null,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  primary key(id)
);
create unique index uk_name on users(name(20)); 

create table content(
 id int(11) auto_increment,
 title varchar(255) not null,
 content text,
 receive_time date,
 create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 primary key(id)
);
create index idx_title on content(title(20));
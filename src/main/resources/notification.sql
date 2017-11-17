create database newsnotification default charset utf8;
use newsnotification;
create table users(
  id int(11) auto_increment,
  name varchar(255) not null,
  password varchar(255) not null,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  primary key(id)
);
create unique index uk_name on users(name(20)); 
insert into users(name,password) values('admin','gdyb21LQTcIANtvYMT7QVQ==');
create table content(
 id int(11) auto_increment,
 title varchar(255) not null,
 content text,
 receive_time date,
 status TINYINT(1) default 0,
 create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 primary key(id)
);
create index idx_title on content(title(20));
create table tag(
id int(11) auto_increment,
name varchar(255) not null,
create_time TIMESTAMP DEFAULT current_timestamp,
primary key(id)
);
create table content_tag(
id int(11) auto_increment,
c_id int(11) not null,
t_id int(11) not null,
primary key(id),
constraint fk_tag foreign key(t_id) references tag(id),
constraint fk_content foreign key(c_id) references content(id)
);


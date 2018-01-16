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


create table module(
  mid int(11) auto_increment PRIMARY key,
  name VARCHAR(255) not null
);
create table role(
  rid int(11) auto_increment primary key,
  name varchar(100) not null
);
create table role_module(
  mid int(11) not null,
  rid int(11) not null,
  PRIMARY key(mid,rid)
);
create table user_role(
  uid int(11) not null,
  rid int(11) not null,
  PRIMARY key(uid,rid)
);

INSERT INTO `module` VALUES ('1', 'add');
INSERT INTO `module` VALUES ('2', 'delete');
INSERT INTO `module` VALUES ('3', 'query');
INSERT INTO `module` VALUES ('4', 'update');

INSERT INTO `role_module` VALUES ('1', '1');
INSERT INTO `role_module` VALUES ('1', '2');
INSERT INTO `role_module` VALUES ('1', '3');
INSERT INTO `role_module` VALUES ('1', '4');
INSERT INTO `role_module` VALUES ('2', '1');
INSERT INTO `role_module` VALUES ('2', '3');

INSERT INTO `role` VALUES ('1', 'admin');
INSERT INTO `role` VALUES ('2', 'customer');

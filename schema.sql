use surabirestaurant;

create table users(
      username varchar(50) not null primary key,
      password varchar(200) not null,
      enabled boolean not null
);

create table authorities (
      username varchar(50) not null,
      authority varchar(50) not null,
      constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

create table restaurant_item ( 
   item_id int primary key not null auto_increment, 
   item_name varchar(50) not null, 
   item_price decimal 
);

create table restaurant_order_details(
order_id int primary key not null  auto_increment,
order_status varchar(50) not null,
order_bill_amount decimal not null,
order_user_name varchar(50) not null,
order_date timestamp not null,
constraint fk_restaurant_order_user foreign key(order_user_name) references users(username)
);

create table restaurant_item_order_details(
itor_id int primary key not null auto_increment,
itor_order_id int not null,      
itor_item_id int not null,
itor_price decimal not null,
constraint fk_restaurant_order_item foreign key(itor_item_id) references restaurant_item(item_id)
);

create table audit_log(
id int  primary key not null  auto_increment,
create_date date not null,
description varchar(4000) not null
); 

commit;
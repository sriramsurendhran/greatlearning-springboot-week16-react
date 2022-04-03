insert into restaurant_item(item_name,item_price) values('burger','100');
insert into restaurant_item(item_name,item_price) values('pizza','20');
insert into restaurant_item(item_name,item_price) values('french fries','50');


insert into users(username,password,enabled)
values('sriram','$2a$12$jTT4Th3ivohI9N/1.ym9B.WguNmn4.iRowRCkcwB4fxSBSgxkKFw2',true);

insert into authorities(username,authority)
values('sriram','ROLE_ADMIN');
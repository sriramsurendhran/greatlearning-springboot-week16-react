use surabirestaurant;

delete from authorities;
delete from users;
delete from restaurant_item_order_details;
delete from restaurant_order_details;
delete from restaurant_item;
delete from audit_log;

commit;
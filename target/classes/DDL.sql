create table if not exists user(
	userid varchar(100),
	password varchar(100),
	email varchar(100),
	name varchar(100),
	address varchar(255),
	join_date DATE,
		level varchar(20) default 'member'
);
alter table user add primary key (userid);


create table if not exists auth_info(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id varchar(100) not null,
	expire_date date,
	refresh_token varchar(255) not null
);

alter table auth_info add foreign key(user_id) references user(userid);	


create table if not exists product(
	product_id int not null auto_increment primary key,
	category_id int not null,
	product_name varchar(255) not null,
	sub_product_name varchar(255) not null,
	price int not null,
	product_desc text not null,
	keyword varchar(255) not null,
	product_image varchar(255) not null,
	update_date date
);

create table if not exists category(
	category_id int not null auto_increment primary key,
    category varchar(50) not null
    );
    
alter table product add foreign key(category_id) references category(category_id);
    
create table if not exists product_information(
	product_option_id int not null auto_increment primary key,
    product_id int not null,
    size varchar(100) not null,
    color varchar(100) not null,
    stock int not null
    );
alter table product_information add foreign key(product_id) references product(product_id);

 create table if not exists cart(
	product_option_id int not null primary key,
	userid varchar(100) not null,
    quantity int not null,
    last_update_time timestamp
    );
alter table cart add foreign key(product_option_id) references product_information(product_option_id);
alter table cart add foreign key(userid) references user(userid);

create table if not exists order_info(
	order_id int not null auto_increment primary key,
    userid varchar(100) not null,
    phone_number varchar(50) not null,
    shipment_address varchar(100) not null,
    order_state varchar(50) default 'pending',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    whole_price int not null
    );

alter table order_info add foreign key(userid) references user(userid);

create table if not exists order_table(
product_option_id int not null,
order_id int not null,
quantity int not null
);
alter table order_table add foreign key(product_option_id) references product_information(product_option_id);
alter table order_table add foreign key(order_id) references order_info(order_id);

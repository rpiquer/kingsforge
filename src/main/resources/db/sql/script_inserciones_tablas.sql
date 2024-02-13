SET MODE MySQL;

create table supplier(
	supplier_id INT(5) PRIMARY KEY NOT NULL,
	contactPhone CHAR(15),
	description_es VARCHAR(1000), 
	name VARCHAR(100),
	description_en VARCHAR(1000), 
	homePage VARCHAR(50), 
	postalCode INT(15), 
	street VARCHAR(400), 
	number INT(10), 
	city VARCHAR(20), 
	country VARCHAR(20)
	);

create table product(
	product_id INT(5) PRIMARY KEY NOT NULL,
	description_es VARCHAR(1000), 
	description_en VARCHAR(1000), 
	name_es VARCHAR(100), 
	name_en VARCHAR(100),
	price INT(10),
	stock INT(10), 
	supplier_id INT(5), 
	category_id INT(5)
	);


create table product_line(
	quantity INT(5),
	unitPrice INT(5),
	product_id INT(5),
	cart_id INT(5),
	primary key (product_id, cart_id)
	);
    


create table cart(
	cart_id INT(5) PRIMARY KEY NOT NULL AUTO_INCREMENT, 
	dateCreated DATE, 
	datePayed DATE,
	payed BOOLEAN,
	customer_id INT(5) 
	);
    
create table customer(
	customer_id INT(5) PRIMARY KEY NOT NULL AUTO_INCREMENT, 
	firstName VARCHAR(100), 
	surname1 VARCHAR(100), 
	surname2 VARCHAR(100),
	username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(1000) NOT NULL,
	email VARCHAR(100), 
	phone INT(100), 
	street VARCHAR(100), 
	number INT(5), 
	postalCode INT(10), 
	city VARCHAR(20), 
	country VARCHAR(20)
	);


create table category(
	category_id INT(15) PRIMARY KEY NOT NULL, 
	name_es VARCHAR(100), 
	name_en VARCHAR(100), 
	father_id INT(15)
	);





alter table product add FOREIGN KEY (category_id) REFERENCES category(category_id);

alter table product_line add FOREIGN KEY (product_id) REFERENCES product(product_id);

alter table product_line add FOREIGN KEY (cart_id) REFERENCES cart(cart_id);

alter table cart add FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

alter table product add FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id);

alter table category add FOREIGN KEY (father_id) REFERENCES category(category_id);
insert into category values (0,'prueba','test',0);

insert into category values (1, 'name_es1', 'name_en1', 0);
insert into category values (2, 'name_es2', 'name_en2', 0);

insert into category values (11, 'name_es1', 'name_en1', 1);
insert into category values (12, 'name_es2', 'name_en2', 1);

insert into supplier (supplier_id, contactPhone, description_es, name, description_en, homePage, postalCode, street, number, city, country)  VALUES(1, 1, 'descrpcio_es1', 'name1', 'descripcion_en1', 'homepage1', 1, 'street1', '1', 'city1', 'country1');
insert into supplier (supplier_id, contactPhone, description_es, name, description_en, homePage, postalCode, street, number, city, country) VALUES(2, 2, 'descrpcio_es2', 'name2', 'descripcion_en2', 'homepage2', 2, 'street2', '2', 'city2', 'country2');

insert into product (product_id, stock, price, name_es, description_es, category_id, name_en, description_en, supplier_id) values(1, 1, 1, 'name_es1', 'descripcion_es1', 1, 'name_en1', 'descripcion_en1', 1);
insert into product (product_id, stock, price, name_es, description_es, category_id, name_en, description_en, supplier_id) values(2, 2, 2, 'name_es2', 'descripcion_es2', 2, 'name_en2', 'descripcion_en2', 2);
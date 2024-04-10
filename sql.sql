DROP DATABASE IF EXISTS `dbclpm`;
CREATE DATABASE `dbclpm`;
USE `dbclpm`;
CREATE TABLE `roles` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255)
);
insert into roles
values(1,"customer"),
(2,"employee"),
(3,"manager");
CREATE TABLE `address` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    details_address VARCHAR(255),
    province_or_city VARCHAR(255),
    district VARCHAR(255),
    wards VARCHAR(255)
);
insert into address values
(1,"44 Đường Yên Phụ","Hà Nội","Ba Đình","Trúc Bạch"),
(2,"66 Đường Yên Phụ","Hà Nội","Ba Đình","Trúc Bạch");
CREATE TABLE base (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    ordinal_numbers VARCHAR(255),
    num_of_customer bigint,
    address_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES Address(id)
);
insert into base values
(1,"Công ty TNHH Nước sạch Hà Nội","123",0,1);
CREATE TABLE info_common (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    phone_number VARCHAR(255),
    name VARCHAR(255)
);
insert into info_common
values(1,"nguyenvanemployee@gmail.com","0111111111","Nguyễn Văn Employee");
-- (2,"nguyenvanb@gmail.com","0222222222","Nguyễn Văn B"),
-- (3,"nguyenvanc@gmail.com","0333333333","Nguyễn Văn C"),
-- (4,"nguyenvanc@gmail.com","0444444444","Nguyễn Văn D"),
-- (5,"nguyenvanc@gmail.com","0555555555","Nguyễn Văn E"),
-- (6,"nguyenvanc@gmail.com","0666666666","Nguyễn Văn F");
CREATE TABLE residential_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);
CREATE TABLE customer (
    id BIGINT auto_increment primary KEY,
    ordinal_numbers VARCHAR(255),
    water_index bigint,
    base_id BIGINT,
    info_common_id BIGINT,
    residential_type_id BIGINT,
    address_id BIGINT,
    actived boolean,
    front_image longtext,
    back_image longtext,
    certificate_of_poverty longtext,
    FOREIGN KEY (base_id) REFERENCES base(id),
    FOREIGN KEY (info_common_id) REFERENCES info_common(id),
    FOREIGN KEY (residential_type_id) REFERENCES residential_type(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);
create table `employee`(
	id bigint auto_increment primary key,
    base_id bigint,
    info_common_id bigint,
    address_id bigint,
    FOREIGN KEY (base_id) REFERENCES base(id),
     FOREIGN KEY (info_common_id) REFERENCES info_common(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);
insert into dbclpm.employee
values(1,1,1,2);
CREATE TABLE `users` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    customer_id bigint,
    employee_id bigint,
    role_id int,
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
	FOREIGN KEY (employee_id) REFERENCES employee(id)
);
insert into dbclpm.users (id,username,password,customer_id,employee_id,role_id)
values(1,"manager","123456",null,null,3),
(2,"employee","123456",null,1,2);
CREATE TABLE formula (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    price_smaller_or_equal_to_10m3 BIGINT,
    price_from_10m3_to_20m3 BIGINT,
    price_from_20m3_to_30m3 BIGINT,
    price_greater_or_equal_30m3 BIGINT,
    vat_tax_per DOUBLE,
    bvmt_tax DOUBLE,
    base_id BIGINT,
    actived boolean,
    residential_type_id BIGINT,
    FOREIGN KEY (base_id) REFERENCES base(id),
    FOREIGN KEY (residential_type_id) REFERENCES residential_type(id)
);

CREATE TABLE invoice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    new_number BIGINT,
    old_number BIGINT,
    period DATE,
    total_all BIGINT,
    total_tax BIGINT,
    total_no_tax BIGINT,
    status VARCHAR(255),
    customer_id BIGINT,
    formula_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (formula_id) REFERENCES formula(id)
);

insert into residential_type
values (1,"Hộ dân cư"),
(2,"Hộ nghèo, cận nghèo"),
(3,"Cơ quan hành chính, sự nghiệp"),
(4,"Đơn vị sự nghiệp, phục vụ công cộng"),
(5,"Doanh nghiệp sản xuẩt"),
(6,"Kinh doanh - Dịch vụ");

insert into formula values
(1,8500,9900,16000,27000,5,10,1,1,true),
(2,5973,9900,16000,27000,5,10,1,2,true),
(3,13500,13500,13500,13500,5,10,1,3,true),
(4,13500,13500,13500,13500,5,10,1,4,true),
(5,16000,16000,16000,16000,5,10,1,5,true),
(6,29000,29000,29000,29000,5,10,1,6,true);

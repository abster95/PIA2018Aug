-- we don't know how to generate schema pia (class Schema) :(
create table bus
(
	id int auto_increment
		primary key,
	model varchar(50) not null,
	creator varchar(50) not null,
	capacity int not null
)
;

create table bus_companys
(
	id int auto_increment
		primary key,
	name varchar(50) not null,
	number varchar(50) not null,
	adrerss varchar(50) not null,
	logo varchar(255) not null
)
;

create table bus_pictures
(
	id int not null,
	bus_id int not null,
	name varchar(255) not null,
	constraint bus_pictures_bus_id_fk
		foreign key (bus_id) references bus (id)
			on update cascade on delete cascade
)
;

create table city_line
(
	id int auto_increment
		primary key,
	bus_id int not null,
	first_station varchar(50) not null,
	other_stations text not null comment 'Same as for inter city lines, One Query to display them all, One Query to search them all, One Query to fetch them all and to variable bind them. 
In the land of ETF where teaching assistants lie.',
	last_station int not null,
	departure_times text not null comment 'Contains departure times, in format hh:mm, hh:mm, hh:mm.',
	constraint city_line_bus_id_fk
		foreign key (bus_id) references bus (id)
			on update cascade on delete cascade
)
;

create table driver
(
	id int auto_increment
		primary key,
	firstname varchar(50) not null,
	lastname varchar(50) not null,
	dob date not null comment 'Date of birth.',
	started_driving date not null
)
;

create table employment
(
	id int auto_increment
		primary key,
	name varchar(50) not null,
	discount int not null
)
;

create table inter_city_line
(
	id int auto_increment
		primary key,
	bus_company_id int not null,
	bus_id int not null,
	driver_id int not null,
	departure datetime not null,
	first_station varchar(50) not null,
	arrival datetime not null,
	last_station varchar(50) null,
	other_stations text null comment 'Text which contains all stations, so we can search it with one query. Since we won''t be doing advance features don''t need it to be too good.
It shold store something like station1,station2,station3 and we query it.',
	is_active tinyint(1) default '1' not null,
	constraint inter_city_line_bus_companys_id_fk
		foreign key (bus_company_id) references bus_companys (id)
			on update cascade on delete cascade,
	constraint inter_city_line_bus_id_fk
		foreign key (bus_id) references bus (id)
			on update cascade on delete cascade,
	constraint inter_city_line_driver_id_fk
		foreign key (driver_id) references driver (id)
			on update cascade on delete cascade
)
;

create table user
(
	id int auto_increment
		primary key,
	firstname varchar(50) not null,
	lastname varchar(50) not null,
	username varchar(50) not null,
	pass varchar(255) not null comment 'Dont need to hash it, only if you want to.',
	adress varchar(50) not null,
	dob date null comment 'Date of birth',
	phone varchar(50) not null,
	emplotment_cat_id int not null comment 'Id which points to category employment category.',
	email int null,
	constraint user_username_uindex
		unique (username),
	constraint user_employment_id_fk
		foreign key (emplotment_cat_id) references employment (id)
			on update cascade on delete cascade
)
;

create table monthly_cards
(
	id int auto_increment
		primary key,
	user_id int not null,
	line_id int not null,
	constraint monthly_cards_city_line_id_fk
		foreign key (line_id) references city_line (id)
			on update cascade on delete cascade,
	constraint monthly_cards_user_id_fk
		foreign key (user_id) references user (id)
			on update cascade on delete cascade
)
;

create table reservations
(
	id int auto_increment
		primary key,
	user_id int not null,
	number_of_cards int not null,
	line_id int not null,
	constraint reservations_inter_city_line_id_fk
		foreign key (line_id) references inter_city_line (id)
			on update cascade on delete cascade,
	constraint reservations_user_id_fk
		foreign key (user_id) references user (id)
			on update cascade on delete cascade
)
;


create table image
(
	id uuid default uuid_generate_v4() not null
		constraint image_pk
			primary key,
	name varchar(255) not null
);

alter table image owner to postgres;

create unique index image_name_uindex
	on image (name);

create table product
(
	id uuid default uuid_generate_v4() not null
		constraint product_pk
			primary key,
	title varchar(255) not null,
	description varchar(255),
	price double precision not null,
	added timestamp default now() not null,
	available boolean default true not null,
	image uuid
		constraint fk_product_image
			references image
				on update cascade on delete cascade,
	category integer not null
);

alter table product owner to postgres;

create table products_images
(
	product_id uuid default uuid_generate_v4() not null
		constraint products_fk
			references product
				on update cascade on delete cascade,
	image_id uuid default uuid_generate_v4() not null
		constraint images__fk
			references image
				on update cascade on delete cascade,
	id serial not null
		constraint products_images_pk
			primary key
);

alter table products_images owner to postgres;


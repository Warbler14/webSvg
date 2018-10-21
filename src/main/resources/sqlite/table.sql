
drop TABLE linedb;
CREATE TABLE linedb(
	line_data	varchar(20),
	line_date 	varchar(30)
);

drop TABLE imageForm;
CREATE TABLE imageForm(
	img_id	number,
	img_name 	varchar(50),
	img_path 	varchar(100),
	reg_date	date,
	mod_date	date
);

delete from linedb;

insert into linedb( line_data, line_date )
values ( 'test1', '20180929163600')
;

insert into linedb( line_data, line_date )
values ( 'test2', '20180929202200')
;

delete from imageForm;

insert into imageForm( img_id, img_name, img_path, reg_date, mod_date)
values ( 1, 'test1' , '/test1.png', datetime('now'), datetime('now') )
;

insert into imageForm( img_id, img_name, img_path, reg_date, mod_date)
values ( 2, 'test2' , '/test2.png', datetime('now'), datetime('now') )
;

insert into imageForm( img_id, img_name, img_path, reg_date, mod_date)
values ( 3, 'test3' , '/test3.png', datetime('now'), datetime('now') )
;

insert into imageForm( img_id, img_name, img_path, reg_date, mod_date)
values ( 4, 'test4' , '/test4.png', datetime('now'), datetime('now') )
;


		SELECT img_id
				, img_name
				, img_path
				, datetime(reg_date) AS reg_date
				, datetime(mod_date) AS mod_date
		FROM imageForm
		ORDER BY reg_date DESC
		;

select datetime(reg_date)
from imageForm
;
;


select *
from linedb
;

select count(*) cnt from linedb;
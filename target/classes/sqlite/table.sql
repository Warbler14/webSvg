
drop TABLE linedb;
CREATE TABLE linedb(
	line_data	varchar(20),
	line_date 	varchar(30)
);

delete from linedb;

insert into linedb( line_data, line_date )
values ( 'test1', '20180929163600')
;

insert into linedb( line_data, line_date )
values ( 'test2', '20180929202200')
;

select *
from linedb
;

select count(*) cnt from linedb;
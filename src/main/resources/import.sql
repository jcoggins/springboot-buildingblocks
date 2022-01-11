insert into user values(101,'jcoggins@example.com', 'Joe', 'Coggins', 'admin', 'ssn101', 'jcoggins');
insert into user values(102,'bshwartz@example.com', 'Bill', 'Shwartz', 'admin', 'ssn102', 'bshwartz');
insert into user values(103,'junderwood@example.com', 'John', 'Underwood', 'admin', 'ssn103', 'junderwood');

--insert into user values(101,'kreddy@stacksimplify.com', 'Kalyan', 'Reddy', 'admin', 'ssn101', 'kreddy');
--insert into user values(102,'gwiser@stacksimplify.com', 'Greg', 'Wiser', 'admin', 'ssn102', 'gwiser');
--insert into user values(103, 'dmark@stacksimplify.com', 'David', 'Mark', 'admin', 'ssn103', 'dmark');
    
--Option#1: Verify Column and create insert query
insert into orders values(2001, 'order11', 101);
insert into orders values(2002, 'order12', 101);
insert into orders values(2003, 'order13', 101);
insert into orders values(2004, 'order21', 102);
insert into orders values(2005, 'order22', 102);
insert into orders values(2006, 'order31', 103);

--Option#2: Verify Foreign Key name in DB before creating below insert queries
--insert into orders (orderId, orderDescription , user_id) values(2001, 'order11', 101);
--insert into orders (orderId, orderDescription , user_id) values(2002, 'order12', 101);
--insert into orders (orderId, orderDescription , user_id) values(2003, 'order13', 101);
--insert into orders (orderId, orderDescription , user_id) values(2004, 'order21', 103);
--insert into orders (orderId, orderDescription , user_id) values(2005, 'order22', 102);
--insert into orders (orderId, orderDescription , user_id) values(2006, 'order31', 103);
    
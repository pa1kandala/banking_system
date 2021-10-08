/* Table Structure for user_pass */
create table user_pass (user_id number(15) not null, username varchar(20) not null, password varchar(20) not null, unique (user_id), primary key (username));

/* Data for the table user_pass */
insert all 
into user_pass (user_id,username,password) values (1, 'edureka','edur$ka') 
into user_pass (user_id,username,password) values (2, 'pavan','p@van')
into user_pass (user_id,username,password) values (3, 'siddhu','$iddhu')
into user_pass (user_id,username,password) values (4, 'vishnu','vi$hnu')
select 1 from dual;

/* Table Structure for account_details */
create table account_details (user_id number(15) not null, acct_id number(15) default 1000000000 not null, acct_name varchar(20) not null, acct_dtob varchar(20) default null, acct_addr varchar(100) default null, acct_email varchar(50) default null, acct_type varchar(20) default null, primary key (user_id, acct_id));

/* Sequence creation for account_details table */
create sequence act_det_seq start with 1000000000 increment by 1;

/* Table Structure for account_transactions */
CREATE TABLE account_transactions (txn_id number(15) not null, acct_id number(15) not null, cheque_no number(15) default 0, txn_type varchar(20) not null, txn_amount number(15) default 0, txn_balance_amount number(15) default 0, txn_comments varchar(150) default null, txn_date varchar(20) not null, primary key (txn_id));

/* Sequence creation for account_transactions table */
create sequence act_txn_seq start with 1 increment by 1;

/* Table Structure for credit_card */
CREATE TABLE credit_card (card_number number(16) not null, card_name varchar(50) default null, card_expirydate varchar(20) not null, card_cvv number(5) not null, card_limit number(30) not null, primary key (card_number));

/* Data for the table credit_card */
insert all 
into credit_card (card_number, card_name, card_expirydate, card_cvv, card_limit) values (4050123489982334, 'Edureka', '12/31/19', 417, 100000) 
into credit_card (card_number, card_name, card_expirydate, card_cvv, card_limit) values (2334405012348998, 'Pavan', '01/01/20', 007, 100000)
into credit_card (card_number, card_name, card_expirydate, card_cvv, card_limit) values (8998233440501234, 'Sunil', '12/31/18', 369, 100000)
into credit_card (card_number, card_name, card_expirydate, card_cvv, card_limit) values (1234233440502235, 'Sirish', '12/31/15', 182, 100000)
into credit_card (card_number, card_name, card_expirydate, card_cvv, card_limit) values (8776899812348998, 'Kumar', '12/31/16', 576, 100000)
select 1 from dual;
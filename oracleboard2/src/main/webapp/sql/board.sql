-- spring/spring123
select * from tab;
select * from seq;

insert into myboard2 values(myboard2_seq.nextval,'홍길동','1234',
'스프링 게시판 연습','게시판 내용',0, sysdate);

select count(*) from myboard2;

create table myboard2(
	  no number primary key,
	  writer varchar2(20),
      passwd varchar2(20),
	  subject varchar2(50),
	  content varchar2(100),
	  readcount number,
	  register date );

create sequence myboard2_seq
           start with 1
           increment by 1
           nocache;
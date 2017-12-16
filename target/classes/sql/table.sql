drop table if exists t_person;

drop table if exists t_word;

drop table if exists t_word_note;

/*==============================================================*/
/* Table: t_person                                              */
/*==============================================================*/
create table t_person
(
   id                   int not null auto_increment,
   login_name           varchar(25),
   password             varchar(25),
   name                 varchar(25),
   gender               varchar(25),
   remeber_count        bigint,
   role                 tinyint comment '0表示学生1表示老师',
   delete_flag          tinyint,
   primary key (id)
);

/*==============================================================*/
/* Table: t_word                                                */
/*==============================================================*/
create table t_word
(
   id                   int not null auto_increment,
   china_name           varchar(255),
   english_name         varchar(25),
   detail               text,
   create_time          datetime,
   delete_flag          tinyint,
   type                 int comment '0 四级 1 六级',
   primary key (id)
);


/*==============================================================*/
/* Table: t_word_note                                           */
/*==============================================================*/
create table t_word_note
(
   id                   int not null auto_increment,
   person_id            int,
   word_id              int,
   right_number         int,
   show_number          int,
   right_rate           float,
   delete_flag          tinyint,
   primary key (id)
);

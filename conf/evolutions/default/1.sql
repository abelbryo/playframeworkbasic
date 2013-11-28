# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "USERS" ("USER_ID" SERIAL NOT NULL PRIMARY KEY,"FIRST_NAME" VARCHAR(254) NOT NULL,"MIDDLE_NAME_INIT" VARCHAR(254) NOT NULL,"LAST_NAME" VARCHAR(254) NOT NULL,"AGE" INTEGER NOT NULL);

# --- !Downs

drop table "USERS";


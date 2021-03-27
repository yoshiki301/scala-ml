-- !Ups

create table "exec_result" (
    "id" integer not null
    , "param_id" integer not null unique
    , "execute_filepath" text
    , "output_dirpath" text
    , "start_timestamp" timestamp
    , "end_timestamp" timestamp
    , "is_succeed" boolean
    , primary key (id)
);

insert into "exec_result" values (
    5
    , 8
    , './src/main/resources/light-gbm.py'
    , './src/main/resources/output-5'
    , '2021-02-14 14:00:00'
    , '2021-02-14 14:10:00'
    , 'true'
), (
    6
    , 9
    , './src/main/resources/light-gbm.py'
    , './src/main/resources/output-6'
    , '2021-02-18 14:00:00'
    , '2021-02-18 14:10:00'
    , 'true'
);

create table "params" (
    "param_id" integer not null
    , "param_label" text
    , "param_value" text
    , foreign key (param_id) references exec_result(param_id)
);

insert into "params" values (
    8
    , 'test_size'
    , '0.2'
), (
    8
    , 'random_state'
    , '42'
), (
    8
    , 'lgb_params'
    , '{
    "objective": "regression",
    "metric": "rmse",
    "num_leaves": 31,
    "max_depth": 3,
    "verbosity": -1
    }'
), (
    9
    , 'test_size'
    , '0.3'
), (
    9
    , 'random_state'
    , '42'
), (
    9
    , 'lgb_params'
    , '{
    "objective": "regression",
    "metric": "rmse",
    "num_leaves": 32,
    "max_depth": 4,
    "verbosity": -1
    }'
);

-- !Downs
drop table exec_result cascade;
drop table params cascade;

create type exit_status_text as enum ('success', 'failure');

create table exec_result (
    id integer not null
    , param_id integer not null
    , execute_filepath text
    , output_dirpath text
    , start_timestamp timestamp
    , end_timestamp timestamp
    , exit_status exit_status_text
    , primary key (id)
);

insert into exec_result values (
    5
    , 8
    , './src/main/resources/light-gbm.py'
    , './src/main/resources/output-5'
    , '2021-02-14 14:00:00'
    , '2021-02-14 14:10:00'
    , 'success'
);

create table params (
    param_id integer not null
    , param_label text
    , param_value numeric
    , foreign key (param_id) references exec_result(param_id)
);
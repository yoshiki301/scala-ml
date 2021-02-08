create type exit_status_text as enum ('success', 'error');

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

create table params (
    param_id integer not null
    , param_label text
    , param_value numeric
    , foreign key (param_id) references exec_result(id)
);
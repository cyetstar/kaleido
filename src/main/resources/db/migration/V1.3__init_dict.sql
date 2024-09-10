insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (1, null, 'writeComicInfo', '1', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (2, null, 'writeMovieNFO', '1', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (3, null, 'refreshMetadata', '1', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (4, null, 'downloadLyricSleepSecond', '3', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (5, null, 'matchInfoSleepSecond', '3', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (6, null, 'plexRetries', '3', 0, now(), now(), 'system', 'system');

insert into sys_dict_type (id, type, name, is_deleted, create_time, update_time, created_by, updated_by)
values (1, 'sfbz', '是否标志', 0, now(), now(), 'system', 'system');

insert into sys_dict (id, dict_type, label, value, sort, is_enabled, is_deleted, create_time, update_time, created_by,
                      updated_by)
values (1, 'sfbz', '是', '1', 1, 1, 0, now(), now(), 'system', 'system');

insert into sys_dict (id, dict_type, label, value, sort, is_enabled, is_deleted, create_time, update_time, created_by,
                      updated_by)
values (2, 'sfbz', '否', '0', 2, 1, 0, now(), now(), 'system', 'system');
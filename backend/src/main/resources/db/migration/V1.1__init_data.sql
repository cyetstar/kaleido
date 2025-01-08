INSERT INTO oauth_client_details (client_id, client_name, resource_ids, client_secret, scope,
                                  authorized_grant_types, web_server_redirect_uri, authorities,
                                  access_token_validity, refresh_token_validity,
                                  additional_information, autoapprove)
VALUES ('kaleido', 'kaleido', NULL, '$2a$10$9ILIX1HdiIPTtIbU/Ae4pOkXIWIc4StWnGPrn2lZhS/0aczK7L1c2', 'read',
        'password,refresh_token', NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO sys_user (id, username, password, name, id_card_number, mobile, description, dept_code, dept_id,
                      filter_code, is_enabled, is_account_non_expired, is_credentials_non_expired,
                      is_account_non_locked, is_deleted, create_time, update_time, created_by, updated_by)
VALUES (1, 'cyetstar', '$2a$10$XwEzK.zpjSNMyHPKGh4mw.768Rc.sLIMiqAW/1sczx91tzuV0.3ce', 'cyetstar', '', '', '', '', 0,
        '', 1,
        1, 1, 1, 0, now(), now(), 'system', 'system');

INSERT INTO sys_role (id, parent_id, code, name, description, sub_count, is_deleted, create_time, update_time,
                      created_by, updated_by)
VALUES (1, 0, 'ROLE_ADMIN', '管理员', '管理员', 0, 1, now(), now(), 'system', 'system');

INSERT INTO sys_user_role (id, user_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (1, 1, 1, now(), now(), 'system', 'system');

INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (1, 0, '', '', 'movie', '电影', '', '', '', '', '', 1, 0, 1, 0, '', 0, now(),
        now(), '', '', NULL);
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (2, 1, '', '/movie/movieBasic/page', 'movieBasicPage', '电影', '', '', '', '', '', 1, 0, 11, 0, '', 0,
        now(), now(), '', '', 'movieBasic:page');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (3, 1, '', '/movie/movieCollection/page', 'movieCollectionPage', '电影合集', '', '', '', '', '', 1, 0, 12, 0, '',
        0, now(), now(), '', '', 'movieCollection:page');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (4, 1, '', '/movie/movieDoubanWeekly/page', 'movieDoubanWeeklyPage', '豆瓣口碑榜', '', '', '', '', '', 1, 0, 13,
        0, '', 0, now(), now(), '', '', 'movieDoubanWeekly:page');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (5, 0, '', '', 'tvshow', '剧集', '', '', '', '', '', 1, 0, 2, 0, '', 0, now(),
        now(), '', '', NULL);
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (6, 5, '', '/tvshow/tvshowShow/page', 'tvshowShowPage', '剧集', '', '', '', '', '', 1, 0, 21, 0, '', 0,
        now(), now(), '', '', 'tvshowShow:page');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (7, 0, '', '', 'music', '音乐', '', '', '', '', '', 1, 0, 3, 0, '', 0, now(),
        now(), '', '', NULL);
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (8, 7, '', '/music/musicAlbum/page', 'musicAlbumPage', '专辑', '', '', '', '', '', 1, 0, 31, 0, '', 0,
        now(), now(), '', '', 'musicAlbum:page');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (9, 7, '', '/music/artist/page', 'artistPage', '艺术家', '', '', '', '', '', 1, 0, 32, 0, '', 0,
        now(), now(), '', '', 'artist:page');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (10, 0, '', '', 'comic', '漫画', '', '', '', '', '', 1, 0, 4, 0, '', 0, now(),
        now(), '', '', NULL);
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (11, 10, '', '/comic/comicSeries/page', 'comicSeriesPage', '漫画系列', '', '', '', '', '', 1, 0, 41, 0, '', 0,
        now(), now(), '', '', 'comicSeries:page');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (12, 10, '', '/author/page', 'authorPage', '作者', '', '', '', '', '', 1, 0, 42, 0, '', 0, now(),
        now(), '', '', 'author:page');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (13, 0, '', '', 'sysadmin', '系统管理', '', '', '', '', '', 1, 0, 5, 0, '', 0, now(),
        now(), '', '', NULL);
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (14, 13, '', '/sysadmin/sysUser/page', 'SysUserPage', '用户管理', '', '', '', '', '', 1, 0, 51, 0, '', 0,
        now(), now(), '', '', 'sysUser:page');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (15, 13, '', '/sysadmin/sysRole/page', 'SysRolePage', '角色管理', '', '', '', '', '', 1, 0, 52, 0, '', 0,
        now(), now(), '', '', 'sysRole:page');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (16, 13, '', '/sysadmin/sysMenu/page', 'SysMenuPage', '菜单管理', '', '', '', '', '', 1, 0, 53, 0, '', 0,
        now(), now(), '', '', 'sysMenu:page');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (17, 13, '', '/sysadmin/sysResource/page', 'SysResourcePage', '资源管理', '', '', '', '', '', 1, 0, 54, 0, '', 0,
        now(), now(), '', '', 'sysResource:page');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (18, 13, '', '/sysadmin/sysDictType/page', 'sysDictTypePage', '字典管理', '', '', '', '', '', 1, 0, 55, 0, '', 0,
        now(), now(), '', '', 'sysDictType:page');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (19, 13, '', '/sysadmin/sysConfig/form', 'SysConfigForm', '系统配置', '', '', '', '', '', 1, 0, 56, 0, '', 0,
        now(), now(), '', '', 'sysConfig:create');
INSERT INTO sys_menu (id, parent_id, type, path, name, title, description, redirect, component, icon, meta,
                      is_show_root, is_hidden, order_num, sub_count, app, is_deleted, create_time, update_time,
                      created_by, updated_by, permission)
VALUES (20, 13, '', '/sysadmin/sysLog/page', 'SysLogPage', '系统日志', '', '', '', '', '', 1, 0, 58, 0, '', 0,
        now(), now(), '', '', 'sysLog:page');

INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (1, 'sysUser:page', 'sysUser', 'page', '/sysUser/page', '', '', now(), now(), '',
        '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (2, 'sysUser:view', 'sysUser', 'view', '/sysUser/view', '', '', now(), now(), '',
        '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (3, 'sysUser:create', 'sysUser', 'create', '/sysUser/create', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (4, 'sysUser:update', 'sysUser', 'update', '/sysUser/update', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (5, 'sysUser:delete', 'sysUser', 'delete', '/sysUser/delete', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (6, 'sysUser:export', 'sysUser', 'export', '/sysUser/export', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (7, 'sysUser:other', 'sysUser', 'other', '/sysUser/other', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (8, 'sysMenu:page', 'sysMenu', 'page', '/sysMenu/page', '', '', now(), now(), '',
        '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (9, 'sysMenu:view', 'sysMenu', 'view', '/sysMenu/view', '', '', now(), now(), '',
        '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (10, 'sysMenu:create', 'sysMenu', 'create', '/sysMenu/create', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (11, 'sysMenu:update', 'sysMenu', 'update', '/sysMenu/update', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (12, 'sysMenu:delete', 'sysMenu', 'delete', '/sysMenu/delete', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (13, 'sysMenu:export', 'sysMenu', 'export', '/sysMenu/export', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (14, 'sysMenu:other', 'sysMenu', 'other', '/sysMenu/other', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (15, 'sysRole:page', 'sysRole', 'page', '/sysRole/page', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (16, 'sysRole:view', 'sysRole', 'view', '/sysRole/view', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (17, 'sysRole:create', 'sysRole', 'create', '/sysRole/create', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (18, 'sysRole:update', 'sysRole', 'update', '/sysRole/update', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (19, 'sysRole:delete', 'sysRole', 'delete', '/sysRole/delete', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (20, 'sysRole:export', 'sysRole', 'export', '/sysRole/export', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (21, 'sysRole:other', 'sysRole', 'other', '/sysRole/other', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (22, 'sysResource:page', 'sysResource', 'page', '/sysResource/page', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (23, 'sysResource:view', 'sysResource', 'view', '/sysResource/view', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (24, 'sysResource:create', 'sysResource', 'create', '/sysResource/create', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (25, 'sysResource:update', 'sysResource', 'update', '/sysResource/update', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (26, 'sysResource:delete', 'sysResource', 'delete', '/sysResource/delete', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (27, 'sysResource:export', 'sysResource', 'export', '/sysResource/export', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (28, 'sysResource:other', 'sysResource', 'other', '/sysResource/other', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (29, 'sysDictType:page', 'sysDictType', 'page', '/sysDictType/page', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (30, 'sysDictType:view', 'sysDictType', 'view', '/sysDictType/view', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (31, 'sysDictType:create', 'sysDictType', 'create', '/sysDictType/create', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (32, 'sysDictType:update', 'sysDictType', 'update', '/sysDictType/update', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (33, 'sysDictType:delete', 'sysDictType', 'delete', '/sysDictType/delete', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (34, 'sysDictType:export', 'sysDictType', 'export', '/sysDictType/export', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (35, 'sysDictType:other', 'sysDictType', 'other', '/sysDictType/other', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (36, 'sysDict:page', 'sysDict', 'page', '/sysDict/page', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (37, 'sysDict:view', 'sysDict', 'view', '/sysDict/view', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (38, 'sysDict:create', 'sysDict', 'create', '/sysDict/create', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (39, 'sysDict:update', 'sysDict', 'update', '/sysDict/update', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (40, 'sysDict:delete', 'sysDict', 'delete', '/sysDict/delete', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (41, 'sysDict:export', 'sysDict', 'export', '/sysDict/export', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (42, 'sysDict:other', 'sysDict', 'other', '/sysDict/other', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (43, 'sysConfig:page', 'sysConfig', 'page', '/sysConfig/page', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (44, 'sysConfig:view', 'sysConfig', 'view', '/sysConfig/view', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (45, 'sysConfig:create', 'sysConfig', 'create', '/sysConfig/create', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (46, 'sysConfig:update', 'sysConfig', 'update', '/sysConfig/update', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (47, 'sysConfig:delete', 'sysConfig', 'delete', '/sysConfig/delete', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (48, 'sysConfig:export', 'sysConfig', 'export', '/sysConfig/export', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (49, 'sysConfig:other', 'sysConfig', 'other', '/sysConfig/other', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (50, 'sysLog:page', 'sysLog', 'page', '/sysLog/page', '', '', now(), now(), '',
        '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (51, 'sysLog:view', 'sysLog', 'view', '/sysLog/view', '', '', now(), now(), '',
        '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (52, 'sysLog:create', 'sysLog', 'create', '/sysLog/create', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (53, 'sysLog:update', 'sysLog', 'update', '/sysLog/update', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (54, 'sysLog:delete', 'sysLog', 'delete', '/sysLog/delete', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (55, 'sysLog:export', 'sysLog', 'export', '/sysLog/export', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (56, 'sysLog:other', 'sysLog', 'other', '/sysLog/other', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (57, 'actor:page', 'actor', 'page', '/actor/page', '', '', now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (58, 'actor:view', 'actor', 'view', '/actor/view', '', '', now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (59, 'actor:create', 'actor', 'create', '/actor/create', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (60, 'actor:update', 'actor', 'update', '/actor/update', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (61, 'actor:delete', 'actor', 'delete', '/actor/delete', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (62, 'actor:export', 'actor', 'export', '/actor/export', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (63, 'actor:other', 'actor', 'other', '/actor/other', '', '', now(), now(), '',
        '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (64, 'artist:page', 'artist', 'page', '/artist/page', '', '', now(), now(), '',
        '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (65, 'artist:view', 'artist', 'view', '/artist/view', '', '', now(), now(), '',
        '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (66, 'artist:create', 'artist', 'create', '/artist/create', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (67, 'artist:update', 'artist', 'update', '/artist/update', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (68, 'artist:delete', 'artist', 'delete', '/artist/delete', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (69, 'artist:export', 'artist', 'export', '/artist/export', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (70, 'artist:other', 'artist', 'other', '/artist/other', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (71, 'author:page', 'author', 'page', '/author/page', '', '', now(), now(), '',
        '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (72, 'author:view', 'author', 'view', '/author/view', '', '', now(), now(), '',
        '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (73, 'author:create', 'author', 'create', '/author/create', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (74, 'author:update', 'author', 'update', '/author/update', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (75, 'author:delete', 'author', 'delete', '/author/delete', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (76, 'author:export', 'author', 'export', '/author/export', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (77, 'author:other', 'author', 'other', '/author/other', '', '', now(), now(),
        '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (78, 'movieBasic:page', 'movieBasic', 'page', '/movieBasic/page', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (79, 'movieBasic:view', 'movieBasic', 'view', '/movieBasic/view', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (80, 'movieBasic:create', 'movieBasic', 'create', '/movieBasic/create', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (81, 'movieBasic:update', 'movieBasic', 'update', '/movieBasic/update', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (82, 'movieBasic:delete', 'movieBasic', 'delete', '/movieBasic/delete', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (83, 'movieBasic:export', 'movieBasic', 'export', '/movieBasic/export', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (84, 'movieBasic:other', 'movieBasic', 'other', '/movieBasic/other', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (85, 'movieCollection:page', 'movieCollection', 'page', '/movieCollection/page', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (86, 'movieCollection:view', 'movieCollection', 'view', '/movieCollection/view', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (87, 'movieCollection:create', 'movieCollection', 'create', '/movieCollection/create', '', '',
        now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (88, 'movieCollection:update', 'movieCollection', 'update', '/movieCollection/update', '', '',
        now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (89, 'movieCollection:delete', 'movieCollection', 'delete', '/movieCollection/delete', '', '',
        now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (90, 'movieCollection:export', 'movieCollection', 'export', '/movieCollection/export', '', '',
        now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (91, 'movieCollection:other', 'movieCollection', 'other', '/movieCollection/other', '', '',
        now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (92, 'movieDoubanWeekly:page', 'movieDoubanWeekly', 'page', '/movieDoubanWeekly/page', '', '',
        now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (93, 'movieDoubanWeekly:view', 'movieDoubanWeekly', 'view', '/movieDoubanWeekly/view', '', '',
        now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (94, 'movieDoubanWeekly:create', 'movieDoubanWeekly', 'create', '/movieDoubanWeekly/create', '', '',
        now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (95, 'movieDoubanWeekly:update', 'movieDoubanWeekly', 'update', '/movieDoubanWeekly/update', '', '',
        now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (96, 'movieDoubanWeekly:delete', 'movieDoubanWeekly', 'delete', '/movieDoubanWeekly/delete', '', '',
        now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (97, 'movieDoubanWeekly:export', 'movieDoubanWeekly', 'export', '/movieDoubanWeekly/export', '', '',
        now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (98, 'movieDoubanWeekly:other', 'movieDoubanWeekly', 'other', '/movieDoubanWeekly/other', '', '',
        now(), now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (99, 'tvshowShow:page', 'tvshowShow', 'page', '/tvshowShow/page', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (100, 'tvshowShow:view', 'tvshowShow', 'view', '/tvshowShow/view', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (101, 'tvshowShow:create', 'tvshowShow', 'create', '/tvshowShow/create', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (102, 'tvshowShow:update', 'tvshowShow', 'update', '/tvshowShow/update', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (103, 'tvshowShow:delete', 'tvshowShow', 'delete', '/tvshowShow/delete', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (104, 'tvshowShow:export', 'tvshowShow', 'export', '/tvshowShow/export', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (105, 'tvshowShow:other', 'tvshowShow', 'other', '/tvshowShow/other', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (106, 'musicAlbum:page', 'musicAlbum', 'page', '/musicAlbum/page', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (107, 'musicAlbum:view', 'musicAlbum', 'view', '/musicAlbum/view', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (108, 'musicAlbum:create', 'musicAlbum', 'create', '/musicAlbum/create', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (109, 'musicAlbum:update', 'musicAlbum', 'update', '/musicAlbum/update', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (110, 'musicAlbum:delete', 'musicAlbum', 'delete', '/musicAlbum/delete', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (111, 'musicAlbum:export', 'musicAlbum', 'export', '/musicAlbum/export', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (112, 'musicAlbum:other', 'musicAlbum', 'other', '/musicAlbum/other', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (113, 'comicSeries:page', 'comicSeries', 'page', '/comicSeries/page', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (114, 'comicSeries:view', 'comicSeries', 'view', '/comicSeries/view', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (115, 'comicSeries:create', 'comicSeries', 'create', '/comicSeries/create', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (116, 'comicSeries:update', 'comicSeries', 'update', '/comicSeries/update', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (117, 'comicSeries:delete', 'comicSeries', 'delete', '/comicSeries/delete', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (118, 'comicSeries:export', 'comicSeries', 'export', '/comicSeries/export', '', '', now(),
        now(), '', '');
INSERT INTO sys_resource (id, code, type, name, url, method, description, create_time, update_time, created_by,
                          updated_by)
VALUES (119, 'comicSeries:other', 'comicSeries', 'other', '/comicSeries/other', '', '', now(),
        now(), '', '');

INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (1, 15, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (2, 16, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (3, 17, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (4, 18, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (5, 19, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (6, 20, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (7, 21, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (8, 99, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (9, 100, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (10, 101, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (11, 102, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (12, 103, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (13, 104, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (14, 105, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (15, 113, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (16, 114, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (17, 115, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (18, 116, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (19, 117, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (20, 118, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (21, 119, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (22, 64, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (23, 65, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (24, 66, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (25, 67, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (26, 68, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (27, 70, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (28, 69, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (29, 71, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (30, 72, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (31, 73, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (32, 74, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (33, 75, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (34, 76, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (35, 77, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (36, 50, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (37, 51, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (38, 52, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (39, 53, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (40, 54, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (41, 55, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (42, 56, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (43, 8, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (44, 9, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (45, 10, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (46, 11, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (47, 12, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (48, 13, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (49, 14, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (50, 22, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (51, 24, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (52, 25, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (53, 23, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (54, 26, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (55, 27, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (56, 28, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (57, 79, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (58, 80, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (59, 81, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (60, 82, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (61, 83, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (62, 84, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (63, 78, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (64, 57, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (65, 58, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (66, 59, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (67, 60, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (68, 61, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (69, 62, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (70, 63, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (71, 43, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (72, 44, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (73, 45, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (74, 47, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (75, 46, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (76, 48, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (77, 49, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (78, 31, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (79, 33, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (80, 34, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (81, 35, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (82, 30, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (83, 29, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (84, 32, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (85, 85, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (86, 86, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (87, 87, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (88, 88, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (89, 89, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (90, 90, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (91, 91, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (92, 92, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (93, 93, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (94, 94, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (95, 95, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (96, 96, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (97, 97, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (98, 98, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (99, 1, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (100, 2, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (101, 3, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (102, 4, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (103, 5, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (104, 6, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (105, 7, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (106, 106, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (107, 107, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (108, 108, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (109, 109, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (110, 110, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (111, 111, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (112, 112, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (113, 36, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (114, 38, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (115, 40, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (116, 41, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (117, 42, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (118, 39, 1, now(), now(), '', '');
INSERT INTO sys_role_resource (id, resource_id, role_id, create_time, update_time, created_by, updated_by)
VALUES (119, 37, 1, now(), now(), '', '');

insert into sys_dict_type (id, type, name, is_deleted, create_time, update_time, created_by, updated_by)
values (1, 'sfbz', '是否标志', 0, now(), now(), 'system', 'system');

insert into sys_dict_type (id, type, name, is_deleted, create_time, update_time, created_by, updated_by)
values (2, 'wjzt', '完结状态', 0, now(), now(), 'system', 'system');

insert into sys_dict (id, dict_type, label, value, sort, is_enabled, is_deleted, create_time, update_time, created_by,
                      updated_by)
values (1, 'sfbz', '是', '1', 1, 1, 0, now(), now(), 'system', 'system');

insert into sys_dict (id, dict_type, label, value, sort, is_enabled, is_deleted, create_time, update_time, created_by,
                      updated_by)
values (2, 'sfbz', '否', '0', 2, 1, 0, now(), now(), 'system', 'system');

insert into sys_dict (id, dict_type, label, value, sort, is_enabled, is_deleted, create_time, update_time, created_by,
                      updated_by)
values (3, 'wjzt', '已完结', 'ENDED', 1, 1, 0, now(), now(), 'system', 'system');

insert into sys_dict (id, dict_type, label, value, sort, is_enabled, is_deleted, create_time, update_time, created_by,
                      updated_by)
values (4, 'wjzt', '未完结', 'ONGOING', 2, 1, 0, now(), now(), 'system', 'system');

###############
init config ###############

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (1, 'writeMovieNFO', 'writeMovieNFO', '0', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (2, 'writeTvshowNFO', 'writeTvshowNFO', '0', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (3, 'writeAudioTag', 'writeAudioTag', '0', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (4, 'writeComicInfo', 'writeComicInfo', '0', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (5, 'refreshMetadata', 'refreshMetadata', '0', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (6, 'downloadLyricSleepSecond', 'downloadLyricSleepSecond', '3', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (7, 'matchInfoSleepSecond', 'matchInfoSleepSecond', '3', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (8, 'plexRetries', 'plexRetries', '3', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (9, 'lastMovieAnalyzeTime', 'lastMovieAnalyzeTime', '0', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (10, 'lastMovieMatchInfo', 'lastMovieMatchInfo', '0', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (11, 'lastTvshowMatchInfo', 'lastTvshowMatchInfo', '0', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (12, 'lastMusicMatchInfo', 'lastMusicMatchInfo', '0', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (13, 'lastComicMatchInfo', 'lastComicMatchInfo', '0', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (14, 'movieLibraryPath', 'movieLibraryPath', '/movie/library', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (15, 'tvshowLibraryPath', 'tvshowLibraryPath', '/tvshow/library', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (16, 'musicLibraryPath', 'musicLibraryPath', '/music/library', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (17, 'comicLibraryPath', 'comicLibraryPath', '/comic/library', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (18, 'plexUrl', 'plexUrl', 'http://192.168.3.100:32400', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (19, 'plexToken', 'plexToken', 'cjoAzc5xAiJxTxYtSsk3', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (20, 'komgaUrl', 'komgaUrl', 'http://192.168.3.100:8800', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (21, 'komgaUsername', 'komgaUsername', 'cyetstar@gmail.com', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (22, 'komgaPassword', 'komgaPassword', 'lenovot430', 0, now(), now(), 'system', 'system');

insert into sys_config (id, config_name, config_key, config_value, is_deleted, create_time,
                        update_time, created_by, updated_by)
values (23, 'tmmUrl', 'tmmUrl', 'http://192.168.3.100:38002', 0, now(), now(), 'system', 'system');
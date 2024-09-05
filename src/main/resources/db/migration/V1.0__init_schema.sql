create table oauth_client_details
(
    client_id               varchar(256) not null comment '客户端ID',
    client_name             varchar(255) comment '客户端名称',
    resource_ids            varchar(256) comment '资源ID集合,多个资源时用逗号(,)分隔',
    client_secret           varchar(256) comment '客户端密匙',
    scope                   varchar(256) comment '客户端申请的权限范围',
    authorized_grant_types  varchar(256) comment '客户端支持的grant_type',
    web_server_redirect_uri varchar(256) comment '重定向URI',
    authorities             varchar(256) comment '客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔',
    access_token_validity   int comment '访问令牌有效时间值(单位:秒)',
    refresh_token_validity  int comment '更新令牌有效时间值(单位:秒)',
    additional_information  varchar(4096) comment '预留字段',
    autoapprove             varchar(256) comment '用户是否自动Approval操作',
    primary key (client_id)
);

alter table oauth_client_details
    comment '客户端信息表';

create table sys_config
(
    id           bigint unsigned  not null auto_increment comment 'id',
    config_name  varchar(100)     not null comment '配置名称',
    config_key   varchar(100)     not null comment '配置键名',
    config_value varchar(1024)    not null comment '配置键值',
    is_deleted   tinyint unsigned not null default '0' comment '是否已删除1：已删除，0：未删除',
    create_time  datetime         not null default CURRENT_TIMESTAMP comment '创建时间',
    update_time  datetime         not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    created_by   varchar(100)     not null default '' comment '创建人',
    updated_by   varchar(100)     not null default '' comment '更新人',
    primary key (id)
);

alter table sys_config
    comment '系统配置表';

create table sys_dict
(
    id          bigint unsigned  not null auto_increment comment '字典详情id',
    dict_type   varchar(50)      not null comment '字典类型',
    label       varchar(200)     not null comment '字典标签',
    value       varchar(50)      not null comment '字典值',
    sort        int              not null default '1' comment '顺序号',
    is_enabled  tinyint unsigned not null default '1' comment '是否启用',
    is_deleted  tinyint unsigned not null default '0' comment '是否已删除1：已删除，0：未删除',
    create_time datetime         not null default CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime         not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    created_by  varchar(100)     not null default '' comment '创建人',
    updated_by  varchar(100)     not null default '' comment '更新人',
    primary key (id)
);

alter table sys_dict
    comment '字典表';

create table sys_dict_type
(
    id          bigint unsigned  not null auto_increment comment '字典id',
    type        varchar(50)      not null comment '字典类型',
    name        varchar(100)     not null comment '字典名称',
    is_deleted  tinyint unsigned not null default '0' comment '是否已删除1：已删除，0：未删除',
    create_time datetime         not null default CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime         not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    created_by  varchar(100)     not null default '' comment '创建人',
    updated_by  varchar(100)     not null default '' comment '更新人',
    primary key (id)
);

alter table sys_dict_type
    comment '字典表类型表';

create table sys_menu
(
    id           bigint unsigned  not null auto_increment comment 'id',
    parent_id    bigint unsigned  not null default '0' comment '父菜单id',
    type         varchar(100)     not null default '' comment '菜单类型',
    path         varchar(200)     not null default '' comment '菜单路径',
    name         varchar(200)     not null default '' comment '菜单名称',
    title        varchar(200)     not null default '' comment '菜单标题',
    description  varchar(500)     not null default '' comment '描述',
    redirect     varchar(200)     not null default '' comment '重定向路径',
    component    varchar(200)     not null default '' comment '菜单组件',
    icon         varchar(200)     not null default '' comment '菜单图标',
    meta         varchar(1000)    not null default '' comment '额外信息，json格式',
    is_show_root tinyint unsigned not null default '1' comment '是否显示根',
    is_hidden    tinyint unsigned not null default '0' comment '是否显示',
    order_num    int              not null default '1' comment '顺序号',
    sub_count    int unsigned     not null default '0' comment '子节点数',
    app          varchar(50)               default '' comment '所属应用',
    is_deleted   tinyint unsigned not null default '0' comment '是否已删除1：已删除，0：未删除',
    create_time  datetime         not null default CURRENT_TIMESTAMP comment '创建时间',
    update_time  datetime         not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    created_by   varchar(100)     not null default '' comment '创建人',
    updated_by   varchar(100)     not null default '' comment '更新人',
    permission   varchar(100),
    primary key (id)
);

alter table sys_menu
    comment '菜单表';

create table sys_resource
(
    id          bigint unsigned not null auto_increment comment '资源id',
    code        varchar(100)    not null comment '资源code',
    type        varchar(100)    not null comment '资源类型',
    name        varchar(200)    not null comment '资源名称',
    url         varchar(200)    not null comment '资源url',
    method      varchar(20)     not null default '' comment '资源方法',
    description varchar(500)    not null default '' comment '简介',
    create_time datetime        not null default CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime        not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    created_by  varchar(100)    not null default '' comment '创建人',
    updated_by  varchar(100)    not null default '' comment '更新人',
    primary key (id),
    unique key uk_code (code)
);

alter table sys_resource
    comment '资源表';


create table sys_role
(
    id          bigint unsigned  not null auto_increment comment '角色id',
    parent_id   bigint unsigned  not null default '0' comment '父角色id',
    code        varchar(100)     not null comment '角色code',
    name        varchar(200)     not null comment '角色名称',
    description varchar(500)     not null default '' comment '简介',
    sub_count   int unsigned     not null default '0' comment '子节点数',
    is_deleted  tinyint unsigned not null default '0' comment '是否已删除1：已删除，0：未删除',
    create_time datetime         not null default CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime         not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    created_by  varchar(100)     not null default '' comment '创建人',
    updated_by  varchar(100)     not null default '' comment '更新人',
    primary key (id)
);

alter table sys_role
    comment '角色表';

create table sys_role_resource
(
    id          bigint unsigned not null auto_increment comment '关系id',
    resource_id bigint          not null comment '角色id',
    role_id     bigint          not null comment '资源id',
    create_time datetime        not null default CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime        not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    created_by  varchar(100)    not null default '' comment '创建人',
    updated_by  varchar(100)    not null default '' comment '更新人',
    primary key (id)
);

alter table sys_role_resource
    comment '角色和资源关系表';

create table sys_user
(
    id                         bigint unsigned  not null auto_increment comment '用户id',
    username                   varchar(100)     not null comment '用户名',
    password                   varchar(100)     not null comment '用户密码密文',
    name                       varchar(200)     not null comment '用户姓名',
    id_card_number             varchar(100)     not null default '' comment '用户公民身份号码',
    mobile                     varchar(100)     not null default '' comment '用户手机',
    description                varchar(500)     not null default '' comment '简介',
    dept_code                  varchar(32)      not null default '' comment '部门编码',
    dept_id                    bigint unsigned  not null default '0' comment '部门id',
    filter_code                varchar(32)      not null default '',
    is_enabled                 tinyint unsigned not null default '1' comment '是否有效用户',
    is_account_non_expired     tinyint unsigned not null default '1' comment '账号是否未过期',
    is_credentials_non_expired tinyint unsigned not null default '1' comment '密码是否未过期',
    is_account_non_locked      tinyint unsigned not null default '1' comment '是否未锁定',
    is_deleted                 tinyint unsigned not null default '0' comment '是否已删除1：已删除，0：未删除',
    create_time                datetime         not null default CURRENT_TIMESTAMP comment '创建时间',
    update_time                datetime         not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    created_by                 varchar(100)     not null default '' comment '创建人',
    updated_by                 varchar(100)     not null default '' comment '更新人',
    primary key (id),
    unique key uk_username (username)
);

alter table sys_user
    comment '用户表';

create table sys_user_role
(
    id          bigint unsigned not null auto_increment comment '关系id',
    user_id     bigint unsigned not null comment '用户id',
    role_id     bigint unsigned not null comment '角色id',
    create_time datetime        not null default CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime        not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    created_by  varchar(100)    not null default '' comment '创建人',
    updated_by  varchar(100)    not null default '' comment '更新人',
    primary key (id)
);

alter table sys_user_role
    comment '用户和角色关系表';

create table web_log
(
    id          bigint not null auto_increment comment 'id',
    description varchar(50)  default '' comment '操作描述',
    username    varchar(50)  default '' comment '操作用户',
    start_time  bigint       default '-1' comment '操作时间',
    spend_time  int          default '-1' comment '消耗时间',
    base_path   varchar(200) default '' comment '根路径',
    parameter   longtext comment '请求参数',
    uri         varchar(128) default '' comment 'URI',
    url         varchar(128) default '' comment 'URL',
    method      varchar(200) default '' comment '请求类型',
    ip          varchar(50)  default '' comment 'IP地址',
    cjr         varchar(100) comment '创建人',
    cjrzh       varchar(30) comment '创建人帐号',
    cjrxq       varchar(15) comment '创建人辖区',
    cjrip       varchar(30) comment '创建人IP',
    cjsj        varchar(14) comment '创建时间',
    xgr         varchar(100) comment '修改人',
    xgrzh       varchar(30) comment '修改人帐号',
    xgrip       varchar(30) comment '修改人IP',
    xgrxq       varchar(15) comment '修改人辖区',
    xgsj        varchar(14) comment '修改时间',
    yxbz        varchar(1) comment '有效标志',
    primary key (id)
);

alter table web_log
    comment '操作日志表';

create table actor
(
    id             varchar(36)  not null comment '主键',
    name           varchar(200) not null comment '姓名',
    original_name  varchar(200) comment '原名',
    thumb          varchar(500) comment '缩略图',
    douban_id      varchar(10) comment '豆瓣编号',
    disambiguation varchar(200) comment '消歧',
    primary key (id)
);

alter table actor
    comment '演职员';

create index name on actor
    (
     name
        );

create index thumb on actor
    (
     thumb
        );

create index douban_id on actor
    (
     douban_id
        );

create table alternate_title
(
    id           varchar(36) not null comment '主键',
    title        varchar(500) comment '标题',
    subject_id   varchar(36) comment '项目id',
    subject_type varchar(20) comment '项目类型',
    primary key (id)
);

alter table alternate_title
    comment '别名';

create index title on alternate_title
    (
     title
        );

create index subject_id on alternate_title
    (
     subject_id
        );

create table attribute
(
    id    varchar(36) not null comment '主键',
    value varchar(30) comment '属性值',
    type  varchar(20) comment '类型',
    primary key (id)
);

alter table attribute
    comment '属性';

create table author
(
    id            varchar(36)  not null comment '主键',
    name          varchar(200) not null comment '姓名',
    original_name varchar(200) comment '原名',
    primary key (id)
);

alter table author
    comment '作者';

create index name on author
    (
     name
        );

create table comic_book
(
    id                varchar(36) not null comment '主键',
    series_id         varchar(36) comment '系列id',
    title             varchar(500) comment '标题',
    original_title    varchar(500) comment '原标题',
    book_number       int comment '卷号',
    sort_number       int comment '排序号',
    page_count        int comment '页数',
    path              varchar(500) comment '路径',
    file_size         bigint comment '文件大小',
    bgm_id            varchar(36) comment '番组计划编号',
    cover_page_number int comment '封面页码',
    cover_box_data    varchar(500) comment '封面裁切数据',
    added_at          bigint comment '加入时间',
    updated_at        bigint comment '更新时间',
    primary key (id)
);

alter table comic_book
    comment '漫画书籍';

create index series_id on comic_book
    (
     series_id
        );

create table comic_series
(
    id             varchar(36) not null comment '主键',
    title          varchar(500) comment '标题',
    original_title varchar(500) comment '原标题',
    year           varchar(4) comment '年份',
    summary        text comment '简介',
    publisher      varchar(200) comment '出版社',
    book_count     int comment '卷数',
    rating         decimal(8, 2) comment '评分',
    status         varchar(10) comment '状态',
    path           varchar(500) comment '路径',
    bgm_id         varchar(36) comment '番组计划编号',
    added_at       bigint comment '加入时间',
    updated_at     bigint comment '更新时间',
    primary key (id)
);

alter table comic_series
    comment '漫画系列';

create index title on comic_series
    (
     title
        );

create index bgm_id on comic_series
    (
     bgm_id
        );

create table comic_series_author
(
    id        varchar(36) not null comment '主键',
    series_id varchar(36) comment '系列id',
    author_id varchar(36) comment '作者id',
    role      varchar(20) comment '角色',
    primary key (id)
);

alter table comic_series_author
    comment '漫画系列作者关联表';

create index series_id on comic_series_author
    (
     series_id
        );

create index author_id on comic_series_author
    (
     author_id
        );

create index role on comic_series_author
    (
     role
        );

create table movie_basic
(
    id                      varchar(36)  not null comment '主键',
    title                   varchar(500) not null comment '电影名',
    original_title          varchar(500) comment '原片名',
    title_sort              varchar(500) comment '排序名',
    year                    varchar(10) comment '首映年份',
    thumb                   varchar(500) comment '海报',
    art                     varchar(500) comment '艺术图',
    path                    varchar(500) comment '路径',
    user_rating             int comment '用户评分',
    summary                 text comment '简介',
    duration                int comment '片长(秒)',
    content_rating          varchar(10) comment '电影评级',
    originally_available_at varchar(10) comment '首映日期',
    studio                  varchar(200) comment '电影公司',
    rating                  decimal(8, 2) comment '评分',
    last_viewed_at          bigint comment '观看时间',
    view_count              int comment '观看次数',
    website                 varchar(500) comment '电影网站',
    imdb_id                 varchar(36) comment 'IMDb编号',
    douban_id               varchar(36) comment '豆瓣编号',
    tmdb_id                 varchar(36) comment 'TMDB编号',
    added_at                bigint comment '加入时间',
    updated_at              bigint comment '更新时间',
    primary key (id)
);

alter table movie_basic
    comment '电影';

create index title on movie_basic
    (
     title
        );

create index original_title on movie_basic
    (
     original_title
        );

create index updated_at on movie_basic
    (
     updated_at
        );

create index imdb_id on movie_basic
    (
     imdb_id
        );

create index douban_id on movie_basic
    (
     douban_id
        );

create index tmdb_id on movie_basic
    (
     tmdb_id
        );

create table movie_basic_actor
(
    id        varchar(36) not null comment '主键',
    movie_id  varchar(36) not null comment '电影id',
    actor_id  varchar(36) not null comment '演职员id',
    role      varchar(20) comment '角色',
    play_role varchar(200) comment '饰演角色',
    primary key (id)
);

alter table movie_basic_actor
    comment '电影演职员关联表';

create index actor_id on movie_basic_actor
    (
     actor_id
        );

create index movie_id on movie_basic_actor
    (
     movie_id
        );

create index role on movie_basic_actor
    (
     role
        );

create table movie_basic_collection
(
    id             varchar(36)  not null comment '主键',
    movie_id       varchar(36)  not null comment '电影id',
    collection_id  varchar(36)  not null comment '集合id',
    title          varchar(500) not null comment '电影名',
    original_title varchar(500) comment '原片名',
    year           varchar(10) comment '首映年份',
    douban_id      varchar(36) comment '豆瓣编号',
    thumb          varchar(500) comment '海报',
    status         varchar(1) comment '状态',
    comment        varchar(2000) comment '评语',
    pos            int comment '顺序',
    primary key (id)
);

alter table movie_basic_collection
    comment '电影集合关联表';

create index movie_d on movie_basic_collection
    (
     movie_id
        );

create index collection_id on movie_basic_collection
    (
     collection_id
        );

create table movie_collection
(
    id          varchar(36) not null comment '主键',
    title       varchar(500) comment '标题',
    summary     text comment '简介',
    thumb       varchar(500) comment '海报',
    child_count int comment '项目数量',
    douban_id   varchar(36) comment '豆瓣编号',
    added_at    bigint comment '加入时间',
    updated_at  bigint comment '更新时间',
    primary key (id)
);

alter table movie_collection
    comment '电影集合';

create table movie_douban_weekly
(
    id             varchar(36) not null comment '主键',
    title          varchar(500) comment '电影名',
    original_title varchar(500) comment '原片名',
    year           varchar(10) comment '首映年份',
    thumb          varchar(500) comment '海报',
    listing_detail varchar(2000) comment '上榜情况',
    status         varchar(1) comment '在榜状态',
    top            int comment '最高名次',
    douban_id      varchar(36) comment '豆瓣编号',
    memo           varchar(100) comment '备注',
    primary key (id)
);

alter table movie_douban_weekly
    comment '豆瓣电影口碑榜';

create table music_album
(
    id                      varchar(36) not null comment '主键',
    title                   varchar(500) comment '标题',
    artists                 varchar(500) comment '艺术家',
    summary                 text comment '简介',
    type                    varchar(50) comment '专辑类型',
    release_country         varchar(100) comment '发行国家',
    label                   varchar(200) comment '唱片公司',
    year                    varchar(4) comment '首发年份',
    originally_available_at varchar(10) comment '首发日期',
    disc_count              int comment '碟数',
    track_count             int comment '音轨数',
    thumb                   varchar(500) comment '封面图',
    musicbrainz_id          varchar(36) comment 'MusicBrainz编号',
    netease_id              varchar(36) comment '网易云音乐编号',
    added_at                bigint comment '加入时间',
    updated_at              bigint comment '更新时间',
    primary key (id)
);

alter table music_album
    comment '专辑';

create table music_artist
(
    id             varchar(36) not null comment '主键',
    title          varchar(500) comment '名称',
    title_sort     varchar(500) comment '排序名称',
    area           varchar(100) comment '国家地区',
    summary        text comment '简介',
    thumb          varchar(500) comment '封面图',
    musicbrainz_id varchar(36) comment 'MusicBrainz编号',
    netease_id     varchar(36) comment '网易云音乐编号',
    added_at       bigint comment '加入时间',
    updated_at     bigint comment '更新时间',
    primary key (id)
);

alter table music_artist
    comment '艺术家';

create index title on music_artist
    (
     title
        );

create index musicbrainz_id on music_artist
    (
     musicbrainz_id
        );

create index netease_id on music_artist
    (
     netease_id
        );

create table music_artist_album
(
    id        varchar(36) not null comment '主键',
    album_id  varchar(36) comment '专辑id',
    artist_id varchar(36) comment '艺术家id',
    primary key (id)
);

alter table music_artist_album
    comment '艺术家专辑关联表';

create index album_id on music_artist_album
    (
     album_id
        );

create index artist_id on music_artist_album
    (
     artist_id
        );

create table music_track
(
    id             varchar(36) not null comment '主键',
    album_id       varchar(36) comment '专辑id',
    title          varchar(500) comment '标题',
    artists        varchar(500) comment '艺术家',
    duration       int comment '曲长(毫秒)',
    track_number   int comment '曲号',
    disc_number    int comment '碟号',
    format         varchar(20) comment '文件格式',
    path           varchar(500) comment '文件路径',
    musicbrainz_id varchar(36) comment 'MusicBrainz编号',
    netease_id     varchar(36) comment '网易云音乐编号',
    added_at       bigint comment '加入时间',
    updated_at     bigint comment '更新时间',
    primary key (id)
);

alter table music_track
    comment '曲目';

create index album_id on music_track
    (
     album_id
        );

create index title on music_track
    (
     title
        );

create table subject_attribute
(
    id           varchar(36) not null comment '主键',
    attribute_id varchar(36) comment '属性id',
    subject_id   varchar(36) comment '项目id',
    primary key (id)
);

alter table subject_attribute
    comment '项目属性关联表';

create index attribute_id on subject_attribute
    (
     attribute_id
        );

create index subject_id on subject_attribute
    (
     subject_id
        );

create table task
(
    id           varchar(36) not null comment '主键',
    subject_id   varchar(36) comment '项目id',
    subject_type varchar(20) comment '项目类型',
    task_type    varchar(20) comment '任务类型',
    added_at     bigint comment '加入时间',
    task_status  varchar(1) comment '任务状态',
    primary key (id)
);

alter table task
    comment '任务';

create index task_type on task
    (
     task_type
        );

create table tvshow_episode
(
    id                      varchar(36) not null comment '主键',
    show_id                 varchar(36) comment '剧集id',
    season_id               varchar(36) comment '单季id',
    title                   varchar(500) comment '标题',
    studio                  varchar(200) comment '制片公司',
    content_rating          varchar(10) comment '剧集评级',
    summary                 text comment '简介',
    year                    varchar(10) comment '首播年份',
    originally_available_at varchar(10) comment '首播日期',
    season_number           int comment '季号',
    episode_number          int comment '集号',
    rating                  decimal(8, 2) comment '评分',
    duration                int comment '片长(秒)',
    thumb                   varchar(500) comment '海报',
    art                     varchar(500) comment '艺术图',
    added_at                bigint comment '加入时间',
    updated_at              bigint comment '更新时间',
    primary key (id)
);

alter table tvshow_episode
    comment '单集';

create index season_id on tvshow_episode
    (
     season_id
        );

create index show_id on tvshow_episode
    (
     show_id
        );

create table tvshow_season
(
    id                      varchar(36) not null comment '主键',
    show_id                 varchar(36) comment '剧集id',
    title                   varchar(500) comment '标题',
    original_title          varchar(500) comment '原标题',
    summary                 text comment '简介',
    year                    varchar(10) comment '首映年份',
    originally_available_at varchar(10) comment '首播日期',
    rating                  decimal(8, 2) comment '评分',
    season_number           int comment '季号',
    thumb                   varchar(500) comment '海报',
    art                     varchar(500) comment '艺术图',
    imdb_id                 varchar(36) comment 'IMDb编号',
    douban_id               varchar(36) comment '豆瓣编号',
    tmdb_id                 varchar(36) comment 'TMDB编号',
    added_at                bigint comment '加入时间',
    updated_at              bigint comment '更新时间',
    primary key (id)
);

alter table tvshow_season
    comment '单季';

create index show_id on tvshow_season
    (
     show_id
        );

create table tvshow_season_actor
(
    id        varchar(36) not null comment '主键',
    actor_id  varchar(36) comment '演职员id',
    season_id varchar(36) comment '单季id',
    role      varchar(20) comment '角色',
    play_role varchar(200) comment '饰演角色',
    primary key (id)
);

alter table tvshow_season_actor
    comment '单季演职员关联表';


create index season_id on tvshow_season_actor
    (
     season_id
        );

create index actor_id on tvshow_season_actor
    (
     actor_id
        );


create table tvshow_show
(
    id                      varchar(36) not null comment '主键',
    title                   varchar(500) comment '剧集名',
    original_title          varchar(500) comment '原剧集名',
    studio                  varchar(200) comment '制片公司',
    content_rating          varchar(10) comment '剧集评级',
    summary                 text comment '简介',
    year                    varchar(10) comment '首播年份',
    originally_available_at varchar(10) comment '首播日期',
    rating                  decimal(8, 2) comment '评分',
    thumb                   varchar(500) comment '海报',
    art                     varchar(500) comment '艺术图',
    path                    varchar(500) comment '路径',
    season_count            int comment '季数',
    imdb_id                 varchar(36) comment 'IMDb编号',
    douban_id               varchar(36) comment '豆瓣编号',
    tmdb_id                 varchar(36) comment 'TMDB编号',
    added_at                bigint comment '加入时间',
    updated_at              bigint comment '更新时间',
    primary key (id)
);

alter table tvshow_show
    comment '剧集';
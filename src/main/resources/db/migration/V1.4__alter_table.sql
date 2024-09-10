alter table movie_basic
    change column title_sort sort_title varchar(500) comment '排序名';

alter table tvshow_episode
    add column original_title varchar(500) comment '原标题' after title,
    add column season_index   int comment '季号' after episode_index,
    add column filename       varchar(500) comment '文件名' after art,
    add column tmdb_id       varchar(36) comment 'TMDB编号' after filename;

alter table tvshow_season
    add column sort_title varchar(500) comment '排序名' after original_title;

alter table tvshow_show
    add column sort_title varchar(500) comment '排序名' after original_title;

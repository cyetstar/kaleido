alter table tvshow_episode
    drop column season_number,
    change column episode_number episode_index int comment '集号';

alter table tvshow_season
    change column season_number season_index int comment '季号';

alter table tvshow_show
    change column season_count total_seasons int comment '总季数';
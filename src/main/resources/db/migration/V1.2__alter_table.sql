alter table tvshow_episode
    drop column season_number,
    change column episode_number episode_index int;

alter table tvshow_season
    change column season_number season_index int;

alter table tvshow_show
    change column season_count total_seasons int;
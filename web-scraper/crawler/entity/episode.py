from dataclasses import dataclass, field


@dataclass
class Episode:
    tmdb_id: str = None
    premiered: str = None
    year: str = None
    episode_number: str = None
    season_number: str = None
    title: str = None
    plot: str = None
    average: str = None
    votes: str = None
    runtime: str = None
    thumb: str = None

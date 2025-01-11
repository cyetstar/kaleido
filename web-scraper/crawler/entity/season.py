from dataclasses import dataclass, field

from crawler.entity.actor import Actor
from crawler.entity.episode import Episode


@dataclass
class Season:
    douban_id: str = None
    tmdb_id: str = None
    imdb_id: str = None
    title: str = None
    original_title: str = None
    year: str = None
    plot: str = None
    directors: list[Actor] = field(default_factory=list)
    writers: list[Actor] = field(default_factory=list)
    actors: list[Actor] = field(default_factory=list)
    genres: list = field(default_factory=list)
    languages: list = field(default_factory=list)
    countries: list = field(default_factory=list)
    premiered: str = None
    votes: str = None
    average: str = None
    poster: str = None
    season_number: str = None
    episodes: list[Episode] = field(default_factory=list)
    episode_count: str = None

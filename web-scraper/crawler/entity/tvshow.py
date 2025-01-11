from dataclasses import dataclass, field

from crawler.entity.actor import Actor


@dataclass
class Tvshow:
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
    mpaa: str = None
    akas: list = field(default_factory=list)
    studios: list = field(default_factory=list)
    seasons: list = field(default_factory=list)

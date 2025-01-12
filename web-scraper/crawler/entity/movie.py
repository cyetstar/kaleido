from dataclasses import dataclass, field
from typing import List
from crawler.entity.actor import Actor


@dataclass
class Movie:
    douban_id: str = None
    tmdb_id: str = None
    imdb_id: str = None
    title: str = None
    original_title: str = None
    year: str = None
    plot: str = None
    tags: List = field(default_factory=list)
    directors: List[Actor] = field(default_factory=list)
    writers: List[Actor] = field(default_factory=list)
    actors: List[Actor] = field(default_factory=list)
    genres: List = field(default_factory=list)
    languages: List = field(default_factory=list)
    countries: List = field(default_factory=list)
    premiered: str = None
    votes: str = None
    average: str = None
    poster: str = None
    mpaa: str = None
    akas: List = field(default_factory=list)
    doulists: List = field(default_factory=list)
    douban_top250: str = None
    website: str = None

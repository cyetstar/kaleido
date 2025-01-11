# _*_ coding:utf-8 _*_
import sys

from crawler.entity.episode import Episode
from crawler.entity.movie import Movie
from crawler.entity.season import Season


sys.path.append(".")
sys.path.append("..")

import json
from lxml import etree
from lxml.html import tostring

from helper import *
from crawler.entity.tvshow import Tvshow
from crawler.entity.actor import Actor
import config

poster_sizes = ["w92", "w154", "w185", "w342", "w500", "w780", "original"]
profile_sizes = ["w45", "w185", "h632", "original"]
still_sizes = ["w92", "w185", "w300", "original"]


def get_tmdb_id(imdb_id, type="movie"):
    if not is_empty(imdb_id):
        return None
    url = "https://api.themoviedb.org/3/find/{}?external_source=imdb_id".format(imdb_id)
    result = __get_result(url)
    if type == "movie":
        movie_results = result["movie_results"]
        if movie_results is not None and len(movie_results) > 0:
            return str(movie_results[0].get("id"))
    elif type == "tvshow":
        tvshow_results = result["tv_results"]
        tv_episode_results = result["tv_episode_results"]
        if tvshow_results is not None and len(tvshow_results) > 0:
            return str(tvshow_results[0].get("id"))
        elif tv_episode_results is not None and len(tv_episode_results) > 0:
            return str(tv_episode_results[0].get("show_id"))
    else:
        return None


def search_tv(keyword):
    url = "https://api.themoviedb.org/3/search/tv?query={}&language=zh&include_adult=true".format(
        keyword
    )
    result = __get_result(url)
    results = result["results"]
    if results is not None and len(results) > 0:
        result = [__deal_search_tv_item(item) for item in results]
        return list(filter(lambda x: x is not None, result))
    else:
        return None


def get_movie_by_imdb_id(imdb_id):
    tmdb_id = get_tmdb_id(imdb_id)
    if not is_empty(tmdb_id):
        return get_movie(imdb_id)
    return None


def get_movie(tmdb_id):
    try:
        movie_detail = _req_movie_detail(tmdb_id)
        movie = Movie()
        movie.tmdb_id = tmdb_id
        movie.imdb_id = _req_tv_external_ids(tmdb_id)
        movie.title = movie_detail["name"]
        movie.original_title = movie_detail["original_name"]
        movie.year = movie_detail["first_air_date"][0:4]
        movie.plot = movie_detail["overview"]
        movie.genres = list(map(lambda x: x["name"], movie_detail["genres"]))
        movie.premiered = movie_detail["first_air_date"]
        movie.votes = movie_detail["vote_count"]
        movie.average = movie_detail["vote_average"]
        movie.poster = __get_image_url(movie_detail["poster_path"])
        movie.mpaa = _req_tv_content_ratings(tmdb_id)
        credits = _req_movie_credits(tmdb_id)
        movie.directors = list(
            map(
                lambda x: _deal_tv_credits_item(x, "director"),
                filter(lambda x: x["job"] == "Director", credits["crew"]),
            )
        )
        movie.writers = list(
            map(
                lambda x: _deal_tv_credits_item(x, "writer"),
                filter(lambda x: x["job"] == "Writer", credits["crew"]),
            )
        )
        movie.actors = list(
            map(lambda x: _deal_tv_credits_item(x, "actor"), credits["cast"])
        )
        return movie
    except Exception as e:
        print("TheMovieDB >> 获取电影信息发生错误：" + str(e))
        return None


def _req_movie_detail(tmdb_id):
    url = "https://api.themoviedb.org/3/movie/{}?language=zh".format(tmdb_id)
    result = __get_result(url)
    return result


def get_tv(tmdb_id):
    try:
        tvshow_detail = _req_tv_detail(tmdb_id)
        tvshow = Tvshow()
        tvshow.tmdb_id = tmdb_id
        tvshow.imdb_id = _req_tv_external_ids(tmdb_id)
        tvshow.title = tvshow_detail["name"]
        tvshow.original_title = tvshow_detail["original_name"]
        tvshow.year = tvshow_detail["first_air_date"][0:4]
        tvshow.plot = tvshow_detail["overview"]
        tvshow.genres = list(map(lambda x: x["name"], tvshow_detail["genres"]))
        tvshow.premiered = tvshow_detail["first_air_date"]
        tvshow.votes = tvshow_detail["vote_count"]
        tvshow.average = tvshow_detail["vote_average"]
        tvshow.poster = __get_image_url(tvshow_detail["poster_path"])
        studios = [
            list(map(lambda x: x["name"], tvshow_detail["production_companies"])),
            list(map(lambda x: x["name"], tvshow_detail["networks"])),
        ]
        studios = [j for i in studios for j in i]
        studios = list(set(studios))
        tvshow.studios = studios
        tvshow.mpaa = _req_tv_content_ratings(tmdb_id)
        credits = _req_tv_credits(tmdb_id)
        tvshow.directors = list(
            map(
                lambda x: _deal_tv_credits_item(x, "director"),
                filter(lambda x: x["job"] == "Director", credits["crew"]),
            )
        )
        tvshow.writers = list(
            map(
                lambda x: _deal_tv_credits_item(x, "writer"),
                filter(lambda x: x["job"] == "Writer", credits["crew"]),
            )
        )
        tvshow.actors = list(
            map(lambda x: _deal_tv_credits_item(x, "actor"), credits["cast"])
        )
        tvshow.seasons = list(
            map(
                lambda x: _deal_tv_seasons_item(x, tmdb_id, tvshow),
                tvshow_detail["seasons"],
            )
        )
        return tvshow
    except Exception as e:
        print("TheMovieDB >> 获取电视剧信息发生错误：" + str(e))
        return None


def _req_tv_detail(tmdb_id):
    try:
        url = "https://api.themoviedb.org/3/tv/{}?language=zh".format(tmdb_id)
        result = __get_result(url)
        return result
    except Exception as e:
        print("TheMovieDB >> 获取电视剧详情发生错误：" + str(e))
        return None


def _req_tv_external_ids(series_id):
    try:
        url = "https://api.themoviedb.org/3/tv/{}/external_ids".format(series_id)
        result = __get_result(url)
        return result.get("imdb_id", None)
    except Exception as e:
        print("TheMovieDB >> 获取电视剧外部ID发生错误：" + str(e))
        return None


def _req_tv_content_ratings(tmdb_id):
    try:
        url = "https://api.themoviedb.org/3/tv/{}/content_ratings".format(tmdb_id)
        result = __get_result(url)
        results = result["results"]
        if len(results) == 0:
            return None
        data = list(filter(lambda x: x["iso_3166_1"] == "US", results))
        if len(data) == 0:
            data = list(filter(lambda x: x["iso_3166_1"] == "UK", results))
        if len(data) == 0:
            data = list(filter(lambda x: x["iso_3166_1"] == "JP", results))
        if len(data) == 0:
            data = list(filter(lambda x: x["iso_3166_1"] == "HK", results))
        if len(data) == 0:
            data = list(filter(lambda x: x["iso_3166_1"] == "TW", results))
        if len(data) > 0:
            return data[0]["rating"]
    except Exception as e:
        print("TheMovieDB >> 获取电视剧内容评级发生错误：" + str(e))
        return None


def _req_tv_credits(tmdb_id):
    try:
        url = "https://api.themoviedb.org/3/tv/{}/credits?language=zh".format(tmdb_id)
        result = __get_result(url)
        return result
    except Exception as e:
        print("TheMovieDB >> 获取电视剧演职员发生错误：" + str(e))
        return None


def _deal_tv_credits_item(item, actor_type):
    try:
        actor = Actor()
        actor.douban_id = None
        actor.thumb = __get_image_url(item.get("profile_path"), profile_sizes[2])
        actor.role = item.get("character")
        actor.cn_name = item.get("name")
        actor.en_name = item.get("original_name")
        actor.actor_type = actor_type
        return actor
    except Exception as e:
        print("TheMovieDB >> 处理电视剧演职员发生错误：" + str(e))
        return None


def _deal_tv_seasons_item(item, series_id, tvshow):
    try:
        season_number = item.get("season_number")
        season_detail = _req_tv_season_detail(series_id, season_number)
        season = Season()
        season.season_number = season_number
        season.tmdb_id = season_detail.get("id")
        season.imdb_id = _req_season_external_ids(series_id, season_number)
        season.title = season_detail.get("name")
        season.original_title = season_detail.get("original_name")
        season.year = (
            season_detail.get("air_date")[0:4]
            if season_detail.get("air_date") is not None
            else None
        )
        season.premiered = season_detail.get("air_date")
        season.plot = season_detail.get("overview")
        season.poster = __get_image_url(season_detail.get("poster_path"))
        season.languages = tvshow.languages
        season.countries = tvshow.countries
        season.genres = tvshow.genres
        credits = _req_season_credits(series_id, season_number)
        season.directors = list(
            map(
                lambda x: _deal_tv_credits_item(x, "director"),
                filter(lambda x: x["job"] == "Director", credits["crew"]),
            )
        )
        season.writers = list(
            map(
                lambda x: _deal_tv_credits_item(x, "writer"),
                filter(lambda x: x["job"] == "Writer", credits["crew"]),
            )
        )
        season.actors = list(
            map(lambda x: _deal_tv_credits_item(x, "actor"), credits["cast"])
        )
        season.votes = season_detail.get("vote_count")
        season.average = season_detail.get("vote_average")
        season.episode_count = season_detail.get("episode_count")
        season.episodes = list(
            map(lambda x: _deal_tv_episodes_item(x), season_detail.get("episodes"))
        )

        return season
    except Exception as e:
        print("TheMovieDB >> 处理电视剧季集发生错误：" + str(e))
        return None


def _req_tv_season_detail(tmdb_id, season_number):
    try:
        url = "https://api.themoviedb.org/3/tv/{}/season/{}?language=zh".format(
            tmdb_id, season_number
        )
        result = __get_result(url)
        return result
    except Exception as e:
        print("TheMovieDB >> 获取电视剧季集详情发生错误：" + str(e))
        return None


def _req_season_external_ids(series_id, season_number):
    try:
        url = "https://api.themoviedb.org/3/tv/{}/season/{}/external_ids".format(
            series_id, season_number
        )
        result = __get_result(url)
        return result.get("imdb_id", None)
    except Exception as e:
        print("TheMovieDB >> 获取电视剧季集外部ID发生错误：" + str(e))
        return None


def _req_season_credits(series_id, season_number):
    try:
        url = "https://api.themoviedb.org/3/tv/{}/season/{}/credits?language=zh".format(
            series_id, season_number
        )
        result = __get_result(url)
        return result
    except Exception as e:
        print("TheMovieDB >> 获取电视剧季集演职员发生错误：" + str(e))
        return None


def _deal_tv_episodes_item(item):
    try:
        episode = Episode()
        episode.tmdb_id = item["id"]
        episode.premiered = item["air_date"]
        episode.year = item["air_date"][0:4] if item["air_date"] is not None else None
        episode.episode_number = item["episode_number"]
        episode.season_number = item["season_number"]
        episode.title = item["name"]
        episode.plot = item["overview"]
        episode.average = item["vote_average"]
        episode.votes = item["vote_count"]
        episode.runtime = item["runtime"]
        episode.thumb = __get_image_url(item["still_path"])
        return episode
    except Exception as e:
        print("TheMovieDB >> 处理电视剧季集剧集发生错误：" + str(e))
        return None


def __transform_movie(movie):
    try:
        credits = _req_movie_credits(movie["id"])
        return {
            "douban_id": None,
            "tmdb_id": str(movie["id"]),
            "imdb_id": str(movie["imdb_id"]),
            "title": movie["title"],
            "original_title": movie["original_title"],
            "year": movie["release_date"][0:4],
            "plot": movie["overview"],
            "tags": [],
            "directors": list(
                map(
                    __get_actor,
                    filter(lambda x: x["job"] == "Director", credits["crew"]),
                )
            ),
            "writers": list(
                map(
                    __get_actor, filter(lambda x: x["job"] == "Writer", credits["crew"])
                )
            ),
            "actors": list(map(__get_actor, credits["cast"])),
            "genres": list(map(lambda x: x["name"], movie["genres"])),
            "premiered": movie["release_date"],
            "doulists": [],
            "votes": movie["vote_count"],
            "average": movie["vote_average"],
            "poster": __get_image_url(movie["poster_path"]),
            "mpaa": None,
            "source": "tmdb",
        }
    except Exception as e:
        print("TheMovieDB >> 获取影片信息发生错误：" + str(e))


# TV


def search_movie(keyword):
    url = "https://api.themoviedb.org/3/search/movie?query={}&language=zh&include_adult=true".format(
        keyword
    )
    result = __get_result(url)
    results = result["results"]
    if results is not None and len(results) > 0:
        result = [__deal_search_movie_item(item) for item in results]
        return list(filter(lambda x: x is not None, result))
    else:
        return None


def __deal_search_tv_item(item):
    return {
        "tmdb_id": item.get("id"),
        "year": item.get("first_air_date")[0:4],
        "title": item.get("name"),
        "original_title": item.get("original_name"),
        "abstract": item.get("overview"),
        "poster": __get_image_url(item.get("poster_path"), poster_sizes[2]),
    }


def __deal_search_movie_item(item):
    return {
        "tmdb_id": item.get("id"),
        "year": item.get("release_date")[0:4],
        "title": item.get("title"),
        "original_title": item.get("original_title"),
        "abstract": item.get("overview"),
        "poster": __get_image_url(item.get("poster_path"), poster_sizes[2]),
    }


def get_movie_certification(tmdb_id):
    url = "https://api.themoviedb.org/3/movie/{}/release_dates".format(tmdb_id)
    result = __get_result(url)
    results = list(
        filter(
            lambda x: x["certification"] != "",
            map(
                lambda x: {
                    "country": x["iso_3166_1"],
                    "certification": x["release_dates"][0]["certification"],
                },
                result["results"],
            ),
        )
    )
    if len(results) == 0:
        return None
    data = list(filter(lambda x: x["country"] == "US", results))
    if len(data) == 0:
        data = list(filter(lambda x: x["country"] == "JP", results))
    if len(data) == 0:
        data = list(filter(lambda x: x["country"] == "HK", results))
    if len(data) == 0:
        data = list(filter(lambda x: x["country"] == "TW", results))
    if len(data) > 0:
        return data[0]["certification"]


def __get_detail_by_imdb(imdb_id):
    url = "https://api.themoviedb.org/3/find/{}?external_source=imdb_id".format(imdb_id)
    result = __get_result(url)
    movie_results = result["movie_results"]
    if len(movie_results) > 0:
        movie = movie_results[0]
        return _req_movie_detail(movie["id"])
    else:
        return None


def _req_movie_credits(tmdb_id):
    url = "https://api.themoviedb.org/3/movie/{}/credits?language=zh".format(tmdb_id)
    result = __get_result(url)
    return result


def __get_actor(item):
    return {
        "douban_id": None,
        "tmdb_id": item["id"],
        "cn_name": None,
        "en_name": item["name"],
        "role": item.get("character"),
        "thumb": __get_image_url(item["profile_path"], profile_sizes[2]),
    }


def __get_image_url(path, size="original"):
    if path is None:
        return None
    return "https://image.tmdb.org/t/p/{}".format(size) + path


def __get_result(url):
    api_key = config.Config().tmdb().get("api_key")
    headers = {
        "accept": "application/json",
        "Authorization": "Bearer {}".format(api_key),
    }
    result = get_html(url, headers=headers)
    result = json.loads(result.text)
    return result


if __name__ == "__main__":
    print(get_tv("274959"))

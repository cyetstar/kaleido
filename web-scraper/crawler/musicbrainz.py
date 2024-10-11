# _*_ coding:utf-8 _*_
import sys

sys.path.append('.')
sys.path.append('..')

import json
import re
import random
from lxml import etree
from lxml.html import tostring

from helper import *
import config

poster_sizes = [
    "w92",
    "w154",
    "w185",
    "w342",
    "w500",
    "w780",
    "original"
]

profile_sizes = [
    "w45",
    "w185",
    "h632",
    "original"
]

still_sizes = [
    "w92",
    "w185",
    "w300",
    "original"
]


def search(keyword):
    url = "https://api.themoviedb.org/3/search/movie?query={}&language=zh&include_adult=true".format(keyword)
    result = __get_result(url)
    results = result["results"]
    if results is not None and len(results) > 0:
        result = [__deal_search_item(item) for item in results]
        return list(filter(lambda x: x is not None, result))
    else:
        return None


def get_movie_by_imdb_id(imdb_id):
    tmdb_id = get_tmdb_id(imdb_id)
    movie = None
    if tmdb_id is not None and len(tmdb_id) > 0:
        movie = __get_movie_detail(tmdb_id)
    if movie is not None:
        return __transform_movie(movie)
    return None


def get_movie(tmdb_id):
    movie = __get_movie_detail(tmdb_id)
    if movie is not None:
        return __transform_movie(movie)
    return None


def __get_tvshow_content_rating(tmdb_id):
    url = "https://api.themoviedb.org/3/tv/{}/content_ratings".format(tmdb_id)
    result = __get_result(url)
    results = result['results']
    if len(results) == 0:
        return None
    data = list(filter(lambda x: x['iso_3166_1'] == 'US', results))
    if len(data) == 0:
        data = list(filter(lambda x: x['iso_3166_1'] == 'UK', results))
    if len(data) == 0:
        data = list(filter(lambda x: x['iso_3166_1'] == 'JP', results))
    if len(data) == 0:
        data = list(filter(lambda x: x['iso_3166_1'] == 'HK', results))
    if len(data) == 0:
        data = list(filter(lambda x: x['iso_3166_1'] == 'TW', results))
    if len(data) > 0:
        return data[0]['rating']


def get_tvshow(season_imdb_id):
    series_id = get_tmdb_id(season_imdb_id, type="tvshow")
    if series_id is None:
        raise Exception("根据imdb获取tmdb的series_id失败")
    tvshow_detail = __get_tvshow_detail(series_id)
    series_imdb_id = __get_external_id(series_id)
    content_rating = __get_tvshow_content_rating(series_id)
    studios = [list(map(lambda x: x['name'], tvshow_detail['production_companies'])),
               list(map(lambda x: x['name'], tvshow_detail['networks']))]
    studios = [j for i in studios for j in i]
    studios = list(set(studios))
    tvshow = {
        "tmdb_id": series_id,
        "imdb_id": series_imdb_id,
        "studios": studios,
        "mpaa": content_rating,
        "seasons": tvshow_detail['seasons']
    }
    return tvshow


def supply_season(series_id, season):
    season_detail = __get_season_detail(series_id, season['season_number'])
    if season_detail:
        season['episodes'] = list(map(lambda x: __transform_episode(x), season_detail['episodes']))
        season['tmdb_id'] = season_detail['id']
    return season


def __get_external_id(series_id):
    url = "https://api.themoviedb.org/3/tv/{}/external_ids".format(series_id)
    result = __get_result(url)
    return result['imdb_id']


def __transform_episode(episode):
    return {
        "tmdb_id": episode['id'],
        "premiered": episode['air_date'],
        "year": episode['air_date'][0:4] if episode['air_date'] is not None else None,
        "episode_number": episode['episode_number'],
        "season_number": episode['season_number'],
        "title": episode['name'],
        "plot": episode['overview'],
        "average": episode['vote_average'],
        "votes": episode['vote_count'],
        "runtime": episode['runtime'],
        "thumb": __get_image_url(episode['still_path'])
    }


def __get_season_detail(tmdb_id, season_number):
    url = "https://api.themoviedb.org/3/tv/{}/season/{}?language=zh".format(tmdb_id, season_number)
    result = __get_result(url)
    return result


def __transform_movie(movie):
    try:
        credits = __get_credits(movie['id'])
        return {
            "douban_id": None,
            "tmdb_id": str(movie['id']),
            "imdb_id": str(movie['imdb_id']),
            "title": movie['title'],
            "original_title": movie['original_title'],
            "year": movie['release_date'][0:4],
            "plot": movie['overview'],
            "tags": [],
            "directors": list(map(__get_actor, filter(lambda x: x['job'] == 'Director', credits['crew']))),
            "writers": list(map(__get_actor, filter(lambda x: x['job'] == 'Writer', credits['crew']))),
            "actors": list(map(__get_actor, credits['cast'])),
            "genres": list(map(lambda x: x['name'], movie['genres'])),
            "premiered": movie['release_date'],
            "doulists": [],
            "votes": movie['vote_count'],
            "average": movie['vote_average'],
            "poster": __get_image_url(movie['poster_path']),
            "mpaa": None,
            "source": "tmdb"
        }
    except Exception as e:
        print("TheMovieDB >> 获取影片信息发生错误：" + str(e))


def __get_movie_detail(tmdb_id):
    url = "https://api.themoviedb.org/3/movie/{}?language=zh".format(tmdb_id)
    result = __get_result(url)
    return result


def __get_tvshow_detail(tmdb_id):
    url = "https://api.themoviedb.org/3/tv/{}?language=zh".format(tmdb_id)
    result = __get_result(url)
    return result


def get_tmdb_id(imdb_id, type="movie"):
    url = "https://api.themoviedb.org/3/find/{}?external_source=imdb_id".format(imdb_id)
    result = __get_result(url)
    if type == 'movie':
        movie_results = result["movie_results"]
        if movie_results is not None and len(movie_results) > 0:
            return str(movie_results[0].get("id"))
    elif type == 'tvshow':
        tvshow_results = result["tv_results"]
        tv_episode_results = result["tv_episode_results"]
        if tvshow_results is not None and len(tvshow_results) > 0:
            return str(tvshow_results[0].get("id"))
        elif tv_episode_results is not None and len(tv_episode_results) > 0:
            return str(tv_episode_results[0].get("show_id"))
    else:
        return None


def search_movie(keyword):
    url = "https://api.themoviedb.org/3/search/movie?query={}&language=zh&include_adult=true".format(keyword)
    result = __get_result(url)
    results = result["results"]
    if results is not None and len(results) > 0:
        result = [__deal_search_item(item) for item in results]
        return list(filter(lambda x: x is not None, result))
    else:
        return None


def __deal_search_item(item):
    return {
        "tmdb_id": item.get('id'),
        "year": item.get('release_date')[0:4],
        "title": item.get('title'),
        "original_title": item.get('original_title'),
        "abstract": item.get('overview'),
        "poster": __get_image_url(item.get('poster_path'), poster_sizes[2]),

    }


def get_movie_certification(tmdb_id):
    url = "https://api.themoviedb.org/3/movie/{}/release_dates".format(tmdb_id)
    result = __get_result(url)
    results = list(filter(lambda x: x['certification'] != '', map(lambda x: {
        "country": x['iso_3166_1'],
        "certification": x['release_dates'][0]['certification']
    }, result['results'])))
    if len(results) == 0:
        return None
    data = list(filter(lambda x: x['country'] == 'US', results))
    if len(data) == 0:
        data = list(filter(lambda x: x['country'] == 'JP', results))
    if len(data) == 0:
        data = list(filter(lambda x: x['country'] == 'HK', results))
    if len(data) == 0:
        data = list(filter(lambda x: x['country'] == 'TW', results))
    if len(data) > 0:
        return data[0]['certification']


def __get_detail_by_imdb(imdb_id):
    url = "https://api.themoviedb.org/3/find/{}?external_source=imdb_id".format(imdb_id)
    result = __get_result(url)
    movie_results = result["movie_results"]
    if len(movie_results) > 0:
        movie = movie_results[0]
        return __get_movie_detail(movie['id'])
    else:
        return None


def __get_credits(tmdb_id):
    url = "https://api.themoviedb.org/3/movie/{}/credits?language=zh".format(tmdb_id)
    result = __get_result(url)
    return result


def __get_actor(item):
    return {
        "douban_id": None,
        "tmdb_id": item['id'],
        "cn_name": None,
        "en_name": item['name'],
        "role": item.get('character'),
        "thumb": __get_image_url(item['profile_path'], profile_sizes[2])
    }


def __get_image_url(path, size="original"):
    if path is None:
        return None
    return "https://image.tmdb.org/t/p/{}".format(size) + path


def __get_result(url):
    api_key = config.Config().themoviedb().get("api_key")
    headers = {
        "accept": "application/json",
        "Authorization": "Bearer {}".format(api_key),
    }
    result = get_html(url, headers=headers)
    result = json.loads(result.text)
    return result


if __name__ == "__main__":
    print(get_movie_by_imdb_id("tt11681250"))

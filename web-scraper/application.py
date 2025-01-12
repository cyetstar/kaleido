from dataclasses import asdict
from flask import Flask, request, Response, jsonify, abort
from flask_cors import cross_origin

from crawler import douban, eastgame, bgm, netease, musicbrainz, tmdb, tlf

from crawler.entity.movie import Movie
from crawler.entity.season import Season
from crawler.entity.tvshow import Tvshow
from helper import *

app = Flask(__name__)
app.config["JSON_AS_ASCII"] = False


@app.route("/v1/movie/search")
@cross_origin(supports_credentials=True)
def movie_search():
    try:
        keyword = request.args.get("keyword")
        source = request.args.get("source", "douban")
        if source == "douban":
            data = douban.search(keyword)
        else:
            data = tmdb.search_movie(keyword)
        return _success(data)
    except Exception as e:
        return _error(4, str(e))


@app.route("/v1/movie/view")
@cross_origin(supports_credentials=True)
def movie_view():
    try:
        movie = Movie()
        douban_id = request.args.get("douban_id", None)
        tmdb_id = request.args.get("tmdb_id", None)
        imdb_id = request.args.get("imdb_id", None)

        if not is_empty(douban_id):
            movie = douban.get_movie(douban_id)
        if movie is None and not is_empty(imdb_id):
            movie = douban.get_movie_by_imdb_id(imdb_id)
        if movie is None and not is_empty(tmdb_id):
            movie = tmdb.get_movie(tmdb_id)
        if movie is None and not is_empty(imdb_id):
            movie = tmdb.get_movie_by_imdb_id(imdb_id)

        if movie is not None:
            tmdb_id = movie.tmdb_id
            imdb_id = movie.imdb_id

        if is_empty(tmdb_id) and not is_empty(imdb_id):
            tmdb_id = tmdb.get_tmdb_id(imdb_id)

        if not is_empty(tmdb_id):
            certification = tmdb.get_movie_certification(tmdb_id)
            movie.mpaa = certification

        return _success(asdict(movie))
    except Exception as e:
        _error(4, str(e))


@app.route("/v1/movie/weekly")
@cross_origin(supports_credentials=True)
def movie_weekly():
    try:
        data = douban.get_weekly()
        return _success(data)
    except Exception as e:
        _error(4, str(e))


@app.route("/v1/show/search")
@cross_origin(supports_credentials=True)
def show_search():
    try:
        keyword = request.args.get("keyword")
        source = request.args.get("source", "douban")
        if source == "tmdb":
            data = tmdb.search_tv(keyword)
        else:
            data = douban.search(keyword)
        return _success(data)
    except Exception as e:
        return _error(4, str(e))


@app.route("/v1/show/view")
@cross_origin(supports_credentials=True)
def show_view():
    try:
        douban_id = request.args.get("douban_id", None)
        tmdb_id = request.args.get("tmdb_id", None)

        if is_empty(douban_id) and is_empty(tmdb_id):
            raise Exception("参数错误")

        tvshow = None
        douban_season = None
        if not is_empty(douban_id):
            # 通过豆瓣id获取剧集信息
            douban_season = douban.get_season(douban_id)
            # 如果剧集信息中包含imdb_id，则通过imdb_id获取tmdb_id
            if (
                is_empty(tmdb_id)
                and douban_season is not None
                and not is_empty(douban_season.imdb_id)
            ):
                tmdb_id = tmdb.get_tmdb_id(douban_season.imdb_id)

        # 如果tmdb_id不为空，则通过tmdb_id获取剧集信息
        if not is_empty(tmdb_id):
            tvshow = tmdb.get_tv(tmdb_id)

        if tvshow is not None and douban_season is not None:
            if douban_season.season_number == 1:
                tvshow = _season_to_show(douban_season, tvshow)
            elif not is_empty(tvshow.imdb_id):
                first_sesson = douban.get_season_by_imdb_id(tvshow.imdb_id)
                tvshow = _season_to_show(first_sesson, tvshow)
            tvshow.seasons = list(
                map(
                    lambda x: (
                        _season_to_season(douban_season, x)
                        if x.season_number == douban_season.season_number
                        else x
                    ),
                    tvshow.seasons,
                )
            )
        elif tvshow is None and douban_season is not None:
            tvshow = Tvshow()
            tvshow = _season_to_show(douban_season, tvshow)
            tvshow.seasons = [douban_season]
        elif tvshow is None and douban_season is None:
            raise Exception("无法查询到剧集信息")
        return _success(asdict(tvshow))
    except Exception as e:
        return _error(4, str(e))


@app.route("/v1/album/search")
@cross_origin(supports_credentials=True)
def album_search():
    try:
        keyword = request.args.get("keyword")
        source = request.args.get("source", "netease")
        if source == "musicbrainz":
            data = musicbrainz.search(keyword)
        else:
            data = netease.search_album(keyword)
        return _success(data)
    except Exception as e:
        return _error(4, str(e))


@app.route("/v1/album/view")
@cross_origin(supports_credentials=True)
def album_view():
    try:
        netease_id = request.args.get("netease_id", "")
        data = netease.get_album(netease_id)
        return _success(data)
    except Exception as e:
        return _error(4, str(e))


@app.route("/v1/lyric/view")
@cross_origin(supports_credentials=True)
def lyric_view():
    try:
        netease_id = request.args.get("netease_id", "")
        data = netease.get_lyric(netease_id)
        return _success(data)
    except Exception as e:
        return _error(4, str(e))


@app.route("/v1/artist/search")
@cross_origin(supports_credentials=True)
def artist_search():
    try:
        keyword = request.args.get("keyword")
        source = request.args.get("source", "netease")
        if source == "musicbrainz":
            data = musicbrainz.search(keyword)
        else:
            data = netease.search_artist(keyword)
        return _success(data)
    except Exception as e:
        return _error(4, str(e))


@app.route("/v1/comic/search")
@cross_origin(supports_credentials=True)
def comic_search():
    try:
        keyword = request.args.get("keyword")
        source = request.args.get("source", "bgm")
        if source == "bgm_v0":
            data = bgm.search(keyword, "0")
        else:
            data = bgm.search(keyword, "1")
        return _success(data)
    except Exception as e:
        return _error(4, str(e))


@app.route("/v1/comic/view")
@cross_origin(supports_credentials=True)
def comic_view():
    try:
        bgm_id = request.args.get("bgm_id")
        data = bgm.get_book(bgm_id)
        return _success(data)
    except Exception as e:
        return _error(4, str(e))


@app.route("/v1/doulist/view")
@cross_origin(supports_credentials=True)
def doulist_view():
    try:
        douban_id = request.args.get("douban_id", "")
        data = douban.get_doulist(douban_id)
        return _success(data)
    except Exception as e:
        return _error(4, str(e))


@app.route("/v1/doulist/movies")
@cross_origin(supports_credentials=True)
def doulist_movies():
    try:
        douban_id = request.args.get("douban_id", "")
        start = request.args.get("start", "0")
        data = douban.get_doulist_movies(douban_id, start)
        return _success(data)
    except Exception as e:
        return _error(4, str(e))


def _season_to_show(douban_season: Season, tvshow: Tvshow):
    def _deal_tvshow_title(title):
        if title:
            title = re.sub(r"第\w季", "", title).strip()
            title = re.sub(r"Season\s\d+", "", title).strip()
            return title

    if douban_season is None:
        return tvshow

    tvshow.title = _deal_tvshow_title(douban_season.title)
    tvshow.original_title = _deal_tvshow_title(douban_season.original_title)
    tvshow.douban_id = douban_season.douban_id
    tvshow.imdb_id = (
        douban_season.imdb_id if not is_empty(douban_season.imdb_id) else tvshow.imdb_id
    )
    tvshow.year = (
        douban_season.year if not is_empty(douban_season.year) else tvshow.year
    )
    tvshow.plot = (
        douban_season.plot if not is_empty(douban_season.plot) else tvshow.plot
    )
    tvshow.directors = douban_season.directors
    tvshow.writers = douban_season.writers
    tvshow.actors = douban_season.actors
    tvshow.genres = douban_season.genres
    tvshow.languages = douban_season.languages
    tvshow.countries = douban_season.countries
    tvshow.premiered = douban_season.premiered
    tvshow.votes = douban_season.votes
    tvshow.average = douban_season.average
    tvshow.poster = douban_season.poster
    tvshow.akas = list(map(_deal_tvshow_title, douban_season.akas))
    return tvshow


def _season_to_season(douban_season: Season, season: Season):
    season.douban_id = douban_season.douban_id
    season.imdb_id = (
        douban_season.imdb_id if not is_empty(douban_season.imdb_id) else season.imdb_id
    )
    season.year = (
        douban_season.year if not is_empty(douban_season.year) else season.year
    )
    season.title = douban_season.title
    season.original_title = douban_season.original_title
    season.premiered = douban_season.premiered
    season.plot = douban_season.plot
    season.poster = douban_season.poster
    season.languages = douban_season.languages
    season.countries = douban_season.countries
    season.genres = douban_season.genres
    season.directors = douban_season.directors
    season.writers = douban_season.writers
    season.actors = douban_season.actors
    return season


def _success(data):
    return _result(data)


def _error(code, message):
    return _result(None, code, message)


def _result(data, code=0, message=None):
    result = {
        "data": data,
        "code": code,
        "message": message,
    }
    return jsonify(result)


if __name__ == "__main__":
    app.run(port=6000)

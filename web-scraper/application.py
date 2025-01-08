from flask import Flask, request, Response, jsonify, abort
from flask_cors import cross_origin

from crawler import douban, eastgame, bgm, netease, musicbrainz, tmdb, tlf

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
        return __success(data)
    except Exception as e:
        return __error(4, str(e))


@app.route("/v1/movie/view")
@cross_origin(supports_credentials=True)
def movie_view():
    try:
        data = None
        douban_id = request.args.get("douban_id", "")
        tmdb_id = request.args.get("tmdb_id", "")
        imdb_id = request.args.get("imdb_id", "")

        if len(douban_id) > 0:
            data = douban.get_movie(douban_id)
        if data is None and len(imdb_id) > 0:
            data = douban.get_movie_by_imdb_id(imdb_id)
        if data is None and len(tmdb_id) > 0:
            data = tmdb.get_movie(tmdb_id)
        if data is None and len(imdb_id) > 0:
            data = tmdb.get_movie_by_imdb_id(imdb_id)

        if data is not None:
            tmdb_id = str(data.get("tmdb_id", ""))
            imdb_id = str(data.get("imdb_id", ""))

        if len(tmdb_id) == 0 and len(imdb_id) > 0:
            tmdb_id = tmdb.get_tmdb_id(imdb_id)

        if tmdb_id is not None and len(tmdb_id) > 0:
            certification = tmdb.get_movie_certification(tmdb_id)
            data["mpaa"] = certification

        return __success(data)
    except Exception as e:
        __error(4, str(e))


@app.route("/v1/movie/weekly")
@cross_origin(supports_credentials=True)
def movie_weekly():
    try:
        data = douban.get_weekly()
        return __success(data)
    except Exception as e:
        __error(4, str(e))


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
        return __success(data)
    except Exception as e:
        return __error(4, str(e))


@app.route("/v1/show/view")
@cross_origin(supports_credentials=True)
def show_view():
    try:
        douban_id = request.args.get("douban_id", None)
        season = douban.get_season(douban_id)

        tmdb_id = request.args.get("tmdb_id", tmdb.get_tmdb_id(season["imdb_id"]))
        tvshow = tmdb.get_tvshow(tmdb_id)

        first_sesson = None
        if season["season_number"] == 1 or season["season_number"] is None:
            first_sesson = season
            # 如果为第一季，则直接补充剧集信息
            tvshow = douban.supply_tvshow(season, tvshow)
        elif tvshow["imdb_id"] is not None:
            # 通过imdb_id获取第一季信息
            first_sesson = douban.get_season_by_imdb_id(tvshow["imdb_id"])

        if first_sesson is not None:
            tvshow = douban.supply_tvshow(first_sesson, tvshow)
        else:
            raise Exception("无法补充剧集信息")

        if tvshow.get("tmdb_id") is not None:
            tvshow["seasons"] = [tmdb.supply_season(tvshow.get("tmdb_id"), season)]
        return __success(tvshow)
    except Exception as e:
        return __error(4, str(e))


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
        return __success(data)
    except Exception as e:
        return __error(4, str(e))


@app.route("/v1/album/view")
@cross_origin(supports_credentials=True)
def album_view():
    try:
        netease_id = request.args.get("netease_id", "")
        data = netease.get_album(netease_id)
        return __success(data)
    except Exception as e:
        return __error(4, str(e))


@app.route("/v1/lyric/view")
@cross_origin(supports_credentials=True)
def lyric_view():
    try:
        netease_id = request.args.get("netease_id", "")
        data = netease.get_lyric(netease_id)
        return __success(data)
    except Exception as e:
        return __error(4, str(e))


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
        return __success(data)
    except Exception as e:
        return __error(4, str(e))


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
        return __success(data)
    except Exception as e:
        return __error(4, str(e))


@app.route("/v1/comic/view")
@cross_origin(supports_credentials=True)
def comic_view():
    try:
        bgm_id = request.args.get("bgm_id")
        data = bgm.get_book(bgm_id)
        return __success(data)
    except Exception as e:
        return __error(4, str(e))


@app.route("/v1/doulist/view")
@cross_origin(supports_credentials=True)
def doulist_view():
    try:
        douban_id = request.args.get("douban_id", "")
        data = douban.get_doulist(douban_id)
        return __success(data)
    except Exception as e:
        return __error(4, str(e))


@app.route("/v1/doulist/movies")
@cross_origin(supports_credentials=True)
def doulist_movies():
    try:
        douban_id = request.args.get("douban_id", "")
        start = request.args.get("start", "0")
        data = douban.get_doulist_movies(douban_id, start)
        return __success(data)
    except Exception as e:
        return __error(4, str(e))


def __success(data):
    return __result(data)


def __error(code, message):
    return __result(None, code, message)


def __result(data, code=0, message=None):
    result = {
        "data": data,
        "code": code,
        "message": message,
    }
    return jsonify(result)


if __name__ == "__main__":
    app.run(port=6000)

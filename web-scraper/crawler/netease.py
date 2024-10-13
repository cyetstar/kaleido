# _*_ coding:utf-8 _*_
import datetime
import sys

sys.path.append('.')
sys.path.append('..')

import json

from helper import *
import config


def search_album(keyword):
    conf = config.Config().netease()
    url = '{}/cloudsearch?keywords={}&type=10&limit=30'.format(conf.get('url'), keyword)
    result = __get_result(url)
    result = result['result']
    album_count = result['albumCount']
    if album_count is not None and album_count > 0:
        return [__deal_album(item) for item in result['albums']]
    else:
        return None


def search_artist(keyword):
    conf = config.Config().netease()
    url = '{}/cloudsearch?keywords={}&type=100&limit=30'.format(conf.get('url'), keyword)
    result = __get_result(url)
    result = result['result']
    artist_count = result['artistCount']
    if artist_count is not None and artist_count > 0:
        return [__deal_artist(item) for item in result['artists']]
    else:
        return None


def get_album(netease_id):
    conf = config.Config().netease()
    url = '{}/album?id={}'.format(conf.get('url'), netease_id)
    result = __get_result(url)
    album = __deal_album(result['album'])
    if result['songs'] is not None:
        album['songs'] = [__deal_song(song) for song in result['songs']]
    return album


def get_lyric(netease_id):
    conf = config.Config().netease()
    url = '{}/lyric?id={}'.format(conf.get('url'), netease_id)
    result = __get_result(url)
    lrc = result['lrc']
    return lrc['lyric']


def __deal_album(album):
    return {
        'netease_id': album.get('id'),
        'title': album.get('name'),
        'company': album.get('company'),
        'pic_url': album.get('picUrl'),
        'description': album.get('description'),
        'publish_time': __timestamp_to_date(album.get('publishTime')),
        'artist': __deal_artist(album.get('artist')),
        'songs': [__deal_song(song) for song in album.get('songs')],
    }


def __deal_song(song):
    artists = []
    if song.get('ar') is not None:
        artists = [__deal_artist(artist) for artist in song.get('ar')]
    return {
        'netease_id': song.get('id'),
        'title': song.get('name'),
        'track_index': song.get('no'),
        'artists': artists,
        'duration': song.get('dt') / 1000,
    }


def __deal_artist(artist):
    return {
        'netease_id': artist.get('id'),
        'name': artist.get('name'),
        'trans': artist.get('trans'),
        'pic_url': artist.get('picUrl'),
    }


def __get_result(url):
    headers = {
        "accept": "application/json",
    }
    result = get_html(url, headers=headers)
    result = json.loads(result.text)
    return result


def __timestamp_to_date(timestamp):
    if timestamp is None:
        return None
    # 将毫秒时间戳转换为秒并获取日期
    return datetime.datetime.fromtimestamp(timestamp / 1000).strftime('%Y-%m-%d')

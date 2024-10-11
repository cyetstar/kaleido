# _*_ coding:utf-8 _*_
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
        return [__deal_search_album(item) for item in result['albums']]
    else:
        return None


def search_artist(keyword):
    conf = config.Config().netease()
    url = '{}/cloudsearch?keywords={}&type=100&limit=30'.format(conf.get('url'), keyword)
    result = __get_result(url)
    result = result['result']
    artist_count = result['artistCount']
    if artist_count is not None and artist_count > 0:
        return [__deal_search_artist(item) for item in result['artists']]
    else:
        return None


def get_album(netease_id):
    conf = config.Config().netease()
    url = '{}/album?id={}'.format(conf.get('url'), netease_id)
    result = __get_result(url)
    album = result['album']
    album['songs'] = result['songs']
    album = __deal_search_album(album)
    return album


def get_lyric(netease_id):
    conf = config.Config().netease()
    url = '{}/lyric?id={}'.format(conf.get('url'), netease_id)
    result = __get_result(url)
    lrc = result['lrc']
    return lrc['lyric']


def __deal_search_album(album):
    return {
        'netease_id': album.get('id'),
        'name': album.get('name'),
        'company': album.get('company'),
        'pic_url': album.get('picUrl'),
        'description': album.get('description'),
        'publish_time': album.get('publishTime'),
        'artist': __deal_search_artist(album.get('artist')),
        'song': [__deal_search_song(song) for song in album.get('songs')],
    }


def __deal_search_song(song):
    return {
        'netease_id': song.get('id'),
        'name': song.get('name'),
        'no': song.get('no'),
    }


def __deal_search_artist(artist):
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

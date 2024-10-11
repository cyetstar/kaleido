# _*_ coding:utf-8 _*_
import html
import sys
from urllib.parse import unquote

sys.path.append('.')
sys.path.append('..')

import json
import re
import random
from lxml import etree
from lxml.html import tostring

from helper import *
import config


def search(keyword, ver):
    if ver == '0':
        url = "https://api.bgm.tv/v0/search/subjects"
        json = {
            "keyword": keyword,
            "filter": {
                "type": [1]
            }
        }
        result = __get_result(url, json=json, method='POST')
        results = result.get('data')
    else:
        url = "https://api.bgm.tv/search/subject/{}?type=1".format(keyword)
        result = __get_result(url, method='POST')
        results = result.get('list')
    if results is not None and len(results) > 0:
        result = [__deal_search_item(item, ver) for item in results]
        return list(filter(lambda x: x is not None, result))
    else:
        return None


def search_book(keyword, ver='0'):
    if ver == '0':
        url = "https://api.bgm.tv/v0/search/subjects"
        json = {
            "keyword": keyword,
            "filter": {
                "type": [1]
            }
        }
        result = __get_result(url, json=json, method='POST')
        results = result.get('data')
    else:
        url = "https://api.bgm.tv/search/subject/{}?type=1".format(keyword)
        result = __get_result(url, method='POST')
        results = result.get('list')
    if results is not None and len(results) > 0:
        result = [__deal_search_item(item, ver) for item in results]
        return list(filter(lambda x: x is not None, result))
    else:
        return None


def get_book(bgm_id):
    url = "https://api.bgm.tv/v0/subjects/{}".format(bgm_id)
    result = __get_result(url)
    relation_results = __get_result(url + "/subjects")
    relation_persons = __get_result(url + "/persons")
    if result is not None:
        return __transform(result, relation_results, relation_persons)
    return None


def __transform(result, relation_results, relation_persons):
    try:
        rating = result.get('rating', {})
        images = result.get('images', {})
        infobox = result.get('infobox', {})
        return {
            "bgm_id": result["id"],
            "volume_count": result["volumes"],
            "series": __unescape(result["name_cn"]),
            "original_series": __unescape(result["name"]),
            "year": result["date"][0:4] if result["date"] is not None else None,
            "summary": result["summary"],
            "tags": list(map(lambda x: x["name"], result["tags"])),
            "akas": __transform_akas(infobox),
            "publishers": list(map(lambda x: x['name'], filter(lambda x: x["relation"] == '出版社', relation_persons))),
            "authors": __transform_authors(infobox, relation_persons),
            "date": result['date'],
            "votes": rating['total'],
            "average": rating['score'],
            "cover": images['large'],
            "volumes": list(map(lambda x: __transform_volume(x[0], x[1]),
                                enumerate(filter(lambda x: x["relation"] == '单行本', relation_results))))
        }
    except Exception as e:
        print("TheMovieDB >> 获取漫画信息发生错误：" + str(e))


def __transform_authors(infobox, relation_persons):
    authors = list(map(lambda x: {"name": x["name"], "role": x["relation"]},
                       filter(lambda x: x["relation"] in ["作画", "原作", "作者"], relation_persons)))
    if len(authors) > 0:
        return authors
    else:
        return list(
            map(lambda x: {"name": x["value"], "role": x["key"]},
                filter(lambda x: x["key"] in ["作画", "原作", "作者"], infobox)))


def __transform_publishers(infobox):
    publishers = list(map(lambda x: x["value"].split('、'), filter(lambda x: x["key"] == "出版社", infobox)))
    return [item for row in publishers for item in row]


def __transform_akas(infobox):
    akas = list(map(lambda x: __unescape(x["value"]), filter(lambda x: x["key"] == "别名", infobox)))
    # 判断akas[0]是否为数组
    if len(akas) > 0 and isinstance(akas[0], list):
        akas = list(map(lambda x: __unescape(x['v']), akas[0]))
    return akas


def __transform_volume(index, item):
    return {
        "bgm_id": item["id"],
        "title": __unescape(item["name_cn"]),
        "original_title": item["name"],
        "cover": item["images"]["large"],
        "volume_number": index + 1
    }


def __deal_search_item(item, ver):
    if ver == '0':
        return {
            "bgm_id": item.get('id'),
            "year": item.get('date')[0:4] if item.get('date') is not None else None,
            "series": item.get('name_cn'),
            "original_series": item.get('name'),
            "summary": item.get('summary'),
            "cover": item.get('image'),
        }
    else:
        return {
            "bgm_id": item.get('id'),
            "year": item.get('air_data')[0:4] if item.get('air_data') is not None else None,
            "series": item.get('name_cn'),
            "original_series": item.get('name'),
            "summary": item.get('summary'),
            "cover": item['images']['medium'].replace('http:', 'https:'),
        }


def __get_result(url, method="GET", json: dict = None):
    headers = {
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_0_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36",
        "Authorization": "Bearer {}".format(config.Config().bgm().get("access_token")),
    }
    if method == "GET":
        result = get_html(url, headers=headers)
    else:
        result = post_html(url, json=json, headers=headers)
    return result.json()


def __unescape(str):
    if str:
        return html.unescape(str)
    return None


if __name__ == "__main__":
    print(html.unescape("Q&amp;A"))
    # print(get_book("48108"))

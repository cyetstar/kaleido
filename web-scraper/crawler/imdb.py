# _*_ coding:utf-8 _*_
import sys
sys.path.append('.')
sys.path.append('..')

import re
import random
from lxml import etree
from lxml.html import tostring

from helper import *
import config

mpaa_list = ("I", "IIA", "IIB", "III", "G", "PG",
             "PG-13", "R", "NC-17", "Not Rated")


def get_movie(imdb):
    url = "https://www.imdb.com/title/%s/" % imdb
    try:
        #新版页面无法获取以下数据
        #result = get_html(url, headers=_default_headers())
        #htmlcode = result.content
        #votes = _get_votes(htmlcode)
        #average = _get_average(htmlcode)
        #top250 = _get_top250(htmlcode)
        mpaa = _get_mpaa(imdb)

        return {
            # "imdb": imdb,
            # "imdb_votes": votes,
            # "imdb_average": average,
            # "top250": top250,
            "mpaa": mpaa,
        }
    except Exception as e:
        print("IMDb >> 详情发生错误：" + str(e))


def _get_votes(htmlcode: str):
    try:
        result = get_xpath_text(htmlcode, "//span[@itemprop='ratingCount']")
        return result.replace(',', '')
    except:
        print("IMDb >> 解析vote发生错误")


def _get_average(htmlcode: str):
    try:
        return get_xpath_text(htmlcode, "//span[@itemprop='ratingValue']")
    except:
        print("IMDb >> 解析average发生错误")


def _get_top250(htmlcode: str):
    try:
        result = get_xpath_text(
            htmlcode, "//*[@id='titleAwardsRanks']/strong/a")
        if result:
            return re.search("#(\d+)", result).group(1)
    except:
        print("IMDb >> 解析top250发生错误")


def _get_mpaa(imdb: str):
    url = "https://www.imdb.com/title/%s/parentalguide" % imdb
    result = get_html(url, headers=_default_headers())
    htmlcode = result.content
    result = _get_certificates(htmlcode, "United States")
    if result is None:
        result = _get_certificates(htmlcode, "Canada")
    if result is None:
        result = _get_certificates(htmlcode, "Hong Kong")
    if result is None:
        result = "Not Rated"
    return result


def _get_certificates(htmlcode, country):
    try:
        search_text = country + ":"
        html = etree.fromstring(htmlcode, etree.HTMLParser())
        elm = html.xpath(
            "//*[@id='certificates']//ul/li/a[contains(text(),'" + search_text + "')]/text()")
        result = list(filter(lambda x: x in mpaa_list, map(
            lambda x: str(x).lstrip(search_text), elm)))
        if len(result) > 0:
            return result[0]
    except:
        print("IMDb >> 解析mpaa发生错误")


def _default_headers():
    user_agent = [
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_0_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36",
        "Mozilla/5.0 (X11; Ubuntu; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2919.83 Safari/537.36",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2866.71 Safari/537.36",
        "Mozilla/5.0 (X11; Ubuntu; Linux i686 on x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2820.59 Safari/537.36",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2762.73 Safari/537.36",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2656.18 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML like Gecko) Chrome/44.0.2403.155 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36",
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36",
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2226.0 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.4; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2225.0 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2225.0 Safari/537.36",
        "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2224.3 Safari/537.36",
        "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.93 Safari/537.36",
        "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.93 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36",
        "Mozilla/5.0 (Windows NT 4.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.67 Safari/537.36",
        "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.67 Safari/537.36",
        "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.3319.102 Safari/537.36",
        "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.2309.372 Safari/537.36",
        "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.2117.157 Safari/537.36",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.47 Safari/537.36",
        "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1866.237 Safari/537.36",
    ]
    headers = {
        "Accept": "text/html,application/xhtml+xml,application/xml",
        "Accept-Encoding": "gzip, deflate",
        "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,ja;q=0.7",
        "Cache-Control": "max-age=0",
        "Connection": "keep-alive",
        "Referer": "https://www.imdb.com",
    }
    headers["User-Agent"] = random.choice(user_agent)
    return headers


if __name__ == "__main__":
    print(get_movie("tt0111161"))

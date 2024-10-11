# -*- coding: utf-8 -*-
import sys

sys.path.append('.')
sys.path.append('..')

from lxml.html import tostring
from lxml import etree
import re
from helper import *
import config

COOKIE_NAME = "tlf.cookie"


def list_thread(page=0):
    url = "https://pt.eastgame.org/torrents.php?source=17&page={}".format(page)
    result = get_html(
        url, headers=_default_headers())
    html = etree.fromstring(result.content, etree.HTMLParser())
    elements = __get_threads(html)
    threads = list(filter(lambda x: x['id'] is not None, map(__deal_thread_item, elements)))
    return threads


def get_thread(thread_id):
    url = "https://bbs.eastgame.org/viewthread.php?tid=%s" % thread_id
    cookies = load_cookies(COOKIE_NAME)
    if cookies is None:
        cookies = login()
    result = get_html(
        url, headers=_default_headers(), cookies=cookies)
    if result.status_code != 200:
        cookies = login()
        result = get_html(
            url, headers=_default_headers(), cookies=cookies)
    html = etree.fromstring(result.content, etree.HTMLParser())
    try:
        if _alert_error(html):
            title = _get_title(html)
            return {
                "thread_id": thread_id,
                "title": title,
                "imdb": _match_imdb(title),
                "rating": _match_rating(title),
            }
        else:
            title = _get_title(html)
            type = _get_type(html)
            thanks = _get_thanks(html)
            imdb = _match_imdb(title, str(html))
            rating = _match_rating(title)
            publish_date = _get_publish_date(html)
            douban_id = None
            links = set()
            filenames = set()
            post_elements = _get_posts(html)
            for post_element in post_elements:
                post_htmlcode = etree.tostring(
                    post_element, encoding='unicode')
                if douban_id is None:
                    douban_id = _match_douban_id(post_htmlcode)
                for link in _match_link(post_htmlcode):
                    links.add(link)
                    filename = _match_filename(link)
                    if filename is not None:
                        filenames.add(filename)
            return {
                "thread_id": thread_id,
                "title": title,
                "url": url,
                "type": type,
                "thanks": thanks,
                "imdb": imdb,
                "douban_id": douban_id,
                "rating": rating,
                "publish_date": publish_date,
                "links": list(links),
                "filenames": list(filenames)
            }
    except Exception as e:
        print("TLF >> 获取详情发生错误：" + str(e))


def thank_thread(thread_id):
    url = "https://bbs.eastgame.org/ThankYou.php?action=thank&start=yes&tid=%s" % thread_id
    cookies = load_cookies(COOKIE_NAME)
    if cookies is None:
        cookies = login()
    result = get_html(
        url, headers=_default_headers(), cookies=cookies)
    if result.status_code != 200:
        cookies = login()
        result = get_html(
            url, headers=_default_headers(), cookies=cookies)
    text = get_xpath_text(result.text, "//div[@class='alert_info']/p")
    return {
        "thread_id": thread_id,
        "thanks": text.startswith('感谢您')
    }


def login():
    conf = config.Config().tlf()
    username = conf.get("username")
    password = conf.get("password")
    formhash = conf.get("formhash")
    data = {"formhash": formhash, "referer": "https://bbs.eastgame.org/", "loginfield": "username",
            "username": username, "password": password, "questionid": 0, "answer": "", "cookietime": 2592000}
    url = "https://bbs.eastgame.org/logging.php?action=login&loginsubmit=yes&floatlogin=yes&inajax=1"
    result = post_html(url, data=data, headers=_default_headers())
    save_cookies(COOKIE_NAME, result.cookies)
    return result.cookies


def __get_threads(html):
    return html.xpath("//table[@class='torrents']/tr")


def _filter_thread(element):
    node_id = element.get("id")
    return node_id is not None


def __deal_thread_item(elm):
    catalog = get_regex_text(get_xpath_val(elm, 'td[1]/a/@href'), r'cat=(\d+)', 1)
    title = get_xpath_val(elm, 'td/table[@class="torrentname"]//td[@class="embedded"]//a/@title')
    thread_id = get_regex_text(
        get_xpath_val(elm, 'td/table[@class="torrentname"]//td[@class="embedded"]//a/@href'), r'id=(\d+)', 1)
    publish_date = get_xpath_val(elm, "td[4]/span/@title")
    return {
        "id": thread_id,
        "catalog": catalog,
        "title": title,
        "publish_date": publish_date
    }


def _get_id(element):
    thread_id = element.get("id")
    return thread_id[thread_id.index("_") + 1:]


def _get_info_from_tbody(element):
    type = None
    a = element.find("tr/th/span/a")
    title = a.text
    url = "https://bbs.eastgame.org/" + a.get("href")
    em = element.find("tr/th/em")
    if em is not None:
        type = em.find('a').text
    return title, url, type


def _alert_error(html):
    return len(html.xpath("//div[@class='alert_error']")) > 0


def _get_title(html):
    title = get_xpath_text(html, "//title")
    return title[:title.index(" - ")]


def _get_type(html):
    return get_xpath_text(html, "//div[@id='threadtitle']/h1/a")[1:-1]


def _get_thanks(html):
    return len(html.xpath("//span[@id='thank_btn']")) == 0


def _get_publish_date(html):
    return get_xpath_text(html, "//div[@id='postlist']//div[@class='authorinfo'][1]/em").replace("发表于", "").replace(
        ":", "").strip()


def _get_posts(html):
    return html.xpath("//div[@id='postlist']//div[@class='defaultpost']")


def _match_imdb(title, html=None):
    match = re.compile(".*[\[|【](tt\d+)[\]|】].*").match(title)
    if not match and html:
        match = re.compile(".*title/(tt\d+).*").match(html)
    if match:
        return match.group(1)
    return None


def _match_douban_id(str):
    for result in re.compile("douban\.com/subject/(\d+)").finditer(str):
        return result.group(1)
    return None


def _match_rating(str):
    match = re.compile(".*[\[|【](\d\.?\d?)[\]|】].*").match(str)
    if match:
        return match.group(1)
    return None


def _match_link(str):
    links = []
    # re_baidu_link = re.compile(
    #     '>(https?://pan\.baidu\.com/s/[\w|\-]+)<.+(密码|提取码)[:|：]\s*(\w{4})', re.S)
    baidu_results = set(re.compile('https?://pan\.baidu\.com/s/[\w|\-]+').findall(str))
    pwd_results = re.compile('[密码|提取码][:|：]\s*(\w{4})').findall(str)
    if len(baidu_results) > 0:
        for index, result in enumerate(baidu_results):
            if len(pwd_results) > index:
                pwd = pwd_results[index]
            else:
                pwd = ''
            links.append(result + "#" + pwd)
    for result in re.compile('ed2k://.+?\|/').finditer(str):
        links.append(result.group())
    for result in re.compile('https?://pt\.eastgame\.org/details\.php\?id=\d+').finditer(str):
        links.append(result.group())
    return links


def _match_filename(str):
    match = re.compile('^ed2k://\|file\|(.+?)\|.+/$').match(str)
    if match:
        return match.group(1)


def _default_headers():
    headers = {
        "Accept": "text/html,application/xhtml+xml,application/xml",
        "Accept-Encoding": "gzip, deflate, sdch",
        "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,ja;q=0.7",
        "Cache-Control": "max-age=0",
        "Connection": "keep-alive",
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_0_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36",
        "Host": "pt.eastgame.org",
        "Cookie": "c_secure_uid=Mjk2OTQ%3D; c_secure_pass=158ee141f59b0c48b3311bcc198f7f55; c_secure_ssl=eWVhaA%3D%3D; c_secure_tracker_ssl=eWVhaA%3D%3D; c_secure_login=bm9wZQ%3D%3D; OUTFOX_SEARCH_USER_ID_NCOO=147191347.17357275; __utmz=160627708.1706756768.105.22.utmcsr=192.168.3.100:8000|utmccn=(referral)|utmcmd=referral|utmcct=/; __utma=160627708.7976078.1693316768.1707215490.1707490535.114; __utmc=160627708; __utmb=160627708.6.10.1707490535"
    }
    return headers


if __name__ == "__main__":
    # print(list_thread(1))
    # print(get_thread(250))
    print(list_thread())

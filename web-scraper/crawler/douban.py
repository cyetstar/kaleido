# _*_ coding:utf-8 _*_
import json
import sys

from flask import jsonify

sys.path.append('.')
sys.path.append('..')

import execjs  # 这个库是PyExecJS

from helper import *
import config

title_list = ['第一季', '第二季', '第三季', '第四季', '第五季', '第六季', '第七季', '第八季', '第九季', '第十季',
              '第十一季', '第十二季']

cn_to_num = {
    '一': 1, '二': 2, '三': 3, '四': 4, '五': 5, '六': 6, '七': 7, '八': 8, '九': 9, '十': 10,
    '十一': 11, '十二': 12, '十三': 13, '十四': 14, '十五': 15, '十六': 16, '十七': 17, '十八': 18, '十九': 19,
    '二十': 20, '二十一': 21, '二十二': 22, '二十三': 23, '二十四': 24, '二十五': 25, '二十六': 26, '二十七': 27,
    '二十八': 28, '二十九': 29, '三十': 30
}


def get_weekly():
    url = "https://api.douban.com/v2/movie/weekly"
    headers = {
        "Accept": "text/html,application/xhtml+xml,application/xml",
        "Accept-Encoding": "gzip, deflate",
        "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,ja;q=0.7",
        "Cache-Control": "max-age=0",
        "Connection": "keep-alive",
        'Content-Type': 'application/x-www-form-urlencoded',
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_0_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36",
    }
    data = {
        'apikey': config.Config().get("douban", "api_key"),
    }
    result = post_html(url, data=data, headers=headers)
    result = json.loads(result.text)
    result = result.get("subjects")
    return [__deal_weekly_item(item) for item in result]


def __deal_weekly_item(item):
    subject = item.get('subject')
    return {
        "douban_id": subject.get('id'),
        "title": subject.get('title'),
        "original_title": subject.get('original_title'),
        "poster": subject.get('images').get('small'),
        "year": subject.get('year'),
        "pos": item.get('rank'),
    }


def search(keyword):
    url = "https://search.douban.com/movie/subject_search?search_text={}&cat=1002".format(keyword)
    html = __fetch_twice(url, host="search.douban.com")
    html_str = etree.tostring(html, encoding="utf-8").decode("utf-8")
    data = re.search('window.__DATA__\s+=\s+(.+);', html_str).group(1)  # 加密的数据
    data = json.loads(data)
    result = [__deal_search_item(item) for item in data['items']]
    return list(filter(lambda x: x is not None, result))


def __deal_search_item(item):
    try:
        text = item.get("title")
        text = text.replace('\u200e', '')
        year = re.search(r"\((\d+)\)", text).group(1)
        titles = text.rstrip(' (' + year + ')').split(' ')
        title = titles[0]
        original_title = None
        if len(titles) > 1:
            if titles[1] in title_list:
                title = ' '.join(titles[0:2])
                original_title = ' '.join(titles[2:])
            else:
                original_title = ' '.join(titles[1:])
        return {
            "douban_id": item.get('id'),
            "year": year,
            "title": title,
            "original_title": original_title,
            "abstract": item.get('abstract'),
            "abstract2": item.get('abstract_2'),
            "poster": item.get('cover_url'),
        }
    except Exception as e:
        return None


def search_movie(keyword):
    url = "https://search.douban.com/movie/subject_search?search_text={}&cat=1002".format(keyword)
    html = __fetch_twice(url, host="search.douban.com")
    try:
        html_str = etree.tostring(html, encoding="utf-8").decode("utf-8")
        data = re.search('window.__DATA__\s+=\s+(.+);', html_str).group(1)  # 加密的数据
        data = json.loads(data)
        # data = __decrypt_search_result(data)
        result = [__deal_search_item(item) for item in data['items']]
        return list(filter(lambda x: x is not None, result))
    except Exception as e:
        print("Douban >> 搜索发生错误：" + str(e))


def get_movie_by_imdb_id(imdb_id):
    movie_list = search_movie(imdb_id)
    if movie_list is not None and len(movie_list) > 0:
        douban_id = movie_list[0].get("douban_id")
        return get_movie(douban_id)
    return None


def get_movie(douban_id):
    url = "https://movie.douban.com/subject/{}/".format(douban_id)
    html = __fetch_twice(url, host="movie.douban.com")
    if html is None:
        return None
    try:
        return __get_movie_detail(douban_id, html)
    except Exception as e:
        print("Douban >> 详情发生错误：" + str(e))


def get_season(douban_id):
    try:
        url = "https://movie.douban.com/subject/{}/".format(douban_id)
        html = __fetch_twice(url, host="movie.douban.com")
        if html is not None:
            return __get_movie_detail(douban_id, html)
    except Exception as e:
        print("Douban >> 详情发生错误：" + str(e))
    return None


def get_season_by_imdb_id(imdb_id):
    return get_movie_by_imdb_id(imdb_id)


def supply_season(imdb_id, season):
    movie_detail = get_movie_by_imdb_id(imdb_id)
    movie_detail['episodes'] = season['episodes']
    movie_detail['tmdb_id'] = season['tmdb_id']
    return movie_detail


def supply_tvshow(season, tvshow):
    tvshow['douban_id'] = season['douban_id']
    tvshow['imdb_id'] = season['imdb_id']
    tvshow['title'] = __deal_tvshow_title(season['title'])
    tvshow['original_title'] = __deal_tvshow_title(season['original_title'])
    tvshow['year'] = season['year']
    tvshow['plot'] = season['plot']
    tvshow['directors'] = season['directors']
    tvshow['credits'] = season['credits']
    tvshow['actors'] = season['actors']
    tvshow['genres'] = season['genres']
    tvshow['languages'] = season['languages']
    tvshow['countries'] = season['countries']
    tvshow['premiered'] = season['premiered']
    tvshow['votes'] = season['votes']
    tvshow['average'] = season['average']
    tvshow['poster'] = season['poster']
    tvshow['akas'] = list(map(__deal_tvshow_title, season['akas']))
    return tvshow


def get_doulist(douban_id):
    url = "https://www.douban.com/doulist/{}".format(douban_id)
    html = __fetch_twice(url)
    title = get_xpath_val(html, '//div[@id="content"]/h1/span/text()')
    total = get_xpath_val(html, '//div[@class="doulist-filter"]/a/span/text()', '0').strip('()')
    thumb = get_xpath_val(html, "//div[@class='doulist-cover']/img/@src")
    about = ''.join(html.xpath("//div[@class='doulist-about']/text()")).strip()
    updated = get_regex_text(get_xpath_val(html, "//div[@id='doulist-info']//span[@class='time']/text()"),
                             r"(\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2})更新", 1)
    return {
        "douban_id": douban_id,
        "title": title,
        "total": int(total),
        "thumb": thumb,
        "about": about,
        "updated": updated
    }


def get_doulist_movies(douban_id, start=0):
    url = "https://www.douban.com/doulist/{}/?start={}".format(douban_id, start)
    html = __fetch_twice(url)
    return list(
        map(__deal_doulist_item, html.xpath('//*[@id="content"]//div[@class="doulist-item"]')))


def get_watched_movie(page=0):
    douban = config.Config().douban()
    start = page * 15
    url = "https://movie.douban.com/people/%s/collect?start=%s" % (
        douban.get("username"), start)
    result = get_html(url, headers={
        **__default_headers(), **{"Cookie": douban.get("cookie")}},
                      proxies=__proxies())
    htmlcode = result.content
    html = etree.fromstring(htmlcode, etree.HTMLParser())
    elements = html.xpath("//div[@class='grid-view']/div[@class='item']")
    return list(map(__deal_watched_item, elements))


def download_image(url):
    try:
        result = get_html(url, headers=__default_headers("image"))
        if result.status_code == 200:
            return result.content
    except:
        print("Douban >> 下载图片发生错误")


def __get_season_douban_id_list(html):
    try:
        return html.xpath(
            "//*[@id='season']//option/@value")
    except Exception as e:
        print("Douban >> 解析sseason_douban_id_list发生错误:" + str(e))


def __get_movie_detail(douban_id, html):
    info = __get_info(html)
    title = __get_title(html)
    directors = __get_directors_new(html)
    if len(directors) == 0:
        directors = __get_directors(html)
    credits = __get_credits_new(html)
    if len(credits) == 0:
        credits = __get_credits(html)
    actors = __get_actors_new(html)
    if len(actors) == 0:
        actors = __get_actors(html)
    season_number = __get_season_number(html)
    return {
        "douban_id": douban_id,
        "imdb_id": info['imdb_id'],
        "title": title,
        "original_title": __get_original_title(html, title),
        "year": __get_year(html),
        "plot": __get_summary(html),
        "tags": __get_tags(html),
        "directors": directors,
        "credits": credits,
        "actors": actors,
        "genres": __get_genres(html),
        "languages": info['languages'],
        "countries": info['countries'],
        "premiered": __get_release_date(html),
        "season_number": season_number,
        "episode_total": info['episode_total'],
        "doulists": __get_doulists(html),
        "votes": __get_votes(html),
        "average": __get_average(html),
        "douban_top250": __get_top250(html),
        "akas": info['akas'],
        "poster": __get_poster(html),
        "website": info['website'],
        "mpaa": None,
        "source": "douban",
    }


def __generate_tvshow(season):
    title = __deal_tvshow_title(season['title'])
    original_title = __deal_tvshow_title(season['original_title'])
    return {
        "douban_id": season['douban_id'],
        "imdb_id": season['imdb_id'],
        "title": title,
        "original_title": original_title,
        "year": season['year'],
        "plot": season['plot'],
        "directors": season['directors'],
        "credits": season['credits'],
        "actors": season['actors'],
        "genres": season['genres'],
        "languages": season['languages'],
        "countries": season['countries'],
        "premiered": season['premiered'],
        "votes": season['votes'],
        "average": season['average'],
        "akas": list(map(__deal_tvshow_title, season['akas'])),
        "poster": season['poster'],
        "source": "douban",
    }


def __deal_tvshow_title(title):
    if title:
        title = re.sub(r'第\w季', '', title).strip()
        title = re.sub(r'Season\s\d+', '', title).strip()
        return title


def __deal_doulist_item(elm):
    try:
        douban_id = get_regex_text(get_xpath_val(elm, './/div[@class="title"]/a/@href'), r'/(\d+)', 1)
        if douban_id is None:
            return None
        title_text = ''.join(elm.xpath('.//div[@class="title"]/a/text()')).strip()
        titles = title_text.split(' ') if title_text else []
        abstract = ''.join(elm.xpath('.//div[@class="abstract"]/text()')).strip()
        poster = get_xpath_val(elm, './/div[@class="post"]/a/img/@src')
        pos = get_xpath_val(elm, './/span[@class="pos"]/text()')
        comment_elm = elm.xpath('.//blockquote[@class="comment"]/text()')
        comment = None
        if len(comment_elm) > 1:
            comment = comment_elm[1].strip()
        year = re.search(r"年份: (\d+)", abstract).group(1)
        title = titles[0]
        original_title = None
        if len(titles) > 1:
            if titles[1] in title_list:
                title = ' '.join(titles[0:2])
                original_title = ' '.join(titles[2:])
            else:
                original_title = ' '.join(titles[1:])
        return {
            "douban_id": douban_id,
            "title": title,
            "original_title": original_title,
            "poster": poster,
            "year": year,
            "comment": comment,
            "pos": pos,
        }
    except Exception as e:
        print("Douban >> 解析doulist发生错误：" + str(e))


def __decrypt_search_result(data):
    with open('crawler/douban.js', 'r', encoding='utf-8') as f:
        decrypt_js = f.read()
    ctx = execjs.compile(decrypt_js)
    return ctx.call('decrypt', data)


def __fetch_twice(url, host="www.douban.com"):
    headers = __default_headers()
    headers["Host"] = host
    headers["Cookie"] = config.Config().get("douban", "cookie")
    result = get_html(url, headers=headers, proxies=__proxies())
    htmlcode = result.text
    if htmlcode is None or '页面不存在' in htmlcode or '条目不存在' in htmlcode or '豆瓣不收录' in htmlcode:
        return None
    return etree.fromstring(result.content, etree.HTMLParser())


def __get_title(html):
    try:
        result = get_xpath_text(html, "//head/title")
        return result.replace("(豆瓣)", "").strip()
    except Exception as e:
        print("Douban >> 解析title发生错误:" + str(e))


def __get_original_title(html, title):
    try:
        result = get_xpath_text(html, "//*[@id='content']/h1/span[1]")
        return result.replace(title, '').strip()
    except Exception as e:
        print("Douban >> 解析original_title发生错误:" + str(e))


def __get_year(html):
    try:
        result = get_xpath_text(
            html, "//*[@id='content']/h1/span[@class='year']")
        return result.strip("()")
    except Exception as e:
        print("Douban >> 解析year发生错误:" + str(e))


def __get_summary(html):
    try:
        result = html.xpath(
            "//div[@id='link-report-intra']//span[contains(@class, 'all')]/node()")
        if len(result) == 0:
            result = html.xpath(
                "//div[@id='link-report-intra']/span[@property='v:summary']/node()")
        result = list(filter(lambda x: not hasattr(x, "text"), result))
        result = "\n".join(map(__deal_plot_item, result))
        return result
    except Exception as e:
        print("Douban >> 解析summary发生错误:" + str(e))


def __deal_plot_item(item):
    return "\u3000\u3000" + str(item).strip()


def __get_votes(html):
    try:
        return get_xpath_text(html, "//div[@id='interest_sectl']//span[@property='v:votes']")
    except Exception as e:
        print("Douban >> 解析votes发生错误:" + str(e))


def __get_average(html):
    try:
        return get_xpath_text(
            html, "//div[@id='interest_sectl']//strong[@property='v:average']")
    except Exception as e:
        print("Douban >> 解析average发生错误:" + str(e))


def __get_tags(html):
    try:
        return list(map(str, html.xpath("//div[@class='tags-body']/a/text()")))
    except Exception as e:
        print("Douban >> 解析tag发生错误:" + str(e))


def __get_directors(html):
    try:
        return list(filter(lambda x: x is not None,
                           map(__deal_actor_item, html.xpath("//*[@id='info']//a[@rel='v:directedBy']"))))
    except Exception as e:
        print("Douban >> 解析director发生错误:" + str(e))


def __get_credits(html):
    try:
        return list(filter(lambda x: x is not None, map(__deal_actor_item, html.xpath(
            "//*[@id='info']/span/span[contains(text(),'编剧')]/following-sibling::span/a"))))
    except Exception as e:
        print("Douban >> 解析writer发生错误:" + str(e))


def __get_actors(html):
    try:
        return list(
            filter(lambda x: x is not None,
                   map(__deal_actor_item, html.xpath("//*[@id='info']//a[@rel='v:starring']"))))
    except Exception as e:
        print("Douban >> 解析actor发生错误:" + str(e))


def __get_directors_new(html):
    try:
        return list(
            filter(lambda x: x is not None and x['actor_type'] == 'director',
                   map(__deal_actor_item_new, html.xpath("//*[@id='celebrities']/ul/li[@class='celebrity']"))))
    except Exception as e:
        print("Douban >> 解析director发生错误:" + str(e))


def __get_credits_new(html):
    try:
        return list(
            filter(lambda x: x is not None and x['actor_type'] == 'writer',
                   map(__deal_actor_item_new, html.xpath("//*[@id='celebrities']/ul/li[@class='celebrity']"))))
    except Exception as e:
        print("Douban >> 解析writer发生错误:" + str(e))


def __get_actors_new(html):
    try:
        return list(
            filter(lambda x: x is not None and x['actor_type'] == 'actor',
                   map(__deal_actor_item_new, html.xpath("//*[@id='celebrities']/ul/li[@class='celebrity']"))))
    except Exception as e:
        print("Douban >> 解析actor发生错误:" + str(e))


def __deal_actor_item_new(elm):
    try:
        role = get_xpath_val(elm, 'div/span[@class="role"]/@title')
        douban_id = get_regex_text(get_xpath_val(elm, 'div/span/a[@class="name"]/@href'), r".+/(\d+)/", 1)
        thumb = get_regex_text(get_xpath_val(elm, 'a/div[contains(@class, "avatar")]/@style'), r'url\((\S+)\)(?!,)', 1)
        names = get_xpath_val(elm, 'div/span/a[@class="name"]/@title')
        name = get_xpath_val(elm, 'div/span/a[@class="name"]/text()')
        cn_name = None
        en_name = None
        if is_chinese_name(name):
            cn_name = name
        if name != names:
            name = names.replace(name, '', 1).strip()
        else:
            name = names
        if not is_chinese_name(name):
            en_name = name
        actor_type = None
        if '饰' in role or '配' in role or '演员' in role:
            actor_type = 'actor'
        elif '导演' in role:
            actor_type = 'director'
        elif '编剧' in role:
            actor_type = 'writer'
        role = role.replace('饰', '').replace('配', '').replace('演员', '').strip()
        return {
            "douban_id": douban_id,
            "thumb": thumb,
            "role": role,
            "cn_name": cn_name,
            "en_name": en_name,
            "actor_type": actor_type
        }
    except Exception as e:
        print("Douban >> 解析new actor发生错误:" + str(e))
    return None


def __deal_actor_item(elm):
    try:
        result = re.search(r".+/(\d+)/", elm.get("href"))
        if result:
            douban_id = result.group(1)
            return {
                "douban_id": douban_id,
                "cn_name": elm.text
            }
        return None
    except Exception as e:
        print("Douban >> 解析actor发生错误:" + str(e))


def __get_genres(html):
    try:
        return list(map(str, html.xpath("//*[@id='info']//span[@property='v:genre']/text()")))
    except Exception as e:
        print("Douban >> 解析genre发生错误:" + str(e))


def __get_release_date(html):
    try:
        elements = html.xpath(
            "//*[@id='info']//span[@property='v:initialReleaseDate']/text()")
        release_dates = []
        for elm in elements:
            try:
                release_dates.append(
                    re.search('\d{4}-\d{2}-\d{2}', str(elm)).group())
            except:
                pass
        release_dates.sort()
        return release_dates[0] if len(release_dates) > 0 else None
    except Exception as e:
        print("Douban >> 解析release_date发生错误:" + str(e))


def __get_season_number(html):
    try:
        result = html.xpath(
            "//*[@id='season']//option[@selected='selected']/text()")
        if len(result) > 0:
            # 根据下拉框确定季号
            season_number = result[0]
            return int(season_number)
        else:
            # 根据属性确定季号
            info = __get_info(html)
            if info['season_number']:
                return int(info['season_number'])
        title = __get_title(html)
        season_number = __extract_season(title)
        if season_number is not None:
            return season_number
        return 1
    except Exception as e:
        print("Douban >> 解析season_number发生错误:" + str(e))


def __extract_season(title):
    """提取标题中的季数并转换为阿拉伯数字"""
    # 匹配中文或阿拉伯数字的 "第X季"
    match = re.search(r'第([一二三四五六七八九十百零两\d]+)季', title)
    if match:
        season_str = match.group(1)  # 提取到的 "X"
        if season_str.isdigit():  # 如果是阿拉伯数字
            return int(season_str)
        else:  # 中文数字的情况
            return __cn_num_to_arabic(season_str)
    return None


def __cn_num_to_arabic(cn_num):
    """将中文数字转为阿拉伯数字"""
    return cn_to_num.get(cn_num, None)


def __get_first_season_douban_id(html):
    try:
        result = html.xpath(
            "//*[@id='season']//*[contains(text(),'1')]/@value")
        if len(result) > 0:
            first_season_douban_id = result[0]
            return first_season_douban_id
        return None
    except Exception as e:
        print("Douban >> 解析first_season_douban_id发生错误:" + str(e))


def __get_runtimes(html):
    try:
        return list(map(str, html.xpath("//*[@id='info']//span[@property='v:runtime']/text()")))
    except Exception as e:
        print("Douban >> 解析runtime发生错误:" + str(e))


def __get_info(html):
    elements = html.xpath("//div[@id='info']/node()")
    info = {}
    key = None
    try:
        for elm in elements:
            if not hasattr(elm, "text"):
                __put_dict(info, key, str(elm).strip())
            elif elm.xpath("@class='pl'"):
                key = elm.text
            elif elm.tag == "a":
                __put_dict(info, key, elm.text.strip())
    except Exception as e:
        print("Douban >> 解析info发生错误:" + str(e))

    countries = info.get("制片国家/地区:", "").split("/")
    languages = info.get("语言:", "").split("/")
    akas = info.get("又名:", "").split("/")

    return {
        "website": info.get("官方网站:"),
        "countries": list(map(lambda x: x.strip(), filter(lambda x: x != "", countries))),
        "languages": list(map(lambda x: x.strip(), filter(lambda x: x != "", languages))),
        "akas": list(map(lambda x: x.strip(), filter(lambda x: x != "", akas))),
        "imdb_id": info.get("IMDb:"),
        "episode_total": info.get("集数:"),
        "season_number": info.get("季数:")
    }


def __put_dict(dict: dict, key: str, value: str):
    if key is None or value is None:
        return
    value = value.strip()
    if value == "":
        return
    dict[key] = value


def __get_doulists(html):
    try:
        return list(map(__deal_doulists_item, html.xpath("//*[@id='subject-doulist']/ul/li/a")))
    except Exception as e:
        print("Douban >> 解析sets发生错误:" + str(e))


def __deal_doulists_item(element):
    return {
        "douban_id": re.search("/(\d+)/", element.get("href", "")).group(1),
        "title": element.text,
    }


def __get_top250(html):
    try:
        result = get_xpath_text(html, "//span[@class='top250-no']")
        return result.replace('No.', '') if result else None
    except Exception as e:
        print("Douban >> 解析top250发生错误:" + str(e))


def __get_poster(html):
    try:
        result = str(html.xpath("//*[@id='mainpic']/a/img/@src")[0])
        if "s_ratio_poster" in result:
            result = result.replace("s_ratio_poster", "l")
            return result
    except Exception as e:
        print("Douban >> 解析poster发生错误:" + str(e))


def __deal_watched_item(elm):
    douban_id = __get_watched_id(elm)
    title = __get_watched_title(elm)
    user_rating = __get_watched_rating(elm)
    user_note = __get_watched_note(elm)
    return {
        "douban_id": douban_id,
        "title": title,
        "user_rating": user_rating,
        "user_note": user_note,
    }


def __get_watched_id(elm):
    try:
        elm = elm.xpath("div/ul/li[@class='title']/a/@href")
        if len(elm) > 0:
            return re.search(r"https://.+/(\d+)/", elm[0]).group(1)
    except:
        print("Douban >> 解析id发生错误")


def __get_watched_title(elm):
    try:
        return elm.xpath("div/ul/li[@class='title']/a/em/text()")[0]
    except:
        print("Douban >> 解析id发生错误")


def __get_watched_rating(elm):
    try:
        elm = elm.xpath("div/ul/li/span[starts-with(@class,'rating')]/@class")
        if len(elm) > 0:
            return int(re.search(r"rating(\d)\-t", elm[0]).group(1))
    except:
        print("Douban >> 解析rating发生错误")


def __get_watched_note(elm):
    try:
        elm = elm.xpath("div/ul/li/span[@class='comment']")
        if len(elm) > 0:
            return elm[0].text
    except:
        print("Douban >> 解析note发生错误")


def __default_headers(type=None):
    headers = {
        "Accept": "text/html,application/xhtml+xml,application/xml",
        "Accept-Encoding": "gzip, deflate",
        "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,ja;q=0.7",
        "Cache-Control": "max-age=0",
        "Connection": "keep-alive",
        "Referer": "https://movie.douban.com",
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_0_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36",
    }
    if type == "search":
        headers["Host"] = "search.douban.com"
    elif type == "movie":
        headers["Host"] = "movie.douban.com"
    elif type == "image":
        headers["Accept"] = "image/jpg,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
    else:
        headers["Host"] = "www.douban.com"
    return headers


def __proxies():
    proxy_enable = config.Config().douban().get("proxy_enable")
    if proxy_enable:
        proxy = config.Config().proxy().get("proxy")
        type = config.Config().proxy().get("type")
        return get_proxies(proxy, type)


if __name__ == "__main__":
    # print(search_movie("1857662"))
    # print(get_doulist('136414497'))
    # print(get_tvshow('26784967'))
    print(get_movie('34822085'))

# -*- coding: utf-8 -*-

import os
import io
import re

from PIL import Image
import pickle
import requests
from lxml import etree
import config

SUPPORT_PROXY_TYPE = ("http", "socks5", "socks5h")

directory = os.path.dirname(os.path.abspath(__file__))


def get_html(url, headers: dict = None, cookies: dict = None, proxies: dict = None):
    timeout = config.Config().http().get("timeout")
    retry_times = config.Config().http().get("retry_times")
    result = None
    for i in range(retry_times):
        try:
            result = requests.get(
                str(url),
                headers=headers,
                proxies=proxies,
                cookies=cookies,
                timeout=timeout,
            )
            if result.status_code == 200:
                return result
        except Exception as e:
            print("GET请求发生错误：" + str(e))
    return result


def post_html(
    url: str, data: dict = None, json: dict = None, headers: dict = None, timeout=30
) -> requests.Response:
    try:
        result = requests.post(
            url, data=data, json=json, headers=headers, timeout=timeout
        )
        return result
    except Exception as e:
        print("POST请求发生错误" + str(e))
        raise Exception("HTML请求发生错误")


def save_cookies(filename, cookies):
    path = os.path.join(directory, "conf/" + filename)
    with open(path, "wb") as f:
        pickle.dump(cookies, f)


def load_cookies(filename):
    path = os.path.join(directory, "conf/" + filename)
    if os.path.isfile(path):
        with open(path, "rb") as f:
            return pickle.load(f)
    else:
        return None


def get_xpath_text(html, xpath_str, default=None):
    elm = html.xpath(xpath_str)
    if len(elm) > 0:
        return elm[0].text.strip()
    return default


def get_xpath_val(html, xpath_str, default=None):
    elm = html.xpath(xpath_str)
    if len(elm) > 0:
        return elm[0].strip()
    return default


def get_regex_text(text, regex_str, index=0, default=None):
    if not text:
        return None
    result = re.search(regex_str, text)
    if result:
        return result.group(index)
    return default


def get_proxies(proxy: str, proxy_type: str = None) -> dict:
    """获得代理参数，默认http代理"""
    if proxy:
        if proxy_type in SUPPORT_PROXY_TYPE:
            proxies = {
                "http": proxy_type + "://" + proxy,
                "https": proxy_type + "://" + proxy,
            }
        else:
            proxies = {"http": "http://" + proxy, "https": "https://" + proxy}
    else:
        proxies = {}

    return proxies


def cut_image(filename, data, cut_type="right"):
    try:
        filepath = "temp/" + filename
        if not os.path.exists("temp"):
            os.mkdir("temp")
        with open(filepath, "wb") as code:
            code.write(data)
        img = Image.open(filepath)
        w, h = img.size
        if cut_type == "right":
            img = img.crop((w - h / 1.43, 0, w, h))
        elif cut_type == "left":
            img = img.crop((0, 0, w - h / 1, h))
        data = io.BytesIO()
        img.save(data, format="PNG")
        return data.getvalue()
    except:
        pass
    finally:
        try:
            os.remove(filepath)
        except:
            pass


def is_chinese_name(name):
    pattern = r"^[\u4e00-\u9fa5·\-]+$"
    match = re.match(pattern, name)
    return match is not None


def is_empty(value):
    return value is None or value == ""

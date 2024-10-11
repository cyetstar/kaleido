import json
import requests
from lxml import etree
import re
import time

headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2224.3 Safari/537.36",
    "Accept": "text/html,application/xhtml+xml,application/xml",
    "Accept-Encoding": "gzip, deflate",
    "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,ja;q=0.7",
    "Cache-Control": "max-age=0",
    "Connection": "keep-alive",
    "origin": "https://open.cd",
    "referer": "https://open.cd/",
    "Cookie": "_ga=GA1.2.310876146.1607874146; c_secure_uid=NzM4MjU%3D; c_secure_pass=2b9752ec7f6fe9f820c65e0d758f2644; c_secure_ssl=eWVhaA%3D%3D; c_secure_tracker_ssl=eWVhaA%3D%3D; c_secure_login=bm9wZQ%3D%3D; c_lang_folder=cht; __cfduid=dbc7251682b6f541ba2b3c6b564f9f90e1616812780; _gid=GA1.2.218447514.1618138134",
}

f = open("/Users/cyetstar/name.txt")
names = f.read().split("\n")
for name in names:
    page = 0
    while True:
        result = requests.get(
            "https://open.cd/torrents.php?search=" + name + "&page=" + str(page), headers=headers)
        htmlcode = result.text
        if "沒有種子" in htmlcode:
            break
        html = etree.fromstring(htmlcode, etree.HTMLParser())
        elements = html.xpath(
            "//table[@class='torrentname']/tr/td[@class='embedded']/a[@title]")
        if len(elements) == 0:
            break
        for elm in elements:
            try:
                if not elm.get("title").startswith(name + " - "):
                    continue
                id = re.search('id=(\d+)', elm.get("href")).group(1)
                result = requests.get(
                    "https://open.cd/download.php?id=" + id, headers=headers)
                filename = re.search('filename="(.+\.torrent)"',
                                     result.headers["Content-Disposition"]).group(1)
                filename = name + " - " + \
                    filename.encode('ISO-8859-1').decode('utf8')
                f = open("/Users/cyetstar/Downloads/torrent/" + filename, "wb")
                f.write(result.content)
                print(filename + " >>> Download")
                time.sleep(4)
            except Exception as e:
                print(e)
        page = page + 1

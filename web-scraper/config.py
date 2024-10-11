# _*_ coding:utf-8 _*_
import os
import configparser

directory = os.path.dirname(os.path.abspath(__file__))


class Config:
    def __init__(self, path: str = "conf/config.ini"):
        path = os.path.join(directory, path)
        if not os.path.exists(path):
            path = os.path.join(directory, "conf/default_config.ini")
        self.conf = configparser.RawConfigParser()
        try:
            self.conf.read(path, encoding="utf-8-sig")
        except:
            self.conf.read(path, encoding="utf-8")

    def http(self):
        return {
            "timeout": self.conf.getint("http", "timeout"),
            "retry_times": self.conf.getint("http", "retry_times"),
        }

    def proxy(self):
        return {
            "proxy": self.conf.get("proxy", "proxy"),
            "type": self.conf.get("proxy", "type")
        }

    def tlf(self):
        return {
            "username": self.conf.get("tlf", "username"),
            "password": self.conf.get("tlf", "password"),
            "formhash": self.conf.get("tlf", "formhash"),
        }

    def douban(self):
        return {
            "proxy_enable": self.conf.getboolean("douban", "proxy_enable"),
            "username": self.conf.get("douban", "username"),
            "cookie": self.conf.get("douban", "cookie"),
        }

    def tmdb(self):
        return {
            "api_key": self.conf.get("tmdb", "api_key"),
        }

    def bgm(self):
        return {
            "access_token": self.conf.get("bgm", "access_token"),
        }

    def netease(self):
        return {
            "url": self.conf.get("netease", "url"),
        }

    def set(self, section, option, value):
        self.conf.set(section, option, value)
        path = os.path.join(directory, 'conf/config.ini')
        with open(path, 'w') as config_file:
            self.conf.write(config_file)

    def get(self, section, option):
        return self.conf.get(section, option)


if __name__ == "__main__":
    config = Config()
    config.set("douban", "cookie", "bid=90A1r3aejoc;")
    print(config.douban().get("cookie"))

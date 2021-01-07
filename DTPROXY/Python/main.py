#!/usr/bin/python
#coding=utf-8
# 新用户只需要替换14行和15行的orderno和secret即可运行

import sys
import time
import hashlib
import requests

_version = sys.version_info

is_python3 = (_version[0] == 3)

# 个人中心获取orderno与secret
orderno = "DT2019051422xxxxxxxxxxx"    
secret = "f163c1a1cd84f2bxxxxxxxxxx"
ip = "dynamic.xiongmaodaili.com"
#按量订单端口
port = "8088"
#按并发订单端口
#port = "8089"

ip_port = ip + ":" + port

timestamp = str(int(time.time()))                # 计算时间戳
txt = ""
txt = "orderno=" + orderno + "," + "secret=" + secret + "," + "timestamp=" + timestamp

if is_python3:
    txt = txt.encode()

md5_string = hashlib.md5(txt).hexdigest()                 # 计算sign
sign = md5_string.upper()                              # 转换成大写
print(sign)
auth = "sign=" + sign + "&" + "orderno=" + orderno + "&" + "timestamp=" + timestamp + "&change=true"

print(auth)
#http协议的网站用此配置
proxy = {"http":"http://" + ip_port}
#https协议的网站用此配置
#proxy = {"https": "https://" + ip_port}
print(proxy)
headers = {"Proxy-Authorization": auth,"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36"}
#r = requests.get("http://2021.ip138.com", headers=headers, proxies=proxy, verify=False,allow_redirects=False)
#r = requests.get("https://api.ip.la", headers=headers, proxies=proxy, verify=False,allow_redirects=False)
print(headers)
#http协议可用性检测，每访问一次返回的结果换一个IP即为代理成功
r = requests.get("http://2021.ip138.com", headers=headers, proxies=proxy, verify=False,allow_redirects=False)
#https协议可用性检测，每访问一次返回的结果换一个IP即为代理成功
#r = requests.get("https://ip.cn/",headers=headers,proxies=proxy,verify=False,allow_redirects=False)
print(r.encoding)
print(r.apparent_encoding)
#http协议测试采用此中文编码
r.encoding = 'gb2312'
#https协议测试采用此中文编码
#r.encoding = 'utf-8'
print(r.encoding)
print(r.status_code)
print(r.text)
print(str(r.elapsed.total_seconds())+"     秒")

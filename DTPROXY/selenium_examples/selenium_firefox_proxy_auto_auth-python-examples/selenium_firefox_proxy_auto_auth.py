# coding: utf-8  
# selenium_firefox_proxy_auto_auth.py

#环境
#Firefox V55.0  下载地址:http://ftp.mozilla.org/pub/firefox/releases/55.0/win64/zh-CN/ 安装该版本后断网并设置浏览器不检查更新以防止自动更新到最新版本)
#geckodriver v0.20.0  https://github.com/mozilla/geckodriver/releases/tag/v0.20.0
#selenium V3.141.0 pip install selenium==3.141.0
#close-proxy-authentication V1. 1  

import sys  
import time  
from base64 import b64encode  
from selenium import webdriver  

# close-proxy-authentication插件的路径  
PROXY_HELPER_DIR =  'close_proxy_authentication-1.1-sm+tb+fx.xpi'  
  
def test():  
     # HTTP(S)类型代理参数  
    proxy_host =  'dynamic.xiongmaodaili.com'  
    proxy_port =  8091  
    #修改username和password为实际订单中的用户名密码
    proxy_username =  'xxxxxxxx'  
    proxy_password =  'xxxxxxxxx'  
      
    fp = webdriver.FirefoxProfile()  
     # 添加代理认证插件  
    fp.add_extension(PROXY_HELPER_DIR)  
     # 设置代理参数  
    fp.set_preference( 'network.proxy.type',  1)  
    fp.set_preference( 'network.proxy.http', proxy_host)  
    fp.set_preference( 'network.proxy.http_port', proxy_port)  
     # 给close-proxy-authentication插件设置authtoken(即代理认证的用户名和密码)  
    credentials =  '{}:{}'.format(proxy_username, proxy_password)  
    credentials = b64encode(credentials.encode('ascii')).decode('utf-8')
    print(credentials)
                      
    fp.set_preference('extensions.closeproxyauth.authtoken', credentials)

    firefox = webdriver.Firefox(firefox_profile=fp)  

    # 访问http://httpbin.org/ip回显当前IP  
    firefox.get( 'http://httpbin.org/ip')  
    time.sleep( 1000)

      

if __name__ ==  '__main__':  
    test() 
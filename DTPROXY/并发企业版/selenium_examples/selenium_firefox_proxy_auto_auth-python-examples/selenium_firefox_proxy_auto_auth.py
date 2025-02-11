# coding: utf-8  
# selenium_firefox_proxy_auto_auth.py

#����
#Firefox V55.0  ���ص�ַ:http://ftp.mozilla.org/pub/firefox/releases/55.0/win64/zh-CN/ ��װ�ð汾�������������������������Է�ֹ�Զ����µ����°汾)
#geckodriver v0.20.0  https://github.com/mozilla/geckodriver/releases/tag/v0.20.0
#selenium V3.141.0 pip install selenium==3.141.0
#close-proxy-authentication V1. 1  

import sys  
import time  
from base64 import b64encode  
from selenium import webdriver  

# close-proxy-authentication�����·��  
PROXY_HELPER_DIR =  'close_proxy_authentication-1.1-sm+tb+fx.xpi'  
  
def test():  
     # HTTP(S)���ʹ������  
    proxy_host =  'dtqybf.xiongmaodaili.com'  
    proxy_port =  8091  
    #�޸�username��passwordΪʵ�ʶ����е��û�������
    proxy_username =  'xxxxxxxx'  
    proxy_password =  'xxxxxxxxx'  
      
    fp = webdriver.FirefoxProfile()  
     # ��Ӵ�����֤���  
    fp.add_extension(PROXY_HELPER_DIR)  
     # ���ô������  
    fp.set_preference( 'network.proxy.type',  1)  
    fp.set_preference( 'network.proxy.http', proxy_host)  
    fp.set_preference( 'network.proxy.http_port', proxy_port)  
     # ��close-proxy-authentication�������authtoken(��������֤���û���������)  
    credentials =  '{}:{}'.format(proxy_username, proxy_password)  
    credentials = b64encode(credentials.encode('ascii')).decode('utf-8')
    print(credentials)
                      
    fp.set_preference('extensions.closeproxyauth.authtoken', credentials)

    firefox = webdriver.Firefox(firefox_profile=fp)  

    # ����http://httpbin.org/ip���Ե�ǰIP  
    firefox.get( 'http://httpbin.org/ip')  
    time.sleep( 1000)

      

if __name__ ==  '__main__':  
    test() 
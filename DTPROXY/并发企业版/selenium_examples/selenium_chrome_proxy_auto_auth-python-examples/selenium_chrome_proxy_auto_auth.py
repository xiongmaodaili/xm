# coding: gbk  
# selenium_chrome_proxy_auto_auth.py

#环境
#chrome版本 90.0.4430.212（正式版本） （64 位）
#chromedriver版本 89.0.4389.23 http://npm.taobao.org/mirrors/chromedriver/89.0.4389.23/chromedriver_win32.zip
#selenium V3.141.0 pip install selenium==3.141.0

from selenium.webdriver.common.proxy import *
from selenium.webdriver.chrome.options import Options
from pyvirtualdisplay import Display
from selenium import webdriver  
  
def test():  
        chrome_options = Options()      
        chrome_options = webdriver.ChromeOptions()
        chrome_options.add_extension("D:\xxxxx\proxy-examples.zip")      # zip包，包含 background.js 和 manifest.json 两个文件，更改proxy-examples.zip压缩包，background.js文件中username和password为实际订单中的用户名密码
        
        chromedriver = 'F:\xxxxxx\chromedriver-89.0.4389.23.exe'
        browser = webdriver.Chrome(executable_path=chromedriver, chrome_options=chrome_options)        # 打开 Chrome 浏览器
        browser.get('http://httpbin.org/ip')     
        content = browser.page_source
        print(str(content))

if __name__ ==  '__main__':  
    test() 
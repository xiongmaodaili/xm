# coding: gbk  
# selenium_chrome_proxy_auto_auth.py

#����
#chrome�汾 90.0.4430.212����ʽ�汾�� ��64 λ��
#chromedriver�汾 89.0.4389.23 http://npm.taobao.org/mirrors/chromedriver/89.0.4389.23/chromedriver_win32.zip
#selenium V3.141.0 pip install selenium==3.141.0

from selenium.webdriver.common.proxy import *
from selenium.webdriver.chrome.options import Options
from pyvirtualdisplay import Display
from selenium import webdriver  
  
def test():  
        chrome_options = Options()      
        chrome_options = webdriver.ChromeOptions()
        chrome_options.add_extension("D:\xxxxx\proxy-examples.zip")      # zip�������� background.js �� manifest.json �����ļ�������proxy-examples.zipѹ������background.js�ļ���username��passwordΪʵ�ʶ����е��û�������
        
        chromedriver = 'F:\xxxxxx\chromedriver-89.0.4389.23.exe'
        browser = webdriver.Chrome(executable_path=chromedriver, chrome_options=chrome_options)        # �� Chrome �����
        browser.get('http://httpbin.org/ip')     
        content = browser.page_source
        print(str(content))

if __name__ ==  '__main__':  
    test() 
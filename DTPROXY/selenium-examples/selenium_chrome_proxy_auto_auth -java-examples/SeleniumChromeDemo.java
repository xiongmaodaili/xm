import com.test.util.RandomUtil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.Arrays;

/**
 * Selenium启动chrome示例，包含selenium版本、chrome版本、chromedriver版本，代理插件proxy.zip，经测试可用
 */
public class SeleniumChromeDemo {
//        <!-- selenium maven配置 -->
//        <dependency>
//			<groupId>org.seleniumhq.selenium</groupId>
//			<artifactId>selenium-java</artifactId>
//			<version>3.141.59</version>
//		</dependency>
//		<dependency>
//			<groupId>org.seleniumhq.selenium</groupId>
//			<artifactId>selenium-chrome-driver</artifactId>
//			<version>3.141.59</version>
//		</dependency>
//		<dependency>
//			<groupId>org.seleniumhq.selenium</groupId>
//			<artifactId>selenium-api</artifactId>
//			<version>3.141.59</version>
//		</dependency>
//		<dependency>
//			<groupId>org.seleniumhq.selenium</groupId>
//			<artifactId>selenium-remote-driver</artifactId>
//			<version>3.141.59</version>
//		</dependency>
//		<dependency>
//			<groupId>org.seleniumhq.selenium</groupId>
//			<artifactId>selenium-support</artifactId>
//			<version>3.141.59</version>
//		</dependency>
//    chrome版本 90.0.4430.212（正式版本） （64 位）
//    chromedriver版本 89.0.4389.23
//    selenium V3.141.59

    public static void main(String[] args) throws Exception {
        WebDriver driver = null;
        try {
            driver = getChromeDriver();
            driver.get("http://httpbin.org/ip");
            String title = driver.getTitle();
            System.out.println(title);
            Thread.sleep(1000);
            cleanCache(driver);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(driver != null){
                driver.close();
            }
        }
    }

    public static WebDriver getChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        //下载驱动 http://npm.taobao.org/mirrors/chromedriver/
        //http://npm.taobao.org/mirrors/chromedriver/89.0.4389.23/chromedriver_win32.zip
        System.setProperty("webdriver.chrome.driver", "C:/xxxxxx/chromedriver-89.0.4389.23.exe");//此处是chromedriver驱动文件路径，根据不同系统调整
        chromeOptions.addArguments("user-data-dir=C:/Windows/Temp/Google/Chrome/User Data/" + RandomUtil.getRandNumber(999, 1));//启动的用户目录
        chromeOptions.addArguments("start-maximized");//启动就最大化
        chromeOptions.addArguments("test-type --ignore-certificate-errors");//忽略https证书错误
        chromeOptions.addArguments("user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");//ua
        chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));//阻止弹出窗口
        chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));//关闭使用ChromeDriver打开浏览器时上部提示语"Chrome正在受到自动软件的控制
        //更改proxy示例.zip压缩包，background.js文件中username和password为实际订单中的用户名密码
        chromeOptions.addExtensions(new File("D:/proxy-examples.zip"));//添加crx代理插件

        return new ChromeDriver(chromeOptions);
    }

    /**
     * 清理缓存
     * @param driver
     * @throws Exception
     */
    public static void cleanCache(WebDriver driver) throws InterruptedException {
        driver.get("chrome://settings/clearBrowserData");
        driver.switchTo().activeElement();
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement clearData = (WebElement) js.executeScript("return document.querySelector('settings-ui').shadowRoot.querySelector('settings-main').shadowRoot.querySelector('settings-basic-page').shadowRoot.querySelector('settings-section > settings-privacy-page').shadowRoot.querySelector('settings-clear-browsing-data-dialog').shadowRoot.querySelector('#clearBrowsingDataDialog').querySelector('#clearBrowsingDataConfirm')");
        clearData.click();
    }

}

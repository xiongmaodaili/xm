import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * Selenium启动fireFox示例，包含selenium版本、fireFox版本、geckodriver版本、代理插件close-proxy-authentication，经测试可用
 */
public class SeleniumFireFoxDemo {
//        <!-- selenium maven配置 -->
//        <dependency>
//			<groupId>org.seleniumhq.selenium</groupId>
//			<artifactId>selenium-java</artifactId>
//			<version>3.141.59</version>
//		</dependency>
//		<dependency>
//			<groupId>org.seleniumhq.selenium</groupId>
//			<artifactId>selenium-firefox-driver</artifactId>
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
//    selenium与firefox版本对应关系查:https://firefox-source-docs.mozilla.org/testing/geckodriver/Support.html
//    Firefox V55.0 http://ftp.mozilla.org/pub/firefox/releases/55.0/win64/zh-CN/
//    geckodriver v0.20.0 https://github.com/mozilla/geckodriver/releases/tag/v0.20.0
//    selenium V3.141.59
//    close-proxy-authentication V1.1

    // 代理隧道验证信息
	//修改username和password为实际订单中的用户名密码
    final static String proxyUser = "xxxxxxxxxx";
    final static String proxypass = "xxxxxxxxx";

    // 代理服务器
    final static String proxyHost = "dtqybf.xiongmaodaili.com";
    final static int proxyPort = 8091;

    public static void main(String[] args) throws Exception {
        WebDriver driver = null;
        try {
            driver = getFireFoxDriver();
            driver.get("http://httpbin.org/ip");
            cleanCache(driver);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(driver != null){
                driver.close();
            }
        }
    }

    public static WebDriver getFireFoxDriver() throws IOException {
        //浏览器在这里下载http://ftp.mozilla.org/pub/firefox/releases/
        System.setProperty("webdriver.firefox.bin", "C:/Program Files/Mozilla Firefox/firefox.exe");
        //驱动在这里下载 https://github.com/mozilla/geckodriver/releases
        System.setProperty("webdriver.gecko.driver", "C:/Program Files/Mozilla Firefox/geckodriver.exe");

        FirefoxProfile profile = new FirefoxProfile();
        // 使用代理   0 - 不用代理； 1 - 使用代理
        profile.setPreference("network.proxy.type", 1);
        // 代理服务器配置
        profile.setPreference("network.proxy.http", proxyHost);
        profile.setPreference("network.proxy.http_port", proxyPort);

        profile.setPreference("network.proxy.ssl", proxyHost);
        profile.setPreference("network.proxy.ssl_port", proxyPort);

        //添加代理认证插件(该插件支持firefox55版本，下载地址:http://ftp.mozilla.org/pub/firefox/releases/55.0/win64/zh-CN/ 安装该版本后断网并设置浏览器不检查更新以防止自动更新到最新版本)
        profile.addExtension(new File("F:\\xxxxxx\\resources\\close_proxy_authentication-1.1-sm+tb+fx.xpi"));
        String credentials = new String(Base64.getEncoder().encode((proxyUser + ":" + proxypass).getBytes()));
        profile.setPreference("extensions.closeproxyauth.authtoken", credentials);

        // 所有协议公用一种代理配置，如果单独配置，这项设置为false
        profile.setPreference("network.proxy.share_proxy_settings", true);

        // 对于localhost的不用代理，这里必须要配置，否则无法和webdriver通讯
        profile.setPreference("network.proxy.no_proxies_on", "localhost");
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        return new FirefoxDriver(options);
    }

    public static void cleanCache(WebDriver driver) throws Exception{

    }

}
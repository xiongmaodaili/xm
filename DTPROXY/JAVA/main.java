// 新用户替换29行的secret和orderno即可运行本示例代码
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestDynamic {
	//change 参数: false-换ip ，true-不换ip
	public static String authHeader(String orderno, String secret, int timestamp,String change){
		//拼装签名字符串
		String planText = String.format("orderno=%s,secret=%s,timestamp=%d", orderno, secret, timestamp);

		//计算签名
		String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(planText).toUpperCase();

		//拼装请求头Proxy-Authorization的值;change 参数: false-换ip ,true-不换ip
		String authHeader = String.format("sign=%s&orderno=%s&timestamp=%d&change=%s", sign, orderno, timestamp,change);
		return authHeader;
	}

	public static void main(String[] args) throws IOException {
		final String url = "http://2017.ip138.com/ic.asp";
		final int port = 8089;//这里以正式服务器端口地址为准
		final String ip = "dynamic.xiongmaodaili.com";//这里以正式服务器ip地址为准
		int timestamp = (int) (new Date().getTime()/1000);
		//以下订单号，secret参数 须自行改动；最后一个参数: true-换ip ,false-不换ip
		final String authHeader = authHeader("DT21520******NNzLZBtR", "df93fde449*****0e78821c", timestamp,"true");
		System.out.println(authHeader);
		ExecutorService thread = Executors.newFixedThreadPool(10);
		for (int i=0;i<10;i++) {
			thread.execute(new Runnable() {
				@Override
				public void run() {
					Document doc = null ;
					try {
						long a = System.currentTimeMillis();
						doc = Jsoup.connect(url)
								.proxy(ip, port,null)
								.validateTLSCertificates(false)//忽略证书认证,每种语言客户端都有类似的API
								.header("Proxy-Authorization", authHeader)
								.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
								.timeout(10000)
								.get();
						System.out.println("访问结果"+doc.text()+" 访问成功所用时间："+ (System.currentTimeMillis() - a) );
						Thread.sleep(1000);
					} catch (Exception e) {

					}
				}
			});

		}
		thread.shutdown();
	}
}

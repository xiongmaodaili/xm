import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.*;

/**
 * 企业转发代理示例
 */
//<dependency>
//<groupId>org.jsoup</groupId>
//<artifactId>jsoup</artifactId>
//<version>1.13.1</version>
//</dependency>
public class TestProxyExamples {

	public static void main(String[] args) {
		try {
			final String url = "https://httpbin.org/ip";

			final String ip = "dtqybf.xiongmaodaili.com";//这里以正式服务器ip地址为准
			//企业动态按并发
			Integer port = 8091;
			String userName = "xxxxxxx";
			String password = "xxxxxxx";

			ExecutorService threadPool = Executors.newFixedThreadPool(10);
			int totalCount = 10;//请求总数
			List<Future<Boolean>> list = new CopyOnWriteArrayList<>();
			for (long i=1; i<=totalCount; i++) {
				Task task = new Task(url, ip, port, userName, password, i + 1);
				Future<Boolean> future = threadPool.submit(task);
				boolean b = future.get();
			}
			threadPool.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class Task implements Callable<Boolean> {

		private String url;
		private String ip;
		private int port;
		private String userName;
		private String password;
		private long index;

		public Task(String url, String ip, int port, String userName, String password, long index) {
			this.url = url;
			this.ip = ip;
			this.port = port;
			this.userName = userName;
			this.password = password;
			this.index = index;
		}

		@Override
		public Boolean call() {
			boolean isSuccess = true;
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					ip, port));
			//通过从jdk.http.auth.tunneling.disabledSchemes网络属性中删除Basic或在命令行上将同名的系统属性设置为“”（空）来重新激活此身份验证方案
			System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
			Authenticator.setDefault(new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password.toCharArray());
				}
			});
			Long startTime = System.currentTimeMillis();
			try {
				Document doc = Jsoup.connect(url)
						.proxy(proxy)
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
						.ignoreContentType(true)
						.timeout(10000)
						.get();
				System.out.println("访问结果:"+doc.body().html()+" 访问成功所用时间："+ (System.currentTimeMillis() - startTime));
			} catch (Exception e) {
				isSuccess = false;
				System.out.println("访问异常:"+ e);
			}
			return isSuccess;
		}
	}

}

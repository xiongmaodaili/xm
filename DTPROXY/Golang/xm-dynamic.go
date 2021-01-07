// xm-dynamic
//go1.13.3

package main

import (
	"crypto/md5"
	"crypto/tls"
	"encoding/hex"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"net/url"
	"strconv"
	"strings"
	"time"
)

const (
	// 代理服务器接口地址,这里以正式服务器端口地址为准
	proxyServer = "http://dynamic.xiongmaodaili.com:8088"
	//动态并发产品代理设置为:dynamic.xiongmaodaili.com:8089
	//动态按量产品需将代理设置为:dynamic.xiongmaodaili.com:8088

	// 动态代理单号
	orderno = "DT201907261xxxxxxxxxxxx"
	//个人密钥
	secret = "d10c54e4908f08974fxxxxxxxxxxxx"
	//目标网站
	targetUrl = "http://2021.ip138.com"
)

func md5v(s string) string {
	h := md5.New()
	h.Write([]byte(s))
	return hex.EncodeToString(h.Sum(nil))

}

func setAuth() string {
	//时间戳
	timestamp := time.Now().Unix()
	v1 := strconv.FormatInt(timestamp, 10)
	txt := "orderno=" + orderno + ",secret=" + secret + ",timestamp=" + v1
	//fmt.Println(txt)
	//fmt.Println(timestamp)
	sign := strings.ToUpper(md5v(txt))
	//fmt.Println(sign)
	auth := "sign=" + sign + "&" + "orderno=" + orderno + "&" + "timestamp=" + v1 + "&change=true"
	//fmt.Println(auth)
	return auth
}

func main() {

	auth := setAuth()
	fmt.Println(auth)
	proxy, err := url.Parse(proxyServer)
	if err != nil {
		log.Fatal(err)
	}

	netTransport := &http.Transport{
		Proxy:                 http.ProxyURL(proxy),
		MaxIdleConnsPerHost:   10,
		ResponseHeaderTimeout: time.Second * time.Duration(5),
		TLSClientConfig:       &tls.Config{InsecureSkipVerify: true}, //忽略https验证
	}

	httpClient := &http.Client{
		Timeout:   time.Second * 10,
		Transport: netTransport,
	}

	request, _ := http.NewRequest("GET", targetUrl, nil)
	request.Header.Add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.0.1 Safari/605.1.15")
	request.Header.Add("Proxy-Authorization", auth)
	response, err := httpClient.Do(request)

	if err != nil {
		panic("failed to connect: " + err.Error())
		//log.Fatal(err)
		return
	}

	defer response.Body.Close()
	if response.StatusCode != http.StatusOK {
		log.Println(err)
		return
	}

	content, _ := ioutil.ReadAll(response.Body)
	fmt.Println("Response Status:", response.Status)
	fmt.Println("##################")
	str_content := string(content)
	fmt.Println(str_content)
}

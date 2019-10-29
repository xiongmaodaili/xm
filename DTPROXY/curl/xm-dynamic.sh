#!/bin/bash

#时间
time=`date "+%Y-%m-%d %H:%M:%S"`

#时间戳
timestamp=`date '+%s'`
#单号
orderno="DT2019072614xxxxxxxxxx"
#secret
secret="d10c54e4908fxxxxxxxxxx1404b6cc4a97"
#代理地址+端口,这里以正式服务器端口地址为准
proxy="dynamic.xiongmaodaili.com:8088"
#动态并发产品代理设置为:dynamic.xiongmaodaili.com:8089
#动态按量产品需将代理设置为:dynamic.xiongmaodaili.com:8088

#UA
ua="Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"

#目标网站
targetUrl="http://2000019.ip138.com/"
###############################################################################################
#字符串拼接
string="orderno=${orderno},secret=${secret},timestamp=${timestamp}"
#echo $string

#字符串转大写
typeset -u md5_string
md5_string=`echo -n $string |md5sum|awk '{print$1}'`
#echo $md5_string

auth="sign=${md5_string}&orderno=${orderno}&timestamp=${timestamp}"
#echo $auth


#发起请求
curl -s --connect-timeout 15 -k --user-agent "$ua"  -x $proxy -H "Authorization: $auth" $targetUrl


const crypto = require('crypto');
const request = require('request');

let timestamp = parseInt(new Date().getTime()/1000);
let url = 'http://www.baidu.com/';
// 新用户更换orderno,secret
let orderno = 'DT20179xxxxxxxxx';
let secret = 'cb65091847ad42fxxxxxxx';

let txt = 'orderno='+orderno+',secret='+secret+',timestamp='+timestamp;
let md5 = crypto.createHash('md5');
md5.update(txt);
let sign = md5.digest('hex');
sign = sign.toUpperCase();
//动态并发产品代理设置为dtbf.xiongmaodaili.com:8089, 动态按量产品需将代理设置为dtan.xiongmaodaili.com:8088
let options = {
    url:url,
    proxy: "http://dtan.xiongmaodaili.com:8088",
    headers:{
      'Proxy-Authorization':'sign='+sign+'&orderno='+orderno+"&timestamp="+timestamp
    }
};
function callback(error, response, body) {
  if (!error && response.statusCode == 200) {
      console.log(body)
      return false
  }
  console.log(error)
}
request(options, callback);

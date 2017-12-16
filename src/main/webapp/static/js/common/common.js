//关闭浏览器
function andriodLoginClick(obj) {
    var u = navigator.userAgent;
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    if (isiOS) {
        closeAppleWindow();
        return;
    }
    if (isAndroid) {
        JavaObj.javaScriptToGameSdkFinsh(obj);
        return;
    }
}
//将小数转化为百分数
function percentage(rate) {
    var num = rate * 100;
    num = num.toFixed(2);
    return num + "%";
}
//将时间格式化为yyyy-MM-dd
function formatDate(date) {
    return date.substr(0,10);
}
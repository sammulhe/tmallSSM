<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery/2.0.0/jquery.min.js"></script>
<script src="js/jquery/jquery.qrcode.min.js"></script>
<title>Insert title here</title>
</head>
<body>

<div style="width:800px;margin:50px auto;text-align:center">
<h1>把字符串: http://baidu.com 转换为二维码</h1>
<div id="qrcode"></div>
 
</div>
 
<script>
$(function(){
	//注： 其中的canvas还可以换成 table,用于在不支持html5的浏览器中使用。
    $('#qrcode').qrcode({render:'canvas',text:"http://baidu.com",width:260,height:260});   
});
 
</script>
</body>
</html>
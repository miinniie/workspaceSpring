<!-- 지시부(@ 있는곳) -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src= "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="js_css/script.js"></script>
<link rel="stylesheet" href="js_css/style.css" type="text/css"/>
<script>
	$(function(){
		$("img:first").click(function(){
			show();
		})
	});
</script>
<div id="container">
	<img src="resources/image2.jpg"/>
</div>


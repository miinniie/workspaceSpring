<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style>
	#resultView{background: #ddd;}
</style>
<script>
	$(function(){
		//비동기식으로 서버에 접속해 문자열 정보를 가져오기
		$("#ajaxString").click(function() {
			//서버로 보낼 데이터
			var params = "no=1234&username=홍길동";
			var url="/myapp/ajaxString";
			$.ajax({
				type:'GET', //전송방식
				url : url, //접속할 매핑 주소
				data:params,//서버로 전송할 데이터
				success:function(result){ //서버에서 정보를 정상적으로 받았을 때 호출되는 함수
					$("#resultView").append(result);
				},error:function(request, status, error){
					console.log("문자열 받기 ajax 에러 발생");
					console.log("code:"+request.code);
					console.log("responseText:"+request.responseText);
					console.log("error:" + error);
				}
			});
		});
		//비동기식으로 서버에서 Object객체를 가져온다.
		$("#ajaxObject").on('click',function(){
			var url= "/myapp/ajaxObject";
			var params = "tel=010-1234-5678&email=abcd@nate.com";
			$.ajax({
				type:'GET',
				data:params,
				url:url,
				success:function(result){
					var tag="<ul>";
					tag+="<li>번호: "+result.num+"</li>";
					tag+="<li>이름:"+result.username+"</li>";
					tag+="<li>연락처:"+result.tel+"</li>";
					tag+="<li>주소:"+result.addr+"</li></ul>";
					
					console.log(tag);
					
					$("#resultView").html(tag);
					
				},error:function(e){
					console.log(e.responsetText);
				}
			});
		});
//		이벤트,  선택자,		실행함수
		$(document).on('click','#ajaxList',function(){
			$.ajax({
				type:"GET",
				url:"/myapp/ajaxList",
				success:function(result){
					//List의 객체를 순서대로 접근하기 위해서는 result를 $(result)로 변환해야 한다.
					var $result = $(result);
					var tag="<ol>";
					//				idx=0, 1,2,3 vo=vo, vo, vo, vo
					$result.each(function(idx,vo){ //자바스크립트 반복문
						//1개의 vo객체를 이용하여 출력형식을 설정한다.
						tag += "<li>"+vo.num+", "+vo.username+", "+vo.tel+", "+vo.addr+"</li>";
					}); 
					
					tag +="<ol>";
					$("#resultView").html(tag);
					
				},error:function(e){
					console.log(e.responseText);
				}
			}); 
		});
		//클라이언트가 비동기식으로 HashMap객체를 얻어오기
		$("#ajaxMap").click(function(){
			$.ajax({
				type:'GET',
				url:'/myapp/ajaxMap',
				success:function(result){
					var tag="<ul>";
					//		result.key.variable
					tag+="<li>"+result.name1.num+", "+result.name1.username+", "+result.name1.tel+", "+result.name1.addr+"</li>";
					tag+="<li>"+result.name2.num+", "+result.name2.username+", "+result.name2.tel+", "+result.name2.addr+"</li>";
					tag+="<li>"+result.name3.num+", "+result.name3.username+", "+result.name3.tel+", "+result.name3.addr+"</li>";
					tag +="<ul>";
					$("#resultView").html(tag);
					
				},error:function(e){
					console.log(e.responseText);
				}
			});
		});
		
		$("#ajaxJSON").click(function(){
			$.ajax({
				type:'GET',
				url:'/myapp/ajaxJson',
				success:function(result){
					//{"num":"12345","username":"세종대왕","tel":"010-1234-5678"}
					//문자열로 받은 데이터를 자바스크립트의 JSON형식 데이터 변환하기 (스트링을 파싱)
					var jsonData = JSON.parse(result);
					console.log(jsonData); //Object형식으로 key+value로 됨
					
					//스트링으로 리턴
					var tag = "번호 ->"+jsonData.num + "<br/>";
						tag += "이름 ->"+jsonData.username + "<br/>";
						tag += "연락처 ->"+jsonData.tel;
						
					$('#resultView').html(tag);
			},error:function(e){
				console.log(e.responseText);
			}
			
			});
		});
		
		//폼데이터를 ajax를 이용하여 서버로 데이터 보내기
		$("#frm").submit(function(){
			//num=32&username=홍&email=abcd@nate.com
			//폼의 데이터를 전송데이터로 생성해주는 메소드
			var frmData = $("#frm").serialize();
			var url = "/myapp/ajaxForm";
			$.ajax({
				type: "POST",
				url: url,
				data: frmData,
				success: function(result){
					$("#resultView").html(result);
				},error:function(e){
					console.log(e.responseText);
				}
			});
			return false;//submit 실행중지시킨다. > 페이지에 데이터 남아있게
		});
	});
	
</script>
</head>
<body>
	<input type="button" value="ajax(String)" id="ajaxString"/>
	<input type="button" value="ajax(Object)" id="ajaxObject"/>
	<input type="button" value="ajax(List)" id="ajaxList"/>
	<input type="button" value="ajax(Map)" id="ajaxMap"/>
	<input type="button" value="ajax(JSON)" id="ajaxJSON"/>

<hr/>
<form method="post" id="frm"> <!-- form은 submit하면은 action으로 기본적으로 보내고 페이지 바꾸는게 default임 -->
	번호: <input type="text" name="num"/><br/>
	이름: <input type="text" name="username"/><br/>
	이메일: <input type="text" name="email"/><br/>
	<input type="submit" value="서버로 데이터보내기(ajax)"/> <!-- submit이 아닌 type="button"으로 하고 event를 submit이 아닌 click으로 하면 같음 -->
</form>
<div id="resultView"></div>
</body>
</html> 
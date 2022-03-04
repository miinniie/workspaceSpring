<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	#mFrm ul{
		overflow:auto;
	}
	#mFrm li{
		float:left;
		height:40px; 
		line-height:40px;
		border-bottom:1px solid gray;
	}
	#mFrm li:nth-child(2n){width:80%;}
	#mFrm li:nth-child(2n+1){width:20%;}
	
</style>
<script>
	$(function(){
		$("#mFrm").submit(function(){
			//아이디->문자, 숫자, 8~15글자까지
			var userid = $("#userid").val();
			//정규표현식 패턴
			// ^: 처음부터, $: 끝까지
			var reg = /^[a-zA-z]{1}[a-zA-z0-9_$]{7,14}$/;
			if(!reg.test(userid)){ //true:조건에 맞을때, false:조건이 맞지 않을때
				alert("아이디는 첫번째 문자, 영어대소문자, 숫자,_,$만 허용되며\n아이디의 길이는 8~15글자사이여야 합니다.");
				return false;
			}
			if(!reg.test($("#userpwd").val())){ //true:조건에 맞을때, false:조건이 맞지 않을때
				alert("비밀번호는 첫번째 문자, 영어대소문자, 숫자,_,$만 허용되며\n비밀번호의 길이는 8~15글자사이여야 합니다.");
				return false;
			}
			if($("#userpwd").val() != $("#userpwd2").val()){
				alert("비밀번호가 일치하지 않습니다.");
				return false;
			}
			//이름 유효성검사
			reg = /^[가-힣]{2,7}$/;
			if(!reg.test($("#username").val())){
				alert("이름을 잘못입력하였습니다.");
				return false;
			}
			
			reg = /^[0-9]{3,4}$/;
			var reg1 = /^[0-9]{4}$/;
			if(!reg.test($("#tel2").val()) || !reg1.test($("#tel3").val())){
				alert("연락처를 잘못입력하였습니다.")
				return false;
			}
			
			return true;
		});
	});
</script>
<div id="container">
	<h1>회원등록폼</h1>
	<!-- 						폼의 값을 서버로 가져가는 접속어 - controller- method -->
	<form method="post" action="<%=request.getContextPath()%>/member/memWrite" id="mFrm">
		<ul>
			<li>아이디</li>
			<li><input type="text" name="userid" id="userid" maxlength="15" value="dimpna96"/></li>
			<li>비밀번호</li>
			<li><input type="password" name="userpwd" id="userpwd" maxlength="15" value="abcd1234"/></li>
			<li>비밀번호확인</li>
			<li><input type="password" name="userpwd2" id="userpwd2" maxlength="15" value="abcd1234"/></li>
			<li>이름</li>
			<li><input type="text" name="username" id="username" value="배지영"/></li>
			<li>연락처</li>
			<li>
				<select name="tel1" id="tel1">
					<!-- <option value="not">선택</option> -->
					<option value="010">010</option>
					<option value="02">02</option>
					<option value="031">031</option>
				</select> -
				<input type="text" name="tel2" id="tel2"/> -
				<input type="text" name="tel3" id="tel3"/>
			</li>
			<li>이메일</li>
			<li><input type="text" name="email" id="email"/></li>
			<li><input type="submit" value="회원등록"/>
				<input type="reset" value="다시쓰기"/></li>
		</ul>
	</form>	
</div>
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
			if($("#userpwd").val()==""){ //true:조건에 맞을때, false:조건이 맞지 않을때
				alert("비밀번호 입력하세요.");
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
	<form method="post" action="<%=request.getContextPath()%>/member/memberEditOk" id="mFrm">
		<ul>
			<li>아이디</li>
			<li><input type="text" name="userid" id="userid" maxlength="15" value="${vo.userid}" readonly/></li>
			<li>비밀번호</li>
			<li><input type="password" name="userpwd" id="userpwd" maxlength="15"/></li>
			<li>이름</li>
			<li><input type="text" name="username" id="username" value="${vo.username}" readonly/></li>
			<li>연락처</li>
			<li>
				<select name="tel1" id="tel1">
					<!-- <option value="not">선택</option> -->
					<option value="02"<c:if test="${vo.tel1=='02'}">selected</c:if>>02</option>
					<option value="031"<c:if test="${vo.tel1=='031'}">selected</c:if>>031</option>
					<option value="010"<c:if test="${vo.tel1=='010'}">selected</c:if>>010</option>
				</select> -
				<input type="text" name="tel2" id="tel2" value="${vo.tel2}"/> -
				<input type="text" name="tel3" id="tel3" value="${vo.tel3}"/>
			</li>
			<li>이메일</li>
			<li><input type="text" name="email" id="email" value="${vo.email}"/></li>
			<li><input type="submit" value="회원정보수정"/>
				<input type="reset" value="다시쓰기"/></li>
		</ul>
	</form>	
</div>
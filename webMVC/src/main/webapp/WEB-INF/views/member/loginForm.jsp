<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	#mainContent{
		width:420px;
		margin: 0 auto;
		background-color:#fff;
		padding: 10px 140px;		
	}
	#mainContent #logo{
		text-align:center;	
	}
	
	#mainContent #logo>img{
		width:200px;
	}
	#daumFrm>li:first-of-type{
		text-align:right;
	}
	#daumFrm>li:nth-of-type(2)>input,#daumFrm>li:nth-of-type(3)>input,#daumFrm>li:nth-of-type(4)>input{
		width:100%; height:40px; margin:10px 0;
	}
	#daumFrm>li:nth-of-type(4)>input{
		background-color:rgb(76,135,238);
		border:0px;
		color:#fff;
		font-size:1.5em;
	}
	#ipon{float: right;}
	#on{color:rgb(76,135,238); font-weight:bold;}

	#daumFrm>li:nth-of-type(6){
		text-align:center;
		padding:50px 0;
	}
	
	#bottom{
		width:700px;
		margin:0px auto;
		background:#fff;
		text-align:center;
		border-top:1px solid #ddd;
		padding:100px 0;
	}

</style>
<script>
	function logChk(){
		if(document.getElementById("userid").value==""){
			alert("아이디를 입력후 로그인 하세요.");
			return false;
		}
		if(document.getElementById("userpwd").value==""){
			alert("비밀번호를 입력후 로그인 하세요.")
			return false;
		}
		return true;
	}
</script>
<div id="container">
	<div id="mainContent">
		<div id="logo">
			<img src="09.PNG" />
		</div>
		<form method="post" action="/myapp/member/loginCheck" onsubmit="return logChk()">
			<ul id="daumFrm">
				<li><a href="#">다른 방법으로 로그인</a></li>
				<li><input type="text" name = "userid" id="userid" placeholder="아이디" maxlength="15"/></li>
				<li><input type="passWord" name = "userpwd" id="userpwd" placeholder="비밀번호" maxlength="15"/></li>
				<li><input type="submit" value="로그인"/></li>
				<li>
					<input type="checkbox" name="chkStatus" value="ok"/>로그인 상태유지
					<span id="ipon">IP보안 <span id="on">ON</span></span>
				</li>
				<li>
					<a href="">아이디찾기</a> | <a href="">비밀번호찾기</a>
				</li>
				<li><img src="08.PNG" width="420px"/><li>
			</ul>
		</form>
	</div>
</div>
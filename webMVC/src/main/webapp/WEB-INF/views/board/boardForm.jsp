<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	$(function(){
		CKEDITOR.replace("content");
	
		$("#boardBtn").click(function(){
			//제목여부
			if($("#title").val()==""){
				alert("제목을 입력하세요.");
				return;
			}
			//글내용여부
			//if($("#content").val()==""){
			if(CKEDITOR.instances.content.getData()==""){	
				alert("글내용을 입력하세요.");
				return;
			}
			//submit 발생시키기
			$("#frm").submit();
		});
	});
</script>
<div id="container">
	<h1>글쓰기폼</h1>
	<form method="post" id="frm" action="/myapp/board/boardFormOk">
		<ul>
			<li>제목 : <input type="text" name="title" id="title" size="80"/></li>
			<li>글내용 : <textarea name="content" id="content"></textarea></li>
			<li>
				<input type="button" id="boardBtn" value="글등록하기"/>
				<input type="reset" value="다시쓰기"/>	
			</li>
		</ul>
	</form>
</div>
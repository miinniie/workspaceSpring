<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="//cdn.ckeditor.com/4.17.1/standard/ckeditor.js"></script>
<script>
	$(function(){
		CKEDITOR.replace("content");
		
		$("#editFrm").submit(function(){
			if($("#title").val()==""){
				alert("제목을 입력후 수정하세요..");
				return false;
			}
			if(CKEDITOR.instances.content.getData()==""){
				alert("글내용 입력후 수정하세요.");
				return false;
			}
			return true;
		});
	});
</script>
<div id="container">
	<h1>글수정하기</h1>
	<form id="editFrm" method="post" action="/myapp/board/boardEditOk">
		<input type="hidden" name="no" value="${vo.no}"/>
		<ul>
			<li>제목<br/>
				<input type="text" name="title" id="title" value="${vo.title}" size="100"/></li>
			<li>글내용
				<textarea name="content" id="content">${vo.content}</textarea>
			</li>
			<li>
				<input type="submit" value="글수정하기"/>
				<input type="reset" value="다시쓰기"/>
			</li>
		</ul>
	</form>
</div>
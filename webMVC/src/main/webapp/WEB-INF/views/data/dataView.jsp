<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	function dataDelCheck(){
		if(confirm("자료실 글을 삭제하시겠습니까?")){
			location. href = "/myapp/data/dataDel?no=${dataVo.no}"
		}
	}
</script>

<div id="container">
	<h1>자료실 글내용보기</h1>
	<ul>
		<li>번호 : ${dataVo.no}</li>
		<li>작성자 : ${dataVo.userid}</li>
		<li>등록일 : ${dataVo.writedate}</li>
		<li>조회수 : ${dataVo.hit}</li>
		<li>제목 : ${dataVo.title}</li>
		<li>글내용<br/> ${dataVo.content}</li>
		<li>첨부파일 :
			<a href="/myapp/upload/${dataVo.filename1}" download>${dataVo.filename1}</a>
			<c:if test="${dataVo.filename2!=null}">
				,<a href="/myapp/upload/${dataVo.filename2}">${dataVo.filename2}</a>
			</c:if>
		</li>
	</ul>
	<div>
		<!-- 글쓴이만 수정 , 삭제가 되어야한다. -->
		<c:if test="${dataVo.userid==logId}">
		<a href="/myapp/data/dataEdit?no=${dataVo.no}">수정</a>
		<a href="javascript:dataDelCheck()">삭제</a>
		</c:if>
	</div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	#dataList li{
		float:left;
		width:10%;
		height:40px;
		line-height:40px;
		border-bottom:1px solid #ddd;
	}
	
	#dataList li:nth-child(6n+2){
		width:50%
	}
	#dataList>ul{
		overflow:auto;
		padding:50px 0;
	}
</style>


<div id="container">
	<h1>자료실 목록</h1>
	<div><a href="/myapp/dataWrite">글쓰기</a></div>
	<div id="dataList">
		<ul>
			<li>번호</li>
			<li>제목</li>
			<li>글쓴이</li>
			<li>첨부파일</li>
			<li>등록일</li>
			<li>조회수</li>
			<c:forEach var="vo" items="${lst}">
			<li>${vo.no}</li>
			<li><a href="/myapp/data/dataView?no=${vo.no}">${vo.title}</a></li>
			<li>${vo.userid}</li>
			<li>
				<!-- 첫번째 첨부파일 -->
				<a href="/myapp/upload/${vo.filename1}" download><img src="/myapp/resources/disk.png" title="${vo.filename1}"/></a>
				<!-- 두번째 첨부파일은 있을수도 있고 없을수도 있음! -->
				<c:if test="${vo.filename2!=null && vo.filename2!=''}">
					<a href="/myapp/upload/${vo.filename2}" download><img src="/myapp/resources/disk.png" title="${vo.filename2}"/></a>
				</c:if>
						
			</li>
			<li>${vo.writedate}</li>
			<li>${vo.hit}</li>
			</c:forEach>
		</ul>
	</div>
</div>
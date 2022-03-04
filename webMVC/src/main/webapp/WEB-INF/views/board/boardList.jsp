<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	#boardList>li{
		float:left;
		height:40px;
		line-height:40px;
		border-bottom:1px solid #ddd;
		width:10%;
	}
	#boardList>li:nth-child(5n+2){
		width:60%;
		/*자리가 모자라도 줄바꿈 안하도록 설정, 노랩*/
		white-space:nowrap;
		/*넘치는 내용 숨기기*/
		overflow:hidden;
		/*	...	 표시하기*/
		text-overflow:ellipsis;
	}
	/*페이징*/
	#paging{
		padding:20px 200px;
	}
	#paging>ul, #boardList{
		overflow:auto;
	}
	#paging li{
		float:left;
		width:40px;
		text-align:center;
	}
	/*검색*/
	#search{
		padding:20px 200px;
		text-align:center;
	}
</style>
<script>
	//검색어 체크
	function searchWordCheck(){
		if(document.getElementById("searchWord").value==""){
			alert("검색어를 입력후 검색하세요.");
			return false;
		}
		return true;
	}
</script>
<div id="container">
	<h1>게시판 목록</h1>
	<a href="/myapp/board/boardFrm">글쓰기</a>
	<ul id="boardList">
		<li>번호</li>
		<li>제목</li>
		<li>작성자</li>
		<li>조회수</li>
		<li>등록일</li>
		
		<!-- jstl el 표현식 
			items : 컬렉션, 배열형변수를 표시하는 곳
			var : 변수선언
		-->
		
		<c:forEach var="vo" items="${lst}">
			<li>${vo.no}</li>
			<li><a href="/myapp/board/view?no=${vo.no}&pageNum=${pVo.pageNum}<c:if test='${pVo.searchWord!=null}'>&searchKey=${pVo.searchKey}&searchWord=${pVo.searchWord}</c:if>">${vo.title}</a></li>
			<li>${vo.userid}</li>
			<li>${vo.hit}</li>
			<li>${vo.writedate}</li>
		</c:forEach>
	</ul>
	<!-- 페이징 -->
	<div id="paging">
		<ul>
			<!-- 이전 페이지 -->
			<c:if test="${pVo.pageNum==1}">
				<li><a href="#">prev</a></li>
			</c:if>
			<c:if test="${pVo.pageNum>1}">
				<li><a href="/myapp/board/list?pageNum=${pVo.pageNum-1}<c:if test='${pVo.searchWord!=null}'>&searchKey=${pVo.searchKey}&searchWord=${pVo.searchWord}</c:if>">prev</a></li>
			</c:if>
			<!-- 페이지번호 	1				1		5-->
			<!-- 			6				6		10  end는 작거나 같다라는 뜻을 가지고 있음 -->
			<c:forEach var="p" begin="${pVo.startPageNum}" end="${pVo.startPageNum + pVo.onePageCount-1}">
				<c:if test="${pVo.totalPage>=p}">
				<c:if test="${p==pVo.pageNum}">	
					<li style="background:red;color:#fff;"><a href="/myapp/board/list?pageNum=${p}<c:if test='${pVo.searchWord!=null}'>&searchKey=${pVo.searchKey}&searchWord=${pVo.searchWord}</c:if>">${p}</a></li>
				</c:if>
				<c:if test="${p!=pVo.pageNum}">
					<li><a href="/myapp/board/list?pageNum=${p}<c:if test='${pVo.searchWord!=null}'>&searchKey=${pVo.searchKey}&searchWord=${pVo.searchWord}</c:if>">${p}</a></li>
				</c:if>
				</c:if>
			</c:forEach>
			<!-- 다음 페이지 -->
			<c:if test="${pVo.pageNum<pVo.totalPage}">
				<li><a href="/myapp/board/list?pageNum=${pVo.pageNum+1}<c:if test='${pVo.searchWord!=null}'>&searchKey=${pVo.searchKey}&searchWord=${pVo.searchWord}</c:if>">next</a></li>
			</c:if>
			<c:if test="${pVo.pageNum>=pVo.totalPage}">
				<li><a href="#">next</a></li>
			</c:if>
		</ul>
	</div>
	<!-- 검색어 -->
	<div id= "search">
		<form action="/myapp/board/list" id="searchForm" onsubmit="return searchWordCheck()">
			<select name="searchKey">
				<option value="title">제목</option>
				<option value="userid">작성자</option>
				<option value="content">글내용</option>
			</select>
			<input type="text" name="searchWord" id="searchWord"/>
			<input type="submit" value="Search"/>
		</form>
	</div>
</div>
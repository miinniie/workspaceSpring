<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
		#coment{width:500px;height:100px;}
		#replyList>ul>li{
			border-bottom:1px solid #ddd;
			padding:10px 0px;
		}
</style>
<script>
	//삭제여부 확인한 후 페이지 이동
	function delCheck(){
		//alert() >알려주는거 등록했습니다. 삭제되었습니다. 이렇게 통보하는거
		//confirm() : yes/no를 선택할 수 있는 대화상자(true, false가 return됨)
		if(confirm("삭제하시겠습니까?")){
			//확인(yes) 버튼을 선택한 경우
			//자바스크립트에서 페이지 이동하기, get방식으로 간다!
			var link = "/myapp/board/boardDel?no=${vo.no}&pageNum=${pVo.pageNum}";
			
			// 검색어가 있을때 주소를 연결
			if('${pVo.searchWord}' != ''){
				link += "&searchKey=${pVo.searchKey}&searchWord=${pVo.searchWord}";
			}
			console.log(link);
			location.href = link;
		}
	}
</script>
<script>
	$(function(){
		//해당 게시물의 모든 댓글 리스트를 선택하여 변경하는 함수
		function replyAllList(){
			//비동기식으로 서버에 접속하여 레코드선택(List)하여 view페이지의 목록을 변경하기
			var url = "/myapp/replyAllList";
			var params1 = "no=${vo.no}"// 원글 글번호
			
			$.ajax({
				type:'GET',
				url:url,
				data:params1,
				success:function(result){
					var $result = $(result);
					
					var tag="";
					$result.each(function(i, value){
						tag += "<li><div>" +value.userid+" ("+value.writedate+") ";
						// 'dimpna96'	==	'dimpna96'		
						if(value.userid == '${logId}'){
							//수정, 삭제
							tag+= "<input type='button' value='Edit'/>";
							tag+= "<input type='button' value='Del' title='"+value.replyno+"'/>";
						}
						tag += "<br/>"+value.coment+"</div>";
						//본인이 쓴 댓글일 경우에만 수정폼을 생성하여 숨겨둔다.
						if(value.userid=='${logId}'){
							tag += "<div style='display:none'><form method='post'>";
							tag += "<input type='hidden' name='replyno' value='"+value.replyno+"'/>";
							tag += "<textarea name='coment' rows='4' cols='50'>"+value.coment+"</textarea>";
							tag += "<input type='submit' value='댓글수정하기'/>";
							tag += "</form></div>";
						}
						
						tag += "</li>";
					});
					console.log(tag);
					
					//view에 html추가
					$("#replyList>ul").html(tag);
					
				},error:function(e){
					console.log(e.responseText);
				}
			})
		}
		//댓글쓰기
		$("#replyBtn").click(function(){
			
			if($("#coment").val()==""){
				alert("댓글쓰신 후 등록하세요.");
				return false;
			}else{
				var data = $("#replyFrm").serialize();
				var url = "/myapp/replyWrite";
				
				$.ajax({
					type:'POST',
					url:url,
					data:data,
					success:function(result){
						console.log(result);
						$("#coment").val("");
						replyAllList();
					},error:function(e){
						console.log(e.responseText);
					}
				});
			}
		});
		//댓글 수정하기(폼 보여주기)
		$(document).on('click',"#replyList input[value=Edit]", function(){
			//이벤트가 발생한 input버튼의 부모 div는 숨기고
			$(this).parent().css("display","none");
			//부모 div 다음에 있는 div는 보여주기
			$(this).parent().next().css("display","block");
		
		});
		//	
		//댓글 수정하기(DB)
		$(document).on('submit','#replyList form',function(){
			//데이터
			var params2 = $(this).serialize();
			var url = "/myapp/replyEditOk";
			
			$.ajax({
				type:'POST',
				url:url,
				data:params2,
				success:function(result){
					replyAllList();
				},error:function(e){
					console.log(e.responseText);
				}
			});
			return false;
		});
		//댓글 삭제하기
		$(document).on('click','#replyList input[value=Del]',function(){
			if(confirm("댓글을 삭제하시겠습니까?")){
				//예 버튼을 선택했을때 삭제한다.
				var replyData = "replyno="+$(this).attr("title");
				var url = "/myapp/replyDelete";
				
				$.ajax({
					type:'GET',
					data:replyData,
					url:url,
					success:function(result){
						replyAllList();
					},error:function(e){
						console.log(e.responseText);
					}
				});
				
			}
		});
		
		
		//댓글 목록 호출
		replyAllList();
	});
	
	
</script>
<div id= "container">
	<h1>글내용보기</h1>
	<ul>
		<li>번호 : ${vo.no}</li>
		<li>글쓴이 : ${vo.userid}</li>
		<li>등록일 : ${vo.writedate}</li>
		<li>조회수 : ${vo.hit}</li>
		<li>제목 : ${vo.title}</li>
		<li>글내용<br/> ${vo.content}</li> 
	</ul>
	<div>
		<!-- 글쓴이만 수정, 삭제하도록 설정 -->
		<c:if test="${vo.userid==logId}">
			<a href="/myapp/board/boardEdit?no=${vo.no}">수정</a>
			<a href="javascript:delCheck()">삭제</a>
		</c:if>
	</div>
	<!-- 댓글 -->
	<hr/>
	
	<div>
		<form method="post" id="replyFrm">
			<!-- 원글 글번호 -->
			<input type="hidden" name="no" value="${vo.no}"/>
			<textarea name="coment" id="coment"></textarea>
			<input type="button" id="replyBtn" value="댓글쓰기"/>
		</form>
	</div>
	<div id="replyList">
		<ul>
		</ul>
	</div>
</div>






<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	$(function(){
		CKEDITOR.replace("content");
		
		//첨부파일 수정하기
		$("#dataEditForm b").click(function(){
			$(this).parent().css("display","none");
			$(this).parent().next().next().attr("type","file");
			//삭제된 파일명을 서버로 보내기 위한 설정
			$(this).parent().next().attr("name","delFile");
			
			//파일 갯수 조정
			$("#cnt").val($("#cnt").val()-1);
		});
		
		//수정버튼 클릭시
		$("#dataEditForm").submit(function(){
			
			if($("#title").val()==""){
				alert("제목을 입력후 수정하세요.");
				return false;
			}
			if(CKEDITOR.instances.content.getData()==""){
				alert("글내용을 입력후 수정하세요.")
				return false;
			}
			//새로첨부한 파일의수를 확인
			if($("#filename1").attr("type")=='file' && $("#filename1").val()!=""){//새로 선택한 파일이 존재한다는 뜻
				$("#cnt").val(parseInt($("#cnt").val())+1);
			}
			if($("#filename2").attr("type")=='file' && $("#filename2").val()!=""){//새로 선택한 파일이 존재한다는 뜻
				$("#cnt").val(parseInt($("#cnt").val())+1);
			}
			
			if(parseInt($("#cnt").val())<1){
				alert("파일은 1개 이상 첨부되어야 합니다");
				return false;
			}
			
			return true;
		})
	});
	
</script>
<div id= "container">
	<h1>자료실 글 수정하기</h1>
	<form id="dataEditForm" method="post" action="/myapp/data/dataEditOk" enctype="multipart/form-data">
		<input type="hidden" name="no" value="${vo.no}"/>
		<ul>
			<li>제목: <input type="text" name="title" id="title" value="${vo.title}"/></li>
			<li>글내용
				<textarea name="content" id="content">${vo.content}</textarea>
			</li>
			<li>첨부파일<br/>
				<!-- 첫번째 첨부파일 -->
				<div>${vo.filename1} &nbsp; &nbsp; &nbsp; <b>X</b></div>
				<!-- 삭제파일명을 표시하는 input, 사용자가 x를 선택하면! name을 delFile로 수정할거임 -->
				<input type="hidden" name="" value="${vo.filename1}"/>
				<!-- 새로운 파일을 선택할 수 있는 input, X를 선택하면 type를 file로 변경 -->
				<input type="hidden" name="filename" id="filename1"/>
			</li>	
			<li>
				<!-- 두번째 첨부파일 -->
				<!-- 두번째 첨부파일이 있을때 -->
				<c:if test="${vo.filename2!=null}">
					<div>${vo.filename2} &nbsp; &nbsp; &nbsp; <b>X</b></div>
					<input type="hidden" name="" value="${vo.filename2}"/>
					<input type="hidden" name="filename" id="filename2"/> 
					<!-- id가 밑에거랑 같지만 프론트에서는 동시에 실행되지 않음! 위에게 실행되면 밑에건 실행되지 않으니깐~ 같은id 상관 ㄴㄴ -->
				</c:if>
				<!-- 두번째 첨부파일이 없을때  -->
				<c:if test="${vo.filename2==null}">
					<div><input type="file" name="filename" id="filename2"/></div>
				</c:if>
			</li>	
			<li>
				<input type="submit" value="자료실 글 수정하기"/>
				<input type="hidden" name="cnt" id="cnt" value="${cnt}"/>
			</li>
		</ul>
	</form>
	
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	#dataFrm li{
		padding:10px;
	}
	#title{width:100%;}
	
</style>
<script>
	$(function(){
		CKEDITOR.replace("content");
		
		//submit이벤트 발생 - 폼의 데이터확인
		$("#dataFrm").submit(function(){
			if($("#title").val()==""){
				alert("자료실 글 제목을 입력하세요.");
				return false;
			}
			if(CKEDITOR.instances.content.getData()==""){
				alert("자료실 글 내용을 입력하세요.");
				return false;
			}
			//첨부파일 갯수 구하기
			console.log($("#filename1").val());
			console.log($("#filename2").val());
			
			var fileCount=0;
			if($("#filename1").val()!=""){
				fileCount++;
			}
			if($("#filename2").val()!=""){
				fileCount++;
			}
			//fileCount ->0,1,2
			if(fileCount<=0){
				alert("첨부파일은 1개이상 선택되어야 합니다.");
				return false;
			}
			return true;
		});
	});
</script>

<div id="container">
	<h1>자료실 글 올리기</h1>
	<div>
	<!-- form안에 파일 첨부가 있을 경우 반드시 enctype속성을 명시하여야 한다. -->
		<form method="post" id="dataFrm" action="/myapp/dataWriteOk" enctype="multipart/form-data">
			<ul>
				<li>제목</li>
				<li><input type="text" name="title" id="title"/></li>
				<li>글내용</li>
				<li><textarea name="content" id="content"></textarea>
				<li>첨부파일</li>
				<li>
					<input type="file" name="filename" id="filename1"/><br/>
					<input type="file" name="filename" id="filename2"/>
				</li>
				<li>
					<input type="submit" value="자료실글올리기"/>
				</li>	
			</ul>
			
		</form>
	</div>
</div>
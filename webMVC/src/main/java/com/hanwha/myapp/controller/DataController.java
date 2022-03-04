package com.hanwha.myapp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hanwha.myapp.service.DataService;
import com.hanwha.myapp.vo.DataVO;

@Controller
public class DataController {
	@Inject
	DataService service;
	
	@RequestMapping("/data/list")
	public ModelAndView dataList() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("lst",service.dataAllList());
		mav.setViewName("data/dataList");
		return mav;
	}
	
	//자료실 글 올리기 폼
	@RequestMapping("/dataWrite")
	public String dataWrite() {
		return "data/dataForm";
	}
	//자료실 글올리기(파일업로드, DB등록)
	@RequestMapping(value="/dataWriteOk", method=RequestMethod.POST)
	public ModelAndView dataWriteOk(DataVO vo, HttpServletRequest request) {
		//DataVO에는 제목, 글내용이 request된다.
		//현재 서버위치의 절대경로 구하기				myapp			weapp까지구해짐
		String path = request.getSession().getServletContext().getRealPath("/upload");
		System.out.println("path->"+path);
		
		//파일업로드를 하기위해서는 request객체를 multipart객체로 형변환해줘야한다.
		MultipartHttpServletRequest mr =(MultipartHttpServletRequest)request;
		
		//mr객체에서 MultipartFile객체를 얻어와야 한다. 업로드한 파일갯수만큼 존재한다.
		//List라는 컬랙션에 MultipartFile 객체를 담아 리턴해준다.
		List<MultipartFile> files = mr.getFiles("filename");
		
		//첨부파일이 있을때
		if(files!=null) {//if1
			int cnt=0;
			//첨부파일수만큼 반복
			for(int i=0;i<files.size();i++){//i=0  for1
				//1. 파일정보를 얻어오기
				MultipartFile mf = files.get(i);
				//업로드한 실제파일명을 구하기
				String fname = mf.getOriginalFilename(); //test.txt > 현재 파일명과 확장자를 가지고 있다구
				System.out.println("originalFile->" + fname);
				
				
				//2. 파일명 rename
				if(fname!=null && !fname.equals("")) {//파일명이 존재하면
					File f = new File(path, fname);
					
					
					//이미 파일이 존재할 때
					if(f.exists()) {//있으면 true, 없으면 false
						for(int renameNum=1;;renameNum++) {
							//파일명과 확장자를 분리한다.
							int lastPoint = fname.lastIndexOf(".");
							String orgFilename = fname.substring(0,lastPoint); //파일명
							String orgExt = fname.substring(lastPoint+1);//확장자
							
							f = new File(path, orgFilename + "(" + renameNum + ")." + orgExt);
							if(!f.exists()) {//서버에 있는 파일인지 확인
								fname = f.getName();//새로운 파일명 test(1).txt
								break;
							}
																
						}//for
												
					}//if
					//3. 업로드실행
					try {
						mf.transferTo(f);
						
					}catch(Exception e) {}
					
					//첫번째 파일은 vo.filename1, 두번째 파일은 vo.filename2
					if(cnt==0) vo.setFilename1(fname);
					if(cnt==1) vo.setFilename2(fname);
					cnt++;
				}//if	
			}//for1
		}//if1
		
		//vo-> 제목, 글내용, 업로드한 파일명, userid
		//로그인 아이디 구하기
		HttpSession session = request.getSession();
		vo.setUserid((String)session.getAttribute("logId"));
		
		//DB에 레코드 추가
		int result = service.dataInsert(vo);
		ModelAndView mav = new ModelAndView();
		if(result>0) { //레코드가 추가되었을때
			mav.setViewName("redirect:data/list");
		}else { //레코드 추가 실패되었을때
			//추가 실패시 이미 업로도된 파일을 삭제해야 한다.
			try {	
				fileDelete(path, vo.getFilename1());
				fileDelete(path, vo.getFilename2());								
			}catch(Exception e) {}
			
			mav.addObject("errorMessage", "자료실글올리기");
			mav.setViewName("data/dataResult");
		}
		return mav;
		
	}
	
	//글내용보기
	@RequestMapping("/data/dataView")
	public ModelAndView dataView(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("dataVo", service.dataSelect(no));
		mav.setViewName("data/dataView");
		return mav;
	}
	//자료실 글삭제하기
	@RequestMapping("/data/dataDel")
	public ModelAndView dataDelete(int no, HttpServletRequest request) {
		//파일의 위치를 구하기
		String path = request.getSession().getServletContext().getRealPath("/upload");
		
		//레코드 삭제하기 전 DB의 filename을 보관하여야 한다. 레코드를 지우면 파일명이 없어지니..
		DataVO fileVo = service.fileSelect(no);
		
		//레코드 삭제
		int result = service.dataDelete(no);
		//이동하는 객체가 달라지니까 모델엔뷰 객체 생성~?
		ModelAndView mav = new ModelAndView();
		//삭제여부
		if(result>0){//삭제 성공한다면: 파일을 지워야함 fileVo의 파일을 삭제하고, 목록으로 이동해줘
			fileDelete(path, fileVo.getFilename1());
			fileDelete(path, fileVo.getFilename2());
			
			mav.setViewName("redirect:list");
			
		}else {	//삭제를 실패한다면: 글내용보기로 다시보내기
			mav.addObject("no", no);
			mav.setViewName("redirect:dataView");
		}
		return mav;
	}
	public void fileDelete(String path, String filename) {
		if(filename != null) {
			File f = new File(path, filename);
			f.delete();
		}
	}
	
	//자료실 글수정폼
	@RequestMapping("/data/dataEdit")
	public ModelAndView dataEdit(int no) {
		ModelAndView mav = new ModelAndView();
		DataVO vo = service.dataSelect(no);
		//이미 첨부된 파일의 갯수 구하기
		int fileCnt = 1; //첫번째 첨부파일은 반드시 있을것이기 때문에 1로 넣는거!
		if(vo.getFilename2()!=null) fileCnt++;
		
		mav.addObject("cnt", fileCnt);
		
		mav.addObject("vo", vo);
		mav.setViewName("data/dataEdit");
		return mav;
	}
	//자료실 글수정(DB)
	@RequestMapping(value="/data/dataEditOk", method=RequestMethod.POST)
	public ModelAndView dataEditOk(DataVO vo, HttpServletRequest request) {
		//파일위치
		String path = request.getSession().getServletContext().getRealPath("/upload");
		
		//세션에 로그인 사용자id
		vo.setUserid((String)request.getSession().getAttribute("logId"));
		
		//수정하기 전 DB의 파일이름 얻어오기
		DataVO dbFileVo = service.filenameSelect(vo.getNo());
		
		//원래 DB에 있는 파일명
		List<String> dbFile = new ArrayList<String>();
		dbFile.add(dbFileVo.getFilename1());
		
		if(dbFileVo.getFilename2()!=null) {
			dbFile.add(dbFileVo.getFilename2());
		}
		//삭제파일을 DB파일에서 제거
		if(vo.getDelFile()!=null) {//삭제 파일이 있으면
			for(String dFile: vo.getDelFile()) {
				dbFile.remove(dFile);
			}
		}
		for(String d:dbFile) {
		System.out.println("DBFile -->" + d);
		}
		
		//새로 업로드하는 파일이 있으면 업로드 실행
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
		
		//새로 첨부된 MultipartFile 파일 객체를 List로 구한다.
		List<MultipartFile> newFileList = mr.getFiles("filename");
		//새로업로드된 파일명을 보관하기 위한 컬렉션, 초기값을 세팅했다..
		List<String> newUpload = new ArrayList<String>();		
		if(newFileList!=null) {//새로업로드한 파일이 있으면 
			for(int i=0;i<newFileList.size();i++) {
				MultipartFile newMf = newFileList.get(i);
				String org_filename = newMf.getOriginalFilename();//원래파일구하기
				
				if(org_filename!=null && !org_filename.equals("")) {
					File f = new File(path, org_filename);
					if(f.exists()) {//파일이 존재하느냐, 있으면 true, 없으면 false
						//파일명변경
						for(int n=1;;n++) {//1,2,3,4,5 ...... 브레이크 걸때까지 끝없엉~~
							int point = org_filename.lastIndexOf(".");// .의 위치
							String fileNameNoExt = org_filename.substring(0,point);//파일명(확장자를 뺀)
							String fileExt = org_filename.substring(point+1);//확장자구하기
							//새로운 파일명이 존재하는지 확인
							String fn = fileNameNoExt + "(" + n + ")." + fileExt;
							f = new File(path, fn);
							if(!f.exists()) {
								org_filename = fn;
								break;
							}
						}//for
					}//if
					
					//업로드
					try {
					newMf.transferTo(f);
					}catch(Exception e) {}
					
					//새로업로드한 파일명을 dbFile객체에 추가한다.
					dbFile.add(org_filename);
					newUpload.add(org_filename);
				}
				
			}
		}//새로업로드된 파일
		
		//List컬렉션의 파일을 vo에 셋팅
		for(int ii=0; ii<dbFile.size(); ii++) {
			if(ii==0) vo.setFilename1(dbFile.get(ii));
			if(ii==1) vo.setFilename2(dbFile.get(ii));
		}
		
		// DB에 레코드 추가하고
		int result = service.dataUpdate(vo);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("no", vo.getNo());
		
		if(result>0){// 레코드가 수정 되면(성공하면)
			// 삭제된 파일이 있을때는 지운다..
			if(vo.getDelFile()!=null) {//지울 파일이 있으면
				for(String dFile:vo.getDelFile()){
					File ff = new File(path, dFile);
					ff.delete();
				}
			}
			//글내용보기로
			mav.setViewName("redirect:dataView");
		}else{// 수정실패
			// 새로 업로드된 파일이 삭제되어야한다.
			for(String newFile:newUpload) {
				File ff = new File(path, newFile);
				ff.delete();
			}
			// 수정폼으로 이동
			mav.setViewName("redirect:dataEdit");
		}
		////////////////////
		return mav;
	}
}

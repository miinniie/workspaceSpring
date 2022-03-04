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
	
	//�ڷ�� �� �ø��� ��
	@RequestMapping("/dataWrite")
	public String dataWrite() {
		return "data/dataForm";
	}
	//�ڷ�� �ۿø���(���Ͼ��ε�, DB���)
	@RequestMapping(value="/dataWriteOk", method=RequestMethod.POST)
	public ModelAndView dataWriteOk(DataVO vo, HttpServletRequest request) {
		//DataVO���� ����, �۳����� request�ȴ�.
		//���� ������ġ�� ������ ���ϱ�				myapp			weapp����������
		String path = request.getSession().getServletContext().getRealPath("/upload");
		System.out.println("path->"+path);
		
		//���Ͼ��ε带 �ϱ����ؼ��� request��ü�� multipart��ü�� ����ȯ������Ѵ�.
		MultipartHttpServletRequest mr =(MultipartHttpServletRequest)request;
		
		//mr��ü���� MultipartFile��ü�� ���;� �Ѵ�. ���ε��� ���ϰ�����ŭ �����Ѵ�.
		//List��� �÷��ǿ� MultipartFile ��ü�� ��� �������ش�.
		List<MultipartFile> files = mr.getFiles("filename");
		
		//÷�������� ������
		if(files!=null) {//if1
			int cnt=0;
			//÷�����ϼ���ŭ �ݺ�
			for(int i=0;i<files.size();i++){//i=0  for1
				//1. ���������� ������
				MultipartFile mf = files.get(i);
				//���ε��� �������ϸ��� ���ϱ�
				String fname = mf.getOriginalFilename(); //test.txt > ���� ���ϸ�� Ȯ���ڸ� ������ �ִٱ�
				System.out.println("originalFile->" + fname);
				
				
				//2. ���ϸ� rename
				if(fname!=null && !fname.equals("")) {//���ϸ��� �����ϸ�
					File f = new File(path, fname);
					
					
					//�̹� ������ ������ ��
					if(f.exists()) {//������ true, ������ false
						for(int renameNum=1;;renameNum++) {
							//���ϸ�� Ȯ���ڸ� �и��Ѵ�.
							int lastPoint = fname.lastIndexOf(".");
							String orgFilename = fname.substring(0,lastPoint); //���ϸ�
							String orgExt = fname.substring(lastPoint+1);//Ȯ����
							
							f = new File(path, orgFilename + "(" + renameNum + ")." + orgExt);
							if(!f.exists()) {//������ �ִ� �������� Ȯ��
								fname = f.getName();//���ο� ���ϸ� test(1).txt
								break;
							}
																
						}//for
												
					}//if
					//3. ���ε����
					try {
						mf.transferTo(f);
						
					}catch(Exception e) {}
					
					//ù��° ������ vo.filename1, �ι�° ������ vo.filename2
					if(cnt==0) vo.setFilename1(fname);
					if(cnt==1) vo.setFilename2(fname);
					cnt++;
				}//if	
			}//for1
		}//if1
		
		//vo-> ����, �۳���, ���ε��� ���ϸ�, userid
		//�α��� ���̵� ���ϱ�
		HttpSession session = request.getSession();
		vo.setUserid((String)session.getAttribute("logId"));
		
		//DB�� ���ڵ� �߰�
		int result = service.dataInsert(vo);
		ModelAndView mav = new ModelAndView();
		if(result>0) { //���ڵ尡 �߰��Ǿ�����
			mav.setViewName("redirect:data/list");
		}else { //���ڵ� �߰� ���еǾ�����
			//�߰� ���н� �̹� ���ε��� ������ �����ؾ� �Ѵ�.
			try {	
				fileDelete(path, vo.getFilename1());
				fileDelete(path, vo.getFilename2());								
			}catch(Exception e) {}
			
			mav.addObject("errorMessage", "�ڷ�Ǳۿø���");
			mav.setViewName("data/dataResult");
		}
		return mav;
		
	}
	
	//�۳��뺸��
	@RequestMapping("/data/dataView")
	public ModelAndView dataView(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("dataVo", service.dataSelect(no));
		mav.setViewName("data/dataView");
		return mav;
	}
	//�ڷ�� �ۻ����ϱ�
	@RequestMapping("/data/dataDel")
	public ModelAndView dataDelete(int no, HttpServletRequest request) {
		//������ ��ġ�� ���ϱ�
		String path = request.getSession().getServletContext().getRealPath("/upload");
		
		//���ڵ� �����ϱ� �� DB�� filename�� �����Ͽ��� �Ѵ�. ���ڵ带 ����� ���ϸ��� ��������..
		DataVO fileVo = service.fileSelect(no);
		
		//���ڵ� ����
		int result = service.dataDelete(no);
		//�̵��ϴ� ��ü�� �޶����ϱ� �𵨿��� ��ü ����~?
		ModelAndView mav = new ModelAndView();
		//��������
		if(result>0){//���� �����Ѵٸ�: ������ �������� fileVo�� ������ �����ϰ�, ������� �̵�����
			fileDelete(path, fileVo.getFilename1());
			fileDelete(path, fileVo.getFilename2());
			
			mav.setViewName("redirect:list");
			
		}else {	//������ �����Ѵٸ�: �۳��뺸��� �ٽú�����
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
	
	//�ڷ�� �ۼ�����
	@RequestMapping("/data/dataEdit")
	public ModelAndView dataEdit(int no) {
		ModelAndView mav = new ModelAndView();
		DataVO vo = service.dataSelect(no);
		//�̹� ÷�ε� ������ ���� ���ϱ�
		int fileCnt = 1; //ù��° ÷�������� �ݵ�� �������̱� ������ 1�� �ִ°�!
		if(vo.getFilename2()!=null) fileCnt++;
		
		mav.addObject("cnt", fileCnt);
		
		mav.addObject("vo", vo);
		mav.setViewName("data/dataEdit");
		return mav;
	}
	//�ڷ�� �ۼ���(DB)
	@RequestMapping(value="/data/dataEditOk", method=RequestMethod.POST)
	public ModelAndView dataEditOk(DataVO vo, HttpServletRequest request) {
		//������ġ
		String path = request.getSession().getServletContext().getRealPath("/upload");
		
		//���ǿ� �α��� �����id
		vo.setUserid((String)request.getSession().getAttribute("logId"));
		
		//�����ϱ� �� DB�� �����̸� ������
		DataVO dbFileVo = service.filenameSelect(vo.getNo());
		
		//���� DB�� �ִ� ���ϸ�
		List<String> dbFile = new ArrayList<String>();
		dbFile.add(dbFileVo.getFilename1());
		
		if(dbFileVo.getFilename2()!=null) {
			dbFile.add(dbFileVo.getFilename2());
		}
		//���������� DB���Ͽ��� ����
		if(vo.getDelFile()!=null) {//���� ������ ������
			for(String dFile: vo.getDelFile()) {
				dbFile.remove(dFile);
			}
		}
		for(String d:dbFile) {
		System.out.println("DBFile -->" + d);
		}
		
		//���� ���ε��ϴ� ������ ������ ���ε� ����
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
		
		//���� ÷�ε� MultipartFile ���� ��ü�� List�� ���Ѵ�.
		List<MultipartFile> newFileList = mr.getFiles("filename");
		//���ξ��ε�� ���ϸ��� �����ϱ� ���� �÷���, �ʱⰪ�� �����ߴ�..
		List<String> newUpload = new ArrayList<String>();		
		if(newFileList!=null) {//���ξ��ε��� ������ ������ 
			for(int i=0;i<newFileList.size();i++) {
				MultipartFile newMf = newFileList.get(i);
				String org_filename = newMf.getOriginalFilename();//�������ϱ��ϱ�
				
				if(org_filename!=null && !org_filename.equals("")) {
					File f = new File(path, org_filename);
					if(f.exists()) {//������ �����ϴ���, ������ true, ������ false
						//���ϸ���
						for(int n=1;;n++) {//1,2,3,4,5 ...... �극��ũ �ɶ����� ������~~
							int point = org_filename.lastIndexOf(".");// .�� ��ġ
							String fileNameNoExt = org_filename.substring(0,point);//���ϸ�(Ȯ���ڸ� ��)
							String fileExt = org_filename.substring(point+1);//Ȯ���ڱ��ϱ�
							//���ο� ���ϸ��� �����ϴ��� Ȯ��
							String fn = fileNameNoExt + "(" + n + ")." + fileExt;
							f = new File(path, fn);
							if(!f.exists()) {
								org_filename = fn;
								break;
							}
						}//for
					}//if
					
					//���ε�
					try {
					newMf.transferTo(f);
					}catch(Exception e) {}
					
					//���ξ��ε��� ���ϸ��� dbFile��ü�� �߰��Ѵ�.
					dbFile.add(org_filename);
					newUpload.add(org_filename);
				}
				
			}
		}//���ξ��ε�� ����
		
		//List�÷����� ������ vo�� ����
		for(int ii=0; ii<dbFile.size(); ii++) {
			if(ii==0) vo.setFilename1(dbFile.get(ii));
			if(ii==1) vo.setFilename2(dbFile.get(ii));
		}
		
		// DB�� ���ڵ� �߰��ϰ�
		int result = service.dataUpdate(vo);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("no", vo.getNo());
		
		if(result>0){// ���ڵ尡 ���� �Ǹ�(�����ϸ�)
			// ������ ������ �������� �����..
			if(vo.getDelFile()!=null) {//���� ������ ������
				for(String dFile:vo.getDelFile()){
					File ff = new File(path, dFile);
					ff.delete();
				}
			}
			//�۳��뺸���
			mav.setViewName("redirect:dataView");
		}else{// ��������
			// ���� ���ε�� ������ �����Ǿ���Ѵ�.
			for(String newFile:newUpload) {
				File ff = new File(path, newFile);
				ff.delete();
			}
			// ���������� �̵�
			mav.setViewName("redirect:dataEdit");
		}
		////////////////////
		return mav;
	}
}

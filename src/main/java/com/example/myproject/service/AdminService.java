package com.example.myproject.service;

import java.io.File;
import java.util.List;

import com.example.myproject.dao.AdminDao;
import com.example.myproject.model.*;
import jakarta.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;
	
	//@Value("${path.category}")
	private String path_category;
	
	@Resource(name = "adminBean")
	private AdminBean AdminloginBean;
	 
	@Resource(name = "portFolioBean")
	private PortFolioBean AdminAlarm;
  
	
	public void getLoginadminInfo(AdminBean adminTempLoginBean) {
		
		AdminBean AdminLoginBean2 = adminDao.getLoginadminInfo(adminTempLoginBean);
		if(AdminLoginBean2 != null) {
			AdminloginBean.setAdmin_id(AdminLoginBean2.getAdmin_id());
			AdminloginBean.setAdmin_pwd(AdminLoginBean2.getAdmin_pwd()); 
			AdminloginBean.setAdminLogin(true);
		}else {
		    System.out.println("로그인 사용자 정보가 없습니다.");
		}
		System.out.println("Service 현재 로그ㅡㅡ그으ㅡㅇ 관리자 이메일:"+AdminLoginBean2.getAdmin_id());
		System.out.println("Service 현재 로그ㅡㅡ그으ㅡㅇ 관리자 비번:"+AdminLoginBean2.getAdmin_pwd());  
	} 
	
	//모든 회원 정보 가져오기
	public List<UserBean> getAllUsers() {
		return  adminDao.getAllUsers();
	}
	
	public void deleteUser(int user_id) {
		adminDao.deleteUser(user_id);
	}
	
	//pro
	public List<ProUserBean> getAllUProUsers() {
		return adminDao.getAllUProUsers();
	}
	
	public void deleteProUser(int pro_id) {
		adminDao.deleteProUser(pro_id);
	}
	
	//board
	public List<PostBean> getAllUBoard() {
		return adminDao.getAllUBoard();
	}
	
	//작성자 이름 조회 
	public String getPostUserName(int board_id) {
        return adminDao.getPostUserName(board_id);
    }

    public String getPostProUserName(int board_id) {
        return adminDao.getPostProUserName(board_id);
    }

    //포트폴리오 조회 
    public  List<PortFolioBean> getAllPortfolio() { 
		return  adminDao.getAllPortfolio();
	}
    
    //포폴 이름 조회 
    public String getPortfolioName(int portfolio_id) {
        return adminDao.getPortfolioName(portfolio_id);
    }
    
    //포폴 검수 완료 
    public void CompPort(int portfolio_id) {
    	adminDao.CompPort(portfolio_id);
    }
    
    //포폴 삭제
    public void DelPortfolio(int portfolio_id) {
    	adminDao.DelPortfolio(portfolio_id);
    }
    
    // 관리자 게시글 삭제 
    public void deletePost(int board_id) {
    	adminDao.deletePost(board_id);
    }
    
    //카테고리 조회
    public List<DetailCategoryBean> getDetailList() {
    	
    	return adminDao.getDetailList();
    }
    
    //서비스 카테고리 이름 조회
    public List<ServiceCategoryBean> getServiceCategoryName() {
    	
    	return adminDao.getServiceCategoryName();
    }
    
  //저장하는 메소드
  	private String saveUploadFile(MultipartFile file_name) {
  		
  		//String file_name = System.currentTimeMillis() + "_" + upload_file.getOriginalFilename();

  		//경로 시스템오류시
  		String fileName = FilenameUtils.getBaseName(file_name.getOriginalFilename()) + "." + 
  				FilenameUtils.getExtension(file_name.getOriginalFilename());
  		
  		
  		try {
  			file_name.transferTo(new File(path_category + "/" + fileName));
  		}catch(Exception e) {
  			e.printStackTrace();
  		}
  		
  		return fileName;
  	}
    
  //카테고리 추가...
    public void addCategory(DetailCategoryBean addCategoryBean) {
    	
    	// 업로드된 파일들의 이름을 쉼표로 연결하여 설정
    	
    	MultipartFile upload_file = addCategoryBean.getUpload_file();

        // 각 파일을 업로드 경로에 저장
    	if(upload_file.getSize() > 0) {
			String fileName = saveUploadFile(upload_file);
			//System.out.println(file_name);
			addCategoryBean.setFile_name(fileName);
		}
        // DAO를 통해 카테고리 추가
        adminDao.addCategory(addCategoryBean);
    }
    //유저 게시글 카운트
   	public int getUserPostCnt(int user_id) {
   		 
   		 return adminDao.getUserPostCnt(user_id);
   		 
   	 }
   	
   	public int getProPostCnt(int pro_id) { 
   		 return adminDao.getProPostCnt(pro_id); 
   	 }
   	
 	//검수 요청 포트폴리오 정보
   	public List<PortFolioBean> getInspectionPortfolio(){
   		return adminDao.getInspectionPortfolio();
   	}
   	
   	//검수 요청 포트폴리오 개수
   	public int getCntInspectionPortfolio() {
   		return adminDao.getCntInspectionPortfolio();
   	}
    
  //게시글 삭제
    public void deletePostInfo(int board_id){
    	adminDao.deletePostInfo(board_id);
    }
   	
    //신고 누적 횟수
    public void updateUserReportCnt(int user_id) {
    	adminDao.updateUserReportCnt(user_id);
    }
    
    public void updateProuserReportCnt(int pro_id) {
    	adminDao.updateProuserReportCnt(pro_id);
    }
    
  //신고 개수
    public int getCntReport() {
    	return adminDao.getCntReport();
    }
    

    //신고 목록 불러오기
    public List<ReportBean> getReportList(){
    	return adminDao.getReportList();
    }
    
    //신고 반려
    public void deleteReportInfo(int board_id) {
    	adminDao.deleteReportInfo(board_id);
    }
}

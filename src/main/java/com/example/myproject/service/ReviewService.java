package com.example.myproject.service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import com.example.myproject.dao.ReviewDao;
import com.example.myproject.model.*;
import jakarta.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
//@PropertySource("/WEB-INF/properties/option.properties")
public class ReviewService {

	//@Value("${path.portfolio}")
	private String path_portfolio;
	
	@Autowired
	private ReviewDao reviewDao;
	
	@Resource(name = "userBean")
	private UserBean loginUserBean;
	
	public void addReviewInfo(ReviewBean writeReviewBean, List<MultipartFile> uploadFiles) {
		
		String fileNames = uploadFiles.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.joining(","));
		writeReviewBean.setPhotos(fileNames);
		
		for(MultipartFile uploadFile : uploadFiles) {
			   String photo_name = FilenameUtils.getBaseName(uploadFile.getOriginalFilename()) + "." + 
					   			FilenameUtils.getExtension(uploadFile.getOriginalFilename());
			   
			   try {
				uploadFile.transferTo(new File(path_portfolio + "/" + photo_name));
			} catch (Exception e) {
				e.printStackTrace();
			}
			  
		} 
		
		if(loginUserBean.isUserLogin()==true) { 
			writeReviewBean.setUser_id(loginUserBean.getUser_id()); 
		}
		System.out.println("리뷰 작성 아이디: " + writeReviewBean.getUser_id());
		
		reviewDao.addReviewInfo(writeReviewBean);
	}
	
	public List<ReviewBean> getAllReviewList(int pro_id){
		return reviewDao.getAllReviewList(pro_id);
	}
	
	public int getReviewCnt(int pro_id) {
		return reviewDao.getReviewCnt(pro_id);
	}
	
	public Float getAvgReview(int pro_id) {
		Float avgReview = reviewDao.getAvgReview(pro_id);
        return (avgReview != null) ? avgReview : 0.0f; // 기본값 설정
	}
	
	public void deleteReview(int review_id) {
		reviewDao.deleteReview(review_id);
	}
	
	public ProUserBean getProReviewInfo(int pro_Id) {
		return reviewDao.getProReviewInfo(pro_Id);
	}
	
	//---------------ajax 리뷰---------------------------
	
	public List<ReviewBean> getAllReviews(int pro_id){
		return reviewDao.getAllReviews(pro_id);
	}
	
	//------------------일류 프로필 정보--------------------
	
	public ProProfileBean getProProfileInfo(int pro_id) {
		return reviewDao.getProProfileInfo(pro_id);
	}
	
	//-------------------경력------------------------------
	
	public List<CareerBean> getCareerListInfo(int pro_id){
		return reviewDao.getCareerListInfo(pro_id);
	}
	
	public Integer getSumTotalExperience(int pro_id) {
		return reviewDao.getSumTotalExperience(pro_id);
	}
	
	//-------------------학력---------------------------------
	
	public List<EducationBean> getEducationListInfo(int pro_id){
		return reviewDao.getEducationListInfo(pro_id);
	}
	
	//-----------------포트폴리오--------------------------------
	public List<PortFolioBean> getProtfolioListInfo(int pro_id){
		return reviewDao.getProtfolioListInfo(pro_id);
	}
	

	// 리뷰 가져오기(detail_category)
	public List<ReviewBean> getReviwList(int service_category_id) {
		
		return reviewDao.getReviewList(service_category_id);
	}
	
	
	
}
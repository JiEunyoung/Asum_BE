package com.example.myproject.service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import com.example.myproject.dao.EducationDAO;
import com.example.myproject.model.EducationBean;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//@PropertySource("/WEB-INF/properties/option.properties")
@Service
public class EducationService {

	@Autowired
	EducationDAO educationDao;
	
	// @Value("${path.eduUpload}")
	 private String path_eduUpload;
	
	public void addEducationInfo(EducationBean postEducationBean, List<MultipartFile> eduUpload) {
		

		   String fileNames = eduUpload.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.joining(","));
		   postEducationBean.setEvidence_image(fileNames);
		   
		   for(MultipartFile eduUploads : eduUpload) {
			   String photo_name = FilenameUtils.getBaseName(eduUploads.getOriginalFilename()) + "." + 
					   			FilenameUtils.getExtension(eduUploads.getOriginalFilename());
			   
			   try {
				   eduUploads.transferTo(new File(path_eduUpload + "/" + photo_name));
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }
		   
		  educationDao.addEducationInfo(postEducationBean);
	}
	
	public List<EducationBean> getEducationList(int pro_id) {
		
		return educationDao.getEducationList(pro_id);
	}
	
	public EducationBean getEducationInfo(int education_id) {
		
		return educationDao.getEducationInfo(education_id);
	}
	
	public void modifyEducationInfo(EducationBean educationContentModifyBean, List<MultipartFile> eduUpload) {
		
		String fileNames = eduUpload.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.joining(","));
		educationContentModifyBean.setEvidence_image(fileNames);
		   
		   for(MultipartFile eduUploads : eduUpload) {
			   String photo_name = FilenameUtils.getBaseName(eduUploads.getOriginalFilename()) + "." + 
					   			FilenameUtils.getExtension(eduUploads.getOriginalFilename());
			   
			   try {
				   eduUploads.transferTo(new File(path_eduUpload + "/" + photo_name));
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }
		   
		educationDao.modifyEducationInfo(educationContentModifyBean);  
	}
	
	public void deleteEducation(int education_id) {
		
		educationDao.deleteEducation(education_id);
	}
}


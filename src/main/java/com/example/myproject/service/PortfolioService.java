package com.example.myproject.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.myproject.dao.ProPortfolioDAO;
import com.example.myproject.model.PortFolioBean;
import com.example.myproject.model.ProUserBean;
import jakarta.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
//@PropertySource("/WEB-INF/properties/option.properties")
public class PortfolioService {

	@Autowired
	private ProPortfolioDAO proPortfoliodao;

	//@Value("${path.portfolio}")
	private String path_portfolio;

	@Resource(name = "proUserBean")
	private ProUserBean loginProuserBean;

	public void addProPortfolioInfo(PortFolioBean ProPortfolio, List<MultipartFile> uploadFiles) {
		String fileNames = uploadFiles.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.joining(","));
		ProPortfolio.setDetailed_images(fileNames);

		for (MultipartFile uploadFile : uploadFiles) {
			String photo_name = FilenameUtils.getBaseName(uploadFile.getOriginalFilename()) + "."
					+ FilenameUtils.getExtension(uploadFile.getOriginalFilename());

			try {
				uploadFile.transferTo(new File(path_portfolio + "/" + photo_name));
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} 
		ProPortfolio.setPro_id(loginProuserBean.getPro_id());
		proPortfoliodao.addProPortfolioInfo(ProPortfolio);
		System.out.println("PortfolioService"+ProPortfolio.getService_type());
		System.out.println("PortfolioService"+ProPortfolio.getPortfolio_title());
		System.out.println("PortfolioService"+ProPortfolio.getWork_period());
	}

	
	public List<PortFolioBean> getPortfolioList(int pro_id){
		 return proPortfoliodao.getPortfolioList(pro_id);
	}
	
	public PortFolioBean getPortfolioIdList(int portfolio_id){
		return proPortfoliodao.getPortfolioIdList(portfolio_id);
	} 
	
	  public void modifyPortfolioInfo(PortFolioBean ProPortfoliomodify, List<MultipartFile> uploadFiles) { 
		   String fileNames = uploadFiles.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.joining(","));

		   ProPortfoliomodify.setDetailed_images(fileNames);
		   
		   for(MultipartFile uploadFile : uploadFiles) {
			   String photo_name = System.currentTimeMillis() + "_" +  
					      FilenameUtils.getBaseName(uploadFile.getOriginalFilename()) + "." + 
					            FilenameUtils.getExtension(uploadFile.getOriginalFilename());
			   try {
				uploadFile.transferTo(new File(path_portfolio+"/"+photo_name));
			} catch (Exception e) {
				e.printStackTrace();
			}
		   } 
		   
		   System.out.println("수정된 상세글"+ProPortfoliomodify.getDetailed_introduction());
		   proPortfoliodao.modifyPortfolioInfo(ProPortfoliomodify);
	   }
	  
	  
}

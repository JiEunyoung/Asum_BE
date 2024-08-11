package com.example.myproject.dao;

import java.util.List;

import com.example.myproject.mapper.PortFolioMapper;
import com.example.myproject.model.PortFolioBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProPortfolioDAO { 
	
	@Autowired
	PortFolioMapper portfoliMapper;
	 
	public void addProPortfolioInfo(PortFolioBean ProPortfolio){
		
		portfoliMapper.addProPortfolioInfo(ProPortfolio);
	}
	
	public List<PortFolioBean> getPortfolioList(int pro_id){
		List<PortFolioBean> PortfolioList = portfoliMapper.getPortfolioList(pro_id);
		
		return PortfolioList;
	}
	

	public PortFolioBean getPortfolioIdList(int portfolio_id){
		  
		return portfoliMapper.getPortfolioIdList(portfolio_id);
		 
	} 
	
	public void modifyPortfolioInfo(PortFolioBean ProPortfoliomodify) {
		portfoliMapper.modifyPortfolioInfo(ProPortfoliomodify);
	}
}

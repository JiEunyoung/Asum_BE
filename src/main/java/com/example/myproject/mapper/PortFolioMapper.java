package com.example.myproject.mapper;

import java.util.List;

import com.example.myproject.model.PortFolioBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
 
public interface PortFolioMapper {


	@Insert("INSERT INTO portfolio (portfolio_id, pro_id, service_type, portfolio_title, detailed_images, location_info, final_amount, work_year, work_period, detailed_introduction,inspectionNY)\r\n"
			+ "VALUES (portfolio_seq.nextval, #{pro_id}, #{service_type, jdbcType=VARCHAR}, #{portfolio_title, jdbcType=VARCHAR}, #{detailed_images, jdbcType=VARCHAR}, #{location_info, jdbcType=VARCHAR}, #{final_amount}, #{work_year, jdbcType=VARCHAR}, #{work_period, jdbcType=VARCHAR}, #{detailed_introduction, jdbcType=VARCHAR},#{inspectionNY})")
	void addProPortfolioInfo(PortFolioBean ProPortfolio);

	//일류 id를 통해 가져오는 포트폴리오 리스트 
	@Select("select * from portfolio where pro_id=#{pro_id}")
	List<PortFolioBean> getPortfolioList(int pro_id);

	//포트폴리오 아이디를 통해 가져오는 각 값
	@Select("select * from portfolio where portfolio_id=#{portfolio_id} order by portfolio_id asc")
	PortFolioBean getPortfolioIdList(int portfolio_id);
	 
	
	@Update("update portfolio "
			+ "set service_type=#{service_type, jdbcType=VARCHAR}, portfolio_title=#{portfolio_title, jdbcType=VARCHAR}, detailed_images=#{detailed_images, jdbcType=VARCHAR}, location_info=#{location_info, jdbcType=VARCHAR}, final_amount=#{final_amount, jdbcType=VARCHAR},"
			+ "work_year=#{work_year, jdbcType=VARCHAR}, work_period=#{work_period, jdbcType=VARCHAR}, detailed_introduction=#{detailed_introduction, jdbcType=VARCHAR} "  
			+ "where portfolio_id = #{portfolio_id}")
	void modifyPortfolioInfo(PortFolioBean ProPortfoliomodify);
}

package com.example.myproject.dao;

import java.util.List;

import com.example.myproject.mapper.InterestMapper;
import com.example.myproject.model.InterestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InterestDao {

	@Autowired
	private InterestMapper interestMapper;
	
	public void insertInterest(InterestBean interestBean) {
        interestMapper.insertInterest(interestBean);
    }

    public void deleteInterest(int user_id, int pro_id) {
        interestMapper.deleteInterest(user_id, pro_id);
    }

    public InterestBean findInterest(int user_id, int pro_id) {
        return interestMapper.findInterest(user_id, pro_id);
    }
    
    //찜한 일류.. 프로필
    public List<InterestBean> getProfileList(int user_id) {
    	
    	return interestMapper.getProfileList(user_id);
    }
    
    //찜한 일류.. 경력
    public List<InterestBean> getcareerList(int user_id) {
    	
    	return interestMapper.getcareerList(user_id);
    }
    
    //힘들어... 리뷰
    public List<InterestBean> getReviewList(int user_id) {
    	
    	return interestMapper.getReviewList(user_id);
    }
    
	
	//찜한 일류 삭제
    public void InterestDelete(int interest_id) {
	 
	 interestMapper.InterestDelete(interest_id); 
	 
	}
    
}
package com.example.myproject.service;

import java.util.List;

import com.example.myproject.dao.InterestDao;
import com.example.myproject.model.InterestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestService {

	@Autowired
	private InterestDao interestDao;
	
	public void toggleInterest(int user_id, int pro_id) {
        InterestBean existingInterest = interestDao.findInterest(user_id, pro_id);

        if (existingInterest == null) {
            InterestBean newInterest = new InterestBean();
            newInterest.setUser_id(user_id);
            newInterest.setPro_id(pro_id);
            interestDao.insertInterest(newInterest);
        } else {
            interestDao.deleteInterest(user_id, pro_id);
        }
    }
	//찜한 일류...프로필
		public List<InterestBean> getProfileList(int user_id) {
			
			return interestDao.getProfileList(user_id);
		}
	
	//찜한 일류.. 경력
		public List<InterestBean> getcareerList(int user_id) {
			
			return interestDao.getcareerList(user_id);
		}
		
		//리뷰...
		public List<InterestBean> getReviewList(int user_id) {
			
			return interestDao.getReviewList(user_id);
		}
		
		//찜한 일류 삭제
		public void InterestDelete(int interest_id) {
			
			interestDao.InterestDelete(interest_id);
		}
}
package com.example.myproject.service;

import java.util.List;

import com.example.myproject.dao.ProUserDao;
import com.example.myproject.model.ExpertBean;
import com.example.myproject.model.PageBean;
import com.example.myproject.model.ProUserBean;
import jakarta.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
//@PropertySource("/WEB-INF/properties/option.properties")
public class ProUserService {

	@Autowired
	private ProUserDao proUserDao;
	
	@Resource(name = "proUserBean")
	private ProUserBean loginProuserBean;
	
	@Value("${page.listcnt}")
	private int page_listcnt;
	
	@Value("${page.paginationcnt}")
	private int page_paginationcnt;
	

	
	public boolean checkProuserEmailExist(String pro_email) {
		System.out.println("ProUserService pro_email:"+pro_email);
		String pro_name = proUserDao.checkProuserEmailExist(pro_email);
		if(pro_name == null) {
			System.out.println("ProUserService pro_name:"+pro_name);
			return true;
		}else {
			return false;
		}	 
	}
	
	public void addProuserInfo(ProUserBean joinProuserBean) {
	    proUserDao.addProuserInfo(joinProuserBean);
	}
	

	public void getLoginProuserInfo(ProUserBean tempLoginUserBean2) {
		
		 ProUserBean tempLoginProuserBean3 = proUserDao.getLoginProuserInfo(tempLoginUserBean2); 
		 
		if(tempLoginProuserBean3 != null) {
			loginProuserBean.setPro_id(tempLoginProuserBean3.getPro_id());
			loginProuserBean.setPro_email(tempLoginProuserBean3.getPro_email());
			loginProuserBean.setPro_pwd(tempLoginProuserBean3.getPro_pwd());
			loginProuserBean.setPro_name(tempLoginProuserBean3.getPro_name());
			loginProuserBean.setProuserLogin(true);  
			 
			 
		}
		
		System.out.println("Service 현재 로그ㅡㅡ그으ㅡㅇ 일류 이메일:"+tempLoginProuserBean3.getPro_email());
		System.out.println("Service 현재 로그ㅡㅡ그으ㅡㅇ 일류 이름:"+tempLoginProuserBean3.getPro_name());
		System.out.println("Service 현재 로그ㅡㅡ그으ㅡㅇ 일류 번호:"+tempLoginProuserBean3.getPro_id());
		System.out.println("Service 현재 로그ㅡㅡ그으ㅡㅇ 일류 비밀번호:"+tempLoginProuserBean3.getPro_pwd());
		
	} 
	
	
	 public List<ExpertBean> getSearchProUserByName(String pro_name) {
	        return proUserDao.getSearchProUserByName(pro_name);
	    }  
	 //
    public ProUserBean getDetailCategoriesByName(String pro_name) { 
        return proUserDao.getDetailCategoriesByName(pro_name);
    }
    public String getProUserName(int pro_id) {
		
		return proUserDao.getProUserName(pro_id);
	}
    //
    public ProUserBean getCategoriesByProId(Long proId) { 
    	return proUserDao.getCategoriesByProId(proId);
    }
	 //
	 public List<String> getRecoProUserByName(String reco) {
		 return proUserDao.getRecoProUserByName(reco);
	 }
	 public String getCategory1(Long pro_id) {
	      
	      return proUserDao.getCategory1(pro_id);
	   }
	   
	public String getCategory2(Long pro_id) {
	      
	      return proUserDao.getCategory2(pro_id);
	   }

	public String getCategory3(Long pro_id) {
	   
	   return proUserDao.getCategory3(pro_id);
	}
	 public List<ProUserBean> getProUserByName(int page, int size) {

		 int offset = (page - 1) * size;
	        RowBounds rowBounds = new RowBounds(offset, size);
	        return proUserDao.getProUserByName(rowBounds);
	 }
	 
	 public PageBean getProCnt(int currentPage) {
			int content_cnt = proUserDao.getProCnt();
			
			PageBean pageBean = new PageBean(content_cnt, currentPage, page_listcnt, page_paginationcnt);
			
			return pageBean;
		}
	 
	 public List<ExpertBean> getProCategoryAndLocation(String selectedCategory,String active_location) {
		 System.out.println("ProUserService-getProCategoryAndLocation 호출!");
		 return proUserDao.getProCategoryAndLocation(selectedCategory,active_location);
	 }
	 
	 public List<ExpertBean> getselectedCategory(String selectedCategory) { 
		 System.out.println("ProUserService-getselectedCategory 호출!");
			return proUserDao.getselectedCategory(selectedCategory); 
		} 

	public List<ExpertBean> getselectedLocation(String active_location) { 
		 System.out.println("ProUserService-getselectedLocation 호출!");
		return proUserDao.getselectedLocation(active_location); 
	}  
	
	public void ProupdateAccountUser(ProUserBean ProAccountModifyCom)  {
		if(ProAccountModifyCom != null) {
			loginProuserBean.setPro_id(loginProuserBean.getPro_id());
			loginProuserBean.setPro_email(ProAccountModifyCom.getPro_email());
			loginProuserBean.setPro_pwd(ProAccountModifyCom.getPro_pwd());
			loginProuserBean.setPro_name(ProAccountModifyCom.getPro_name());
			loginProuserBean.setProuserLogin(true);
		} 
		
		System.out.println("============UserService-updateAccountUser수행========================");
		System.out.println("수정된 일류 아이디:"+ProAccountModifyCom.getPro_id());
		System.out.println("수정된 일류 이름:"+ProAccountModifyCom.getPro_email());
		System.out.println("수정된 일류 이메일:"+ProAccountModifyCom.getPro_pwd());
		System.out.println("수정된 일류 비밀번호:"+ProAccountModifyCom.getPro_name());
		System.out.println("========================================================================");

		proUserDao.ProupdateAccountUser(ProAccountModifyCom);
	}
	public int ProAccountResign(int pro_id) {
		return proUserDao.ProAccountResign(pro_id);
	}

	public List<ProUserBean> getProCategoryname(List<String> pro_name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//이름 수정
		public void modifyProName(String pro_name, int pro_id) {
			
			proUserDao.modifyProName(pro_name, pro_id);
		} 
		
		public String getCategory11(int pro_id) {
			
			return proUserDao.getCategory11(pro_id);
		}
		
		public String getCategory22(int pro_id) {
				
				return proUserDao.getCategory22(pro_id);
			}
		
		public String getCategory33(int pro_id) {
			
			return proUserDao.getCategory33(pro_id);
		}
		
		//카테고리 수정
		public void modifyCategory(String active_detailcategory1,int pro_id) {
			
			proUserDao.modifyCategory(active_detailcategory1,pro_id);
		}
		
		public void modifyCategory2(String active_detailcategory2,int pro_id) {
			
			proUserDao.modifyCategory2(active_detailcategory2,pro_id);
		}
		
		public void modifyCategory3(String active_detailcategory3,int pro_id) {
			
			proUserDao.modifyCategory3(active_detailcategory3,pro_id);
		}
		
		//카테고리 삭제
		public void deleteCategory1(int pro_id) {
			
			proUserDao.deleteCategory1(pro_id);
		}
		
		public void deleteCategory2(int pro_id) {
				
			proUserDao.deleteCategory2(pro_id);
		}
		
		public void deleteCategory3(int pro_id) {
			
			proUserDao.deleteCategory3(pro_id);
		}
		
		//일류 지역
		//ProUserService
	   public String getActive_location(int pro_id) {
	         
	         return proUserDao.getActive_location(pro_id);
	      }
	   
	   public void modifyActive_location(String active_location, int pro_id) {
	         
	         proUserDao.modifyActive_location(active_location, pro_id);
	      }
}


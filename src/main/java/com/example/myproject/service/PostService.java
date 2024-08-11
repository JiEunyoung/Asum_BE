package com.example.myproject.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.myproject.dao.PostDao;
import com.example.myproject.model.*;
import jakarta.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
//@PropertySource("/WEB-INF/properties/option.properties")
public class PostService {
   
   //@Value("${path.upload}")
   private String path_upload;
   
   @Value("${page.listcnt}")
   private int page_listcnt;
	
   @Value("${page.paginationcnt}")
   private int page_paginationcnt;
   
   @Autowired
   private PostDao postDao;
   
   @Resource(name = "userBean")
   private UserBean loginUserBean;
   
   @Resource(name = "proUserBean")
   private ProUserBean loginProuserBean;
   
   public void addBoardPostInfo(PostBean boardPostBean, List<MultipartFile> uploadFiles) {

	   String fileNames = uploadFiles.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.joining(","));
	   boardPostBean.setPhotos(fileNames);
	   
	   for(MultipartFile uploadFile : uploadFiles) {
		   String photo_name = FilenameUtils.getBaseName(uploadFile.getOriginalFilename()) + "." + 
				   			FilenameUtils.getExtension(uploadFile.getOriginalFilename());
		   
		   try {
			uploadFile.transferTo(new File(path_upload + "/" + photo_name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
	   }  
	   //유저가 0이면(프로 가입)
	   
	   if(loginProuserBean.isProuserLogin()==true) {

		   boardPostBean.setPro_id(loginProuserBean.getPro_id());
	   } 
	   else if(loginUserBean.isUserLogin()==true) { 
		   boardPostBean.setUser_id(loginUserBean.getUser_id()); 
	   }
       postDao.addBoardPostInfo(boardPostBean);
   }
   
   public List<PostBean> getAllPostList(int page){
	   
	   int start = (page - 1) * page_listcnt;
	   RowBounds rowBounds = new RowBounds(start, page_listcnt);
	   
	   return postDao.getAllPostList(rowBounds);
   }
   
   public PostBean getPostInfo(int board_id) {
	   return postDao.getPostInfo(board_id);
   }
   
   public void modifyPostInfo(PostBean modifyPostBean, List<MultipartFile> uploadFiles) { 
	   String fileNames = uploadFiles.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.joining(","));
	   modifyPostBean.setPhotos(fileNames);
	   
	   for(MultipartFile uploadFile : uploadFiles) {
		   String photo_name = FilenameUtils.getBaseName(uploadFile.getOriginalFilename()) + "." + 
				   			FilenameUtils.getExtension(uploadFile.getOriginalFilename());
		   
		   try {
			uploadFile.transferTo(new File(path_upload + "/" + photo_name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
	   }
	   
	   postDao.modifyPostInfo(modifyPostBean);
   }
   
   public void deletePostInfo(int board_id) {
	   postDao.deletePostInfo(board_id);
   }

   
   public void plusCnt(int board_id)
   {
	   postDao.plusCnt(board_id);
   }
   
 //내가 쓴 글들
   public List<PostBean> getMyPosts(Integer user_id, Integer pro_id, int page) {
	   
	   int start = (page - 1) * page_listcnt;
	   RowBounds rowBounds = new RowBounds(start, page_listcnt);
	   
	   return postDao.getMyPosts(user_id, pro_id, rowBounds);
   }
   
   //내가 쓴 글들 갯수
   	public PageBean getMyPostsPage(int currentPage, Integer user_id, Integer pro_id) {
	   
	   int contentCnt = postDao.getMyPostsCnt(user_id, pro_id);
	   
	   PageBean pageBean2 = new PageBean(contentCnt, currentPage, page_listcnt, page_paginationcnt);
	   
	   return pageBean2;
   }
   
   //---------------------신고-------------------
   
   public void addReportInfo(ReportBean writeReportBean) {
		/*
		 * if(loginProuserBean.isProuserLogin()==true) {
		 * 
		 * writeReportBean.setPro_id(loginProuserBean.getPro_id()); } else
		 * if(loginUserBean.isUserLogin()==true) {
		 * writeReportBean.setUser_id(loginUserBean.getUser_id()); }
		 */
       postDao.addReportInfo(writeReportBean);
   }
   
   //------------------------댓글------------------------
   
   public void addComment(CommentBean writeCommentBean) {
	   
	   if(loginProuserBean.isProuserLogin()==true) {

		   writeCommentBean.setPro_id(loginProuserBean.getPro_id());
	   } 
	   else if(loginUserBean.isUserLogin()==true) { 
		   writeCommentBean.setUser_id(loginUserBean.getUser_id()); 
	   }
       postDao.addComment(writeCommentBean);
   }
   
   public List<CommentBean> getAllComments(int board_id){
	   return postDao.getAllComments(board_id);
   }
   
   public int commentCnt(int board_id) {
	   return postDao.commentCnt(board_id);
   }
   
   public void modifyComment(CommentBean modifyCommentBean) {
	   postDao.modifyComment(modifyCommentBean);
   }
   
   public void deleteComment(int comment_id) {
		 postDao.deleteComment(comment_id);
	 }
   
   public int commentCntAtPost(int board_id) {
	   return postDao.commentCntAtPost(board_id);
   }
   
   //-------------페이징 처리---------------------
   
   public PageBean getPost(int currentPage) {
	   
	   int contentCnt = postDao.getPostCnt(); //게시글의 수 
	   
	   PageBean pageBean = new PageBean(contentCnt, currentPage, page_listcnt, page_paginationcnt);
	   
	   return pageBean;
   }
   
   //----------------검색-----------------------------
   
   public List<PostBean> getSearchedPostList(int page, String searchType, String searchText) {
       int start = (page - 1) * page_listcnt;
       RowBounds rowBounds = new RowBounds(start, page_listcnt);
       return postDao.getSearchedPostList(rowBounds, searchType, searchText);
   }
   
   public PageBean getSearchedPostPage(int currentPage, String searchType, String searchText) {
       int contentCnt = postDao.getSearchedPostCnt(searchType, searchText);
       PageBean pageBean = new PageBean(contentCnt, currentPage, page_listcnt, page_paginationcnt);
       return pageBean;
   }
   
   //내가 쓴 댓글
   public List<CommentBean> getMyComment(Integer user_id, Integer pro_id, int page) {
	   
	   int start = (page - 1) * page_listcnt;
	   RowBounds rowBounds = new RowBounds(start, page_listcnt);
	   
	   return postDao.getMyComment(user_id, pro_id, rowBounds);
   }
   
   //내가 쓴 댓글 갯수
   public PageBean getMyCommentPage(int currentPage, Integer user_id, Integer pro_id) {
	   
	   int contentCnt = postDao.getMyPostsCnt(user_id, pro_id);
	   PageBean pageBean3 = new PageBean(contentCnt, currentPage, page_listcnt, page_paginationcnt);
	   
	   return pageBean3;
   }
   
   
}
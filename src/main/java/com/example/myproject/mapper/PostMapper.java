package com.example.myproject.mapper;

import java.util.List;

import com.example.myproject.model.CommentBean;
import com.example.myproject.model.PostBean;
import com.example.myproject.model.ReportBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface PostMapper {
	
	@SelectKey(statement = "select board_seq.nextval from dual", keyProperty = "board_id", 
				before = true, resultType = int.class)
	@Insert("insert into board(board_id, user_id, pro_id, title, content, category, location, photos, ReportedPostSt, board_date, viewCnt) "
			+ "values(board_seq.nextval, #{user_id, jdbcType=INTEGER, javaType=Integer}, #{pro_id, jdbcType=INTEGER, javaType=Integer}, #{title}, #{content}, #{category}, #{location}, #{photos, jdbcType=VARCHAR}, #{ReportedPostSt}, sysdate, #{viewCnt})")
	void addBoardPostInfo(PostBean boardPostBean);
	
	@Select("select b.board_id, b.user_id, b.pro_id, b.category, b.location, b.title, b.content, b.photos, b.viewCnt, b.board_date, NVL(c.commentCnt,0) as commentCnt "
			+ "from board b, "
			+ "(select board_id, count(*) as commentCnt from comments "
			+ "group by board_id) c "
			+ "where b.board_id = c.board_id(+) "
			+ "order by b.board_id desc")
	List<PostBean> getAllPostList(RowBounds rowBounds);
	
	@Select("SELECT b.board_id, b.user_id, b.pro_id, u.user_name AS writer_name, pu.pro_name AS prowriter_name, b.title, b.content, b.category, b.location, "
	         + " b.viewCnt, b.board_date, b.photos "
	         + "FROM board b "
	         + "LEFT JOIN users u ON b.user_id = u.user_id "
	         + "LEFT JOIN pro_user pu ON b.pro_id = pu.pro_id "
	         + "WHERE b.board_id = #{b.board_id}")
	PostBean getPostInfo(int board_id);
	
	@Update("update board "
			+ "set title=#{title}, content=#{content}, category=#{category}, location=#{location}, photos=#{photos, jdbcType=VARCHAR} " 
			+ "where board_id = #{board_id}")
	void modifyPostInfo(PostBean modifyPostBean);
	
	@Delete("delete from board where board_id=#{board_id}")
	void deletePostInfo(int board_id);

	@Update("update board set viewcnt = viewcnt+1 where board_id=#{board_id}")
	void plusCnt(int board_id);
	
	@Select("select count(*) from board")
	int getPostCnt();
	
	//내가 쓴 글들
	@Select("select board_id, user_id, pro_id, title, board_date,content from board where (user_id = #{user_id, jdbcType=INTEGER} or pro_id = #{pro_id, jdbcType=INTEGER}) order by board_id desc")
	List<PostBean> getMyPosts(@Param("user_id") Integer user_id, @Param("pro_id") Integer pro_id, RowBounds rowBounds);
	
	//내가 쓴 글들 갯수
	@Select("select count(*) \r\n"
			+ "from board\r\n"
			+ "where (user_id = #{user_id, jdbcType=INTEGER} or pro_id = #{pro_id, jdbcType=INTEGER})")
	int getMyPostsCnt(@Param("user_id") Integer user_id, @Param("pro_id") Integer pro_id);
		
	
	//----------------신고-----------------------------
	
	@Insert("insert into report(report_id, board_id, report_state, report_msg, report_date) "
			+ "values(report_seq.nextval, #{board_id}, #{report_state}, #{report_msg}, sysdate)")
	void addReportInfo(ReportBean writeReportBean);
	
	//------------------댓글---------------------------------
	
	@Insert("insert into comments(comment_id, board_id, user_id, pro_id, comment_content, comment_date) "
			+ "values(comments_seq.nextval, #{board_id}, #{user_id, jdbcType=INTEGER, javaType=Integer}, #{pro_id, jdbcType=INTEGER, javaType=Integer}, #{comment_content}, sysdate)")
	void addComment(CommentBean writeCommentBean);
	
	@Select("SELECT c.board_id, c.user_id, c.pro_id, u.user_name as comment_writer_name, p.pro_name as comment_prowriter_name, "
	         + "pr.certification_documents_images, c.comment_id, c.comment_content, c.comment_date "
	         + "FROM comments c "
	         + "LEFT JOIN users u ON c.user_id = u.user_id "
	         + "LEFT JOIN pro_user p ON c.pro_id = p.pro_id "
	         + "LEFT JOIN pro_profile pr ON c.pro_id = pr.pro_id "
	         + "LEFT JOIN board b ON c.board_id = b.board_id "
	         + "WHERE b.board_id = #{board_id} "
	         + "order by comment_date")
	   List<CommentBean> getAllComments(int board_id);
	
	@Select("select count(*) from comments where board_id = #{board_id}")
	int commentCnt(int board_id);
	
	@Update("update comments "
			+ "set comment_content = #{comment_content} "
			+ "where comment_id = #{comment_id}")
	void modifyComment(CommentBean modifyCommentBean);
	
	@Delete("delete from comments where comment_id=#{comment_id}")
	void deleteComment(int comment_id);
	
	@Select("select count(*) from board b, comments c "
			+ "where b.board_id = c.board_id and b.board_id = #{board_id}")
	int commentCntAtPost(int board_id);
	
	//------------------검색---------------------------------
	
	@Select("SELECT b.board_id, b.user_id, b.pro_id, b.category, b.location, b.title, b.content, b.photos, b.viewCnt, b.board_date, NVL(c.commentCnt, 0) AS commentCnt " +
	        "FROM board b " +
	        "LEFT JOIN (SELECT board_id, COUNT(*) AS commentCnt FROM comments GROUP BY board_id) c ON b.board_id = c.board_id " +
	        "WHERE " +
	        "(#{searchType} IS NULL OR (#{searchType} = 'title' AND b.title LIKE '%' || #{searchText} || '%') OR " +
	        "(#{searchType} = 'content' AND b.content LIKE '%' || #{searchText} || '%') OR " +
	        "(#{searchType} = 'category' and b.category like '%' || #{searchText} || '%') OR " +
	        "(#{searchType} = 'location' and b.location like '%' || #{searchText} || '%')) " +
	        "ORDER BY b.board_id DESC")
	List<PostBean> getSearchedPostList(RowBounds rowBounds, @Param("searchType") String searchType, @Param("searchText") String searchText);

	@Select("SELECT COUNT(*) FROM board b " +
	        "LEFT JOIN (SELECT board_id, COUNT(*) AS commentCnt FROM comments GROUP BY board_id) c ON b.board_id = c.board_id " +
	        "WHERE " +
	        "(#{searchType} IS NULL OR (#{searchType} = 'title' AND b.title LIKE '%' || #{searchText} || '%') OR " +
	        "(#{searchType} = 'content' AND b.content LIKE '%' || #{searchText} || '%') OR " +
	        "(#{searchType} = 'category' and b.category like '%' || #{searchText} || '%') OR " +
	        "(#{searchType} = 'location' and b.location like '%' || #{searchText} || '%')) ")
	int getSearchedPostCnt(@Param("searchType") String searchType, @Param("searchText") String searchText);
	
	//내가 쓴 댓글
	@Select("SELECT c.comment_id, c.board_id, c.user_id, c.pro_id, c.comment_content, c.comment_date,\r\n"
			+ "       b.title as board_title\r\n"
			+ "FROM comments c\r\n"
			+ "JOIN board b ON c.board_id = b.board_id\r\n"
			+ "WHERE c.user_id = #{user_id, jdbcType=INTEGER}\r\n"
			+ "or c.pro_id = #{pro_id, jdbcType=INTEGER} order by comment_id desc ")
	List<CommentBean> getMyComment(@Param("user_id") Integer user_id, @Param("pro_id") Integer pro_id, RowBounds rowBounds);
	
	//내가 쓴 댓글 갯수
	@Select("select count(comment_id)\r\n"
			+ "from comments\r\n"
			+ "where (user_id = #{user_id, jdbcType=INTEGER} or pro_id = #{pro_id, jdbcType=INTEGER})")
	int getMyCommentCnt(@Param("user_id") Integer user_id, @Param("pro_id") Integer pro_id);
	


}
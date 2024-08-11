package com.example.myproject.mapper;

import java.util.List;

import com.example.myproject.model.ChatHistoryBean;
import com.example.myproject.model.ChatRoomBean;
import com.example.myproject.model.ChatRoomSelect;
import com.example.myproject.model.QuoteBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface ChatMapper {
	 // 기존 채팅방 ID 조회
    @Select("SELECT room_id FROM chatroom WHERE pro_id = #{pro_id} AND user_id = #{user_id}")
    Integer findChatroomIdByProIdAndUserId(@Param("pro_id") int proId, @Param("user_id") int userId);
    
    //프로가 채팅방 ID 조회
    @Select("SELECT room_id FROM chatroom WHERE pro_id = #{pro_id}")
    Integer findChatroomIdByProId(@Param("pro_id") int proId);
    
    //채팅문 생성
    @Insert("INSERT INTO chatroom (room_id ,pro_id, user_id, createdate) VALUES (chatroom_seq.NEXTVAL, #{pro_id}, #{user_id}, DEFAULT)")
    @SelectKey(statement = "select chatroom_seq.nextval from dual", keyProperty = "room_id", 
    before = true, resultType = int.class)
    void insertChatroom(ChatRoomBean chatroom);

	/*
	 * @Select("SELECT c.room_id, p.pro_name, c.pro_id " +
	 * "FROM chatroom c JOIN pro_user p ON c.pro_id = p.pro_id " +
	 * "WHERE c.user_id = #{user_id}")
	 */
    
    @Select("SELECT c.room_id, p.pro_name, c.pro_id,f.certification_documents_images,p.active_detailcategory1,p.active_detailcategory2,p.active_detailcategory3 "
    		+ "FROM chatroom c, pro_user p, pro_profile f "
    		+ "where c.pro_id = p.pro_id "
    		+ "and c.pro_id=f.pro_id "
    		+ "and c.user_id = #{user_id}")
    List<ChatRoomSelect> findChatroomsWithProNameByUserId(@Param("user_id") int userId);
    
    @Select("SELECT c.room_id, u.user_name, c.user_id " +
            "FROM chatroom c JOIN users u ON c.user_id = u.user_id " +
            "WHERE c.pro_id = #{pro_id}")
    List<ChatRoomSelect> findChatroomsWithUserNameByProId(@Param("pro_id") int proId);
    
    @Insert("INSERT INTO chatHistory(chat_id, room_id, content, senderId, chattime) VALUES(chatHistory_seq.NEXTVAL, #{room_id}, #{content}, #{senderId}, DEFAULT)")
    void insertChatHistory(ChatHistoryBean chatHistory);
    
    @Select("SELECT * FROM chatHistory WHERE room_id = #{room_id} ORDER BY chattime ASC")
    List<ChatHistoryBean> findChatHistoryByRoomId(@Param("room_id") int roomId);   
    
 //////////////////////////////////////////// 
    @Insert("insert into received_quote values(received_quote_seq.nextval, #{received_quote},#{pro_id},#{user_id},#{service_category_id})" )
    void insertReceiverQuote(QuoteBean quoteBean);
    
    @Select("Select *from received_quote")
    List<QuoteBean> receviedQuote();
    
    @Select("select user_name \r\n"
    		+ "from received_quote, users \r\n"
    		+ "where received_quote.user_id=users.user_id and quote_history_id=#{quote_history_id}")
    String getSendQuoteUsername(int quote_history_id);
    
    //견적서 삭제 
    @Delete("delete from received_quote where quote_history_id=#{quote_history_id}")
    int delQuote(int quote_history_id);
    
    //서비스카테고리 이름 
	   // chatService.getreceivedServiceCategory();
 
}
  
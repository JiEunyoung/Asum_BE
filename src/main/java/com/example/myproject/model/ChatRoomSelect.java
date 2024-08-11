package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ChatRoomSelect {

    private int room_id;
    private String pro_name; // 전문가 이름 필드
    private int pro_id;
    private int user_id;
    private String user_name;
    private String certification_documents_images;
    private String active_detailcategory1;
    private String active_detailcategory2;
    private String active_detailcategory3;

    public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	// 기본 생성자
    public ChatRoomSelect() {
    }

    // 모든 필드를 포함하는 생성자
    public ChatRoomSelect(int room_id, String pro_name) {
        this.room_id = room_id;
        this.pro_name = pro_name;
    }
    
}
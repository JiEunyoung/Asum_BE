package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Component
public class ExpertBean {

	private int pro_profile_id;
    private int pro_id;
    private String pro_profile_image;
    private int profile_completion;
    private int review_rating;
    private String activity_region;
    private int movable_distance;
    private String certification_documents_images;
    private String pro_detailed_introduction;
    private String price;
    private MultipartFile upload_file;

    private String pro_name;
    private int review_cnt;
    private int career_sum;
    
}
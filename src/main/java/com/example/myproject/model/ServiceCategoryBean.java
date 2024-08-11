package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ServiceCategoryBean {

	private int service_category_id;
    private String service_category_name;

}

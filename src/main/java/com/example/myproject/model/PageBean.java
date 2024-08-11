package com.example.myproject.model;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
//@Component
public class PageBean {

	private int min;
	private int max;
	private int prevPage;
	private int nextPage;
	private int pageCnt;
	private int currentPage;
	
	private String SearchType;
	private String SearchText;
	private String category;
	private String location;

	public PageBean(int contentCnt, int currentPage, int contentPageCnt, int paginationCnt) {
		
		this.currentPage = currentPage;
		
		pageCnt = contentCnt / contentPageCnt;

		if(contentCnt % contentPageCnt > 0) {
			pageCnt++;
		}

		
		min = ((currentPage - 1) / contentPageCnt) * contentPageCnt + 1;
		max = min + paginationCnt - 1;

		if(max > pageCnt) {
			max = pageCnt;
		}
		
		prevPage = min - 1;
		nextPage = max + 1;

		if(nextPage > pageCnt) {
			nextPage = pageCnt;
		}
	}
}
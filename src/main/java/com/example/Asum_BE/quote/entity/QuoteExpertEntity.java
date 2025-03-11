package com.example.Asum_BE.quote.entity;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteExpertEntity {

    private Long userId;
    private Long categoryId;
    private String categoryName;
    private Long quoteId;
    private Long expertId;
    private String expertName;
    private String location;
    private Character gender;
}

package com.example.Asum_BE.quote.event;

import com.example.Asum_BE.quote.entity.QuoteEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuoteSavedEvent {
    private final QuoteEntity entity;
    private final String content;
}

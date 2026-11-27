package com.feedbackflow.dto.response;

import com.feedbackflow.enums.Sentiment;
import com.feedbackflow.enums.FeedbackCategory;
import com.feedbackflow.enums.FeedbackStatus;

import java.time.LocalDateTime;

public record FeedbackResponse(
        Long id,
        String title,
        String content,
        Sentiment sentiment,
        FeedbackCategory category,
        FeedbackStatus status,
        Integer priority,
        Long workspaceId,
        LocalDateTime createdAt
) {
}
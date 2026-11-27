package com.feedbackflow.repository;

import com.feedbackflow.entity.Feedback;
import com.feedbackflow.enums.FeedbackStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository
        extends JpaRepository<Feedback, Long> {

    List<Feedback> findByWorkspaceId(Long workspaceId);

    List<Feedback> findByWorkspaceIdAndStatus(
            Long workspaceId,
            FeedbackStatus status
    );
}
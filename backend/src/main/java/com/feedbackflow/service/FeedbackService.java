package com.feedbackflow.service;

import com.feedbackflow.entity.Feedback;
import com.feedbackflow.entity.Workspace;
import com.feedbackflow.repository.FeedbackRepository;
import com.feedbackflow.repository.WorkspaceRepository;
import com.feedbackflow.dto.response.FeedbackResponse;
import com.feedbackflow.dto.request.CreateFeedbackRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final WorkspaceRepository workspaceRepository;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            WorkspaceRepository workspaceRepository
    ) {
        this.feedbackRepository = feedbackRepository;
        this.workspaceRepository = workspaceRepository;
    }

    @Transactional
public FeedbackResponse createFeedback(
        Long workspaceId,
        CreateFeedbackRequest request
) {

    Workspace workspace = workspaceRepository.findById(workspaceId)
            .orElseThrow(() ->
                    new RuntimeException("Workspace not found")
            );

    Feedback feedback = new Feedback();

    feedback.setTitle(request.getTitle());
    feedback.setContent(request.getContent());
    feedback.setWorkspace(workspace);

    Feedback savedFeedback = feedbackRepository.save(feedback);

    return mapToResponse(savedFeedback);
}

    @Transactional(readOnly = true)
    public List<Feedback> getFeedbackByWorkspace(Long workspaceId) {

        return feedbackRepository.findByWorkspaceId(workspaceId);
    }

    private FeedbackResponse mapToResponse(Feedback feedback) {

    return new FeedbackResponse(
            feedback.getId(),
            feedback.getTitle(),
            feedback.getContent(),
            feedback.getSentiment(),
            feedback.getCategory(),
            feedback.getStatus(),
            feedback.getPriority(),
            feedback.getWorkspace().getId(),
            feedback.getCreatedAt()
    );
}
}
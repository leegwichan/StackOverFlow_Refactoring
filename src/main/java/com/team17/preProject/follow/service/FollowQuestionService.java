package com.team17.preProject.follow.service;

import com.team17.preProject.follow.entity.FollowQuestion;
import org.springframework.data.domain.Page;

public interface FollowQuestionService {

    Page<FollowQuestion> findFollowQuestionsByMember(int page, int size, long memberId);
    FollowQuestion createFollowQuestion(long memberId, long questionId);
    void deleteFollowQuestion(long memberId, long questionId);
}

package com.team17.preProject.follow.service;

import com.team17.preProject.follow.entity.FollowAnswer;
import org.springframework.data.domain.Page;

public interface FollowAnswerService {

    Page<FollowAnswer> findFollowAnswerByMember(int page, int size, long memberId);
    FollowAnswer createFollowAnswer(long memberId, long answerId);
    void deleteFollowAnswer(long memberId, long answerId);
}

package com.team17.preProject.helper.stub;

import com.team17.preProject.domain.follow.entity.FollowAnswer;

public class FollowAnswerStub {

    public static FollowAnswer getEntity() {
        return FollowAnswer.builder()
                .member(MemberStub.getChangeableEntity())
                .answer(AnswerStub.getEntity())
                .build();
    }

    public static FollowAnswer getEntity(long id) {
        return FollowAnswer.builder()
                .faId(id)
                .member(MemberStub.getChangeableEntity())
                .answer(AnswerStub.getEntity())
                .build();
    }
}

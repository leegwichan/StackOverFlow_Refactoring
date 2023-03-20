package com.team17.preProject.helper.stub;

import com.team17.preProject.domain.follow.entity.FollowQuestion;

public class FollowQuestionStub {

    public static FollowQuestion getEntity(long id) {
        return FollowQuestion.builder()
                .fqId(id)
                .member(MemberStub.getChangeableEntity())
                .question(QuestionStub.getChangeableEntity())
                .build();
    }

    public static FollowQuestion getEntity() {
        return FollowQuestion.builder()
                .member(MemberStub.getChangeableEntity())
                .question(QuestionStub.getChangeableEntity())
                .build();
    }
}

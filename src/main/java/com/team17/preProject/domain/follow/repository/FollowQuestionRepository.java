package com.team17.preProject.domain.follow.repository;

import com.team17.preProject.domain.follow.entity.FollowQuestion;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowQuestionRepository extends JpaRepository<FollowQuestion, Long> {
    Page<FollowQuestion> findByMember(Member member, Pageable pageable);
    Optional<FollowQuestion> findByMemberAndQuestion(Member member, Question question);
}

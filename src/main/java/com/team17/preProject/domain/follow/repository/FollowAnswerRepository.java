package com.team17.preProject.domain.follow.repository;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.follow.entity.FollowAnswer;
import com.team17.preProject.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowAnswerRepository extends JpaRepository<FollowAnswer, Long> {
    Page<FollowAnswer> findByMember(Member member, Pageable pageable);
    Optional<FollowAnswer> findByMemberAndAnswer(Member member, Answer answer);
}

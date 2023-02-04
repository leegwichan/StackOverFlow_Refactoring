package com.team17.preProject.follow.repository;

import com.team17.preProject.answer.entity.Answer;
import com.team17.preProject.follow.entity.FollowAnswer;
import com.team17.preProject.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowAnswerRepository extends JpaRepository<FollowAnswer, Long> {
    Page<FollowAnswer> findByMember(Member member, Pageable pageable);
    Optional<FollowAnswer> findByMemberAndAnswer(Member member, Answer answer);
}

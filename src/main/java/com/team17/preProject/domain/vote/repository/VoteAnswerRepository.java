package com.team17.preProject.domain.vote.repository;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.vote.entity.VoteAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VoteAnswerRepository extends JpaRepository<VoteAnswer, Long> {
    Optional<VoteAnswer> findByMemberAndAnswer(Member member, Answer answer);
}

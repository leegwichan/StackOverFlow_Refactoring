package com.team17.preProject.vote.repository;

import com.team17.preProject.answer.entity.Answer;
import com.team17.preProject.member.entity.Member;
import com.team17.preProject.vote.entity.VoteAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteAnswerRepository extends JpaRepository<VoteAnswer, Long> {
    VoteAnswer findByMemberAndAnswer(Member member, Answer answer);
}

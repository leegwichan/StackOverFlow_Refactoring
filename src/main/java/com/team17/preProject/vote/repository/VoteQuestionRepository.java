package com.team17.preProject.vote.repository;

import com.team17.preProject.member.entity.Member;
import com.team17.preProject.question.entity.Question;
import com.team17.preProject.vote.entity.VoteQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteQuestionRepository extends JpaRepository<VoteQuestion, Long> {
    VoteQuestion findByMemberAndQuestion(Member member, Question question);
}

package com.team17.preProject.domain.vote.repository;

import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.vote.entity.VoteQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VoteQuestionRepository extends JpaRepository<VoteQuestion, Long> {
    Optional<VoteQuestion> findByMemberAndQuestion(Member member, Question question);
}

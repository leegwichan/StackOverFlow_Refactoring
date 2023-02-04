package com.team17.preProject.answer.repository;

import com.team17.preProject.answer.entity.Answer;
import com.team17.preProject.member.entity.Member;
import com.team17.preProject.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestion(Question question);
    Page<Answer> findByMember(Member member, Pageable pageable);
}

package com.team17.preProject.question.repository;

import com.team17.preProject.member.entity.Member;
import com.team17.preProject.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByTitleContaining(String title, Pageable pageable);
    Page<Question> findByMember(Member member, Pageable pageable);
}

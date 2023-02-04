package com.team17.preProject.member.entity;

import com.team17.preProject.answer.entity.Answer;
import com.team17.preProject.audit.Auditable;
import com.team17.preProject.follow.entity.FollowAnswer;
import com.team17.preProject.follow.entity.FollowQuestion;
import com.team17.preProject.question.entity.Question;
import com.team17.preProject.vote.entity.VoteAnswer;
import com.team17.preProject.vote.entity.VoteQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 30)
    private String displayName;

    @Column(length = 1000)
    private String image;

    @Column(length = 100)
    private String location;

    @Column(length = 100)
    private String memberTitle;

    @Column(length = 1000)
    private String aboutMe;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.ROLE_USER;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private List<Question> postQuestions = new ArrayList<>();

    // TODO : 회원 삭제시 어떻게 Answer를 처리할지 논의
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private List<Answer> postAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<FollowQuestion> followQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<FollowAnswer> followAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private List<VoteQuestion> voteQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private List<VoteAnswer> voteAnswers = new ArrayList<>();

}

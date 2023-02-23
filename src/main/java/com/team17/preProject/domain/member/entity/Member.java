package com.team17.preProject.domain.member.entity;

import com.team17.preProject.domain.answer.entity.Answer;
import com.team17.preProject.domain.audit.Auditable;
import com.team17.preProject.domain.follow.entity.FollowAnswer;
import com.team17.preProject.domain.follow.entity.FollowQuestion;
import com.team17.preProject.domain.question.entity.Question;
import com.team17.preProject.domain.vote.entity.VoteAnswer;
import com.team17.preProject.domain.vote.entity.VoteQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
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
    @Builder.Default
    private MemberRole role = MemberRole.ROLE_USER;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Question> postQuestions = new ArrayList<>();

    // TODO : 회원 삭제시 어떻게 Answer를 처리할지 논의
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Answer> postAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    @Builder.Default
    private List<FollowQuestion> followQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    @Builder.Default
    private List<FollowAnswer> followAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<VoteQuestion> voteQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<VoteAnswer> voteAnswers = new ArrayList<>();


    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateMember(Member member) {
        this.image = Optional.ofNullable(member.image).orElse(this.image);
        this.displayName = Optional.ofNullable(member.displayName).orElse(this.displayName);
        this.location = Optional.ofNullable(member.location).orElse(this.location);
        this.memberTitle = Optional.ofNullable(member.memberTitle).orElse(this.memberTitle);
        this.aboutMe = Optional.ofNullable(member.aboutMe).orElse(this.aboutMe);
    }
}

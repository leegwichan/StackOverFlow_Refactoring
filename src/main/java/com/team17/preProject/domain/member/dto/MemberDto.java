package com.team17.preProject.domain.member.dto;

import lombok.*;

import javax.validation.constraints.*;

@Getter
public class MemberDto {

    @AllArgsConstructor
    @Getter
    @Setter
    public static class SubResponse{
        private long memberId;
        private String email;
        private String displayName;
        private String image;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Response{
        private long memberId;
        private String email;
        private String displayName;
        private String image;
        private String location;
        private String memberTitle;
        private String aboutMe;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch{
        private long memberId;

        @Size(max = 30, message = "display 의 최대 글자는 30자 입니다.")
        private String displayName;

        @Size(max = 1000, message = "이미지 url 주소의 길이는 최대 1000자 입니다.")
        private String image;

        @Size(max = 100, message = "location은 최대 100자 입니다.")
        private String location;

        @Size(max = 100, message = "title은 최대 100자 입니다.")
        private String memberTitle;

        @Size(max = 1000, message = "aboutMe는 최대 1000자 입니다.")
        private String aboutMe;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Post{

        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;


        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).{8,15}$",
                message = "비밀 번호는 8-15자 문자, 숫자, 특수기호(!@#$%^&+=)를 포함하여햐 합니다.")
        private String password;

        @Size(max = 20, message = "display 의 최대 글자는 20자 입니다.")
        @NotBlank(message = "displayName은 공백이 아니어야 합니다.")
        private String displayName;
    }
}

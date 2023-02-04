package com.team17.preProject.security.auth;

import com.team17.preProject.member.entity.Member;
import com.team17.preProject.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member memberEntity = memberRepository.findByEmail(email);

        if (memberEntity != null){
            return new PrincipalDetails(memberEntity);
        }
        return null;
    }
}

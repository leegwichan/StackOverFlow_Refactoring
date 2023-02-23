package com.team17.preProject.security.auth;

import com.team17.preProject.domain.member.entity.Member;
import com.team17.preProject.domain.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> memberEntity = memberRepository.findByEmail(email);

        if (!memberEntity.isEmpty()){
            return new PrincipalDetails(memberEntity.get());
        }
        return null;
    }
}

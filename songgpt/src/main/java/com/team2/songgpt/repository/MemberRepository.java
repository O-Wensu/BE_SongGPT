package com.team2.songgpt.repository;

import com.team2.songgpt.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
}

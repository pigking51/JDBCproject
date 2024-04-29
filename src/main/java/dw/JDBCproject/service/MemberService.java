package dw.JDBCproject.service;

import dw.JDBCproject.model.Member;
import dw.JDBCproject.repository.JdbcMemberRepository;
import dw.JDBCproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository; // 약한결합
    // 아마 repository에 있는거 다 입력하고 실행하면 오류가 뜰 건데 그건 구현체(repository에 있는 class)가 
    // 2개가 있어 생기는 오류
    // 그럴경우 여기 부분 바꾸지 않고 우선적으로 사용할 구현체에 @Primary라는 어노테이션 붙이면 됨
    
    public Member saveMember(Member member){
        return memberRepository.save(member);
    }

    public List<Member> findAllMembers(){
        return memberRepository.findAll();
    }
}

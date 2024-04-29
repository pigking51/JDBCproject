package dw.JDBCproject.controller;

import dw.JDBCproject.model.Member;
import dw.JDBCproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/member/new")
    public ResponseEntity<Member> saveMember(@RequestBody Member member){
            return ResponseEntity.ok(memberService.saveMember(member));
            // → 일종의 함수형 프로그램
            // ↓ 아래꺼랑 같은것임
//          return new ResponseEntity<>(memberService.saveMember(member),
//                HttpStatus.OK);
        
    }

}

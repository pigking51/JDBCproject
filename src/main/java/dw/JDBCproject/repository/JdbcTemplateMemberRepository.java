package dw.JDBCproject.repository;

import dw.JDBCproject.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JdbcTemplateMemberRepository implements MemberRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Member save(Member member) {
//        jdbcTemplate.update("insert into members(name) values(?)",
//                member.getName());
//        return member;
//        // ↑ GetMapping에만 적합하고 나머지에는 별로인 답안

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into members(name) values(?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, member.getName());
            return ps;
        }, keyHolder); // 쓴 녀석을 다시 돌려달라는 식(첫번째 변수), 받은 키 값을 저장(두번째 변수)
        member.setId(keyHolder.getKey().longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return jdbcTemplate
                .query("select * from members where id = ?"
                        ,memberRowMapper(), id)
                .stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return jdbcTemplate
                .query("select * from members where name = ?"
                        ,memberRowMapper(), name)
                .stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from members",
                memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong(1));
            member.setName(rs.getString(2));
            return member;
        };
    }
}

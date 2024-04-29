package dw.JDBCproject.repository;

import dw.JDBCproject.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemberRepository implements MemberRepository {
    @Autowired
    DataSource dataSource;

    @Override
    public Member save(Member member) {
        String sql = "insert into members(name) values(?)";
        // ↓ SQL 패키지내의 클래스 정의(여기서 null은 초기화임)
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
             conn = DataSourceUtils.getConnection(dataSource);
             pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, member.getName());
             // → sql에 만들었던 members 테이블에 입력한 내용을 넣으라는 뜻
             pstmt.executeUpdate();
             rs = pstmt.getGeneratedKeys();
             if(rs.next()){ 
                 member.setId(rs.getLong(1)); // 1번째의 row를 읽어오는것을 말함
             }else{
                 throw new SQLException("ID 조회실패");
             }

             return member;
        }catch (Exception e) {
            // 예외처리
            throw new IllegalStateException(e);
        } finally {
            // 연결종료
            close(conn, pstmt, rs);
        }

    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from members";
        // ↓ SQL 패키지내의 클래스 정의(여기서 null은 초기화임)
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DataSourceUtils.getConnection(dataSource);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery(); // rs를
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong(1));
                member.setName(rs.getString(2));
                members.add(member);
            }
            return members;
        }catch (Exception e) {
            // 예외처리
            throw new IllegalStateException(e);
        } finally {
            // 연결종료
            close(conn, pstmt, rs);
        }
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
        // 열 때와 반대로 닫음(rs → pstmt → conn)
        try{
            if(rs != null){
                rs.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(pstmt != null){
                pstmt.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(conn != null){
                conn.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}

package dw.JDBCproject.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// 엔티티는 쓰지않음(엔티티는 JPA로 만드는 것이기 때문)
// → JDBC로 만들게 되면 테이블을 직접 만들어줘야됨
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private long id;
    private String name;
}

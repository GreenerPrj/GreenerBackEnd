package BitProject.Greener.domain.members;

import static javax.persistence.GenerationType.IDENTITY;

import BitProject.Greener.common.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Members extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private LocalDateTime birthday;

    @Column(nullable = false)
    private String nickName;

}

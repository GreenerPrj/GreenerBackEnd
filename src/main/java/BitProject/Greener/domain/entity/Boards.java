package BitProject.Greener.domain.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import BitProject.Greener.common.BaseEntity;

import javax.persistence.*;

import BitProject.Greener.common.BoardsType;
import lombok.Getter;
import org.springframework.data.util.Lazy;

@Entity
@Getter
public class Boards extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "members_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


    @Enumerated(STRING)
    private BoardsType boardsType;


    public static Boards of(String title,String content, String nickName ,BoardsType boardsType){
        Boards instance = new Boards();
        instance.title = title;
        instance.content = content;
        instance.boardsType = boardsType;
        instance.nickName = nickName;
        return instance;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public void mapMembers(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

}

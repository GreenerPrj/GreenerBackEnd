package BitProject.Greener.domain.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import BitProject.Greener.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import BitProject.Greener.common.BoardsType;
import lombok.Getter;

@Entity
@Getter
public class Boards extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "members_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String imagePath;

    @Enumerated(STRING)
    private BoardsType boardsType;

    public static Boards of(String title,String content, String imagePath, BoardsType boardsType){
        Boards instance = new Boards();
        instance.title = title;
        instance.content = content;
        instance.imagePath = imagePath;
        instance.boardsType = boardsType;
        return instance;
    }

    public void update(String title, String imagePath, String content) {
        this.title = title;
        this.imagePath = imagePath;
        this.content = content;
    }
    public void mapMembers(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

}

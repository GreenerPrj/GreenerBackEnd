package BitProject.Greener.domain.members.config.boards;

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

import BitProject.Greener.domain.members.Domain.Entity.UserEntity;
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
    private UserEntity members;

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
    public void mapMembers(UserEntity members) {
        this.members = members;
    }

}

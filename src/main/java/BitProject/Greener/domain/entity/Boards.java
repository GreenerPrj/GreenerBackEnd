package BitProject.Greener.domain.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import BitProject.Greener.common.BaseEntity;

import javax.persistence.*;

import lombok.Getter;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "boards_category_id")
    private BoardsCategory category;

    private void mappingCategory(BoardsCategory boardsCategory){
        this.category = boardsCategory;
        boardsCategory.mappingPost(this);
    }

    public static Boards of(String title,String content, String nickName, BoardsCategory category){
        Boards instance = new Boards();
        instance.title = title;
        instance.content = content;
        instance.nickName = nickName;
        instance.mappingCategory(category);
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

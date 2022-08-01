package BitProject.Greener.domain.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import BitProject.Greener.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;

@Entity
@Getter
public class Comments extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "boards_id", referencedColumnName = "id")
    private Boards boards;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "members_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comments_id")
    private Comments parentComments;

    @Column(nullable = false)
    private String content;

    public static Comments of(String content) {
        Comments instance = new Comments();
        instance.content = content;
        return instance;
    }

    public void update(String content) {
        this.content = content;
    }

    public void mapMembersAndBoards(UserEntity userEntity, Boards boards) {
        this.userEntity = userEntity;
        this.boards = boards;
    }

    public void mapMembersAndBoardsAndParentComments(UserEntity userEntity, Boards boards, Comments parentComments){
        this.userEntity = userEntity;
        this.boards = boards;
        this.parentComments=parentComments;
    }

}
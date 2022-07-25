package BitProject.Greener.domain.boards;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import BitProject.Greener.common.BaseEntity;
import BitProject.Greener.domain.members.Members;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import BitProject.Greener.domain.plants.Plants;
import lombok.Getter;

import java.lang.reflect.Member;

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
    private Members members;

    @Column(nullable = false)
    private String content;

    public static Comments of(String content) {
        Comments instance = new Comments();
        instance.content = content;
        return instance;
    }
    public void mapMembersAndBoards(Members members, Boards boards) {
        this.members = members;
        this.boards = boards;
    }

}

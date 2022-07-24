package BitProject.Greener.domain.boards;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import BitProject.Greener.common.BaseEntity;
import BitProject.Greener.domain.members.Members;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Boards extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "members_id", referencedColumnName = "id")
    private Members members;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String imagePath;

    @Enumerated(STRING)
    private BoardType boardType;

    public static Boards of(String title,String content, String imagePath){
        Boards instance = new Boards();
        instance.title = title;
        instance.content = content;
        instance.imagePath = imagePath;
        return instance;
    }
    public void mapMembers(Members members) {
        this.members = members;
    }

}

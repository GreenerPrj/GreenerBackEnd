package BitProject.Greener.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;
import BitProject.Greener.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class BoardFiles extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boards_id", referencedColumnName = "id")
    private Boards boards;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    public static BoardFiles of(String originFileName, String fileName, String filePath){
        BoardFiles instance = new BoardFiles();
        instance.originFileName = originFileName;
        instance.fileName = fileName;
        instance.filePath = filePath;
        return instance;
    }
    public void mapBoards(Boards boards){
        this.boards = boards;
    }

}

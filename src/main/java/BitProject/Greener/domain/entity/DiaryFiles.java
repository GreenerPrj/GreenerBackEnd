package BitProject.Greener.domain.entity;

import BitProject.Greener.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class DiaryFiles extends BaseEntity {
    
    @GeneratedValue(strategy = IDENTITY)
    @Id
    @Column(nullable = false)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", referencedColumnName = "id")
    private Diary diary;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;
    
    public static DiaryFiles of(String originFileName, String fileName, String filePath){
        DiaryFiles instance = new DiaryFiles();
        instance.originFileName = originFileName;
        instance.fileName = fileName;
        instance.filePath = filePath;
        return instance;
    }
    public void mapDiary(Diary diary) {this.diary = diary;}
}

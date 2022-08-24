package BitProject.Greener.domain.entity;

import BitProject.Greener.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class MyPlantsFiles extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myPlants_id", referencedColumnName = "id")
    private MyPlants myPlants;

    @Column(nullable = true)
    private String originFileName;

    @Column(nullable = true)
    private String fileName;

    @Column(nullable = true)
    private String filePath;

    public static MyPlantsFiles of(String originFileName, String fileName, String filePath) {
        MyPlantsFiles instance = new MyPlantsFiles();
        instance.originFileName = originFileName;
        instance.fileName = fileName;
        instance.filePath = filePath;
        return instance;
    }

    public void mapMyPlants(MyPlants myPlants) { this.myPlants = myPlants; }



}

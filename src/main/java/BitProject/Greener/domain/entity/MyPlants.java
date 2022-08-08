package BitProject.Greener.domain.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import BitProject.Greener.common.BaseEntity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class MyPlants extends BaseEntity {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plants_id", referencedColumnName = "id")
    private Plants plants;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "members_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String bornDate;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;


    public static MyPlants of(String name, String bornDate,String originFileName, String fileName, String filePath){
        MyPlants instance = new MyPlants();
        instance.name = name;
        instance.bornDate = bornDate;
        instance.originFileName = originFileName;
        instance.fileName = fileName;
        instance.filePath = filePath;
        return instance;
    }

    public void update(String name, String bornDate, String imagePath) {
        this.name = name;
        this.bornDate = bornDate;
        this.imagePath = imagePath;
    }

    public void mapMembersAndPlants(UserEntity userEntity, Plants plants){
        this.userEntity = userEntity;
        this.plants = plants;
    }

}

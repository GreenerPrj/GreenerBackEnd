package BitProject.Greener.domain.plants;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import BitProject.Greener.common.BaseEntity;
import BitProject.Greener.domain.members.Members;
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
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plants_id", referencedColumnName = "id")
    private Plants plants;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "members_id", referencedColumnName = "id")
    private Members members;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime bornDate;

    @Column(nullable = false)
    private String imagePath;


    public static MyPlants of(String name, LocalDateTime bornDate, String imagePath){
        MyPlants instance = new MyPlants();
        instance.name = name;
        instance.bornDate = bornDate;
        instance.imagePath = imagePath;
        return instance;
    }

    public void mapMembersAndPlants(Members members, Plants plants){
        this.members = members;
        this.plants = plants;
    }

}

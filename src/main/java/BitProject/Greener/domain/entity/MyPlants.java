package BitProject.Greener.domain.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
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
public class MyPlants{
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plants_id", referencedColumnName = "id")
    private Plants plants;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "members_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private LocalDateTime bornDate;







    public static MyPlants of(String name, LocalDateTime bornDate){
        MyPlants instance = new MyPlants();
        instance.name = name;
        instance.bornDate = bornDate;
        return instance;
    }

    public void update(String name, LocalDateTime bornDate) {
        this.name = name;
        this.bornDate = bornDate;
    }

    public void mapMembersAndPlants(UserEntity userEntity, Plants plants){
        this.userEntity = userEntity;
        this.plants = plants;
    }

}

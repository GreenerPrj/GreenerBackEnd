package BitProject.Greener.domain.plants;

import static javax.persistence.GenerationType.IDENTITY;

import BitProject.Greener.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Plants extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer sunShine;

    @Column(nullable = false)
    private Integer water;

    @Column(nullable = false)
    private Integer temperature;

    public Plants create(String name,String content, Integer sunshine, Integer water, Integer temperature){
        this.name =name;
        this.content = content;
        this.sunShine = sunshine;
        this.temperature = temperature;
        this.water = water;

        return this;
    }





}

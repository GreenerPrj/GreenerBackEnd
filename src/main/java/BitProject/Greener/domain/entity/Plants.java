package BitProject.Greener.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Plants{

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    private String imgUrl;
    private String temperature;
    private String sunshine;
    private String place;
    private String tip;
    private String springWater;
    private String summerWater;
    private String fallWater;
    private String winterWater;

}

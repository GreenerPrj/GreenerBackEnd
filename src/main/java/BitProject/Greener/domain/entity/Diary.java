package BitProject.Greener.domain.entity;

import BitProject.Greener.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class Diary extends BaseEntity {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "myPlants_id", referencedColumnName = "id")
    private MyPlants myPlants;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "members_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Column(nullable = false)
    private String content;
    @Column(nullable = true)
    private LocalDateTime createDateTime;

    public static Diary of(String content, LocalDateTime createDateTime) {
        Diary instance = new Diary();
        instance.content = content;
        instance.createDateTime();
        return instance;
    }

    public void mapMembersAndMyPlants(UserEntity userEntity, MyPlants myPlants) {
        this.userEntity = userEntity;
        this.myPlants = myPlants;
    }

    public void update(String content) {
        this.content = content;
    }
}

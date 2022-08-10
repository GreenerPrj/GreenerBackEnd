package BitProject.Greener.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardsCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String value;

    @OneToMany(mappedBy = "category")
    private List<Boards> boardsList = new ArrayList<>();

    public void mappingPost(Boards boards) {
        this.boardsList.add(boards);
    }
}

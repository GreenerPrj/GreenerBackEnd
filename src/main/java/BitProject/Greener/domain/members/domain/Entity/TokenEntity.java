package BitProject.Greener.domain.members.domain.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenEntity {


    @Id
    private String email;

    private String access;

    private String refresh;

}

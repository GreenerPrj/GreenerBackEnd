package BitProject.Greener.repository;

import BitProject.Greener.domain.entity.MyPlants;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MyPlantsRepository extends JpaRepository<MyPlants, Long> {

    @Query("select m from MyPlants m join fetch m.userEntity where m.userEntity.id = :userId")
    List<MyPlants> getMyPlantsByUserId(Long userId);

}

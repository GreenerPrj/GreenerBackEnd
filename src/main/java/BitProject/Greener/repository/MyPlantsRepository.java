package BitProject.Greener.repository;

import BitProject.Greener.domain.entity.MyPlants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyPlantsRepository extends JpaRepository<MyPlants, Long> {

}

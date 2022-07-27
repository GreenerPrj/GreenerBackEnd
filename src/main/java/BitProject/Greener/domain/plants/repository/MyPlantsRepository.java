package BitProject.Greener.domain.plants.repository;

import BitProject.Greener.domain.plants.Entity.MyPlants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyPlantsRepository extends JpaRepository<MyPlants, Long> {

}

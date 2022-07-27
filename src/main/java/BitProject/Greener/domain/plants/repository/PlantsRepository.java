package BitProject.Greener.domain.plants.repository;

import BitProject.Greener.domain.plants.Entity.Plants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantsRepository extends JpaRepository<Plants, Long> {

}

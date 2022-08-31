package BitProject.Greener.repository;

import BitProject.Greener.domain.entity.MyPlants;
import BitProject.Greener.domain.entity.MyPlantsFiles;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyPlantsFilesRepository extends JpaRepository<MyPlantsFiles, Long> {

    Optional<MyPlantsFiles> findByMyPlants(MyPlants myPlants);

    Optional<MyPlantsFiles> findByMyPlantsId(Long myPlantsId);
}

package BitProject.Greener.repository;

import BitProject.Greener.domain.entity.Diary;
import BitProject.Greener.domain.entity.DiaryFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiaryFilesRepository extends JpaRepository<DiaryFiles, Long> {

    Optional<DiaryFiles> findByDiary(Diary diary);

    Optional<DiaryFiles> findByDiaryId(Long id);
}

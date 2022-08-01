package BitProject.Greener.domain.dto;

import javax.persistence.Column;
import lombok.Getter;

@Getter
public class BoardFilesDTO {

    private Long id;
    private String originFileName;
    private String fileName;
    private String filePath;

}

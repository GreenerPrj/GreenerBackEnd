package BitProject.Greener.domain.dto;

import BitProject.Greener.common.BoardsType;
import BitProject.Greener.domain.entity.BoardsCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardsCategoryDTO {
    private Long id;
    private BoardsType name;
    private String value;

    public static BoardsCategoryDTO convertToBoardsCategoryDTO(BoardsCategory boardsCategory){
        return BoardsCategoryDTO.builder()
                .id(boardsCategory.getId())
                .name(boardsCategory.getName())
                .value(boardsCategory.getValue())
                .build();
    }
}

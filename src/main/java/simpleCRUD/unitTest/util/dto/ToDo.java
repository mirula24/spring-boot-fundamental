package simpleCRUD.unitTest.util.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDo {
    private Integer id;
    private Integer userId;
    private String title;
    private Boolean complated;
}

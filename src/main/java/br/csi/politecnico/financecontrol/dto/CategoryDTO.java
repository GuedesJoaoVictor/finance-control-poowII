package br.csi.politecnico.financecontrol.dto;

import br.csi.politecnico.financecontrol.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String type;
    private UserDTO user;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.type = category.getType();
        this.user = new UserDTO(category.getUser());
    }
}

package com.errabi.sandbox.web.model;

import lombok.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ProductDto extends AbstractMessageDto {
    private Long id;
    @NotEmpty(message = "Product name is mandatory")
    @Size(max = 30, message = "Product name must not exceed 30 characters")
    private String name;
    @NotEmpty(message = "Please add a description")
    private String description;
    @NotNull(message = "Please add product status")
    private boolean enabled;
    @NotNull(message = "Please add product visibility")
    private boolean visibility;
    @NotEmpty(message = "Color is mandatory")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Invalid color format. It should be a HEX color.")
    private String color;
    private Long workspaceId;
    private String workspaceName;
    private List<ReleaseDto> releases;
}

package com.errabi.sandbox.web.model;

import jakarta.validation.constraints.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ModuleDto extends AbstractMessageDto{
    private Long id;
    @NotEmpty(message = "Module name is mandatory")
    @Size(max = 30, message = "Module name must not exceed 30 characters")
    private String name;
    @NotEmpty(message = "Please add a description")
    private String description;
    @NotEmpty(message = "Please add Model status")
    private String enabled;
    @NotNull(message = "Please add product visibility")
    private boolean visibility;
    @NotEmpty(message = "Color is mandatory")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Invalid color format. It should be a HEX color.")
    private String color;
    @NotEmpty(message = "Please add an image")
    private String image;
    private Long solutionId;
}

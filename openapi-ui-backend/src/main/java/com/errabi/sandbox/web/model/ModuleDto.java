package com.errabi.sandbox.web.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

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
    @NotNull(message = "Please add module status")
    private boolean enabled;
    @NotNull(message = "Please add module visibility")
    private boolean visibility;
    @NotEmpty(message = "Color is mandatory")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Invalid color format. It should be a HEX color.")
    private String color;
    @NotEmpty(message = "Please add an image")
    private String image;
    private Long solutionId;
    private String solutionName;
    private List<ApiDto> apis;
}

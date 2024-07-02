package com.errabi.sandbox.web.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ReleaseDto extends AbstractMessageDto{
    private Long id;
    @NotEmpty(message = "Release name is mandatory")
    @Size(max = 30, message = "Release name must not exceed 30 characters")
    private String name;
    @NotEmpty(message = "Please add a description")
    private String description;
    @NotEmpty(message = "Please add a release status")
    private String enabled;
    @NotNull(message = "Please add release visibility")
    private boolean visibility;
    @NotEmpty(message = "Color is mandatory")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Invalid color format. It should be a HEX color.")
    private String color;
}

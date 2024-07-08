package com.errabi.sandbox.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class WorkspaceDto extends AbstractMessageDto{
    @JsonIgnore
    private Long id;
    @NotEmpty(message = "Workspace name is mandatory")
    @Size(max = 30, message = "Workspace name must not exceed 30 characters")
    private String name;
    @NotEmpty(message = "Please add a description")
    private String description;
    @NotEmpty(message = "Please add Workspace status")
    private String enabled;
    @NotNull(message = "Please add Workspace visibility")
    private boolean visibility;
    private List<ProductDto> products;
}

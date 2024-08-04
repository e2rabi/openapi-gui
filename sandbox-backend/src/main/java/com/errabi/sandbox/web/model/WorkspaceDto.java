package com.errabi.sandbox.web.model;

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
    private Long id;
    @NotEmpty(message = "Workspace name is mandatory")
    @Size(max = 30, message = "Workspace name must not exceed 30 characters")
    private String name;
    @NotEmpty(message = "Please add a description")
    private String description;
    @NotNull(message = "Please add Workspace status")
    private boolean enabled;
    @NotNull(message = "Please add Workspace visibility")
    private boolean visibility;
    private List<ProductDto> products;
    private Long nbOfUsers;
}

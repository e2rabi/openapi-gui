package com.errabi.sandbox.web.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ApiDto extends AbstractMessageDto{
    private Long id;
    @NotEmpty(message = "Module name is mandatory")
    @Size(max = 30, message = "Module name must not exceed 30 characters")
    private String name;
    @NotEmpty(message = "Please add a description")
    private String description;
    @NotNull(message = "Please add Model status")
    private boolean enabled;
    @NotNull(message = "Please add Api visibility")
    private boolean visibility;
    @NotEmpty(message = "Please add url")
    private String url;
    @NotEmpty(message = "Please add http verb")
    private String httpVerb;
    @NotEmpty(message = "Please add open api schema")
    private String openApiSchema;
    private Long moduleId;
}

package com.errabi.openapi.web.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ConfigurationDto extends AbstractMessageDto{
    private Long id;
    @NotEmpty(message = "Please add a key")
    private String key;
    @NotEmpty(message = "Please add a value")
    private String value;
}

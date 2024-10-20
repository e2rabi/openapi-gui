package com.errabi.openapi.web.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuditDto extends AbstractMessageDto{
    private Long id;
    @NotEmpty(message = "username name is mandatory")
    private String userName;
    @NotEmpty(message = "please add data")
    private String data;
    @NotEmpty(message = "please add a component")
    private String component;
}

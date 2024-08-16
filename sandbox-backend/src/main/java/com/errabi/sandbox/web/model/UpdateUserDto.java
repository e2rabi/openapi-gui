package com.errabi.sandbox.web.model;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UpdateUserDto extends AbstractMessageDto {
    private Long id;
    @NotEmpty(message = "Please add firstname")
    private  String firstName ;
    @NotEmpty(message = "Please add lastname")
    private  String lastName ;
    private  Boolean enabled ;
    @NotEmpty(message = "Please add phone")
    private  String phone ;
    private String expiryDate ;
    private WorkspaceDto workspace;
}

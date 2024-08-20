package com.errabi.sandbox.web.model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UpdateUserDto extends AbstractMessageDto {
    private Long id;
    @NotEmpty(message = "Firstname is required")
    private  String firstName ;
    @NotEmpty(message = "Lastname is required")
    private  String lastName ;
    private  Boolean enabled ;
    @NotEmpty(message = "Phone is required")
    @Pattern(regexp = "\\d+", message = "Phone must contain only numbers")
    private  String phone ;
    private String expiryDate ;
    private WorkspaceDto workspace;
}

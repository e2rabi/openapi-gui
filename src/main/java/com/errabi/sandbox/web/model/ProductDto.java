package com.errabi.sandbox.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends  AbstractResponse{
    private Long id;
    @NotBlank(message = "Product name is mandatory")
    @Size(max = 30, message = "Product name must not exceed 30 characters")
    private String name;
    @NotBlank(message = "Please add a description")
    private String description;
    @NotBlank(message = "Please add product status")
    private String status;
    @NotBlank(message = "Please add product visibility")
    private boolean visibility;
    @NotBlank(message = "Color is mandatory")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Invalid color format. It should be a HEX color.")
    private String color;
}

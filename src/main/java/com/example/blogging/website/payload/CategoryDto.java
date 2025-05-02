package com.example.blogging.website.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;

    @NotEmpty(message = "This field cannot be empty")
    @Size(min = 3,message = "Must be minimum of 3 Characters.")
    private String categoryTile;
    @NotEmpty(message = "This field cannot be empty")
    @Size(min = 5,message = "Must be minimum of 5 Characters.")
    private String categoryDescription;
}

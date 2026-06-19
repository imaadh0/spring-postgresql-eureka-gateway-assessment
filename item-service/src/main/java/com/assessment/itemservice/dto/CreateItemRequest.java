package com.assessment.itemservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateItemRequest {

    @NotBlank(message = "name is required")
    @Size(max = 255, message = "name must be 255 characters or fewer")
    private String name;

    @Size(max = 1000, message = "description must be 1000 characters or fewer")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

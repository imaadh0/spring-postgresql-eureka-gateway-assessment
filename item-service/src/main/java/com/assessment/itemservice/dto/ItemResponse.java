package com.assessment.itemservice.dto;

import com.assessment.itemservice.entity.Item;

import java.time.LocalDateTime;

public record ItemResponse(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt
) {

    public static ItemResponse from(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getCreatedAt()
        );
    }
}

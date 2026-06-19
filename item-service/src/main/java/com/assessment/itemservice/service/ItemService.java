package com.assessment.itemservice.service;

import com.assessment.itemservice.dto.CreateItemRequest;
import com.assessment.itemservice.dto.ItemResponse;
import com.assessment.itemservice.entity.Item;
import com.assessment.itemservice.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemResponse create(CreateItemRequest request) {
        Item item = new Item();
        item.setName(request.getName().trim());
        item.setDescription(request.getDescription());

        return ItemResponse.from(itemRepository.save(item));
    }

    public List<ItemResponse> findAll() {
        return itemRepository.findAll()
                .stream()
                .map(ItemResponse::from)
                .toList();
    }
}

package com.omnilab.templatekotlin.repository.item;

import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.domain.item.ItemDto;
import org.springframework.data.domain.*;

import java.util.List;

public interface ItemRepositoryJpql {
    Page<Item> findAll(ItemSearch itemSearch, Pageable pageable);

}

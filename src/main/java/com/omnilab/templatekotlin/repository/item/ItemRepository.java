package com.omnilab.templatekotlin.repository.item;


import com.omnilab.templatekotlin.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryJpql {

//    @Query("SELECT i FROM Item i where i.dtype = :dtype")
//    List<Item> findItemByDtypeIs(@Param("dtype") String dtype);

    List<Item> findByDtype(@Param("dtype") String dtype);

}

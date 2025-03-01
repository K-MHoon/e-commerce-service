package com.kmhoon.app.repository;

import com.kmhoon.app.entity.TagEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface TagRepository extends ReactiveCrudRepository<TagEntity, UUID> {

    @Query("select t.* from ecomm.proudct p, ecomm.tag t, ecom.product_tag pt where p.id = :id and p.id = pt.product_id and t.id = pt.tag_id")
    Flux<TagEntity> findTagsByProductId(UUID id);
}
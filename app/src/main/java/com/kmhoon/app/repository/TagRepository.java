package com.kmhoon.app.repository;

import com.kmhoon.app.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TagRepository extends CrudRepository<TagEntity, UUID> {
}
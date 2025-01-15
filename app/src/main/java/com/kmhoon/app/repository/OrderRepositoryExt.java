package com.kmhoon.app.repository;

import com.kmhoon.app.entity.OrderEntity;
import com.kmhoon.app.model.NewOrder;

import java.util.Optional;

public interface OrderRepositoryExt  {
    Optional<OrderEntity> insert(NewOrder m);
}
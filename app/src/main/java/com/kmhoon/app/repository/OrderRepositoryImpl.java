package com.kmhoon.app.repository;

import com.kmhoon.app.entity.CartEntity;
import com.kmhoon.app.entity.ItemEntity;
import com.kmhoon.app.entity.OrderEntity;
import com.kmhoon.app.entity.OrderItemEntity;
import com.kmhoon.app.exceptions.ResourceNotFoundException;
import com.kmhoon.app.model.NewOrder;
import com.kmhoon.app.model.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Repository
@Transactional
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryExt {

    @PersistenceContext
    private final EntityManager em;

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Optional<OrderEntity> insert(NewOrder m) {
        Iterable<ItemEntity> dbItems = itemRepository.findByCustomerId(m.getCustomerId());
        List<ItemEntity> items = StreamSupport.stream(dbItems.spliterator(), false).toList();

        if(items.size() < 1) {
            throw new ResourceNotFoundException(String.format("장바구니에서 해당 하는 고객의 Item을 찾을 수 없습니다. (ID: %s) ", m.getCustomerId()));
        }
        BigDecimal total = BigDecimal.ZERO;
        for (ItemEntity item : items) {
            total = (BigDecimal.valueOf(item.getQuantity()).multiply(item.getPrice())).add(total);
        }
        Timestamp orderDate = Timestamp.from(Instant.now());
        em.createNativeQuery("""
    INSERT INTO eshop.orders (address_id, card_id, customer_id, order_date, total, status) VALUES (?, ?, ?, ?, ?, ?) 
""")
                .setParameter(1, m.getAddress().getId())
                .setParameter(2, m.getCard().getId())
                .setParameter(3, m.getCustomerId())
                .setParameter(4, orderDate)
                .setParameter(5, total)
                .setParameter(6, Order.StatusEnum.CREATED.getValue())
                .executeUpdate();

        CartEntity cart = cartRepository.findByCustomerId(UUID.fromString(m.getCustomerId())).orElseThrow(() -> new ResourceNotFoundException(String.format("customerId = %s를 찾을 수 없습니다.", m.getCustomerId())));

        itemRepository.deleteCartItemJoinById(cart.getItems().stream().map(ItemEntity::getId).toList(), cart.getId());

        OrderEntity entity = (OrderEntity) em.createNativeQuery("""
                            SELECT o.* FROM eshop.orders o WHERE o.customer_id = ? AND o.order_date >= ?
                        """, OrderEntity.class)
                .setParameter(1, m.getCustomerId())
                .setParameter(2, OffsetDateTime.ofInstant(orderDate.toInstant(), ZoneId.of("Z")).truncatedTo(ChronoUnit.MICROS))
                .getSingleResult();

        orderItemRepository.saveAll(cart.getItems().stream().map(i -> OrderItemEntity.builder()
                .orderId(entity.getId())
                .itemId(i.getId())
                .build())
                .toList());
        return Optional.of(entity);
    }

}
package org.gnpt.sample.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.gnpt.sample.entity.QAccount;
import org.gnpt.sample.entity.QCategory;
import org.gnpt.sample.entity.QOrder;
import org.gnpt.sample.entity.QOrderItem;
import org.gnpt.sample.entity.QProduct;
import org.gnpt.sample.filter.QueryFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountRepository {
    private static final QAccount account = QAccount.account;
    private static final QOrder order = QOrder.order;
    private static final QOrderItem orderItem = QOrderItem.orderItem;
    private static final QProduct product = QProduct.product;
    private static final QCategory category = QCategory.category;

    private final EntityManager entityManager;

    public record AccountInfoDto(UUID accountId, String accountName, Long orderCount) {}

    @Transactional(readOnly = true)
    public Page<AccountInfoDto> findAccountsWithOrdersBetweenDates(QueryFilters filters, Pageable pageable) {
        List<AccountInfoDto> content = buildQuery(new JPAQuery<AccountInfoDto>(entityManager)
                .select(Projections.constructor(
                        AccountInfoDto.class,
                        account.id,
                        account.name,
                        order.count()
                )), filters, pageable, false)
                .fetch();

        Long total = buildQuery(new JPAQuery<Long>(entityManager)
                .select(account.countDistinct()), filters, pageable, true)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    private <T> JPAQuery<T> buildQuery(JPAQuery<T> query, QueryFilters filters, Pageable pageable, boolean isCountQuery) {
        query.from(account)
                .join(account.orders, order)
                .join(order.orderItems, orderItem)
                .join(orderItem.product, product)
                .join(product.categories, category);

        if (filters.categoryCode() != null) {
            query.where(category.code.eq(filters.categoryCode()));
        }
        if (filters.dateFrom() != null) {
            query.where(order.orderedAt.after(filters.dateFrom()));
        }
        if (filters.dateTo() != null) {
            query.where(order.orderedAt.before(filters.dateTo()));
        }

        if (!isCountQuery) {
            query.groupBy(account.id, account.name);

            if (filters.orderBy() != null) {
                OrderSpecifier<?> orderSpecifier;
                switch (filters.orderBy()) {
                    case (QueryFilters.OrderBy.ACCOUNT_NAME):
                        orderSpecifier = (filters.orderType() == QueryFilters.OrderType.ASC)
                                ? account.name.asc()
                                : account.name.desc();
                        break;
                    case (QueryFilters.OrderBy.ORDERS_COUNT):
                        orderSpecifier = (filters.orderType() == QueryFilters.OrderType.ASC)
                                ? order.count().asc()
                                : order.count().desc();
                        break;
                    default:
                        orderSpecifier = order.count().desc();
                }
                query.orderBy(orderSpecifier);
            }

            if (pageable.isPaged()) {
                query.offset(pageable.getOffset()).limit(pageable.getPageSize());
            }
        }

        return query;
    }
}
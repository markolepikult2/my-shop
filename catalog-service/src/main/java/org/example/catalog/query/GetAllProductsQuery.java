package org.example.catalog.query;

import org.springframework.data.domain.Pageable;

public class GetAllProductsQuery {
    private final Pageable pageable;

    public GetAllProductsQuery(Pageable pageable) {
        this.pageable = pageable;
    }

    public Pageable getPageable() {
        return pageable;
    }
}

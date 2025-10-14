package org.example.catalog.query;

import java.util.List;

public class GetProductsByIdsQuery {
    private final List<Long> productIds;

    public GetProductsByIdsQuery(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}

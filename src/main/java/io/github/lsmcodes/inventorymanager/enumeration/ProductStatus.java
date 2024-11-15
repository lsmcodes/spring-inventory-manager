package io.github.lsmcodes.inventorymanager.enumeration;

public enum ProductStatus {

    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String productStatus;

    private ProductStatus(String status) {
        this.productStatus = status;
    }

    public String getValue() {
        return this.productStatus;
    }
    
}

package io.github.lsmcodes.inventorymanager.enumeration;

public enum InventoryEventType {

    STOCK_IN("Stock in"),
    STOCK_OUT("Stock out");

    private final String eventType;

    private InventoryEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getValue() {
        return eventType;
    }

}

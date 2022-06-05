package org.crsoft.cartonplast.common.enums;

/**
 * @author lpillaga on 05/06/2022
 */
public enum ReceiptType {
    CEB("CEB"),
    CIB("CIB");

    private String type;

    ReceiptType(String cib) {
        this.type = this.name();
    }

    public String getType() {
        return type;
    }
}

package org.billing;

import org.junit.jupiter.api.Test;
import org.model.Item;
import org.model.ProductDetails;
import org.util.HelperUtil;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ProcessBillTest {

    @Test
    void loadProductDetails() {
        HashMap<String, ProductDetails> productDetails = HelperUtil.loadProductDetails();
        assertNotNull(productDetails, "Product details should not be null");
        assertFalse(productDetails.isEmpty(), "Product details should not be empty");
    }

    @Test
    void testAddItem(){
        Item item = new Item();
        item.setItemId("PROD-101");
        item.setItemName("Wireless Ergonomic Mouse");
        item.setItemPrice(45.50);
        item.setItemQuantity(1);
        ProcessBill processBill = new ProcessBill();
        processBill.addItemToList("PROD-101",item);
        processBill.addItem("PROD-101");
    }
}
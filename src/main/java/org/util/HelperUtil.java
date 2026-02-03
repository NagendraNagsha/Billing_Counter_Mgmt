package org.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.billing.ProcessBill;
import org.model.ProductDetails;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;

public class HelperUtil {

    public static HashMap<String, ProductDetails> loadProductDetails() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = ProcessBill.class.getResourceAsStream("/data/Inventory.json")) {
            Optional.ofNullable(is).orElseThrow(() -> new RuntimeException("Inventory.json file not found in resources"));

            JsonNode inventory = Optional.ofNullable(mapper.readTree(is))
                    .map(root -> root.get("inventory"))
                    .filter(JsonNode::isArray)
                    .orElseThrow(() -> new RuntimeException("Invalid or missing 'inventory' array in JSON"));

            HashMap<String, ProductDetails> map = new HashMap<>();
            inventory.forEach((JsonNode item) -> {
                JsonNode idNode = item.get("productID");
                JsonNode detailsNode = item.get("details");
                if (idNode != null && detailsNode != null) {
                    String productId = idNode.asText();
                    try {
                        ProductDetails pd = mapper.treeToValue(detailsNode, ProductDetails.class);
                        map.put(productId, pd);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to parse product details for productID: " + productId, e);
                    }
                }
            });
            return map;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load product details from `src/main/resources/data/Inventory.json`", e);
        }
    }
}

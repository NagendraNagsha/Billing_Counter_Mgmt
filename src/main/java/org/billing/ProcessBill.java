package org.billing;

import org.model.Item;
import org.model.ProductDetails;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static org.util.HelperUtil.loadProductDetails;

@RedisHash(value = "ProcessBill", timeToLive = 3600)
public class ProcessBill implements Serializable {

    @Id
    private Double tokenId;
    private HashMap<String, Item> itemList;

    private final HashMap<String, ProductDetails> productDetailList;

    public ProcessBill(){
        itemList = new HashMap<>();
        productDetailList = loadProductDetails();
    }


    public void addItem(String productId){
        addItem(productId,1);
    }

    private final Function<ProductDetails, Item> toItem = (productDetails) -> {
        Item item = new Item();
        item.setItemId(productDetails.getName());
        item.setItemName(productDetails.getDescription());
        item.setItemPrice(productDetails.getPrice());
        return item;
    };



    private void addItem(String productId, int quantity){
        itemList.compute(productId, (key, existingItem) -> {
            if (existingItem != null) {
                existingItem.setItemQuantity(existingItem.getItemQuantity() + quantity);
                return existingItem;
            } else {
                Item item = toItem.apply(productDetailList.get(productId));
                item.setItemQuantity(quantity);
                return item;
            }
        });
    }

    private double calculateTotal() {
        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        itemList.forEach((key,item) -> total.updateAndGet(v -> v + item.getItemPrice()));
        return total.get();
    }

    public void addItemToList(String productId, Item item){
        itemList.put(productId,item);
    }

    public void removeItemQuantityFromList(String productId,int quantity){
        itemList.compute(productId,(key,existingItem) -> {
            if (existingItem != null) {
                existingItem.setItemQuantity(existingItem.getItemQuantity() - quantity);
                return existingItem;
            }
            return existingItem;
        });
    }

    public void removeItemFromList(String productId){
        itemList.remove(productId);
    }
}

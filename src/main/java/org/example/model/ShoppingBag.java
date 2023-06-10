package org.example.model;

import org.example.common.PricedItem;

import java.util.HashMap;
import java.util.Map;

// Create ShoppingBag class here
public class ShoppingBag<T extends PricedItem<Integer>>{
    private final Map<T, Integer> shoppingBag;

    public ShoppingBag(){
        this.shoppingBag = new HashMap<>();
    }


  /*public void addItem(T item){
    if(shoppingBag.containsKey(item)){
      shoppingBag.put(item, shoppingBag.get(item) + 1);
    } else {
      shoppingBag.put(item, 1);
    }
  }*/

    public void addItem(T item){
        shoppingBag.put(item, shoppingBag.getOrDefault(item, 0) + 1);
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for(Map.Entry<T, Integer> entry: shoppingBag.entrySet()){
            T item = entry.getKey();
            int quantity = entry.getValue();
            int itemPrice = item.getPrice();

            int itemTotalPrice = itemPrice * quantity;
            totalPrice += itemTotalPrice;
        }
        return totalPrice;

   /*return shoppingBag.entrySet()
    .stream;
    .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue);
    .sum();
  }*/
    }
}
package com.cs442.ssonawa1.myfoodorder;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by sneha on 10/1/2016.
 */
public class Item implements Serializable{
    private static final long serialVersionUID = -1213949467658913456L;
    private String price;
    private String description;
    private String title;
    private String total;
    private String quantity;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }



    @Override
    public String toString() {
       return getId() +". " +getTitle() + " " +getQuantity() +"  * " + "$"+ getPrice();
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Item(String id,String title, String description, String price, String quantity) {
        this.id=id;
        this.title = title;
        this.description = description;
        this.price=price;
        this.quantity=quantity;
    }

    public static ArrayList<Item> getItems() {

        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("1", "Vegetable Potstickers", "This is the first item", "4", "0"));
        items.add(new Item("2", "Grilled Chicken Satay", "This is the second item", "1", "0"));
        items.add(new Item("3", "Chicken Wonton Soup", "This is the third item", "2", "0"));
        items.add(new Item("4", "Kung Pao Chicken", "This is the fourth item", "10", "0"));
        items.add(new Item("5", "Cashew Chicken", "This is the fifth item", "22", "0"));

        return items;
    }

    public static ArrayList<String> getOrderedItems(ArrayList<Item> arrlstSelItems) {

        ArrayList<String> items = new ArrayList<String>();
        for (int i =0; i<arrlstSelItems.size();i++)
        {
            items.add (String.format("%02d", Integer.parseInt(arrlstSelItems.get(i).getId())) + ". "
                    + String.format("%-25s", arrlstSelItems.get(i).getTitle())
                    + String.format ("%03d", Integer.parseInt(arrlstSelItems.get(i).getQuantity()))
                    + " * "
                    + new DecimalFormat().format (Double.parseDouble(arrlstSelItems.get(i).getPrice()))
                    + " = "
                    + new DecimalFormat().format (Double.parseDouble(arrlstSelItems.get(i).getPrice())
                                        *Double.parseDouble(arrlstSelItems.get(i).getQuantity()))
            );
        }

        return items;
    }
}

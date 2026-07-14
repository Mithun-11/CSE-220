package model;

import java.util.Objects;

public class OrderItemBuilder {

    //Must be present
    MenuItem menuItem;
    int quantity;

    // Optional
    Size size=Size.MEDIUM;
    boolean extraCheese=false;
    boolean spicy = false;
    String note ="";

    public OrderItemBuilder(MenuItem menuItem, int quantity)
    {
        this.menuItem=menuItem;
        this.quantity=quantity;
    }

    public OrderItemBuilder setSize(Size size)
    {
        this.size=size;
        return this;
    }

    public OrderItemBuilder setExtraCheese(boolean extraCheese)
    {
        this.extraCheese=extraCheese;
        return this;
    }

    public OrderItemBuilder setSpicy(boolean spicy)
    {
        this.spicy=spicy;
        return this;
    }

    public OrderItemBuilder setNote(String note)
    {
        this.note=note;
        return this;
    }

    private void normalize()
    {
        if(size==null)
        {
            size=Size.MEDIUM;
        }
        note = (note!=null) ? note.trim() :"";
    }

    private void validate() 
    {
        Objects.requireNonNull(menuItem, "Menu item cannot be null");
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }

    public OrderItem build()
    {
        normalize();
        validate();
        return new OrderItem(this);
    }
    
}

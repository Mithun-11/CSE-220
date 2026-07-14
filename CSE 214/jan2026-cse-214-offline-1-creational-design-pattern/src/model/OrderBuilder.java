package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderBuilder {
    // Must have fields
    String orderId;
    String customerName;
    String phone;

    List<OrderItem> items= new ArrayList<>();

    // Optional fields
    DeliveryType deliveryType=DeliveryType.PICKUP;
    String deliveryAddress="";
    PaymentMethod paymentMethod=PaymentMethod.CASH;
    LocalDateTime scheduledTime=null;
    String couponCode="";
    boolean giftWrap=false;
    boolean cutleryRequired=true;
    int loyaltyPointsToRedeem=0;
    boolean rushOrder=false;
    String specialInstructions="";

    public OrderBuilder(String orderId,String customerName,String phone)
    {
        this.orderId=orderId;
        this.customerName=customerName;
        this.phone=phone;
    }

    public OrderBuilder setItems(List<OrderItem> items)
    {
        this.items= (items!=null) ? new ArrayList<>(items) : null;
        return this;
    }

    public OrderBuilder addItem(OrderItem item)
    {
        this.items.add(item);
        return this;
    }

    public OrderBuilder setDeliveryType(DeliveryType deliveryType)
    {
        this.deliveryType=deliveryType;
        return this;
    }

    public OrderBuilder setDeliveryAddress(String deliveryAddress)
    {
        this.deliveryAddress=deliveryAddress;
        return this;
    }

    public OrderBuilder setPaymentMethod(PaymentMethod paymentMethod)
    {
        this.paymentMethod=paymentMethod;
        return this;
    }

    public OrderBuilder setScheduledTime(LocalDateTime scheduledTime)
    {
        this.scheduledTime=scheduledTime;
        return this;
    }

    public OrderBuilder setCouponCode(String couponCode)
    {
        this.couponCode=couponCode;
        return this;
    }

    public OrderBuilder setGiftWrap(boolean giftWrap)
    {
        this.giftWrap=giftWrap;
        return this;
    }

    public OrderBuilder setCutleryRequired(boolean cutleryRequired)
    {
        this.cutleryRequired=cutleryRequired;
        return this;
    }

    public OrderBuilder setLoyaltyPointsToRedeem(int loyaltyPointsToRedeem)
    {
        this.loyaltyPointsToRedeem=loyaltyPointsToRedeem;
        return this;
    }

    public OrderBuilder setRushOrder(boolean rushOrder)
    {
        this.rushOrder=rushOrder;
        return this;
    }

    public OrderBuilder setSpecialInstructions(String specialInstructions)
    {
        this.specialInstructions=specialInstructions;
        return this;
    }

    private void normalize()
    {
        if(deliveryType==null)
        {
            deliveryType=DeliveryType.PICKUP;
        }

        if(paymentMethod==null)
        {
            paymentMethod=PaymentMethod.CASH;
        }

        couponCode = (couponCode != null) ? couponCode.trim().toUpperCase() : "";
        specialInstructions = (specialInstructions != null) ? specialInstructions.trim() : "";
        loyaltyPointsToRedeem = Math.max(0, loyaltyPointsToRedeem);

        if(deliveryType!=DeliveryType.DELIVERY)
        {
            deliveryAddress = (deliveryAddress != null) ? deliveryAddress.trim() : "";
        }
    }

    private void validate()
    {
        orderId = requireNonBlank(orderId, "Order id");
        customerName = requireNonBlank(customerName, "Customer name");
        phone = requireNonBlank(phone, "Phone");

        if (deliveryType == DeliveryType.DELIVERY) 
            {
            deliveryAddress = requireNonBlank(deliveryAddress, "Delivery address");
        }

        Objects.requireNonNull(items, "Items cannot be null");

        if (items.isEmpty()) 
        {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
    }



    private static String requireNonBlank(String value, String fieldName) 
    {
        Objects.requireNonNull(value, fieldName + " cannot be null");
        String trimmed = value.trim();
        if (trimmed.isEmpty()) 
        {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return trimmed;
    }




    public Order build()
    {
        normalize();
        validate();
        return new Order(this);
    }

}

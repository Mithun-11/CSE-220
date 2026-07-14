package service;

import model.DeliveryType;
import model.MenuItem;
import model.Order;
import model.OrderBuilder;
import model.OrderItem;
import model.OrderItemBuilder;
import model.PaymentMethod;
import model.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates order creation.
 *
 * Several methods below repeat long Order constructor calls with many optional
 * parameters. That is intentional assignment material for refactoring.
 */
public class OrderService {
    private int nextNumber = 1001;

    public OrderItem createOrderItem(MenuItem item, int quantity, Size size, 
                                    boolean extraCheese, boolean spicy, String note) {
        return new OrderItemBuilder(item, quantity)
                                    .setSize(size)
                                    .setExtraCheese(extraCheese)
                                    .setSpicy(spicy)
                                    .setNote(note)
                                    .build();
    }

    public Order createDeliveryOrder(String customerName,
                                     String phone,
                                     String address,
                                     List<OrderItem> items,
                                     String couponCode,
                                     boolean rushOrder,
                                     String specialInstructions) {
        return new OrderBuilder(nextOrderId(), customerName, phone)
                               .setDeliveryAddress(address)
                               .setItems(items)         
                               .setDeliveryType(DeliveryType.DELIVERY)
                               .setCouponCode(couponCode)
                               .setRushOrder(rushOrder)
                               .setSpecialInstructions(specialInstructions)
                               .build();
    }

    public Order createPickupOrder(String customerName, String phone, 
                                    List<OrderItem> items) {
        return new OrderBuilder(nextOrderId(), customerName, phone)
                                .setItems(items)
                                .setDeliveryType(DeliveryType.PICKUP)
                                .build();
    }

    public Order createScheduledGiftOrder(String customerName,
                                          String phone,
                                          String address,
                                          List<OrderItem> items,
                                          LocalDateTime scheduledTime) {
        return new OrderBuilder(nextOrderId(), customerName, phone)
                                .setItems(items)
                                .setScheduledTime(scheduledTime)
                                .setDeliveryType(DeliveryType.DELIVERY)
                                .setDeliveryAddress(address)
                                .setPaymentMethod(PaymentMethod.CARD)
                                .setCouponCode("WELCOME10")
                                .setGiftWrap(true)
                                .setCutleryRequired(false)
                                .setLoyaltyPointsToRedeem(25)
                                .setSpecialInstructions("Please call before delivery")
                                .build();
    }

    public Order createSampleFamilyOrder(MenuCatalog catalog) {
        return new OrderBuilder(nextOrderId(), "Sample Family", "01711111111")
                        .addItem(new OrderItemBuilder(catalog.findByCode("P01"), 2)
                                .setSize(Size.LARGE).setExtraCheese(true).setNote("half spicy").build())
                        .addItem(new OrderItemBuilder(catalog.findByCode("B02"), 3)
                                .setExtraCheese(true).setSpicy(true).build())
                        .addItem(new OrderItemBuilder(catalog.findByCode("D02"), 4)
                                .setNote("less sugar").build())
                        .addItem(new OrderItemBuilder(catalog.findByCode("S02"), 2)
                                .setSize(Size.LARGE).setSpicy(true).build())
                        .setDeliveryType(DeliveryType.DELIVERY)
                        .setDeliveryAddress("House 25, Road 4, Dhanmondi")
                        .setPaymentMethod(PaymentMethod.MOBILE_BANKING)
                        .setCouponCode("FAMILY15")
                        .setLoyaltyPointsToRedeem(50)
                        .setRushOrder(true)
                        .setSpecialInstructions("Deliver together")
                        .build();
    }

    private String nextOrderId() {
        return "FF-" + nextNumber++;
    }
}


import java.util.ArrayList;
import java.util.List;

enum CUSTOMER {
REGULAR,PREMIUM
    
}

enum METHOD{CARD,MFS,CASH}

class Purchase{
    double amount;
    CUSTOMER customer;
    METHOD method;
    

    public Purchase(double amount, CUSTOMER customer, METHOD method) {
        this.amount = amount;
        this.customer = customer;
        this.method = method;
    }

    public CUSTOMER getCustomer() {
        return customer;
    }

    public METHOD getMethod() {
        return method;
    }


    
}



interface Discount
{
    double discountedPrice(Purchase purchase);
}

class AmountDiscount implements  Discount{

    @Override
    public double discountedPrice(Purchase purchase) {
        if(purchase.amount<1000) return purchase.amount;
        if(purchase.amount<=1999) return purchase.amount*0.95;
        if(purchase.amount<=2999) return purchase.amount*0.9;
        if(purchase.amount<=3999) return purchase.amount*0.85;
        if(purchase.amount<=4999) return purchase.amount*0.8;
        return  purchase.amount*0.75;
        
    }
    
}

class CustomerDiscount implements  Discount{
    @Override
    public double discountedPrice(Purchase purchase) {
        if(purchase.customer==CUSTOMER.REGULAR) return purchase.amount*0.95;
        else return  purchase.amount*0.85;
    }
}

class PaymentDiscount implements  Discount{
    @Override
    public double discountedPrice(Purchase purchase) {
        if(purchase.method==METHOD.CARD) return purchase.amount*0.98;
        else if(purchase.method==METHOD.MFS) return  purchase.amount*0.95;
        return purchase.amount*0.92;
    }
}

class Calculator{
    Purchase purchase;
    public Calculator(Purchase purchase){
        this.purchase=purchase;
    }

    double getPrice(){
        List<Discount> discounts = new ArrayList<>();
        discounts.add(new AmountDiscount());
        discounts.add(new CustomerDiscount());
        discounts.add(new PaymentDiscount());
        double price=Double.MAX_VALUE;
        for(Discount discount: discounts){
            price=Math.min(price, discount.discountedPrice(purchase));
        }

        return  price;
        
    }
}



public class A2 {

    public static void main(String[] args) {
        
        
    }
}
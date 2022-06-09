package co.za.carolsBoutique.paymentGateway;

import co.za.carolsBoutique.Sale.model.Sale;

public class PaymentGateway {
    public boolean makePayment(Sale sale){
        double check = Math.random()*sale.getTotalPrice();
        if (check<sale.getTotalPrice()*0.15) {
            return false;
        }
        return true;
    }
}

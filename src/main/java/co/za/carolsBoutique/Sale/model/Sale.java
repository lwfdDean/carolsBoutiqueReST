package co.za.carolsBoutique.Sale.model;

import co.za.carolsBoutique.product.model.Product;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    private String id;
    private String employee;
    private Boolean approved;
    private Double totalPrice;
    private List<Product> items;
    private String boutique;
    private String cardNumber;
    private String customerEmail;

    public Sale(String id, String employee, Boolean approved, Double totalPrice, List<Product> items, String boutique, String cardNumber) {
        this.id = id;
        this.employee = employee;
        this.approved = approved;
        this.totalPrice = totalPrice;
        this.items = items;
        this.boutique = boutique;
        this.cardNumber = cardNumber;
    }

    public Sale(String employee, String boutique) {
        this.employee = employee;
        this.boutique = boutique;
        this.cardNumber = "";
        this.customerEmail = "";
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    public void addNewSaleLineItem(Product product) {
        if (items.add(product)) {
            if (product.getDiscountedPrice() == null) {
                calculateTotalPrice(product.getPrice());
            } else {
                calculateTotalPrice(product.getDiscountedPrice());
            }
        }
    }

    public void removeSaleLineItem(String productCode) {
        String[] productInfo = productCode.split(" ");
        for (Product item : items) {
            if (item.getId().equals(productInfo[0])) {
                if (item.getSizes().get(0).equals(productInfo[1])) {
                    items.remove(item);
                    if (item.getDiscountedPrice() == null) {
                        calculateTotalPrice(-item.getPrice());
                    } else {
                        calculateTotalPrice(-item.getDiscountedPrice());
                    }
                    break;
                }
            }
        }
    }

    private void calculateTotalPrice(double productPrice) {
        this.totalPrice += productPrice;
    }

    public void calculatePromoTotal(String categoryId, double discount) {
        totalPrice = 0.0;
        for (Product item : items) {
            if (item.getCategories().contains(categoryId)) {
                double productPrice = item.getPrice();
                double discountAmmount = productPrice * (discount / 100);
                item.setDiscountedPrice(productPrice - discountAmmount);
                totalPrice += item.getDiscountedPrice();
            } else {
                totalPrice += item.getPrice();
            }
        }
    }

    public void calculatePromoTotal(double discount) {
        totalPrice = 0.0;
        for (Product item : items) {
            double productPrice = item.getPrice();
            double discountAmmount = productPrice * (discount / 100);
            item.setDiscountedPrice(productPrice - discountAmmount);
            totalPrice *= item.getDiscountedPrice();
        }
    }
}

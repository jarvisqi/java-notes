package com.basic.async.example;

/**
 * @author Jarvis
 * @date 2018/9/12
 */
public class OrderInfo {

    private CustomerInfo customerInfo;
    private DiscountInfo discountInfo;
    private FoodListInfo foodListInfo;
    private OtherInfo otherInfo;
    private TenantInfo tenantInfo;

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public DiscountInfo getDiscountInfo() {
        return discountInfo;
    }

    public void setDiscountInfo(DiscountInfo discountInfo) {
        this.discountInfo = discountInfo;
    }

    public FoodListInfo getFoodListInfo() {
        return foodListInfo;
    }

    public void setFoodListInfo(FoodListInfo foodListInfo) {
        this.foodListInfo = foodListInfo;
    }

    public OtherInfo getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(OtherInfo otherInfo) {
        this.otherInfo = otherInfo;
    }

    public void setTenantInfo(TenantInfo tenantInfo) {
        this.tenantInfo = tenantInfo;
    }

    public TenantInfo getTenantInfo() {
        return tenantInfo;
    }

}


class CustomerInfo {

}

class DiscountInfo {

}

class FoodListInfo {

}

class OtherInfo {

}

class TenantInfo {

}
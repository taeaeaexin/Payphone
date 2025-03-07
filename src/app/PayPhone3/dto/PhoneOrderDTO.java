package app.PayPhone3.dto;

public class PhoneOrderDTO {
    private int orderId;
    private int companyId;
    private int storageId;
    private int planId;

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }
    public int getStorageId() { return storageId; }
    public void setStorageId(int storageId) { this.storageId = storageId; }
    public int getPlanId() { return planId; }
    public void setPlanId(int planId) { this.planId = planId; }
}
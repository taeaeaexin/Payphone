package app.PayPhone3.dto;

public class PlanDTO {
    private int planId;
    private int speedId;
    private int amountId;
    private int price;

    public int getPlanId() { return planId; }
    public void setPlanId(int planId) { this.planId = planId; }
    public int getSpeedId() { return speedId; }
    public void setSpeedId(int speedId) { this.speedId = speedId; }
    public int getAmountId() { return amountId; }
    public void setAmountId(int amountId) { this.amountId = amountId; }
    public int getPrice() {return price;}
    public void setPrice(int price) {this.price = price;}
}
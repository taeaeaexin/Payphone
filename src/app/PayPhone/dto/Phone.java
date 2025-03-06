package app.PayPhone.dto;

public class Phone {
	private int id;
	private String model;
	private String company;
	private int price;

	public Phone() {}
	public Phone(int id, String model, String company, int price) {
		super();
		this.id = id;
		this.model = model;
		this.company = company;
		this.price = price;
	}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String publisher) {
		this.company = company;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Phone{" +
				"id=" + id +
				", model='" + model + '\'' +
				", company='" + company + '\'' +
				", price=" + price +
				'}';
	}
}

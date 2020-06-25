package njpi.soso.servicePackage;

public  abstract class ServicePackage
{
	 private double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public abstract void showInfo();
}

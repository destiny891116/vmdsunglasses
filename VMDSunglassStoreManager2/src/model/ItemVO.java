package model;

public class ItemVO {
	private int I_number;
	private String I_BrandName;
	private String I_SojipgiName;
	private String I_ModelName;
	private String I_Storage;
	private String I_QuantityOfWarehoused;
	private String I_Price;
	private String I_CompanyName;
	private String I_ReceivingDate;
	private String I_imageView;

	public ItemVO() {
		super();
	}

	public ItemVO(int i_number, String i_QuantityOfWarehoused) {
		super();
		I_number = i_number;
		I_QuantityOfWarehoused = i_QuantityOfWarehoused;
	}

	public ItemVO(int i_number, String i_BrandName, String i_SojipgiName, String i_ModelName) {
		super();
		I_number = i_number;
		I_BrandName = i_BrandName;
		I_SojipgiName = i_SojipgiName;
		I_ModelName = i_ModelName;
	}

	public ItemVO(String i_BrandName, String i_SojipgiName, String i_ModelName, String i_Storage,
			String i_QuantityOfWarehoused, String i_Price, String i_CompanyName, String i_imageView) {
		super();
		I_BrandName = i_BrandName;
		I_SojipgiName = i_SojipgiName;
		I_ModelName = i_ModelName;
		I_Storage = i_Storage;
		I_QuantityOfWarehoused = i_QuantityOfWarehoused;
		I_Price = i_Price;
		I_CompanyName = i_CompanyName;
		I_imageView = i_imageView;
	}

	public ItemVO(String i_BrandName, String i_SojipgiName, String i_ModelName, String i_Storage,
			String i_QuantityOfWarehoused, String i_Price, String i_CompanyName, String i_ReceivingDate,
			String i_imageView) {
		super();
		I_BrandName = i_BrandName;
		I_SojipgiName = i_SojipgiName;
		I_ModelName = i_ModelName;
		I_Storage = i_Storage;
		I_QuantityOfWarehoused = i_QuantityOfWarehoused;
		I_Price = i_Price;
		I_CompanyName = i_CompanyName;
		I_ReceivingDate = i_ReceivingDate;
		I_imageView = i_imageView;
	}

	public ItemVO(int i_number, String i_BrandName, String i_SojipgiName, String i_ModelName, String i_Storage,
			String i_QuantityOfWarehoused, String i_Price, String i_CompanyName, String i_ReceivingDate,
			String i_imageView) {
		super();
		I_number = i_number;
		I_BrandName = i_BrandName;
		I_SojipgiName = i_SojipgiName;
		I_ModelName = i_ModelName;
		I_Storage = i_Storage;
		I_QuantityOfWarehoused = i_QuantityOfWarehoused;
		I_Price = i_Price;
		I_CompanyName = i_CompanyName;
		I_ReceivingDate = i_ReceivingDate;
		I_imageView = i_imageView;
	}

	public int getI_number() {
		return I_number;
	}

	public void setI_number(int i_number) {
		I_number = i_number;
	}

	public String getI_BrandName() {
		return I_BrandName;
	}

	public void setI_BrandName(String i_BrandName) {
		I_BrandName = i_BrandName;
	}

	public String getI_SojipgiName() {
		return I_SojipgiName;
	}

	public void setI_SojipgiName(String i_SojipgiName) {
		I_SojipgiName = i_SojipgiName;
	}

	public String getI_ModelName() {
		return I_ModelName;
	}

	public void setI_ModelName(String i_ModelName) {
		I_ModelName = i_ModelName;
	}

	public String getI_Storage() {
		return I_Storage;
	}

	public void setI_Storage(String i_Storage) {
		I_Storage = i_Storage;
	}

	public String getI_QuantityOfWarehoused() {
		return I_QuantityOfWarehoused;
	}

	public void setI_QuantityOfWarehoused(String i_QuantityOfWarehoused) {
		I_QuantityOfWarehoused = i_QuantityOfWarehoused;
	}

	public String getI_Price() {
		return I_Price;
	}

	public void setI_Price(String i_Price) {
		I_Price = i_Price;
	}

	public String getI_CompanyName() {
		return I_CompanyName;
	}

	public void setI_CompanyName(String i_CompanyName) {
		I_CompanyName = i_CompanyName;
	}

	public String getI_ReceivingDate() {
		return I_ReceivingDate;
	}

	public void setI_ReceivingDate(String i_ReceivingDate) {
		I_ReceivingDate = i_ReceivingDate;
	}

	public String getI_imageView() {
		return I_imageView;
	}

	public void setI_imageView(String i_imageView) {
		I_imageView = i_imageView;
	}

}

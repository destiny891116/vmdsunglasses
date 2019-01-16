package model;

public class FurnitureVO {
	private int I_number;
	private String I_BrandName;
	private String I_SojipgiName;
	private String I_ModelName;
	private String I_StoreName;
	private String I_ForwardingQuantity;
	private String I_ForwardingDate;

	public FurnitureVO() {
		super();
	}

	public FurnitureVO(String i_SojipgiName, String i_ForwardingQuantity) {
		super();
		I_SojipgiName = i_SojipgiName;
		I_ForwardingQuantity = i_ForwardingQuantity;
	}

	public FurnitureVO(String i_BrandName, String i_SojipgiName, String i_ModelName, String i_StoreName,
			String i_ForwardingQuantity, String i_ForwardingDate) {
		super();
		I_BrandName = i_BrandName;
		I_SojipgiName = i_SojipgiName;
		I_ModelName = i_ModelName;
		I_StoreName = i_StoreName;
		I_ForwardingQuantity = i_ForwardingQuantity;
		I_ForwardingDate = i_ForwardingDate;
	}

	public FurnitureVO(int i_number, String i_BrandName, String i_SojipgiName, String i_ModelName, String i_StoreName,
			String i_ForwardingQuantity, String i_ForwardingDate) {
		super();
		I_number = i_number;
		I_BrandName = i_BrandName;
		I_SojipgiName = i_SojipgiName;
		I_ModelName = i_ModelName;
		I_StoreName = i_StoreName;
		I_ForwardingQuantity = i_ForwardingQuantity;
		I_ForwardingDate = i_ForwardingDate;
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

	public String getI_StoreName() {
		return I_StoreName;
	}

	public void setI_StoreName(String i_StoreName) {
		I_StoreName = i_StoreName;
	}

	public String getI_ForwardingDate() {
		return I_ForwardingDate;
	}

	public void setI_ForwardingDate(String i_ForwardingDate) {
		I_ForwardingDate = i_ForwardingDate;
	}

	public String getI_ForwardingQuantity() {
		return I_ForwardingQuantity;
	}

	public void setI_ForwardingQuantity(String i_ForwardingQuantity) {
		I_ForwardingQuantity = i_ForwardingQuantity;
	}

}

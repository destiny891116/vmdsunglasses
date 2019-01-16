package model;

public class StoreVO {

	private int s_number;
	private String s_DepartmentClassification;
	private String s_DepartmentClassification2;
	private String s_BrandName;
	private String s_StoreName;
	private String s_StoreLocation;
	private String s_LocationDate;
	private String s_WithdrawalDate;
	private String s_Material;
	private String s_VisualSize;
	private String gender;
	private String s_CompanyName;
	private String s_ConstructionAmount;
	private String imageView;

	public StoreVO() {
		super();
	}

	public StoreVO(String s_DepartmentClassification, String s_DepartmentClassification2, String s_BrandName,
			String s_StoreName, String s_StoreLocation, String s_LocationDate, String s_WithdrawalDate,
			String s_Material, String s_VisualSize, String gender, String s_CompanyName, String s_ConstructionAmount,
			String imageView) {
		super();
		this.s_DepartmentClassification = s_DepartmentClassification;
		this.s_DepartmentClassification2 = s_DepartmentClassification2;
		this.s_BrandName = s_BrandName;
		this.s_StoreName = s_StoreName;
		this.s_StoreLocation = s_StoreLocation;
		this.s_LocationDate = s_LocationDate;
		this.s_WithdrawalDate = s_WithdrawalDate;
		this.s_Material = s_Material;
		this.s_VisualSize = s_VisualSize;
		this.gender = gender;
		this.s_CompanyName = s_CompanyName;
		this.s_ConstructionAmount = s_ConstructionAmount;
		this.imageView = imageView;
	}

	public StoreVO(int s_number, String s_DepartmentClassification, String s_DepartmentClassification2,
			String s_BrandName, String s_StoreName, String s_StoreLocation, String s_LocationDate,
			String s_WithdrawalDate, String s_Material, String s_VisualSize, String gender, String s_CompanyName,
			String s_ConstructionAmount) {
		super();
		this.s_number = s_number;
		this.s_DepartmentClassification = s_DepartmentClassification;
		this.s_DepartmentClassification2 = s_DepartmentClassification2;
		this.s_BrandName = s_BrandName;
		this.s_StoreName = s_StoreName;
		this.s_StoreLocation = s_StoreLocation;
		this.s_LocationDate = s_LocationDate;
		this.s_WithdrawalDate = s_WithdrawalDate;
		this.s_Material = s_Material;
		this.s_VisualSize = s_VisualSize;
		this.gender = gender;
		this.s_CompanyName = s_CompanyName;
		this.s_ConstructionAmount = s_ConstructionAmount;
	}

	public StoreVO(int s_number, String s_DepartmentClassification, String s_DepartmentClassification2,
			String s_BrandName, String s_StoreName, String s_StoreLocation, String s_Material, String s_VisualSize,
			String gender, String s_CompanyName, String s_ConstructionAmount, String imageView) {
		super();
		this.s_number = s_number;
		this.s_DepartmentClassification = s_DepartmentClassification;
		this.s_DepartmentClassification2 = s_DepartmentClassification2;
		this.s_BrandName = s_BrandName;
		this.s_StoreName = s_StoreName;
		this.s_StoreLocation = s_StoreLocation;
		this.s_Material = s_Material;
		this.s_VisualSize = s_VisualSize;
		this.gender = gender;
		this.s_CompanyName = s_CompanyName;
		this.s_ConstructionAmount = s_ConstructionAmount;
		this.imageView = imageView;
	}

	public StoreVO(String s_DepartmentClassification, String s_DepartmentClassification2, String s_BrandName,
			String s_StoreName, String s_StoreLocation, String s_Material, String s_VisualSize, String gender,
			String s_CompanyName, String s_ConstructionAmount, String imageView) {
		super();
		this.s_DepartmentClassification = s_DepartmentClassification;
		this.s_DepartmentClassification2 = s_DepartmentClassification2;
		this.s_BrandName = s_BrandName;
		this.s_StoreName = s_StoreName;
		this.s_StoreLocation = s_StoreLocation;
		this.s_Material = s_Material;
		this.s_VisualSize = s_VisualSize;
		this.gender = gender;
		this.s_CompanyName = s_CompanyName;
		this.s_ConstructionAmount = s_ConstructionAmount;
		this.imageView = imageView;
	}

	public StoreVO(int s_number, String s_DepartmentClassification, String s_DepartmentClassification2,
			String s_BrandName, String s_StoreName, String s_StoreLocation, String s_LocationDate,
			String s_WithdrawalDate, String s_Material, String s_VisualSize, String gender, String s_CompanyName,
			String s_ConstructionAmount, String imageView) {
		super();
		this.s_number = s_number;
		this.s_DepartmentClassification = s_DepartmentClassification;
		this.s_DepartmentClassification2 = s_DepartmentClassification2;
		this.s_BrandName = s_BrandName;
		this.s_StoreName = s_StoreName;
		this.s_StoreLocation = s_StoreLocation;
		this.s_LocationDate = s_LocationDate;
		this.s_WithdrawalDate = s_WithdrawalDate;
		this.s_Material = s_Material;
		this.s_VisualSize = s_VisualSize;
		this.gender = gender;
		this.s_CompanyName = s_CompanyName;
		this.s_ConstructionAmount = s_ConstructionAmount;
		this.imageView = imageView;
	}

	public int getS_number() {
		return s_number;
	}

	public void setS_number(int s_number) {
		this.s_number = s_number;
	}

	public String getS_DepartmentClassification() {
		return s_DepartmentClassification;
	}

	public void setS_DepartmentClassification(String s_DepartmentClassification) {
		this.s_DepartmentClassification = s_DepartmentClassification;
	}

	public String getS_DepartmentClassification2() {
		return s_DepartmentClassification2;
	}

	public void setS_DepartmentClassification2(String s_DepartmentClassification2) {
		this.s_DepartmentClassification2 = s_DepartmentClassification2;
	}

	public String getS_BrandName() {
		return s_BrandName;
	}

	public void setS_BrandName(String s_BrandName) {
		this.s_BrandName = s_BrandName;
	}

	public String getS_StoreName() {
		return s_StoreName;
	}

	public void setS_StoreName(String s_StoreName) {
		this.s_StoreName = s_StoreName;
	}

	public String getS_StoreLocation() {
		return s_StoreLocation;
	}

	public void setS_StoreLocation(String s_StoreLocation) {
		this.s_StoreLocation = s_StoreLocation;
	}

	public String getS_LocationDate() {
		return s_LocationDate;
	}

	public void setS_LocationDate(String s_LocationDate) {
		this.s_LocationDate = s_LocationDate;
	}

	public String getS_WithdrawalDate() {
		return s_WithdrawalDate;
	}

	public void setS_WithdrawalDate(String s_WithdrawalDate) {
		this.s_WithdrawalDate = s_WithdrawalDate;
	}

	public String getS_Material() {
		return s_Material;
	}

	public void setS_Material(String s_Material) {
		this.s_Material = s_Material;
	}

	public String getS_VisualSize() {
		return s_VisualSize;
	}

	public void setS_VisualSize(String s_VisualSize) {
		this.s_VisualSize = s_VisualSize;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getS_CompanyName() {
		return s_CompanyName;
	}

	public void setS_CompanyName(String s_CompanyName) {
		this.s_CompanyName = s_CompanyName;
	}

	public String getS_ConstructionAmount() {
		return s_ConstructionAmount;
	}

	public void setS_ConstructionAmount(String s_ConstructionAmount) {
		this.s_ConstructionAmount = s_ConstructionAmount;
	}

	public String getImageView() {
		return imageView;
	}

	public void setImageView(String imageView) {
		this.imageView = imageView;
	}

}

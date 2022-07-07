package split.com.app.data.model;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("max_uses_total")
	private int maxUsesTotal;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("max_uses_user")
	private int maxUsesUser;

	@SerializedName("promo_end_date")
	private String promoEndDate;

	@SerializedName("promo_start_date")
	private String promoStartDate;

	@SerializedName("promo_status")
	private boolean promoStatus;

	@SerializedName("promo_code")
	private String promoCode;

	@SerializedName("id")
	private int id;

	@SerializedName("promo_discount")
	private double promoDiscount;

	@SerializedName("updatedAt")
	private String updatedAt;

	public int getMaxUsesTotal(){
		return maxUsesTotal;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getMaxUsesUser(){
		return maxUsesUser;
	}

	public String getPromoEndDate(){
		return promoEndDate;
	}

	public String getPromoStartDate(){
		return promoStartDate;
	}

	public boolean isPromoStatus(){
		return promoStatus;
	}

	public String getPromoCode(){
		return promoCode;
	}

	public int getId(){
		return id;
	}

	public double getPromoDiscount(){
		return promoDiscount;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}
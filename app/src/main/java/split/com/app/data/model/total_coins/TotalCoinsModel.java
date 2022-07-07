package split.com.app.data.model.total_coins;

import com.google.gson.annotations.SerializedName;

public class TotalCoinsModel{

	@SerializedName("coins")
	private double coins;

	@SerializedName("status")
	private boolean status;

	public double getCoins(){
		return coins;
	}

	public boolean isStatus(){
		return status;
	}
}
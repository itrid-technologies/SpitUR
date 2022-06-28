package split.com.app.data.model.wallet_balance;

import com.google.gson.annotations.SerializedName;

public class WalletBalanceModel{

	@SerializedName("wallet_balance")
	private String walletBalance;

	@SerializedName("status")
	private boolean status;

	public String getWalletBalance(){
		return walletBalance;
	}

	public boolean isStatus(){
		return status;
	}
}
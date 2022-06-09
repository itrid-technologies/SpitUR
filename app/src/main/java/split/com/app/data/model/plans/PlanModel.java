package split.com.app.data.model.plans;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PlanModel{

	@SerializedName("data")
	private List<PlanDataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<PlanDataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}
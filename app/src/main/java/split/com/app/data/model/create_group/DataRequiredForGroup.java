package split.com.app.data.model.create_group;

import com.google.gson.annotations.SerializedName;

public class DataRequiredForGroup{

	@SerializedName("slots")
	private int slots;

	@SerializedName("password")
	private String password;

	@SerializedName("visibility")
	private int visibility;

	@SerializedName("cost_per_member")
	private int costPerMember;

	@SerializedName("title")
	private String title;

	@SerializedName("plans_id")
	private int plansId;

	@SerializedName("email")
	private String email;

	public int getSlots() {
		return slots;
	}

	public void setSlots(int slots) {
		this.slots = slots;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public int getCostPerMember() {
		return costPerMember;
	}

	public void setCostPerMember(int costPerMember) {
		this.costPerMember = costPerMember;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPlansId() {
		return plansId;
	}

	public void setPlansId(int plansId) {
		this.plansId = plansId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
package com.splitur.app.data.model.join_group;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Subscriptions{

	@SerializedName("end_at")
	private int endAt;

	@SerializedName("paid_count")
	private int paidCount;

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("notes")
	private List<Object> notes;

	@SerializedName("has_scheduled_changes")
	private boolean hasScheduledChanges;

	@SerializedName("remaining_count")
	private int remainingCount;

	@SerializedName("total_count")
	private int totalCount;

	@SerializedName("created_at")
	private int createdAt;

	@SerializedName("source")
	private String source;

	@SerializedName("start_at")
	private int startAt;

	@SerializedName("current_end")
	private Object currentEnd;

	@SerializedName("charge_at")
	private int chargeAt;

	@SerializedName("short_url")
	private String shortUrl;

	@SerializedName("change_scheduled_at")
	private Object changeScheduledAt;

	@SerializedName("customer_notify")
	private boolean customerNotify;

	@SerializedName("auth_attempts")
	private int authAttempts;

	@SerializedName("id")
	private String id;

	@SerializedName("entity")
	private String entity;

	@SerializedName("plan_id")
	private String planId;

	@SerializedName("ended_at")
	private Object endedAt;

	@SerializedName("expire_by")
	private Object expireBy;

	@SerializedName("status")
	private String status;

	@SerializedName("current_start")
	private Object currentStart;

	public int getEndAt(){
		return endAt;
	}

	public int getPaidCount(){
		return paidCount;
	}

	public int getQuantity(){
		return quantity;
	}

	public List<Object> getNotes(){
		return notes;
	}

	public boolean isHasScheduledChanges(){
		return hasScheduledChanges;
	}

	public int getRemainingCount(){
		return remainingCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public int getCreatedAt(){
		return createdAt;
	}

	public String getSource(){
		return source;
	}

	public int getStartAt(){
		return startAt;
	}

	public Object getCurrentEnd(){
		return currentEnd;
	}

	public int getChargeAt(){
		return chargeAt;
	}

	public String getShortUrl(){
		return shortUrl;
	}

	public Object getChangeScheduledAt(){
		return changeScheduledAt;
	}

	public boolean isCustomerNotify(){
		return customerNotify;
	}

	public int getAuthAttempts(){
		return authAttempts;
	}

	public String getId(){
		return id;
	}

	public String getEntity(){
		return entity;
	}

	public String getPlanId(){
		return planId;
	}

	public Object getEndedAt(){
		return endedAt;
	}

	public Object getExpireBy(){
		return expireBy;
	}

	public String getStatus(){
		return status;
	}

	public Object getCurrentStart(){
		return currentStart;
	}
}
package com.example.demo.vo;

import java.util.Date;

public class Point {
	private String userId;
	private double usedPoint;
	private Date usedDate;
	private String usedReason;
	
	public Point() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getUsedPoint() {
		return usedPoint;
	}

	public void setUsedPoint(double usedPoint) {
		this.usedPoint = usedPoint;
	}

	public Date getUsedDate() {
		return usedDate;
	}

	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}

	public String getUsedReason() {
		return usedReason;
	}

	public void setUsedReason(String usedReason) {
		this.usedReason = usedReason;
	}

	@Override
	public String toString() {
		return "Point [userId=" + userId + ", usedPoint=" + usedPoint + ", usedDate=" + usedDate + ", usedReason="
				+ usedReason + "]";
	}

	
}

package com.secureparking.dao;

public class SrInfrom {
	private String name;
	private String password;
	private String carpark;
	private String station;
	private String bo;
	private String followby;
	private String item;
	private String item2;
	private String item3;
	private String issue;
	private String solution;
	private String qty;
	private String qty2;
	private String qty3;
	private String starttime;
	private String endtime;
	private String id;

	public SrInfrom(String name, String password, String carpark,
			String station, String bo, String followby, String item,
			String issue, String solution, String qty2, String qty3,
			String qty, String starttime, String endtime, String otstarttime,
			String otendtime, String id, String item2, String item3) {
		super();
		this.name = name;
		this.password = password;
		this.carpark = carpark;
		this.station = station;
		this.bo = bo;
		this.followby = followby;
		this.item = item;
		this.item2 = item2;
		this.item3 = item3;
		this.issue = issue;
		this.solution = solution;
		this.qty = qty;
		this.qty2 = qty2;
		this.qty3 = qty3;
		this.starttime = starttime;
		this.endtime = endtime;
		this.otstarttime = otstarttime;
		this.otendtime = otendtime;
		this.id = id;
	}

	public SrInfrom() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String otstarttime;
	private String otendtime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCarpark() {
		return carpark;
	}

	public void setCarpark(String carpark) {
		this.carpark = carpark;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getBo() {
		return bo;
	}

	public void setBo(String bo) {
		this.bo = bo;
	}

	public String getFollowby() {
		return followby;
	}

	public void setFollowby(String followby) {
		this.followby = followby;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItem2() {
		return item2;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public String getItem3() {
		return item3;
	}

	public void setItem3(String item3) {
		this.item3 = item3;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	@Override
	public String toString() {
		return "SrInfrom [name=" + name + ", id=" + id + ", password="
				+ password + ", carpark=" + carpark + ", station=" + station
				+ ", bo=" + bo + ", followby=" + followby + ", item=" + item
				+ ", issue=" + issue + ", solution=" + solution + ", qty="
				+ qty + ", starttime=" + starttime + ", endtime=" + endtime
				+ ", otstarttime=" + otstarttime + ", otendtime=" + otendtime
				+ "]";
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getQty2() {
		return qty2;
	}

	public void setQty2(String qty2) {
		this.qty2 = qty2;
	}

	public String getQty3() {
		return qty3;
	}

	public void setQty3(String qty3) {
		this.qty3 = qty3;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getOtstarttime() {
		return otstarttime;
	}

	public void setOtstarttime(String otstarttime) {
		this.otstarttime = otstarttime;
	}

	public String getOtendtime() {
		return otendtime;
	}

	public void setOtendtime(String otendtime) {
		this.otendtime = otendtime;
	}

}

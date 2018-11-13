package model;

import java.util.*;

public class BeanCase {
	private BeanPrisoner person1;
	private BeanPrisoner person2;
	private Date date;
	private String drugType;
	private double money;
	private double sum;
	private String info;
	public BeanPrisoner getPerson1() {
		return person1;
	}
	public void setPerson1(BeanPrisoner person1) {
		this.person1 = person1;
	}
	public BeanPrisoner getPerson2() {
		return person2;
	}
	public void setPerson2(BeanPrisoner person2) {
		this.person2 = person2;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDrugType() {
		return drugType;
	}
	public void setDrugType(String drugType) {
		this.drugType = drugType;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
}

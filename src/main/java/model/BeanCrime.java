package model;

import java.util.Date;
import java.util.List;

public class BeanCrime {
	private String area;
	private String serial;
	private String procuratorate;
	private List<BeanPrisoner> prisoners;
	private Date date;
	private List<BeanDrug> drugs;
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getProcuratorate() {
		return procuratorate;
	}
	public void setProcuratorate(String procuratorate) {
		this.procuratorate = procuratorate;
	}
	public List<BeanPrisoner> getPrisoners() {
		return prisoners;
	}
	public void setPrisoners(List<BeanPrisoner> prisoners) {
		this.prisoners = prisoners;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<BeanDrug> getDrugs() {
		return drugs;
	}
	public void setDrugs(List<BeanDrug> drugs) {
		this.drugs = drugs;
	}
	
	
}

package model;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanCrime {
    private int crimeid;
    private String area;
    private String serial;
    private String procuratorate;
    private Map<String,BeanPrisoner> prisoners;
    private Set<BeanPrisoner> prisonersSet;
    private Date date;
    private Set<BeanDrug> drugs;
    private List<BeanDrug> drugsList;
    private int firstprisonerid;
    private BeanPrisoner firstPrisoner;
    private Date minimumAge;

    public String showDrugs(){
        if(drugsList==null||drugsList.size()==0)
            return "";
        String drugString=drugsList.get(0).getDrugType()+Float.toString(drugsList.get(0).getDrugPrice())+drugsList.get(0).getDrugPriceMagnitude()+"/"+Float.toString(drugsList.get(0).getDrugNum())+drugsList.get(0).getDrugMagnitude();
        for(int i=1;i<drugsList.size();i++)
        {
            drugString = drugString.concat("|"+drugsList.get(i).getDrugType()+Float.toString(drugsList.get(i).getDrugPrice())+drugsList.get(i).getDrugPriceMagnitude()+"/"+Float.toString(drugsList.get(i).getDrugNum())+drugsList.get(i).getDrugMagnitude());
        }
        return  drugString;
    }

    public String showAverageDrugs(){
        if(drugsList==null||drugsList.size()==0)
            return "";
        String drugString=drugsList.get(0).getDrugType()+Float.toString(drugsList.get(0).getDrugPrice()/drugsList.get(0).getDrugNum())+drugsList.get(0).getDrugPriceMagnitude()+"/"+drugsList.get(0).getDrugMagnitude();
        for(int i=1;i<drugsList.size();i++)
        {
            drugString = drugString.concat("|"+ drugsList.get(i).getDrugType()+Float.toString(drugsList.get(i).getDrugPrice()/drugsList.get(i).getDrugNum())+drugsList.get(i).getDrugPriceMagnitude()+"/"+drugsList.get(i).getDrugMagnitude());
        }
        return  drugString;
    }

    public String sqlShowDrugs(){
        if(drugs==null||drugs.size()==0)
            return "";
        String drugString = "";
        for(BeanDrug drug:drugs)
        {
            drugString = drugString.concat("|"+drug.getDrugType()+Float.toString(drug.getDrugPrice())+drug.getDrugPriceMagnitude()+"/"+Float.toString(drug.getDrugNum())+drug.getDrugMagnitude());
        }
        return  drugString;
    }

    public String sqlShowAverageDrugs(){
        if(drugs==null||drugs.size()==0)
            return "";
        String drugString="";
        for(BeanDrug drug:drugs)
        {
            drugString = drugString.concat("|"+ drug.getDrugType()+Float.toString(drug.getDrugPrice()/drug.getDrugNum())+drug.getDrugPriceMagnitude()+"/"+drug.getDrugMagnitude());
        }
        return  drugString;
    }

    public int getCrimeid() {
        return crimeid;
    }

    public void setCrimeid(int crimeid) {
        this.crimeid = crimeid;
    }

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

    public Map<String, BeanPrisoner> getPrisoners() {
        return prisoners;
    }

    public void setPrisoners(Map<String, BeanPrisoner> prisoners) {
        this.prisoners = prisoners;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFirstprisonerid() {
        return firstprisonerid;
    }

    public void setFirstprisonerid(int firstprisonerid) {
        this.firstprisonerid = firstprisonerid;
    }

    public Date getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(Date minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Set<BeanPrisoner> getPrisonersSet() {
        return prisonersSet;
    }

    public void setPrisonersSet(Set<BeanPrisoner> prisonersSet) {
        this.prisonersSet = prisonersSet;
    }

    public List<BeanDrug> getDrugsList() {
        return drugsList;
    }

    public void setDrugsList(List<BeanDrug> drugsList) {
        this.drugsList = drugsList;
    }

    public Set<BeanDrug> getDrugs() {
        return drugs;
    }

    public void setDrugs(Set<BeanDrug> drugs) {
        this.drugs = drugs;
    }

    public BeanPrisoner getFirstPrisoner() {
        return firstPrisoner;
    }

    public void setFirstPrisoner(BeanPrisoner firstPrisoner) {
        this.firstPrisoner = firstPrisoner;
    }
}

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
    private BeanPrisoner firstPrisoner;
    private Date minimumAge;

    public String showDrugs(){
        if(drugs==null||drugs.size()==0)
            return "";
        String drugString=drugs.get(0).getDrugType()+Float.toString(drugs.get(0).getDrugNum())+drugs.get(0).getDrugMagnitude()+Float.toString(drugs.get(0).getDrugPrice())+drugs.get(0).getDrugPriceMagnitude();
        for(int i=1;i<drugs.size();i++)
        {
            drugString = drugString.concat("|"+drugs.get(i).getDrugType()+Float.toString(drugs.get(i).getDrugNum())+drugs.get(i).getDrugMagnitude()+Float.toString(drugs.get(i).getDrugPrice())+drugs.get(i).getDrugPriceMagnitude());
        }
        return  drugString;
    }

    public String showAverageDrugs(){
        if(drugs==null||drugs.size()==0)
            return "";
        String drugString=drugs.get(0).getDrugType()+Float.toString(drugs.get(0).getDrugPrice()/drugs.get(0).getDrugNum())+drugs.get(0).getDrugPriceMagnitude()+"/"+drugs.get(0).getDrugMagnitude();
        for(int i=1;i<drugs.size();i++)
        {
            drugString = drugString.concat("|"+drugs.get(i).getDrugType()+Float.toString(drugs.get(i).getDrugPrice()/drugs.get(i).getDrugNum())+drugs.get(i).getDrugPriceMagnitude()+"/"+drugs.get(i).getDrugMagnitude());
        }
        return  drugString;
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

    public BeanPrisoner getFirstPrisoner() {
        return firstPrisoner;
    }

    public void setFirstPrisoner(BeanPrisoner firstPrisoner) {
        this.firstPrisoner = firstPrisoner;
    }

    public Date getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(Date minimumAge) {
        this.minimumAge = minimumAge;
    }
}

package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class BeanPrisoner {
    private int prisonerid;
    private String name;
    private String sex;
    private Date birth;
    private String nation;
    private String level;
    private String work;
    private String idCard;
    private String place;
    private String crime;
    private String penalty;
    private float  penaltySum;
    private String prisonType;
    private String prisonTime;
    private Set<BeanCase> cases;
    private int crimeid;
    private BeanCrime beanCrime;

    public int getPrisonerid() {
        return prisonerid;
    }

    public void setPrisonerid(int prisonerid) {
        this.prisonerid = prisonerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPrisonType() {
        return prisonType;
    }

    public void setPrisonType(String prisonTime) {
        this.prisonType = prisonTime;
    }

    public Set<BeanCase> getCases() {
        return cases;
    }

    public void setCases(Set<BeanCase> cases) {
        this.cases = cases;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public float getPenaltySum() {
        return penaltySum;
    }

    public void setPenaltySum(float penaltySum) {
        this.penaltySum = penaltySum;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getPrisonTime() {
        return prisonTime;
    }

    public void setPrisonTime(String prisonTime) {
        this.prisonTime = prisonTime;
    }

    public String getCrime() {
        return crime;
    }

    public void setCrime(String crime) {
        this.crime = crime;
    }

    public BeanCrime getBeanCrime() {
        return beanCrime;
    }

    public void setBeanCrime(BeanCrime beanCrime) {
        this.beanCrime = beanCrime;
    }

    public int getCrimeid() {
        return crimeid;
    }

    public void setCrimeid(int crimeid) {
        this.crimeid = crimeid;
    }


}


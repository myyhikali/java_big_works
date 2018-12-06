package python;

public class BeanCrime4RG {
    private double cyclohexanone;
    private double heroin;
    private double methamphetamine;
    private double marijuana;
    private double hurt;
    private double produce;
    private double accept;
    private double hold;
    private double transport;

    @Override
    public String toString() {
        return "Data{" +
                "K粉=" + cyclohexanone + ',' +
                "海洛因=" + heroin + ',' +
                "甲基苯丙胺=" + methamphetamine + ',' +
                "大麻=" + marijuana + ',' +
                "伤害=" + hurt + ',' +
                "制造=" + produce + ',' +
                "容留=" + accept + ',' +
                "持有=" + hold + ',' +
                "运输=" + transport + ',' +
                "}";
    }

    public double getCyclohexanone() {
        return cyclohexanone;
    }

    public void setCyclohexanone(double cyclohexanone) {
        this.cyclohexanone = cyclohexanone;
    }

    public double getHeroin() {
        return heroin;
    }

    public void setHeroin(double heroin) {
        this.heroin = heroin;
    }

    public double getMethamphetamine() {
        return methamphetamine;
    }

    public void setMethamphetamine(double methamphetamine) {
        this.methamphetamine = methamphetamine;
    }

    public double getMarijuana() {
        return marijuana;
    }

    public void setMarijuana(double marijuana) {
        this.marijuana = marijuana;
    }

    public double getHurt() {
        return hurt;
    }

    public void setHurt(double hurt) {
        this.hurt = hurt;
    }

    public double getProduce() {
        return produce;
    }

    public void setProduce(double produce) {
        this.produce = produce;
    }

    public double getAccept() {
        return accept;
    }

    public void setAccept(double accept) {
        this.accept = accept;
    }

    public double getHold() {
        return hold;
    }

    public void setHold(double hold) {
        this.hold = hold;
    }

    public double getTransport() {
        return transport;
    }

    public void setTransport(double transport) {
        this.transport = transport;
    }
}

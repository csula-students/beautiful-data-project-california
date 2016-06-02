package edu.csula.population;

public class PopulationRecord {

    private int total;

    private int age;

    private int females;

    private int males;

    private String date;

    public PopulationRecord() {

    }

    public PopulationRecord(int total,int age, int females, int males, String date){
        this.total = total;
        this.age = age;
        this.females = females;
        this.males = males;
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getFemales() {
        return females;
    }

    public void setFemales(int females) {
        this.females = females;
    }

    public int getMales() {
        return males;
    }

    public void setMales(int males) {
        this.males = males;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("Total: %d, Year: %d", total, date);
    }
}

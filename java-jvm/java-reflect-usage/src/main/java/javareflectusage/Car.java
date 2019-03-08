package javareflectusage;

public class Car {
    private String name;

    public Car(){
        this("原始车");
    }

    public Car(String name){
        this.name = name;
    }

    public void printInfo(){
        System.out.println(name);
    }

    public String getCarInfo(String tag){
        return this.name + " " + tag;
    }
}

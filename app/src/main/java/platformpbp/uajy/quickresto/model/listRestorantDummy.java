package platformpbp.uajy.quickresto.model;

import java.util.ArrayList;

public class listRestorantDummy {
    public ArrayList<Restorant>resto = new ArrayList<>();
    public static final Restorant r1= new Restorant("Mc Donald's","Fast Food",1.5,"123321",110.3944011,-7.7818098,"https://www.yogyes.com/en/places/sleman/mcdonald-jl-laksda-adisucipto/1.jpg");
    public static final Restorant r2= new Restorant("KFC","Fast Food",3.5,"697809",110.370486,-7.7824567,"https://www.yogyes.com/en/places/jogja/kfc-sudirman/1.jpg");
    public static final Restorant r3= new Restorant("Starbucks","Fast Food",3.5,"324523",110.3632791,-7.7948563,"https://greenpramukacity-sewa.com/wp-content/uploads/2020/01/Menu-Starbucks.jpg");

    public static final Restorant r4= new Restorant("Mc Donald's","Fast Food",1.5,"453687",110.3944011,-7.7818098,"https://www.yogyes.com/en/places/sleman/mcdonald-jl-laksda-adisucipto/1.jpg");
    public static final Restorant r5= new Restorant("KFC","Fast Food",3.5,"303030",110.370486,-7.7824567,"https://www.yogyes.com/en/places/jogja/kfc-sudirman/1.jpg");
    public static final Restorant r6= new Restorant("Starbucks","Fast Food",3.5,"564798",110.3632791,-7.7948563,"https://greenpramukacity-sewa.com/wp-content/uploads/2020/01/Menu-Starbucks.jpg");

    public listRestorantDummy(){
        resto.add(r1);
        resto.add(r2);
        resto.add(r3);
        resto.add(r4);
        resto.add(r5);
        resto.add(r6);
    }
}

package shreyansh.bhavsar.networkingdemo;

/**
 * Created by shreyansh.bhavsar on 9/17/2017.
 */

public class Player {

    final String name;
    final String role;
    final String country;
    final String runs;

    public Player(String name, String role, String country, String runs) {
        this.name = name;
        this.role = role;
        this.country = country;
        this.runs = runs;
    }

    @Override
    public String toString() {
        return name+"  "+role+"  "+country+"   "+runs;
    }
}

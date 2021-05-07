package com.example.applicationfinal;


public class PlatsUtils {
    public static Plat[] getPlats()  {
        Plat p1 = new Plat("Burger",  "Note moyenne : 4,3/5");
        Plat p2 = new Plat("Pizza",  "Note moyenne : 4,9/5");
        Plat p3 = new Plat("Hot dog",  "Note moyenne : 4,1/5");

        return new Plat[] {p1, p2, p3};
    }
}

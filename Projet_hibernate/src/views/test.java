package views;

import manager.ProfManager;

public class test {
    public static void main(String[] args) {
        ProfManager pm = new ProfManager();
        String lc = pm.getLastId();
        System.out.println("Id: "+ lc);
    }
}

package software.ulpgc.view;

public interface ImageDisplay {
    void display(String name) ;
    String current();
    void on(Shift shift);

    interface Shift {
        String left();
        String right();

        public static class Null implements Shift{

            public Null() {
            }

            @Override
            public String left() {
                return "";
            }

            @Override
            public String right() {
                return "";
            }
        }
    }
}
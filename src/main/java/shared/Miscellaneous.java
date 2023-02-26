package shared;

import javax.swing.JOptionPane;

public class Miscellaneous {
    public static int getWeightInput() {
        while (true) {
            try {
                return Integer.parseInt(
                        JOptionPane.showInputDialog("Enter bridge weight", "0")
                );
            } catch (NumberFormatException ignored) {
            }
        }
    }
}

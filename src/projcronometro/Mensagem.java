package projcronometro;

import javax.swing.JOptionPane;

public class Mensagem {
    
    public static void exibir(String msg, int tipo) {
        JOptionPane.showMessageDialog(null, msg, "Cronômetro", tipo);
    }
    
}

package projcronometro;

import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Recursos {

    public Image obterIcone() {
        return new ImageIcon(getClass().getResource("recursos/stopwatch32x32.png")).getImage();
    }
    
    public ImageIcon obterIconePausar() {
        return new ImageIcon(getClass().getResource("recursos/pause.png"));
    }
    
    public ImageIcon obterIconeContinuar() {
      return new ImageIcon(getClass().getResource("recursos/play-button.png"));  
    }

    public Font obterFonteDeTexto() {
        Font font = null;        
        try {            
           font = Font.createFont(Font.TRUETYPE_FONT, CronometroJFrame.class.getResourceAsStream("recursos/Digit.TTF"));
        } catch(Exception e) {
            System.out.println("Problemas ao carregar a fonte de texto Digit. Erro: " + e.getMessage());
            font = new Font("Arial", 0, 0);
        }
        
        return font.deriveFont(Font.PLAIN, 130);
    }

}

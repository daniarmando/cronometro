/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projcronometro;

import java.awt.EventQueue;

/**
 *
 * @author danie
 */
public class Principal {
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CronometroJFrame cronometro = new CronometroJFrame();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                cronometro.iniciar();
            }
        });
    }    
}

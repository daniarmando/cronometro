package projcronometro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static projcronometro.TipoContagem.PROGRESSIVA;
import static projcronometro.TipoContagem.REGRESSIVA;

public class CronometroJFrame extends JFrame {

    private ConfigurarCronometroJFrame configuracao = new ConfigurarCronometroJFrame();
    private Recursos recursos = new Recursos();
    private TipoContagem contagem;
    private int limiteDeTempo;

    private JLabel lblContadorTempo;
    
    private Timer tm;

    private int contador = 0;
    private int segundo = 0;

    private boolean rodando = false;
    private boolean pausado = false;
    private boolean resetado = false;

    public CronometroJFrame() {
        initComponents();
        JOptionPane.setRootFrame(this);
    }

    public void iniciar() {        
        this.getContentPane().setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
        this.setIconImage(recursos.obterIcone());

        lblContadorTempo = new JLabel("00:00:00");
        lblContadorTempo.setFont(recursos.obterFonteDeTexto());        
        lblContadorTempo.setForeground(Color.WHITE);
        lblContadorTempo.setText(configuracao.obterTextoTempo());

        this.add(lblContadorTempo, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
    }

    private void configurar() {
        if (!rodando) {
            configuracao = new ConfigurarCronometroJFrame(lblContadorTempo);
            configuracao.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            configuracao.setSize(500, 300);
            configuracao.setAlwaysOnTop(true);
            configuracao.pack();
            configuracao.setVisible(true);             
        }
    }

    private void obterConfiguracao() {
        contagem = configuracao.obterTipoCronometro();
        limiteDeTempo = configuracao.obterLimiteDeTempo();
    }

    private void rodar() {
        if (!rodando) {
            tm = new Timer();
            rodando = true;
            pausado = false;
            resetado = false;

            obterConfiguracao();
            lblContadorTempo.setText(configuracao.obterTextoTempo());

            if (contagem == PROGRESSIVA) {
                segundo = 1;
                contador = 0;
            } else {
                segundo = -1;
                contador = limiteDeTempo;
            }

            tm.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    contador += segundo;
                    int seg = contador % 60;
                    int min = contador / 60;
                    int hora = min / 60;
                    min %= 60;
                    lblContadorTempo.setText(String.format("%02d:%02d:%02d", hora, min, seg));

                    if ((contagem == PROGRESSIVA && contador == limiteDeTempo) || (contagem == REGRESSIVA && contador == 0)) {

                        animarPiscandoNumeros();

                        tm.cancel();
                        rodando = false;
                        contador = limiteDeTempo;
                    }

                }
            }, 1000, 1000);
        }
    }

    private void pausar() {
        if (rodando) {
            pausado = true;
            resetado = false;
            tm.cancel();
            rodando = false;
        }
    }

    private void continuar() {
        if (!rodando) {
            pausado = false;
            resetado = false;
            tm = new Timer();
            rodando = true;

            tm.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    contador += segundo;
                    int seg = contador % 60;
                    int min = contador / 60;
                    int hora = min / 60;
                    min %= 60;
                    lblContadorTempo.setText(String.format("%02d:%02d:%02d", hora, min, seg));

                    if ((contagem == PROGRESSIVA && contador == limiteDeTempo) || (contagem == REGRESSIVA && contador == 0)) {

                        animarPiscandoNumeros();

                        tm.cancel();
                        rodando = false;
                        contador = limiteDeTempo;
                    }

                }
            }, 1000, 1000);
        }
    }

    private void resetar() {
        resetado = true;
        tm.cancel();
        lblContadorTempo.setText(configuracao.obterTextoTempo());
        rodando = false;
    }

    private void animarPiscandoNumeros() {
        try {
            for (int i = 0; i <= 15; i++) {
                TimeUnit.MILLISECONDS.sleep(200);

                if (lblContadorTempo.getForeground() == Color.WHITE) {
                    lblContadorTempo.setForeground(Color.BLACK);
                } else {
                    lblContadorTempo.setForeground(Color.WHITE);
                }
            }
            lblContadorTempo.setForeground(Color.WHITE);

        } catch (InterruptedException ex) {
            Logger.getLogger(CronometroJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JPopupMenu();
        iniciar = new javax.swing.JMenuItem();
        pausar = new javax.swing.JMenuItem();
        resetar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        configurar = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        sobre = new javax.swing.JMenuItem();

        menu.setToolTipText("");
        menu.setComponentPopupMenu(menu);

        iniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projcronometro/recursos/play-button.png"))); // NOI18N
        iniciar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iniciar.setLabel("Iniciar [Ctrl + I]");
        iniciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                iniciarMousePressed(evt);
            }
        });
        menu.add(iniciar);

        pausar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projcronometro/recursos/pause.png"))); // NOI18N
        pausar.setText("Pausar [Ctrl + P]");
        pausar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pausar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pausarMousePressed(evt);
            }
        });
        menu.add(pausar);

        resetar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projcronometro/recursos/time.png"))); // NOI18N
        resetar.setText("Resetar [Ctrl + R]");
        resetar.setToolTipText("");
        resetar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resetar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                resetarMousePressed(evt);
            }
        });
        menu.add(resetar);
        menu.add(jSeparator1);

        configurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projcronometro/recursos/settings.png"))); // NOI18N
        configurar.setText("Configurar [Ctrl + C]");
        configurar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        configurar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                configurarMousePressed(evt);
            }
        });
        menu.add(configurar);
        menu.add(jSeparator2);

        sobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projcronometro/recursos/question.png"))); // NOI18N
        sobre.setText("Sobre");
        sobre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sobre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sobreMousePressed(evt);
            }
        });
        menu.add(sobre);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cronômetro");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(0, 0, 0));
        setIconImages(null);
        setPreferredSize(new java.awt.Dimension(520, 170));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        //Ctrl + I
        if (evt.getKeyCode() == KeyEvent.VK_I) {
            if (evt.isControlDown()) {
                rodar();
            }
        }

        //Ctrl + P
        if (evt.getKeyCode() == KeyEvent.VK_P) {
            if (evt.isControlDown()) {
                if (!pausado) {
                    pausar();
                } else {
                    continuar();
                }
            }
        }

        //Ctrl + R
        if (evt.getKeyCode() == KeyEvent.VK_R) {
            if (evt.isControlDown()) {
                resetar();
            }
        }

        //Ctrl + C
        if (evt.getKeyCode() == KeyEvent.VK_C) {
            if (evt.isControlDown()) {
                configurar();
            }
        }

    }//GEN-LAST:event_formKeyPressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if (evt.isPopupTrigger()) {

            if (!rodando) {
                iniciar.setEnabled(true);
                pausar.setEnabled(this.pausado && !this.resetado);
                resetar.setEnabled(this.pausado && !this.resetado);

                configurar.setEnabled(!this.pausado || this.resetado);
            } else {
                iniciar.setEnabled(false);
                pausar.setEnabled(!this.pausado);
                resetar.setEnabled(!this.pausado);

                configurar.setEnabled(false);
            }

            if (!this.pausado) {
                pausar.setText("Pausar [Ctrl + P]");
                pausar.setIcon(recursos.obterIconePausar());
            } else {
                pausar.setText("Continuar [Ctrl + P]");
                pausar.setIcon(recursos.obterIconeContinuar());
            }

            menu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_formMouseReleased

    private void configurarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_configurarMousePressed
        configurar();        
    }//GEN-LAST:event_configurarMousePressed

    private void iniciarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iniciarMousePressed
        rodar();
    }//GEN-LAST:event_iniciarMousePressed

    private void pausarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pausarMousePressed
        if (!pausado) {
            pausar();
        } else {
            continuar();
        }
    }//GEN-LAST:event_pausarMousePressed

    private void resetarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetarMousePressed
        resetar();
    }//GEN-LAST:event_resetarMousePressed

    private void sobreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sobreMousePressed
        menu.setVisible(false);
        Mensagem.exibir("- Desenvolvido por Daniel Armando Brandão" + "\n" + 
                "- E-mail: daniel.armando@live.com" + "\n" +
                "- Ícones por Smashicons de flaticon.com" + "\n", 
                QUESTION_MESSAGE);
    }//GEN-LAST:event_sobreMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CronometroJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CronometroJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CronometroJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CronometroJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CronometroJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem configurar;
    private javax.swing.JMenuItem iniciar;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu menu;
    private javax.swing.JMenuItem pausar;
    private javax.swing.JMenuItem resetar;
    private javax.swing.JMenuItem sobre;
    // End of variables declaration//GEN-END:variables
}

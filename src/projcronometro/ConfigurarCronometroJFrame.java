package projcronometro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static projcronometro.TipoContagem.PROGRESSIVA;
import static projcronometro.TipoContagem.REGRESSIVA;

public class ConfigurarCronometroJFrame extends JFrame {

    private TipoContagem contagem;
    private int limiteDeTempo;
    private JLabel lblContadorTempo;

    private final String DIRETORIO_ARQUIVO = "C:/Cronometro/";
    private final String NOME_ARQUIVO = "cronometro.config";        
    
    public ConfigurarCronometroJFrame() {
        initComponents();
        JOptionPane.setRootFrame(this); 
        carregarIcone();
        buscarArquivoConfiguracao();
    }

    public ConfigurarCronometroJFrame(JLabel lblContadorTempo) { 
        this();
        this.lblContadorTempo = lblContadorTempo;
    }
    
    private void carregarIcone() {        
        this.setIconImage(new Recursos().obterIcone());
    }
    
    private void buscarArquivoConfiguracao() {
        File file = new File(DIRETORIO_ARQUIVO + NOME_ARQUIVO);
        if (file.exists()) {
            try (BufferedReader buffRead = new BufferedReader(new FileReader(DIRETORIO_ARQUIVO + NOME_ARQUIVO))) {
                String linha = buffRead.readLine();
                String[] config = linha.split(";");

                //tipo
                if (config[0].substring(0, 8).equals("CONTAGEM")) {
                    if (config[0].substring(9).equals(PROGRESSIVA.toString())) {
                        rdbProgressivo.setSelected(true);
                        rdbProgressivo.requestFocus();
                    } else {
                        rdbRegressivo.setSelected(true);
                        rdbRegressivo.requestFocus();
                    }
                }

                //hora
                if (config[1].substring(0, 4).equals("HORA")) {
                    if (Integer.valueOf(config[1].substring(5)) <= 99) {
                        txtHora.setText(config[1].substring(5));
                    }
                }

                //minuto
                if (config[2].substring(0, 6).equals("MINUTO")) {
                    if (Integer.valueOf(config[2].substring(7)) <= 60) {
                        txtMinuto.setText(config[2].substring(7));
                    }
                }

                //segundo
                if (config[3].substring(0, 7).equals("SEGUNDO")) {
                    if (Integer.valueOf(config[3].substring(8)) <= 60) {
                        txtSegundo.setText(config[3].substring(8));
                    }
                }
            } catch (IOException e) {                
                Mensagem.exibir("Não foi possível ler o arquivo de configuração. Erro: " + e.getMessage(), WARNING_MESSAGE);
                this.rdbProgressivo.setSelected(true);
                this.txtHora.setText("00");
                this.txtMinuto.setText("00");
                this.txtSegundo.setText("00");
            }                        
        }    
        
        prepararConfiguracao();
    }
    
    private void prepararConfiguracao() {
        txtHora.setText(String.format("%02d", Integer.parseInt(txtHora.getText())));
        txtMinuto.setText(String.format("%02d", Integer.parseInt(txtMinuto.getText()))); 
        txtSegundo.setText(String.format("%02d", Integer.parseInt(txtSegundo.getText())));        
        contagem = rdbRegressivo.isSelected() ? REGRESSIVA : PROGRESSIVA;
        limiteDeTempo = Integer.valueOf(txtSegundo.getText())
                + (Integer.valueOf(txtMinuto.getText()) * 60)
                + (Integer.valueOf(txtHora.getText()) * 3600);
    }

    private void salvarArquivoConfiguracao() {
        try {
            //diretório
            File file = new File(DIRETORIO_ARQUIVO);
            file.mkdirs();

            //arquivo
            file = new File(DIRETORIO_ARQUIVO + NOME_ARQUIVO);
            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedWriter buffWrite = new BufferedWriter(new FileWriter(DIRETORIO_ARQUIVO + NOME_ARQUIVO))) {
                buffWrite.append("CONTAGEM=" + contagem.toString());
                buffWrite.append(";");
                buffWrite.append("HORA=" + txtHora.getText());                  
                buffWrite.append(";");
                buffWrite.append("MINUTO=" + txtMinuto.getText());
                buffWrite.append(";");
                buffWrite.append("SEGUNDO=" + txtSegundo.getText());
            } catch (Exception e) {                
                Mensagem.exibir("Não foi possível montar o arquivo de configuração. Erro: " + e.getMessage(), WARNING_MESSAGE);                
            }
        } catch (IOException e) {            
            Mensagem.exibir("Não foi possível salvar o arquivo de configuração no computador. Erro: " + e.getMessage(), WARNING_MESSAGE);
        }
    }  
    
    private boolean configuracaoEstaOK() {
        if (contagem == REGRESSIVA && limiteDeTempo <= 0) {            
            Mensagem.exibir("Quando o tipo do cronômetro for regressivo, é obrigatório informar um limite de tempo.", WARNING_MESSAGE);
            txtSegundo.requestFocus();
            return false;
        }
        return true;
    }   

    public TipoContagem obterTipoCronometro() {
        return contagem == null ? PROGRESSIVA : contagem;
    }

    public int obterLimiteDeTempo() {
        return limiteDeTempo;
    }

    public String obterTextoTempo() {
        if (contagem == PROGRESSIVA) {
            return "00:00:00";
        }
        
        return txtHora.getText() + ":" + txtMinuto.getText() + ":" + txtSegundo.getText();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        rdbProgressivo = new javax.swing.JRadioButton();
        rdbRegressivo = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        txtHora = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMinuto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSegundo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Configurações");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Contagem");
        jLabel1.setToolTipText("");

        buttonGroup1.add(rdbProgressivo);
        rdbProgressivo.setText("Progressiva");
        rdbProgressivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbProgressivoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbRegressivo);
        rdbRegressivo.setText("Regressiva");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Limite de tempo (HH:MM:SS)");

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projcronometro/recursos/save.png"))); // NOI18N
        btnSalvar.setText("SALVAR");
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalvarMouseClicked(evt);
            }
        });

        txtHora.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        txtHora.setText("00");
        txtHora.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHoraKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel3.setText(":");

        txtMinuto.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        txtMinuto.setText("00");
        txtMinuto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMinutoFocusLost(evt);
            }
        });
        txtMinuto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMinutoKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel4.setText(":");

        txtSegundo.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        txtSegundo.setText("00");
        txtSegundo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSegundoFocusLost(evt);
            }
        });
        txtSegundo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSegundoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(rdbProgressivo)
                            .addGap(18, 18, 18)
                            .addComponent(rdbRegressivo)
                            .addGap(3, 3, 3))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtSegundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbProgressivo)
                    .addComponent(rdbRegressivo))
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSegundo, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(btnSalvar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdbProgressivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbProgressivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdbProgressivoActionPerformed

    private void btnSalvarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseClicked
        prepararConfiguracao();        
        
        if (configuracaoEstaOK()) {            
            salvarArquivoConfiguracao();              
            lblContadorTempo.setText(obterTextoTempo());
            this.dispose();
        }
                
    }//GEN-LAST:event_btnSalvarMouseClicked

    private void txtSegundoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSegundoKeyTyped
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }

        if (txtSegundo.getText().length() > 1) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSegundoKeyTyped

    private void txtSegundoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSegundoFocusLost
        if (Integer.valueOf(txtSegundo.getText()) > 60) {            
            Mensagem.exibir("Valor máximo aceito para segundos: 60", WARNING_MESSAGE);
            txtSegundo.requestFocus();
        }
    }//GEN-LAST:event_txtSegundoFocusLost

    private void txtMinutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMinutoFocusLost
        if (Integer.valueOf(txtMinuto.getText()) > 60) {            
            Mensagem.exibir("Valor máximo aceito para minutos: 60", WARNING_MESSAGE);
            txtMinuto.requestFocus();
        }
    }//GEN-LAST:event_txtMinutoFocusLost

    private void txtMinutoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMinutoKeyTyped
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }

        if (txtMinuto.getText().length() > 1) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMinutoKeyTyped

    private void txtHoraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHoraKeyTyped
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }

        if (txtHora.getText().length() > 1) {
            evt.consume();
        }
    }//GEN-LAST:event_txtHoraKeyTyped

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
            java.util.logging.Logger.getLogger(ConfigurarCronometroJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigurarCronometroJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigurarCronometroJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigurarCronometroJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfigurarCronometroJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JRadioButton rdbProgressivo;
    private javax.swing.JRadioButton rdbRegressivo;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtMinuto;
    private javax.swing.JTextField txtSegundo;
    // End of variables declaration//GEN-END:variables

}

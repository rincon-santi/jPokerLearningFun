/*
 * Copyright (C) 2018 Santiago Rincon Martinez <rincon.santi@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package exercises.studentsSection;

import exercises.utils.Message;
import exercises.utils.Question;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import exercises.utils.Card;
import exercises.utils.ImageManager;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class QuesView extends javax.swing.JFrame {
    
    private final Question _q;
    private final javax.swing.JFrame _f;
    private static final String NOCARDPATH="resources/images/img_40008_ins_3732592_600.jpg";
    
    /**
     * Creates new form Quizzing
     */
    public QuesView(Question q, String answ, String expl, javax.swing.JFrame father) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initComponents();
        _q=q;
        _f=father;
        setTitle(_q.getName());
        jLabel15.setText(_q.getDescription());
        Vector<String> aux=new Vector<String>();
        jLabel16.setText(answ);
        jTextArea1.setText(expl);
        String auxString="UNDEFINED";
        for (String st : _q.getOpts().keySet()) if (_q.getOpts().get(st).equals(new Double(10))) auxString=st;
        jLabel20.setText(auxString);
        jLabel18.setText(_q.getPoints(answ).toString());
        jLabel4.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(Card.getCardPath(_q.getMyCards().get(0))).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
        jLabel5.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(Card.getCardPath(_q.getMyCards().get(1))).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
        List<Card> comCards=_q.getCommmunityCards();
        if(comCards.size()>0){
            jLabel6.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(Card.getCardPath(comCards.get(0))).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
            if (comCards.size()>1){
                jLabel10.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(Card.getCardPath(comCards.get(1))).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
                if (comCards.size()>2){
                    jLabel7.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(Card.getCardPath(comCards.get(2))).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
                    if (comCards.size()>3){
                        jLabel9.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(Card.getCardPath(comCards.get(3))).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
                        if (comCards.size()>4){
                            jLabel8.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(Card.getCardPath(comCards.get(4))).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));      
                        }else jLabel8.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
                    }else{
                        jLabel8.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
                        jLabel9.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
                    }
                }else{
                    jLabel8.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
                    jLabel7.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
                    jLabel9.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
                }
            }else{
                jLabel8.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
                jLabel7.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
                jLabel9.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
                jLabel10.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
            }
        }else{
            jLabel6.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
            jLabel7.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
            jLabel8.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
            jLabel9.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
            jLabel10.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(NOCARDPATH).getScaledInstance(67, 75, Image.SCALE_DEFAULT)));
        }
        addWindowListener(new closer(this, _f));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);

        jLabel2.setText("Your explanation");

        jButton1.setBackground(new java.awt.Color(2, 143, 9));
        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Your Hand");

        jLabel11.setText("Community");

        jLabel14.setText("Description");

        jLabel15.setText("jLabel15");

        jLabel1.setText("Your Answer");

        jLabel16.setText("jLabel4");

        jLabel17.setText("Points");

        jLabel18.setText("jLabel4");

        jLabel19.setText("Correct Answer");

        jLabel20.setText("jLabel4");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(60, 60, 60)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3))
                        .addGap(0, 48, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel11)
                        .addGap(72, 72, 72))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel16)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private class closer extends WindowAdapter{
        
        private javax.swing.JFrame _fa, _fafa;
        
        public closer(javax.swing.JFrame fa, javax.swing.JFrame fafa){
            _fa=fa;
            _fafa=fafa;
        }
        @Override
        public void windowClosing(WindowEvent e) {
            _fafa.setVisible(true);
            _fa.dispose();
        }
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        _f.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}

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
public class Quizzing extends javax.swing.JFrame {
    
    private final int _kind;
    private final Question _q;
    private final javax.swing.JFrame _f;
    private static final String NOCARDPATH="resources/images/img_40008_ins_3732592_600.jpg";
    
    /**
     * Creates new form Quizzing
     */
    public Quizzing(Question q, javax.swing.JFrame father, int kind) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Quizzing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Quizzing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Quizzing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Quizzing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initComponents();
        _kind=kind;
        _q=q;
        _f=father;
        setTitle(_q.getName());
        jLabel15.setText(_q.getDescription());
        Vector<String> aux=new Vector<String>();
        jComboBox1.removeAllItems();
        aux=new Vector<String>();
        for (String o : _q.getOpts().keySet()) jComboBox1.addItem(o);
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
        addWindowListener(new closer());
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
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);

        jLabel2.setText("You can explain your answer:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Your Answer");

        jButton1.setBackground(new java.awt.Color(2, 143, 9));
        jButton1.setText("Next");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Your Hand");

        jLabel11.setText("Community");

        jLabel14.setText("Description");

        jLabel15.setText("jLabel15");

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
                        .addGap(23, 23, 23)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(44, 44, 44)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private class closer extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e) {
            new Message("CAN'T CLOSE UNTIL TEST IS FINISHED").setVisible(true);
        }
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (_kind==1) ((QuizzController)_f).answeredQuestion(_q, (String)_q.getOpts().keySet().toArray()[jComboBox1.getSelectedIndex()], jTextArea1.getText());
        else if (_kind==2) ((StudentGUI)_f).answeredQuestion(_q, (String)_q.getOpts().keySet().toArray()[jComboBox1.getSelectedIndex()], jTextArea1.getText());
        else if (_kind==3) {
            _f.setVisible(true);
            dispose();
        }
        if (_kind!=3){
        String best="";
        if (_q.getOpts().containsValue(new Double(10))){
            for (String opt : _q.getOpts().keySet()) if (_q.getOpts().get(opt).equals(new Double(10))) best=opt;
        }
        if (best.equals("")) new Message("THE BEST ANSWER WAS UNDEFINED", "Best Option").setVisible(true);
        else new Message("THE BEST ANSWER WAS "+best, "Best Option").setVisible(true);
        }
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
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

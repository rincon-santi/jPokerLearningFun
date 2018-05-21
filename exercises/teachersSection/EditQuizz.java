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
package exercises.teachersSection;

import exercises.utils.Message;
import exercises.utils.GeneralQuestion;
import exercises.utils.ImageManager;
import exercises.utils.OtherQuestion;
import exercises.utils.Question;
import exercises.utils.Quizz;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class EditQuizz extends javax.swing.JFrame {
    private LinkedList<GeneralQuestion> _questionsUsable;
    private final TeacherGUI _father;
    private LinkedList<GeneralQuestion> _questions;
    private String _name;
    private final boolean editing;
    private final Quizz saved;
    
    private static final String pattern = Pattern.quote(System.getProperty("file.separator"));
    /**
     * Creates new form EditQuizz
     */
    public EditQuizz(LinkedList<GeneralQuestion> questions, TeacherGUI father) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditOtherQuestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditOtherQuestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditOtherQuestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditOtherQuestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Vector<String> aux=new Vector<String>();
        editing=false;
        saved=null;
        _questionsUsable=questions;
        _father=father;
        setTitle("Create Exam");
        initComponents();
        _questions=new LinkedList<GeneralQuestion>();
        for (GeneralQuestion q : _questions){
            aux.add(q.getName());
        }
        jList1.setListData(aux);
        jLabel4.setText(Integer.toString(_questions.size()*10));
        addWindowListener(new closer(this, _father));
    }
    public EditQuizz(Quizz qu, LinkedList<GeneralQuestion> questions, TeacherGUI father) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditOtherQuestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditOtherQuestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditOtherQuestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditOtherQuestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Vector<String> aux=new Vector<String>();
        editing=true;
        saved=qu;
        _questionsUsable=questions;
        _father=father;
        setTitle("Create Quizz");
        initComponents();
        _questions=qu.getQuestions();
        _name=qu.getName();
        jTextField1.setText(_name);
        for (GeneralQuestion q : _questions){
            aux.add(q.getName());
        }
        jList1.setListData(aux);
        jLabel4.setText(Integer.toString(_questions.size()));
        addWindowListener(new closer(this, _father));
    }
    
    private class closer extends WindowAdapter{
        private javax.swing.JFrame _fa, _fafa;
        public closer(javax.swing.JFrame fa, javax.swing.JFrame fafa) {_fa=fa; _fafa=fafa;}
        
        @Override
        public void windowClosing(WindowEvent e) {
            _fafa.setVisible(true);
            _fa.dispose();
        }
    }

    public void addQuestion(GeneralQuestion q){
        if(_questions.contains(q)) new Message("QUESTION ALREADY INCLUDED");
        else{
            Vector<String> aux=new Vector<String>();
            for (GeneralQuestion qu : _questions) aux.add(qu.getName());
            if (aux.contains(q.getName())) {
                String [] opts ={
                    "Rename",
                    "Overwrite"
                };
                Icon icon= new ImageIcon(ImageManager.INSTANCE.getImage(ImageManager.IMAGES_PATH+"poker-chip.png")); 
                String selectOpt= (String) JOptionPane.showInputDialog(null, "Name already in use", "WARNING", JOptionPane.DEFAULT_OPTION, icon, opts, opts[0]);
                switch (selectOpt){
                    case "Rename":
                        q.setName((String) JOptionPane.showInputDialog(null, "Introduce New Name", "Rename", JOptionPane.DEFAULT_OPTION));
                        break;
                    case "Overwrite":
                        for (GeneralQuestion que : _questions) if (que.getName().equals(q.getName())) _questions.remove(que);
                        aux.remove(q.getName());
                        break;
                }
            }
            if (q.getKind()==2){
                String [] auxSt;
                if (((OtherQuestion)q).getImgRoute().split("/")[0].equals("resources")) auxSt=((OtherQuestion)q).getImgRoute().split("/");
                else auxSt = ((OtherQuestion)q).getImgRoute().split(pattern);
                File destine=new File("resources/images/questionImages");
                auxSt[auxSt.length-1]=auxSt[auxSt.length-1].split(" ")[auxSt[auxSt.length-1].split(" ").length-1];
                if (!destine.exists()){
                    destine.mkdirs();
                }
                _father.moveImage(((OtherQuestion)q).getImgRoute(),"resources/images/questionImages/"+_name+auxSt[auxSt.length-1]);
                ((OtherQuestion)q).setImgRoute("resources/images/questionImages/"+_name+auxSt[auxSt.length-1]);
            }
            _questions.add(q);
            for (GeneralQuestion qu : _questions){
                aux.add(qu.getName());
            }
            jList1.setListData(aux);
            setVisible(true);
            jLabel4.setText(Integer.toString(_questions.size()*10));
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

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);

        jLabel1.setText("Name");

        jLabel2.setText("Questions");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jButton1.setBackground(new java.awt.Color(245, 23, 23));
        jButton1.setText("Delete Question");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(2, 143, 9));
        jButton2.setText("Add Existing Question");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Maximum Points");

        jLabel4.setText("0");

        jButton3.setBackground(new java.awt.Color(245, 23, 23));
        jButton3.setText("Cancel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(2, 143, 9));
        jButton4.setText("Submit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(234, 170, 56));
        jButton5.setText("Edit Question");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(2, 143, 9));
        jButton6.setText("Create Question");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(54, 54, 54)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addGap(4, 4, 4)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addGap(8, 8, 8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int index=jList1.getMinSelectionIndex();
        if (index==-1) new Message("NO QUESTIONS SELECTED").setVisible(true);
        else if (index!=jList1.getMaxSelectionIndex()) new Message("PLEASE SELECT ONLY ONE QUESTION").setVisible(true);
        else{
            GeneralQuestion q= _questions.get(index);
            _questions.remove(q);
            _questionsUsable.remove(q);
            if (q.getKind()==1) new EditQuestion((Question)q, this, 2).setVisible(true);
            else if (q.getKind()==2) new EditOtherQuestion((OtherQuestion)q, this, 2).setVisible(true);
            setVisible(false);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Vector<GeneralQuestion> toRem=new Vector<GeneralQuestion>();
        Vector<String> aux3=new Vector<String>();
        if (jList1.getMinSelectionIndex()==-1) new Message("NO QUESTIONS SELECTED").setVisible(true);
        else{
            for (int i=jList1.getMinSelectionIndex(); i<=jList1.getMaxSelectionIndex();i++){
                toRem.add(_questions.get(i));
            }
            for (GeneralQuestion rem : toRem) _questions.remove(rem);
            for (GeneralQuestion opt : _questions){
                aux3.add(opt.getName());
            }
            jList1.setListData(aux3);
            jLabel4.setText(Integer.toString(_questions.size()));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(editing) _father.addQuizz(saved);
        else _father.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (jTextField1.getText().equals("")) new Message("THE QUIZZ SHOULD HAVE A NAME").setVisible(true);
        else if (_questions.size()==0) new Message("THE QUIZZ SHOULD HAVE QUESTIONS").setVisible(true);
        else{
            _name=jTextField1.getText();
            Quizz q = new Quizz(_name, _questions);
            _father.addQuizz(q);
            dispose();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        new ChooseKindQues(this, 2).setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new ChooseQuestion(_questionsUsable, this).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

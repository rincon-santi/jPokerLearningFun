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

import exercises.utils.ChangePassword;
import exercises.utils.ExitConfirmation;
import exercises.utils.Message;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import exercises.studentsSection.OtherQuizzing;
import exercises.studentsSection.QuizzController;
import exercises.studentsSection.Quizzing;
import exercises.utils.Card;
import exercises.utils.SSHConnector;
import exercises.utils.GeneralQuestion;
import exercises.utils.OtherQuestion;
import java.util.LinkedList;
import java.util.Vector;
import exercises.utils.Question;
import exercises.utils.Quizz;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import exercises.utils.ImageManager;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class TeacherGUI extends javax.swing.JFrame {
    
    private String _name;
    private String _key;
    private LinkedList<GeneralQuestion> _questions;
    private LinkedList<Quizz> _quizzes;
    private final LinkedList<GeneralQuestion> _otherTques;
    private HashMap<String, Integer> _guideQuestions;
    private HashMap<String, Integer> _guideQuizzes;
    
    private static final String pattern = Pattern.quote(System.getProperty("file.separator"));

    /**
     * Creates new form TeacherGUI
     */
    public TeacherGUI(String name, String key, LinkedList<GeneralQuestion> questions, LinkedList<GeneralQuestion> otherTquestions, LinkedList<Quizz> quizzes) {
        
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
        initComponents();
        setTitle("Teacher's Area");
        jLabel4.setText(name);
        _name=name;
        _key=key;
        _otherTques=otherTquestions;
        Vector<String> es=new Vector<String>();
        for (GeneralQuestion q : _otherTques) es.add(q.getName());
        jList3.setListData(es);
        actualizeListQuestions(questions);
        actualizeListQuizzes(quizzes);
        addWindowListener(new closer(this));
    }
    
    private class closer extends WindowAdapter{
        javax.swing.JFrame _fa;
        public closer(javax.swing.JFrame fa){
            _fa=fa;
        }
        @Override
        public void windowClosing(WindowEvent e) {
            new ExitConfirmation(_fa).setVisible(true);
        }
    }
    
    
    private void actualizeListQuestions(LinkedList<GeneralQuestion> questions){
        Vector<String> es=new Vector<String>();
        _guideQuestions=new HashMap<String, Integer>();
        _questions=questions;
        for (GeneralQuestion q : questions){
            es.add(q.getName());
            _guideQuestions.put(q.getName(), questions.indexOf(q));
        }
        jList1.setListData(es);      
    }

    private void actualizeListQuizzes(LinkedList<Quizz> quizzes){
        Vector<String> es=new Vector<String>();
        _guideQuizzes=new HashMap<String, Integer>();
        _quizzes=quizzes;
        for (Quizz q : quizzes){
            es.add(q.getName());
            _guideQuizzes.put(q.getName(), quizzes.indexOf(q));
        }
        jList2.setListData(es);
        try {
            TeacherSaver.save(_name, _key, _questions, _quizzes);
        } catch (Exception ex) {
            Logger.getLogger(TeacherGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void changedPassword(String newPassword){
        _key=newPassword;
        try {
            TeacherSaver.save(_name, _key, _questions, _quizzes);
        } catch (Exception ex) {
            Logger.getLogger(TeacherGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        setVisible(true);
    }
    public void addQuestion(GeneralQuestion q){
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
                moveImage(((OtherQuestion)q).getImgRoute(),"resources/images/questionImages/"+_name+auxSt[auxSt.length-1]);
                ((OtherQuestion)q).setImgRoute("resources/images/questionImages/"+_name+auxSt[auxSt.length-1]);
            }
            _questions.add(q);
            actualizeListQuestions(_questions);
            setVisible(true);
        try {
            TeacherSaver.save(_name, _key, _questions, _quizzes);
        } catch (Exception ex) {
            Logger.getLogger(TeacherGUI.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList<>();
        jButton13 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel1.setText("Questions");

        jButton1.setBackground(new java.awt.Color(245, 23, 23));
        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        jLabel2.setText("Exams");

        jButton4.setBackground(new java.awt.Color(234, 170, 56));
        jButton4.setText("Change Password");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel3.setText("Logged In As");

        jLabel4.setText("jLabel4");

        jButton5.setBackground(new java.awt.Color(245, 23, 23));
        jButton5.setText("Exit");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(2, 143, 9));
        jButton6.setText("Add ");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(234, 170, 56));
        jButton2.setText("Edit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(2, 143, 9));
        jButton9.setText("Add");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(234, 170, 56));
        jButton10.setText("Edit");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(245, 23, 23));
        jButton11.setText("Delete");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(2, 143, 9));
        jButton7.setText("Export Students Answers");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(2, 143, 9));
        jButton8.setText("Export Students Answers");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(234, 170, 56));
        jButton3.setText("Preview");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(234, 170, 56));
        jButton12.setText("Preview");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jList3.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList3);

        jButton13.setBackground(new java.awt.Color(234, 170, 56));
        jButton13.setText("Preview");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel5.setText("Other Teachers Questions");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(72, 72, 72)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(37, 37, 37))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(228, 228, 228)
                                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel4)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(51, 51, 51)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1)
                                .addComponent(jButton2)
                                .addComponent(jButton6)
                                .addComponent(jButton3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton13))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton11)
                                .addComponent(jButton10)
                                .addComponent(jButton9)
                                .addComponent(jButton12)))))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new ExitConfirmation(this).setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new ChangePassword(_key, this, 1).setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        new ChooseKindQues(this, 1).setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

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
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int index=jList1.getMinSelectionIndex();
        if (index==-1) new Message("NO QUESTIONS SELECTED").setVisible(true);
        else if (index!=jList1.getMaxSelectionIndex()) new Message("PLEASE SELECT ONLY ONE QUESTION").setVisible(true);
        else{
            GeneralQuestion q= _questions.get(index);
            _questions.remove(q);
            if (q.getKind()==1) new EditQuestion((Question)q, this, 1).setVisible(true);
            else if(q.getKind()==2) new EditOtherQuestion((OtherQuestion)q, this, 1).setVisible(true);
            setVisible(false);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        LinkedList<GeneralQuestion> aux=new LinkedList<GeneralQuestion>();
        for (GeneralQuestion q : _questions){
            if (q.getKind()==1) {
                HashMap<String, Double> auxOpt=new HashMap();
                for (String opt : q.getOpts().keySet()) auxOpt.put(opt, q.getOpts().get(opt));
                Vector<String> auxLab=new Vector();
                for(String la : q.getLabels()) auxLab.add(la);
                LinkedList<Card> auxComCards=new LinkedList();
                LinkedList<Card> auxMyCards=new LinkedList();
                for (Card c : ((Question)q).getCommmunityCards()) auxComCards.add(c);
                for (Card ca : ((Question)q).getMyCards()) auxMyCards.add(ca);
                aux.add(new Question(q.getName(), auxOpt, auxComCards, auxMyCards,q.getDescription(),auxLab));
            }
            else if (q.getKind()==2) {
                HashMap<String, Double> auxOpt=new HashMap();
                for (String opt : q.getOpts().keySet()) auxOpt.put(opt, q.getOpts().get(opt));
                Vector<String> auxLab=new Vector();
                for(String la : q.getLabels()) auxLab.add(la);
                aux.add(new OtherQuestion(q.getName(), auxOpt, ((OtherQuestion)q).getImgRoute(), q.getDescription(), auxLab));
            }
        }
        for (GeneralQuestion q : _otherTques){
            if (q.getKind()==1) {
                HashMap<String, Double> auxOpt=new HashMap();
                for (String opt : q.getOpts().keySet()) auxOpt.put(opt, q.getOpts().get(opt));
                Vector<String> auxLab=new Vector();
                for(String la : q.getLabels()) auxLab.add(la);
                LinkedList<Card> auxComCards=new LinkedList();
                LinkedList<Card> auxMyCards=new LinkedList();
                for (Card c : ((Question)q).getCommmunityCards()) auxComCards.add(c);
                for (Card ca : ((Question)q).getMyCards()) auxMyCards.add(ca);
                aux.add(new Question(q.getName(), auxOpt, auxComCards, auxMyCards,q.getDescription(),auxLab));
            }
            else if (q.getKind()==2) {
                HashMap<String, Double> auxOpt=new HashMap();
                for (String opt : q.getOpts().keySet()) auxOpt.put(opt, q.getOpts().get(opt));
                Vector<String> auxLab=new Vector();
                for(String la : q.getLabels()) auxLab.add(la);
                aux.add(new OtherQuestion(q.getName(), auxOpt, ((OtherQuestion)q).getImgRoute(), q.getDescription(), auxLab));
            }
        }
        new EditQuizz(aux,this).setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int index=jList2.getMinSelectionIndex();
        if (index==-1) new Message("NO EXAMS SELECTED").setVisible(true);
        else if (index!=jList2.getMaxSelectionIndex()) new Message("PLEASE SELECT ONLY ONE EXAM").setVisible(true);
        else{
            Quizz qu= _quizzes.get(index);
            _quizzes.remove(qu);
            LinkedList<GeneralQuestion> aux=new LinkedList();
            for (GeneralQuestion q : _questions){
                if (q.getKind()==1) {
                    HashMap<String, Double> auxOpt=new HashMap();
                    for (String opt : q.getOpts().keySet()) auxOpt.put(opt, q.getOpts().get(opt));
                    Vector<String> auxLab=new Vector();
                    for(String la : q.getLabels()) auxLab.add(la);
                    LinkedList<Card> auxComCards=new LinkedList();
                    LinkedList<Card> auxMyCards=new LinkedList();
                    for (Card c : ((Question)q).getCommmunityCards()) auxComCards.add(c);
                    for (Card ca : ((Question)q).getMyCards()) auxMyCards.add(ca);
                    aux.add(new Question(q.getName(), auxOpt, auxComCards, auxMyCards,q.getDescription(),auxLab));
                }
                else if (q.getKind()==2) {
                    HashMap<String, Double> auxOpt=new HashMap();
                    for (String opt : q.getOpts().keySet()) auxOpt.put(opt, q.getOpts().get(opt));
                    Vector<String> auxLab=new Vector();
                    for(String la : q.getLabels()) auxLab.add(la);
                    aux.add(new OtherQuestion(q.getName(), auxOpt, ((OtherQuestion)q).getImgRoute(), q.getDescription(), auxLab));
                }
            }
            for (GeneralQuestion q : _otherTques){
                if (q.getKind()==1) {
                    HashMap<String, Double> auxOpt=new HashMap();
                    for (String opt : q.getOpts().keySet()) auxOpt.put(opt, q.getOpts().get(opt));
                    Vector<String> auxLab=new Vector();
                    for(String la : q.getLabels()) auxLab.add(la);
                    LinkedList<Card> auxComCards=new LinkedList();
                    LinkedList<Card> auxMyCards=new LinkedList();
                    for (Card c : ((Question)q).getCommmunityCards()) auxComCards.add(c);
                    for (Card ca : ((Question)q).getMyCards()) auxMyCards.add(ca);
                    aux.add(new Question(q.getName(), auxOpt, auxComCards, auxMyCards,q.getDescription(),auxLab));
                }
                else if (q.getKind()==2) {
                    HashMap<String, Double> auxOpt=new HashMap();
                    for (String opt : q.getOpts().keySet()) auxOpt.put(opt, q.getOpts().get(opt));
                    Vector<String> auxLab=new Vector();
                    for(String la : q.getLabels()) auxLab.add(la);
                    aux.add(new OtherQuestion(q.getName(), auxOpt, ((OtherQuestion)q).getImgRoute(), q.getDescription(), auxLab));
                }
            }
            new EditQuizz(qu, aux, this).setVisible(true);
            setVisible(false);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        Vector<Quizz> toRem=new Vector<Quizz>();
        Vector<String> aux3=new Vector<String>();
        if (jList2.getMinSelectionIndex()==-1) new Message("NO QUIZZES SELECTED").setVisible(true);
        else{
            for (int i=jList2.getMinSelectionIndex(); i<=jList2.getMaxSelectionIndex();i++){
                toRem.add(_quizzes.get(i));
            }
            for (Quizz rem : toRem) _quizzes.remove(rem);
            actualizeListQuizzes(_quizzes);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int index=jList1.getMinSelectionIndex();
        if (index==-1) new Message("NO QUESTIONS SELECTED").setVisible(true);
        else if (index!=jList1.getMaxSelectionIndex()) new Message("PLEASE SELECT ONLY ONE QUESTION").setVisible(true);
        else{
            GeneralQuestion q= _questions.get(index);
            try {
                TeacherSaver.exportQuestdata(_name, q);
                new Message("Data exported to question_"+q.getName()+"_data.csv", "EXPORTING CORRECT").setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(TeacherGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int index=jList2.getMinSelectionIndex();
        if (index==-1) new Message("NO EXAMS SELECTED").setVisible(true);
        else if (index!=jList2.getMaxSelectionIndex()) new Message("PLEASE SELECT ONLY ONE EXAM").setVisible(true);
        else{
            Quizz q= _quizzes.get(index);
            try {
                TeacherSaver.exportTestdata(_name, q);
                new Message("Data exported to "+q.getName()+"_data.csv", "EXPORTING CORRECT").setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(TeacherGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int index=jList1.getMinSelectionIndex();
        if (index==-1) new Message("NO QUESTIONS SELECTED").setVisible(true);
        else if (index!=jList1.getMaxSelectionIndex()) new Message("PLEASE SELECT ONLY ONE QUESTION").setVisible(true);
        else{
            GeneralQuestion q= _questions.get(index);
            if (q.getKind()==1) new Quizzing((Question)q, this, 3).setVisible(true);
            else if(q.getKind()==2) new OtherQuizzing((OtherQuestion)q, this, 3).setVisible(true);
            setVisible(false);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int index=jList2.getMinSelectionIndex();
        if (index==-1) new Message("NO EXAMS SELECTED").setVisible(true);
        else if (index!=jList2.getMaxSelectionIndex()) new Message("PLEASE SELECT ONLY ONE EXAM").setVisible(true);
        else{
            Quizz q= _quizzes.get(index);
            new QuizzController(q, this,2).runQuizz();
            setVisible(false);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        int index=jList3.getMinSelectionIndex();
        if (index==-1) new Message("NO QUESTIONS SELECTED").setVisible(true);
        else if (index!=jList3.getMaxSelectionIndex()) new Message("PLEASE SELECT ONLY ONE QUESTION").setVisible(true);
        else{
            GeneralQuestion q= _otherTques.get(index);
            switch (q.getKind()){
                case 1:
                    new Quizzing((Question)q, this,3).setVisible(true);
                    setVisible(false);
                    break;
                case 2:
                    new OtherQuizzing((OtherQuestion)q, this, 3).setVisible(true);
                    setVisible(false);
                    break;
            }
        }
    }//GEN-LAST:event_jButton13ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JList<String> jList3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables

    public void addQuizz(Quizz q) {
        Vector<String> aux=new Vector<String>();
        for (Quizz qu : _quizzes) aux.add(qu.getName());
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
        _quizzes.add(q);
        actualizeListQuizzes(_quizzes);
        setVisible(true);
    }

    public static void moveImage(String imgRoute, String string) {
        File origen = new File(imgRoute);
        File destino = new File(string);
        if (!origen.exists()) try{
            String [] aux=imgRoute.split("/");
            String auxSt="";
            for (int i=0; i<aux.length-2; i++) {
                auxSt+=aux[i];
                auxSt+="/";
            }
            if (aux.length>1) auxSt+=aux[aux.length-2];
            SSHConnector.getFile(auxSt, aux[aux.length-1]);
            origen=new File(imgRoute);
        }catch (Exception ex){
            Logger.getLogger(TeacherGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!destino.exists()) try {
            destino.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(TeacherGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        try {
            InputStream in = new FileInputStream(origen);
            OutputStream out = new FileOutputStream(destino);

            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();
            String [] aux=string.split("/");
            String auxSt="";
            for (int i=0; i<aux.length-2; i++) {
                auxSt+=aux[i];
                auxSt+="/";
            }
            if(aux.length>1) auxSt+=aux[aux.length-2];
            try {
                try {
                    SSHConnector.putFile(auxSt, aux[aux.length-1]);
                } catch (SftpException ex) {
                    Logger.getLogger(TeacherGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (JSchException ex) {
                Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}

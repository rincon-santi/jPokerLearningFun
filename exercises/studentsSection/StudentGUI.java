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

import exercises.utils.ChangePassword;
import exercises.teachersSection.EditOtherQuestion;
import exercises.utils.ExitConfirmation;
import exercises.utils.Message;
import exercises.teachersSection.TeacherGUI;
import exercises.teachersSection.TeacherSaver;
import exercises.utils.GeneralQuestion;
import exercises.utils.OtherQuestion;
import exercises.utils.Question;
import exercises.utils.Quizz;
import exercises.utils.TeacherDatabaseLoad;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import exercises.utils.Pair;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class StudentGUI extends javax.swing.JFrame {

    private Student _student;
    private Double _average;
    private Double _n;
    private Vector<String> _selectedQuestions;
    private Vector<String> _selectedQuizzes;
    private LinkedList<String> _labels;
    private boolean _disabled;
    /**
     * Creates new form StudentGUI
     */
    public StudentGUI(Student s){
        addWindowListener(new closer(this));
        _disabled=true;
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
        _student=s;
        try {
            _labels=TeacherDatabaseLoad.labelsLoad();
            jComboBox1.removeAllItems();
            jComboBox2.removeAllItems();
            jComboBox1.addItem("All");
            jComboBox2.addItem("All");
            for (String lab : _labels){
                jComboBox1.addItem(lab);
                jComboBox2.addItem(lab);
            }
            jComboBox1.setSelectedItem("All");
            jComboBox2.setSelectedItem("All");
            _disabled=false;
        } catch (Exception ex) {
            Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        Vector<String> aux=new Vector<String>();
        for(GeneralQuestion q : _student.getQuestionsAvaliable()){
            if (_student.getQuestionsDone().containsKey(q)) aux.add(q.getName().split("\n")[0]+"->"+q.getName().split("\n")[1]+": "+q.getPoints(_student.getQuestionsDone().get(q).getFirst()));
            else aux.add(q.getName().split("\n")[0]+"->"+q.getName().split("\n")[1]);
        }
        _selectedQuestions=aux;
        aux=new Vector<String>();
        for(Quizz q : _student.getTestsAvaliable()) aux.add(q.getName().split("\n")[0]+"->"+q.getName().split("\n")[1]);
        _selectedQuizzes=aux;
        resetView();
    }
    
    public void resetView(){
        jLabel5.setText(_student.getName());
        Vector<String> aux=new Vector<String>();
        aux=new Vector<String>();
        _average=new Double(0);
        _n=new Double(_student.getTestsDone().keySet().size());
        String item=(String)jComboBox1.getSelectedItem();
        if (item.equals("All")){
            Vector<String> aux2=new Vector<String>();
            for(GeneralQuestion q : _student.getQuestionsAvaliable()){
                if (_student.getQuestionsDone().containsKey(q)) aux2.add(q.getName().split("\n")[0]+"->"+q.getName().split("\n")[1]+": "+q.getPoints(_student.getQuestionsDone().get(q).getFirst()));
                else aux2.add(q.getName().split("\n")[0]+"->"+q.getName().split("\n")[1]);
            }
            _selectedQuestions=aux2;
        } else{
            Vector<String> aux2=new Vector<String>();
            for(GeneralQuestion q : _student.getQuestionsAvaliable()){
                boolean yes=false;
                if (q.getLabels().contains(item)) yes=true;
                if (yes){
                    if (_student.getQuestionsDone().containsKey(q)) aux2.add(q.getName().split("\n")[0]+"->"+q.getName().split("\n")[1]+": "+q.getPoints(_student.getQuestionsDone().get(q).getFirst()));
                    else aux2.add(q.getName().split("\n")[0]+"->"+q.getName().split("\n")[1]);
                }
            }
            _selectedQuestions=aux2;
        }
        item=(String)jComboBox2.getSelectedItem();
        if (item.equals("All")){
            Vector<String> aux2=new Vector<String>();
            for(Quizz q : _student.getTestsAvaliable()) aux2.add(q.getName().split("\n")[0]+"->"+q.getName().split("\n")[1]);
            _selectedQuizzes=aux2;
        } else{
            Vector<String> aux2=new Vector<String>();
            for(Quizz q : _student.getTestsAvaliable()){
                boolean yes=false;
                for (GeneralQuestion que : q.getQuestions()) if (que.getLabels().contains(item)) yes=true;
                if (yes) aux2.add(q.getName().split("\n")[0]+"->"+q.getName().split("\n")[1]);
            }
            _selectedQuizzes=aux2;
        }
        jList1.setListData(_selectedQuestions);
        jList3.setListData(_selectedQuizzes);
        for(Quizz q : _student.getTestsDone().keySet()){
            aux.add(q.getName().split("\n")[0]+"->"+q.getName().split("\n")[1]+": "+(q.getPoints(_student.getTestsDone().get(q))).toString());
            _average=_average+(q.getPoints(_student.getTestsDone().get(q))/q.getMax());
        }
        _average=_average/_n;
        jLabel7.setText(_average.toString());
        jList2.setListData(aux);
    }
    
    public void changedPassword(String newPassword){
        _student.setKey(newPassword);
        try {
            StudentSaver.saveStudent(_student);
        } catch (Exception ex) {
            Logger.getLogger(TeacherGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        setVisible(true);
    }
    
    public void quizzDone(Quizz q, HashMap<GeneralQuestion, Pair<String, String>> answ){
        try {
            _student.addTestDone(q, answ);
        } catch (Exception ex) {
            Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            StudentSaver.saveStudent(_student);
        } catch (IOException ex) {
            Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        resetView();
        setVisible(true);
    }

    void answeredQuestion(GeneralQuestion q, String answ, String expl){
        try {
            _student.addQuestionDone(q, answ, expl);
        } catch (Exception ex) {
            Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            StudentSaver.saveStudent(_student);
        } catch (IOException ex) {
            Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        resetView();
        setVisible(true);
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);

        jLabel1.setText("Questions");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jButton1.setBackground(new java.awt.Color(2, 143, 9));
        jButton1.setText("Ask Me!");
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

        jButton2.setBackground(new java.awt.Color(234, 170, 56));
        jButton2.setText("Test Me!");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Previous Exams");

        jList3.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList3);

        jLabel4.setText("Logged In As");

        jLabel5.setText("jLabel5");

        jLabel6.setText("Average Puntuation");

        jLabel7.setText("jLabel7");

        jButton3.setBackground(new java.awt.Color(234, 170, 56));
        jButton3.setText("Change Password");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(245, 23, 23));
        jButton4.setText("Exit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(2, 143, 9));
        jButton5.setText("View Question");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(2, 143, 9));
        jButton6.setText("View Exam");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(234, 170, 56));
        jButton7.setText("Export Data");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(50, 50, 50)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(jLabel5))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(67, 67, 67))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton5)
                            .addComponent(jButton6))
                        .addGap(0, 5, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new ChangePassword(_student.getKey(), this, 2).setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new ExitConfirmation(this).setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(jList1.getMaxSelectionIndex()==-1) new Message("NO QUESTIONS SELECTED").setVisible(true);
        else if(jList1.getMaxSelectionIndex()!=jList1.getMinSelectionIndex()) new Message("PLEASE SELECT ONLY ONE QUESTION").setVisible(true);
        else{
            String nameOfQuestion=_selectedQuestions.elementAt(jList1.getMaxSelectionIndex());
            GeneralQuestion question=null;
            for (GeneralQuestion q : _student.getQuestionsAvaliable()){
                if ((q.getName().split("\n")[0].equals(nameOfQuestion.split("->")[0])) && (q.getName().split("\n")[1].equals(nameOfQuestion.split("->")[1].split(":")[0]))) question=q;
            }
            if(!(question==null)){
                if (question.getKind()==1){
                    new Quizzing((Question)question, this, 2).setVisible(true);
                }
                else{
                    new OtherQuizzing((OtherQuestion)question, this, 2).setVisible(true);
                }
            }
            setVisible(false);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(jList3.getMaxSelectionIndex()==-1) new Message("NO TESTS SELECTED").setVisible(true);
        else if(jList3.getMaxSelectionIndex()!=jList3.getMinSelectionIndex()) new Message("PLEASE SELECT ONLY ONE TEST").setVisible(true);
        else{
            String nameOfQuizz=_selectedQuizzes.elementAt(jList3.getMaxSelectionIndex());
            Quizz quiz=null;
            for (Quizz q : _student.getTestsAvaliable()){
                if ((q.getName().split("\n")[0].equals(nameOfQuizz.split("->")[0])) && (q.getName().split("\n")[1].equals(nameOfQuizz.split("->")[1]))) quiz=q;
            }
            if(!(quiz==null)) new QuizzController(quiz, this, 1).runQuizz();
            setVisible(false);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (!_disabled) resetView();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        if (!_disabled) resetView();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(jList1.getMaxSelectionIndex()==-1) new Message("NO QUESTIONS SELECTED").setVisible(true);
        else if(jList1.getMaxSelectionIndex()!=jList1.getMinSelectionIndex()) new Message("PLEASE SELECT ONLY ONE QUESTION").setVisible(true);
        else{
            String nameOfQuestion=_selectedQuestions.elementAt(jList1.getMaxSelectionIndex());
            GeneralQuestion question=null;
            for (GeneralQuestion q : _student.getQuestionsDone().keySet()){
                if ((q.getName().split("\n")[0].equals(nameOfQuestion.split("->")[0])) && (q.getName().split("\n")[1].equals(nameOfQuestion.split("->")[1].split(":")[0]))) question=q;
            }
            if(!(question==null)){
                if (question.getKind()==1){
                    new QuesView((Question)question, _student.getQuestionsDone().get(question).getFirst(), _student.getQuestionsDone().get(question).getSecond(),this).setVisible(true);
                    setVisible(false);
                }
                else{
                    new OtherQuesView((OtherQuestion)question, _student.getQuestionsDone().get(question).getFirst(), _student.getQuestionsDone().get(question).getSecond(), this).setVisible(true);
                    setVisible(false);
                }
            }
            else new Message("YOU MUST SELECT A DONE QUESTION TO CHECK").setVisible(true);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (jList2.getMinSelectionIndex()==-1) new Message("NO EXAMS SELECTED").setVisible(true);
        else if (jList2.getMinSelectionIndex()!=jList2.getMaxSelectionIndex()) new Message("PLEASE SELECT ONLY ONE EXAM").setVisible(true);
        else{
            Quizz q=(Quizz)_student.getTestsDone().keySet().toArray()[jList2.getMaxSelectionIndex()];
            new QuizViewer(q, _student.getTestsDone().get(q), this).setVisible(true);
            setVisible(false);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            StudentSaver.exportStuData(_student, _average);
            new Message("Data exported to "+_student.getName()+"_data.csv", "EXPORTING CORRECT").setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(StudentGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JList<String> jList3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}

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
import exercises.utils.Pair;
import exercises.utils.Question;
import exercises.utils.TeacherDatabaseLoad;
import exercises.utils.TeacherDatabaseSave;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import exercises.utils.Card;
import exercises.utils.Card.Rank;
import exercises.utils.Card.Suit;
import exercises.utils.ImageManager;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class EditQuestion extends javax.swing.JFrame {
    private static String [] RANKS = {"TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE","TEN","JACK","QUEEN","KING","ACE"};
    private static String [] SUITS = {"SPADES","HEARTS","DIAMONDS","CLUBS"};
    
    private String _name;
    private LinkedList<Card> _myCards, _comCards;
    private final int _kind;
    private final javax.swing.JFrame _father;
    private HashMap <String, Double> _options;
    private String _desc;
    private boolean _disabled;
    private LinkedList<String> _labels;
    private Vector<String> _actualLabels;
    private final boolean editing;
    private final Question saved;
    
    /**
     * Creates new form EditQuestion
     */
    public EditQuestion(javax.swing.JFrame father, int kind) {
        _disabled=true;
        try{
            _labels=TeacherDatabaseLoad.labelsLoad();
        }catch(Exception e){_labels=new LinkedList<String>();}
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
        Vector<String> aux1=new Vector<String>();
        Vector<String> aux2=new Vector<String>();
        Vector<String> aux3=new Vector<String>();
        initComponents();
        setTitle("Create Question");
        editing=false;
        saved=null;
        _actualLabels=new Vector<String>();
        _name="";
        _kind=kind;
        _father=father;
        _myCards=new LinkedList<Card>();
        _myCards.add(new Card(Suit.CLUB,Rank.ACE));
        _myCards.add(new Card(Suit.DIAMOND, Rank.ACE));
        _comCards=new LinkedList<Card>();
        _options=new HashMap<String,Double>();
        for (Card c : _comCards){
            aux1.add(c.toString());
        }
        jList1.setListData(aux1);
        for (String opt : _options.keySet()){
            String auxOpt=opt+": "+_options.get(opt).toString();
            aux3.add(auxOpt);
        }
        jList3.setListData(aux3);
        jComboBox1.removeAllItems();
        for (String item : RANKS){
            jComboBox1.addItem(item);
        }
        jComboBox3.removeAllItems();
        for (String item : RANKS){
            jComboBox3.addItem(item);
        }
        jComboBox2.removeAllItems();
        for (String item : SUITS){
            jComboBox2.addItem(item);
        }
        jComboBox4.removeAllItems();
        for (String item : SUITS){
            jComboBox4.addItem(item);
        }
        jComboBox1.setSelectedItem(RANKS[Card.toInt(_myCards.getFirst().getRank())]);
        jComboBox2.setSelectedItem(SUITS[Card.toInt(_myCards.getFirst().getSuit())]);
        jComboBox3.setSelectedItem(RANKS[Card.toInt(_myCards.getLast().getRank())]);
        jComboBox4.setSelectedItem(SUITS[Card.toInt(_myCards.getLast().getSuit())]);
        jList4.setListData(_actualLabels);
        paintHandCards();
        _disabled=false;
        addWindowListener(new closer(this, _father));
    }
    
    public EditQuestion(Question q, javax.swing.JFrame father, int kind) {
        _disabled=true;
        try{
            _labels=TeacherDatabaseLoad.labelsLoad();
        }catch(Exception e){_labels=new LinkedList<String>();}
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
        Vector<String> aux1=new Vector<String>();
        Vector<String> aux2=new Vector<String>();
        Vector<String> aux3=new Vector<String>();
        initComponents();
        setTitle("Create Question");
        editing=true;
        saved=q;
        _name=q.getName();
        _kind=kind;
        _father=father;
        _myCards=(LinkedList)q.getMyCards();
        _comCards=(LinkedList)q.getCommmunityCards();
        _options=q.getOpts();
        _desc=q.getDescription();
        _actualLabels=q.getLabels();
        jTextArea1.setText(_desc);
        jTextField1.setText(_name);
        for (Card c : _comCards){
            aux1.add(c.toString());
        }
        jList1.setListData(aux1);
        for (String opt : _options.keySet()){
            String auxOpt=opt+": "+_options.get(opt).toString();
            aux3.add(auxOpt);
        }
        jList3.setListData(aux3);
        jComboBox1.removeAllItems();
        for (String item : RANKS){
            jComboBox1.addItem(item);
        }
        jComboBox3.removeAllItems();
        for (String item : RANKS){
            jComboBox3.addItem(item);
        }
        jComboBox2.removeAllItems();
        for (String item : SUITS){
            jComboBox2.addItem(item);
        }
        jComboBox4.removeAllItems();
        for (String item : SUITS){
            jComboBox4.addItem(item);
        }
        jComboBox1.setSelectedItem(RANKS[Card.toInt(_myCards.getFirst().getRank())]);
        jComboBox2.setSelectedItem(SUITS[Card.toInt(_myCards.getFirst().getSuit())]);
        jComboBox3.setSelectedItem(RANKS[Card.toInt(_myCards.getLast().getRank())]);
        jComboBox4.setSelectedItem(SUITS[Card.toInt(_myCards.getLast().getSuit())]);
        jList4.setListData(_actualLabels);
        paintHandCards();
        _disabled=false;
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
    private void paintHandCards(){
        _myCards=new LinkedList<Card>();
        _myCards.add(new Card(Card.whatSuit(jComboBox2.getSelectedIndex()),Card.whatRank(jComboBox1.getSelectedIndex())));
        _myCards.add(new Card(Card.whatSuit(jComboBox4.getSelectedIndex()),Card.whatRank(jComboBox3.getSelectedIndex())));
        jLabel11.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(Card.getCardPath(_myCards.getFirst())).getScaledInstance(43, 60, Image.SCALE_DEFAULT)));
        jLabel10.setIcon(new ImageIcon(ImageManager.INSTANCE.getImage(Card.getCardPath(_myCards.getLast())).getScaledInstance(49, 60, Image.SCALE_DEFAULT)));
    }
    public void addOption(Pair<String,Double> opt){
        _options.put(opt.getFirst(),opt.getSecond());
        Vector<String> aux3=new Vector<String>();
        for (String opti : _options.keySet()){
            String auxOpt=opti+": "+_options.get(opti).toString();
            aux3.add(auxOpt);
        }
        jList3.setListData(aux3);
        setVisible(true);
    }
    
    public void addCard(Card card){
        if (_comCards.contains(card)) new Message("CAN'T ADD A CARD ALREADY ADDED").setVisible(true);
        else {
            Vector<String> aux1=new Vector<String>();
            _comCards.add(card);
            for (Card c : _comCards){
                aux1.add(c.toString());
            }
            jList1.setListData(aux1);
            setVisible(true);
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

        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList<>();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList4 = new javax.swing.JList<>();
        jButton10 = new javax.swing.JButton();

        jTextField2.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);

        jLabel1.setText("Name");

        jLabel2.setText("Hand");

        jLabel3.setText("Card1");

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

        jLabel4.setText("Card2");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel5.setText("Community Cards");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jButton1.setBackground(new java.awt.Color(245, 23, 23));
        jButton1.setText("Delete Card");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(2, 143, 9));
        jButton2.setText("Add Card");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(245, 23, 23));
        jButton5.setText("Cancel");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(2, 143, 9));
        jButton6.setText("Submit");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel8.setText("Options");

        jList3.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList3);

        jButton7.setBackground(new java.awt.Color(245, 23, 23));
        jButton7.setText("Delete Option");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(2, 143, 9));
        jButton8.setText("Add Option");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel9.setText("Description");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        jLabel10.setText("Card1");

        jLabel11.setText("Card1");

        jLabel12.setText("Labels");

        jButton9.setBackground(new java.awt.Color(2, 143, 9));
        jButton9.setText("Add Label");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jList4.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(jList4);

        jButton10.setBackground(new java.awt.Color(245, 23, 23));
        jButton10.setText("Delete Label");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
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
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox2, 0, 134, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField1)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6)
                    .addComponent(jButton5))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (!_disabled) paintHandCards();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        if(!_disabled) paintHandCards();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
         if (editing){
            switch(_kind){
                case 1:
                    ((TeacherGUI) _father).addQuestion(saved);
                    dispose();
                case 2:
                    ((EditQuizz) _father).addQuestion(saved);
                    dispose();
            }
        }
        else{
            _father.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(jTextField1.getText().equals("")) new Message("EVERY QUESTION SHOULD HAVE A NAME").setVisible(true);
        else if(_options.equals(new HashMap<String,Double>())) new Message("EVERY QUESTION SHOULD HAVE OPTIONS").setVisible(true);
        else {
            _name=jTextField1.getText();
            _desc=jTextArea1.getText();
            if(_comCards.size()==1 || _comCards.size()==2) new Message("THERE IS NO PHASE OF THE GAME WITH THAT COMMUNITY CARDS NUMBER").setVisible(true);
            else{
                    _myCards=new LinkedList<Card>();
                    _myCards.add(new Card(Card.whatSuit(jComboBox2.getSelectedIndex()),Card.whatRank(jComboBox1.getSelectedIndex())));
                    _myCards.add(new Card(Card.whatSuit(jComboBox4.getSelectedIndex()),Card.whatRank(jComboBox3.getSelectedIndex())));
                    if (_myCards.getFirst().equals(_myCards.getLast())) new Message("CARDS ON HAND CAN'T BE EQUAL").setVisible(true);
                    else if(_comCards.contains(_myCards.getFirst())||_comCards.contains(_myCards.getLast())) new Message("CARDS ON HAND CAN'T BE ON TABLE").setVisible(true);
                    else {
                        if (!_options.containsKey(" ")) _options.put(" ", new Double(0));
                        Question q=new Question(_name,_options,_comCards,_myCards, _desc, _actualLabels);
                        if (_kind==1){
                            TeacherGUI featuredFather=(TeacherGUI)_father;
                            featuredFather.addQuestion(q);
                        }
                        else if (_kind==2){
                            EditQuizz featuredFather=(EditQuizz)_father;
                            featuredFather.addQuestion(q);
                        }
                        if (!_options.containsValue(new Double(10))) new Message("THERE ARE NO FULL PUNTUATION OPTIONS", "Warning").setVisible(true);
                        dispose();
                    }
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed
    
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        new CreateOption(this, 1).setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Vector<String> toRem=new Vector<String>();
        Vector<String> aux3=new Vector<String>();
        if (jList3.getMinSelectionIndex()==-1) new Message("NO OPTIONS SELECTED").setVisible(true);
        else{
            for (int i=jList3.getMinSelectionIndex(); i<=jList3.getMaxSelectionIndex();i++){
                toRem.add((String)_options.keySet().toArray()[i]);
            }
            for (String rem : toRem) _options.remove(rem);
            for (String opt : _options.keySet()){
                String auxOpt=opt+": "+_options.get(opt).toString();
                aux3.add(auxOpt);
            }
            jList3.setListData(aux3);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (_comCards.size()==5) new Message("CAN ADD COMMUNITY CARDS, DELETE ONE").setVisible(true);
        else new AddCard(this).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Vector<Card> toRem=new Vector<Card>();
        Vector<String> aux2=new Vector<String>();
        if (jList1.getMinSelectionIndex()==-1) new Message("NO OPTIONS SELECTED").setVisible(true);
        else{
            for (int i=jList1.getMinSelectionIndex(); i<=jList1.getMaxSelectionIndex();i++){
                toRem.add(_comCards.get(i));
            }
            for (Card rem : toRem) _comCards.remove(rem);
            for (Card card : _comCards){
                aux2.add(card.toString());
            }
            jList1.setListData(aux2);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        if(!_disabled) paintHandCards();
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        if (!_disabled) paintHandCards();
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        new SelectLabel(this,1, _labels).setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (jList4.getMinSelectionIndex()==-1) new Message("NO LABELS SELECTED").setVisible(true);
        else{
            for (int i=jList4.getMinSelectionIndex(); i<=jList4.getMaxSelectionIndex();i++){
                _actualLabels.remove(i);
            }
            jList4.setListData(_actualLabels);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList3;
    private javax.swing.JList<String> jList4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    void addLabel(String text) {
        _labels.add(text);
        try {
            TeacherDatabaseSave.saveLabels(_labels);
        } catch (IOException ex) {
            Logger.getLogger(EditQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void selectedLabel(String text){
        if(!_actualLabels.contains(text)){
            _actualLabels.add(text);
            jList4.setListData(_actualLabels);
        }
        setVisible(true);
    }
}

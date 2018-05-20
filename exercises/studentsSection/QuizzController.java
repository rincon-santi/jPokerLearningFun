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

import exercises.utils.GeneralQuestion;
import exercises.utils.OtherQuestion;
import exercises.utils.Pair;
import exercises.utils.Question;
import exercises.utils.Quizz;
import java.util.HashMap;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class QuizzController extends javax.swing.JFrame{
    private final Quizz _q;
    private final javax.swing.JFrame _father;
    private HashMap<GeneralQuestion, Pair<String, String>> _answ;
    private int _pointer, _k;
    
    public QuizzController(Quizz q, javax.swing.JFrame f, int k){
        _k=k;
        _pointer=0;
        _q=q;
        _father=f;
        _answ=new HashMap<GeneralQuestion, Pair<String, String>>();
    }
    
    public void runQuizz(){
        if (_q.getQuestions().size()>_pointer){
            GeneralQuestion que=_q.getQuestions().get(_pointer);
            if (que.getKind()==1) new Quizzing((Question)que, this, 1).setVisible(true);
            else if(que.getKind()==2) new OtherQuizzing((OtherQuestion)que, this, 1).setVisible(true);
            else if (que.getKind()==3) dispose();
        }
        else{
            if (_k==1) ((StudentGUI)_father).quizzDone(_q, _answ);
            else if(_k==2) _father.setVisible(true);
            _answ=new HashMap<GeneralQuestion, Pair<String, String>>();
        }
    }
    
    public void answeredQuestion(GeneralQuestion question, String answ, String expl){
        _answ.put(question, new Pair(answ, expl));
        _pointer+=1;
        runQuizz();
    }
}

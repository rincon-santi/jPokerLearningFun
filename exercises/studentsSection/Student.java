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
import exercises.utils.Pair;
import exercises.utils.Quizz;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class Student {
    private final String _name;
    private String _key;
    private LinkedList<Quizz> _testsAvaliable;
    private LinkedList<GeneralQuestion> _questionsAvaliable;
    private HashMap<Quizz,HashMap<GeneralQuestion,Pair<String, String>>> _testsDone;
    private HashMap<GeneralQuestion,Pair<String, String>> _questionsDone;
    
    public Student(String name, String key, LinkedList<GeneralQuestion> pAva, LinkedList<Quizz> tAva, HashMap<GeneralQuestion,Pair<String, String>> pDone, HashMap<Quizz,HashMap<GeneralQuestion,Pair<String, String>>> tDone){
        _name=name;
        _key=key;
        _questionsAvaliable=pAva;
        _testsAvaliable=tAva;
        _questionsDone=pDone;
        _testsDone=tDone;
    }
    
    public String getName(){
        return _name;
    }
    
    public String getKey(){
        return _key;
    }
    
    public void setKey(String key){
        _key=key;
    }
    
    public LinkedList<GeneralQuestion> getQuestionsAvaliable(){
        return _questionsAvaliable;
    }
    
    public void setQuestionsAvaliable(LinkedList<GeneralQuestion> l){
        _questionsAvaliable=l;
    }
    
    public LinkedList<Quizz> getTestsAvaliable(){
        return _testsAvaliable;
    }
    
    public void setTestAvaliable(LinkedList<Quizz> l){
        _testsAvaliable=l;
    }
    
    public HashMap<GeneralQuestion, Pair<String, String>> getQuestionsDone(){
        return _questionsDone;
    }
    
    public void setQuestionsDone(HashMap<GeneralQuestion, Pair<String, String>> h){
        _questionsDone=h;
    }
    
    public HashMap<Quizz, HashMap<GeneralQuestion, Pair<String, String>>> getTestsDone(){
        return _testsDone;
    }
    
    public void setTestsDone(HashMap<Quizz, HashMap<GeneralQuestion, Pair<String, String>>> h){
        _testsDone=h;
    }
    
    public HashMap<Quizz, HashMap<GeneralQuestion, Pair<String, String>>> addTestDone(Quizz q, HashMap<GeneralQuestion,Pair<String, String>> d) throws Exception{
        if (_testsDone.containsKey(q)) throw new Exception("DUPLICATED TEST");
        if (!_testsAvaliable.contains(q)) throw new Exception("TEST NOT AVALIABLE");
        _testsDone.put(q, d);
        _testsAvaliable.remove(q);
        return _testsDone;
    }
    
    public HashMap<GeneralQuestion, Pair<String, String>> addQuestionDone(GeneralQuestion q, String d, String e) throws Exception{
        if (!_questionsAvaliable.contains(q)) throw new Exception("QUESTION NOT AVALIABLE");
        _questionsDone.put(q, new Pair<String, String>(d, e));
        return _questionsDone;
    }
}

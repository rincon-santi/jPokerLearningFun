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
package exercises.utils;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Santiago Rincón Martínez <rincon.santi@gmail.com>
 */
public class Quizz {
    private LinkedList<GeneralQuestion> _questions;
    private String _name;
    
    public Quizz(String name, LinkedList<GeneralQuestion> questions){
        _name=name;
        _questions=questions;
    }
    
    public String getName(){
        return _name;
    }
    
    public void setName(String newName){
        _name=newName;
    }
    
    public int getMax(){
        return _questions.size();
    }
    
    public LinkedList<GeneralQuestion> getQuestions(){
        return _questions;
    }
    
    public Double getPoints(HashMap<GeneralQuestion, Pair<String, String>> answersMap){
        Double points=new Double(0);
        for (GeneralQuestion q: answersMap.keySet()){
            if (!_questions.contains(q)) return null;
            else if (q.getPoints(answersMap.get(q).getFirst())==null) return null;
            else points=points+q.getPoints(answersMap.get(q).getFirst());
        }
        return points;
    }
    
}

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

import exercises.teachersSection.TeacherLoader;
import exercises.utils.SSHConnector;
import exercises.utils.GeneralQuestion;
import exercises.utils.OtherQuestion;
import exercises.utils.Pair;
import exercises.utils.Question;
import exercises.utils.Quizz;
import exercises.utils.cipherUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;
import exercises.utils.Card;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class StudentLoader {
    public static Student loadStudent(String name, String key, LinkedList<String> teachers) throws Exception{
        String raw_data=validateNread(name, key);
        HashMap<String, Pair<String, String>> auxPractices= new HashMap<String,Pair<String,String>>();
        HashMap<String, HashMap<String, Pair<String, String>>> auxTests= new HashMap<String,HashMap<String,Pair<String, String>>>();
        LinkedList<GeneralQuestion> avaQuestions=new LinkedList<GeneralQuestion>();
        LinkedList<Quizz> avaTests=new LinkedList<Quizz>();
        HashMap<GeneralQuestion, Pair<String, String>> doneQuestions=new HashMap<GeneralQuestion, Pair<String, String>>();
        HashMap<Quizz, HashMap<GeneralQuestion, Pair<String, String>>> doneTests=new HashMap<Quizz, HashMap<GeneralQuestion, Pair<String, String>>>();
        String garbage;
        
        for (String practice : raw_data.split("%EOPRACTICES&")[0].split("%EOPRACTICE&")) if (!practice.equals("")){
            auxPractices.put(practice.split("%EOPRACTICENAME&")[0], new Pair(practice.split("%EOPRACTICENAME&")[1].split("%EOPRACTICERESPOSE&")[0],(practice.split("%EOPRACTICENAME&")[1].split("%EOPRACTICERESPOSE&")[1].equals("%EOPRACTICEEXPLANATION&") ? "" : practice.split("%EOPRACTICENAME&")[1].split("%EOPRACTICERESPOSE&")[1].split("%EOPRACTICEEXPLANATION&")[0])));
        }
        garbage=raw_data.split("%EOPRACTICES&")[1];
        if (!garbage.equals("%EOTESTS&")) for (String test : garbage.split("%EOTESTS&")[0].split("%EOTEST&")) if (!test.equals("")){
            String auxTestName= test.split("%EOTESTNAME&")[0];
            HashMap<String,Pair<String, String>> auxTestQuestions=new HashMap<String,Pair<String, String>>();
            if (!test.split("%EOTESTNAME&")[1].equals("%EOTESTRESPOSES&")) for (String testRespose : test.split("%EOTESTNAME&")[1].split("%EOTESTRESPOSES&")[0].split("%EOTESTRESPOSE&")){
                auxTestQuestions.put(testRespose.split("%EOTESTRESPOSENAME&")[0], new Pair(testRespose.split("%EOTESTRESPOSENAME&")[1].split("%EOTESTRESPOSECHOICE&")[0],(testRespose.split("%EOTESTRESPOSENAME&")[1].split("%EOTESTRESPOSECHOICE&")[1].split("%EOTESTRESPOSEEXPLANATION&").length>0) ? testRespose.split("%EOTESTRESPOSENAME&")[1].split("%EOTESTRESPOSECHOICE&")[1].split("%EOTESTRESPOSEEXPLANATION&")[0] : ""));
            }
            auxTests.put(auxTestName,auxTestQuestions);
        }
        if (!(teachers==null)) for (String teacher : teachers){
            SSHConnector.getFile("resources/data/teachers",teacher+".data");
            File file =new File("resources/data/teachers/"+teacher+".data");
            int longness=(int)file.length();
            FileInputStream reader = new java.io.FileInputStream("resources/data/teachers/"+teacher+".data");
            byte [] ciphered=new byte[longness];
            String t_key;
            longness=reader.read(ciphered);
            t_key=cipherUtils.descifra(ciphered).split("%EOKEY&")[0];
            Pair<LinkedList<GeneralQuestion>, LinkedList<Quizz>> auPair = TeacherLoader.load(teacher, t_key);
            for (GeneralQuestion question : auPair.getFirst()){
                question.setName(teacher+"\n"+question.getName());
                avaQuestions.add(question);
                if (auxPractices.containsKey(question.getName())) doneQuestions.put(question, auxPractices.get(question.getName()));
            }
            for (Quizz quizz : auPair.getSecond()){
                quizz.setName(teacher+"\n"+quizz.getName());
                if (auxTests.containsKey(quizz.getName())){
                    HashMap<GeneralQuestion, Pair<String, String>> auxMap=new HashMap<GeneralQuestion, Pair<String, String>>();
                    for (GeneralQuestion q : quizz.getQuestions()) {
                        auxMap.put(q, auxTests.get(quizz.getName()).get(q.getName()));
                    }
                    doneTests.put(quizz, auxMap);
                }
                else avaTests.add(quizz);
            }
        }           
        return new Student(name, key, avaQuestions, avaTests, doneQuestions, doneTests);
    }

    private static String validateNread(String name, String key) throws Exception {
        SSHConnector.getFile("resources/data/students",name+".data");
        File file =new File("resources/data/students/"+name+".data");
        int longness=(int)file.length();
        FileInputStream reader = new java.io.FileInputStream("resources/data/students/"+name+".data");
        byte [] ciphered=new byte[longness];
        String raw_data;
        String [] divided_data;
        
        longness=reader.read(ciphered);
        raw_data=cipherUtils.descifra(ciphered);
        divided_data=raw_data.split("%EOKEY&");
        if (!key.equals(divided_data[0]))
            throw (new Exception("Incorrect key"));
        return divided_data[1];
    }
}

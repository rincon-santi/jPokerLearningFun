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

import exercises.utils.SSHConnector;
import exercises.utils.GeneralQuestion;
import exercises.utils.Quizz;
import exercises.utils.cipherUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class StudentSaver {
    public static void saveStudent(Student s) throws IOException{
        File file = new File("resources/data/students/"+s.getName()+".data");
        if (!file.exists()) file.createNewFile();
        FileOutputStream saver = new java.io.FileOutputStream("resources/data/students/"+s.getName()+".data");
        byte [] ciphered;
        String raw_data=s.getKey()+"%EOKEY&";
        for(GeneralQuestion q : s.getQuestionsDone().keySet()){
            raw_data+=q.getName();
            raw_data+="%EOPRACTICENAME&";
            raw_data+=s.getQuestionsDone().get(q).getFirst();
            raw_data+="%EOPRACTICERESPOSE&";
            raw_data+=s.getQuestionsDone().get(q).getSecond();
            raw_data+="%EOPRACTICEEXPLANATION&";
            raw_data+="%EOPRACTICE&";
        }
        raw_data+="%EOPRACTICES&";
        for(Quizz q : s.getTestsDone().keySet()){
            raw_data+=q.getName();
            raw_data+="%EOTESTNAME&";
            for(GeneralQuestion que : s.getTestsDone().get(q).keySet()){
                raw_data+=que.getName();
                raw_data+="%EOTESTRESPOSENAME&";
                raw_data+=s.getTestsDone().get(q).get(que).getFirst();
                raw_data+="%EOTESTRESPOSECHOICE&";
                raw_data+=s.getTestsDone().get(q).get(que).getSecond();
                raw_data+="%EOTESTRESPOSEEXPLANATION&";
                raw_data+="%EOTESTRESPOSE&";
            }
            raw_data+="%EOTESTRESPOSES&";
            raw_data+="%EOTEST&";
        }
        raw_data+="%EOTESTS&";
        try {
            ciphered=cipherUtils.cifra(raw_data);
            saver.write(ciphered);
            SSHConnector.putFile("resources/data/students",s.getName()+".data");
        } catch (Exception ex) {
            Logger.getLogger(StudentSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void exportStuData(Student s, Double mark) throws IOException{
        String raw_data=s.getName()+"\n";
        raw_data+="Qualification(0-10):,";
        raw_data+=mark.toString();
        raw_data+="\nExams\n,Name,Teacher,Question,Answer,Explanation,Qualification(0-10)\n";
        for (Quizz q : s.getTestsDone().keySet()){
            raw_data+=",";
            raw_data+=q.getName().split("\n")[1];
            raw_data+=",";
            raw_data+=q.getName().split("\n")[0];
            raw_data+=",,,,";
            Double auxPoinTest=q.getPoints(s.getTestsDone().get(q));
            auxPoinTest=auxPoinTest/(q.getMax()*10);
            raw_data+=auxPoinTest.toString();
            raw_data+="\n";
            for (GeneralQuestion que : s.getTestsDone().get(q).keySet()){
                raw_data+=",,,";
                raw_data+=que.getName();
                raw_data+=",";
                raw_data+=s.getTestsDone().get(q).get(que).getFirst();
                raw_data+=",";
                raw_data+=s.getTestsDone().get(q).get(que).getSecond();
                raw_data+=",";
                raw_data+=que.getPoints(s.getTestsDone().get(q).get(que).getFirst()).toString();
                raw_data+="\n";
            }
            
        }
        raw_data+="PracticeQuestions\n,Name,Teacher,Answer, Explanation, Qualification(0-10)\n";
        for (GeneralQuestion que : s.getQuestionsDone().keySet()){
            raw_data+=",";
            raw_data+=que.getName().split("\n")[1];
            raw_data+=",";
            raw_data+=que.getName().split("\n")[0];
            raw_data+=",";
            raw_data+=s.getQuestionsDone().get(que).getFirst();
            raw_data+=",";
            raw_data+=s.getQuestionsDone().get(que).getSecond();
            raw_data+=",";
            raw_data+=que.getPoints(s.getQuestionsDone().get(que).getFirst());
            raw_data+="\n";
        }
        File file = new File(s.getName()+"_data.csv");
        if (!file.exists()) file.createNewFile();
        FileOutputStream saver = new java.io.FileOutputStream(s.getName()+"_data.csv");
        byte[] buffBytes;
        buffBytes=raw_data.getBytes("UTF-8");
        saver.write(buffBytes);
    }
}

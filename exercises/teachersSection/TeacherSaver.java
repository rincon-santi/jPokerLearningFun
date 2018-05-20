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

import exercises.studentsSection.Student;
import exercises.studentsSection.StudentLoader;
import exercises.utils.SSHConnector;
import exercises.utils.GeneralQuestion;
import exercises.utils.OtherQuestion;
import exercises.utils.Question;
import exercises.utils.Quizz;
import exercises.utils.TeacherDatabaseLoad;
import exercises.utils.cipherUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import exercises.utils.Card;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class TeacherSaver {
     private static final cipherUtils CIPHER=new cipherUtils();
    
    public static void save(String teacher, String key, LinkedList<GeneralQuestion> questions, LinkedList<Quizz> quizzes) throws Exception{
        File file = new File("resources/data/teachers/"+teacher+".data");
        if (!file.exists()) file.createNewFile();
        FileOutputStream saver = new java.io.FileOutputStream("resources/data/teachers/"+teacher+".data");
        byte [] ciphered;
        
        String raw_data=key+"%EOKEY&";
        if (!questions.isEmpty()) for (GeneralQuestion que : questions){
                if(que.getKind()==1){
                    Question quee=(Question)que;
                    raw_data+="1";
                    raw_data+="%EOKIND&";
                    raw_data+=que.getName();
                    raw_data+="%EONAME&";
                    for(String slot : que.getOpts().keySet()){
                        raw_data+=slot;
                        raw_data+="%EOSLOT&";
                        raw_data+=que.getOpts().get(slot).toString();
                        raw_data+="%EOOPTION&";
                    }
                    raw_data+="%EOOPTIONS&";
                    for(Card card : quee.getCommmunityCards()){
                        raw_data+=card.toString();
                        raw_data+="%EOCOMMUNITYCARD&";
                    }
                    raw_data+="%EOCOMMUNITYCARDS&";
                    for(Card card : quee.getMyCards()){
                        raw_data+=card.toString();
                        raw_data+="%EOMYCARD&";
                    }
                    raw_data+="%EOMYCARDS&";
                    raw_data+=que.getDescription();
                    raw_data+="%EODESCRIPTION&";
                    for (String la : que.getLabels()){
                        raw_data+=la;
                        raw_data+="%EOLABEL&";
                    }
                    raw_data+="%EOLABELS&";
                    raw_data+="%EOQUESTION&";
                }
                else if (que.getKind()==2){
                    OtherQuestion quee=(OtherQuestion) que;
                    raw_data+="2";
                    raw_data+="%EOKIND&";
                    raw_data+=que.getName();
                    raw_data+="%EONAME&";
                    for(String slot : que.getOpts().keySet()){
                        raw_data+=slot;
                        raw_data+="%EOSLOT&";
                        raw_data+=que.getOpts().get(slot).toString();
                        raw_data+="%EOOPTION&";
                    }
                    raw_data+="%EOOPTIONS&";
                    raw_data+=quee.getImgRoute();
                    raw_data+="%EOIMGROUTE&";
                    raw_data+=que.getDescription();
                    raw_data+="%EODESCRIPTION&";
                    for (String la : que.getLabels()){
                        raw_data+=la;
                        raw_data+="%EOLABEL&";
                    }
                    raw_data+="%EOLABELS&";
                    raw_data+="%EOQUESTION&";
                }
            }
        raw_data+="%EOQUESTIONS&";
        if(!quizzes.isEmpty()) for (Quizz q : quizzes){
            raw_data+=q.getName();
            raw_data+="%EOQUIZZNAME&";
            for (GeneralQuestion que : q.getQuestions()){
                if(que.getKind()==1){
                    Question quee=(Question)que;
                    raw_data+="1";
                    raw_data+="%EOKIND&";
                    raw_data+=que.getName();
                    raw_data+="%EONAME&";
                    for(String slot : que.getOpts().keySet()){
                        raw_data+=slot;
                        raw_data+="%EOSLOT&";
                        raw_data+=que.getOpts().get(slot).toString();
                        raw_data+="%EOOPTION&";
                    }
                    raw_data+="%EOOPTIONS&";
                    for(Card card : quee.getCommmunityCards()){
                        raw_data+=card.toString();
                        raw_data+="%EOCOMMUNITYCARD&";
                    }
                    raw_data+="%EOCOMMUNITYCARDS&";
                    for(Card card : quee.getMyCards()){
                        raw_data+=card.toString();
                        raw_data+="%EOMYCARD&";
                    }
                    raw_data+="%EOMYCARDS&";
                    raw_data+=que.getDescription();
                    raw_data+="%EODESCRIPTION&";
                    for (String la : que.getLabels()){
                        raw_data+=la;
                        raw_data+="%EOLABEL&";
                    }
                    raw_data+="%EOLABELS&";
                    raw_data+="%EOQUESTION&";
                }
                else if (que.getKind()==2){
                    OtherQuestion quee=(OtherQuestion) que;
                    raw_data+="2";
                    raw_data+="%EOKIND&";
                    raw_data+=que.getName();
                    raw_data+="%EONAME&";
                    for(String slot : que.getOpts().keySet()){
                        raw_data+=slot;
                        raw_data+="%EOSLOT&";
                        raw_data+=que.getOpts().get(slot).toString();
                        raw_data+="%EOOPTION&";
                    }
                    raw_data+="%EOOPTIONS&";
                    raw_data+=quee.getImgRoute();
                    raw_data+="%EOIMGROUTE&";
                    raw_data+=que.getDescription();
                    raw_data+="%EODESCRIPTION&";
                    for (String la : que.getLabels()){
                        raw_data+=la;
                        raw_data+="%EOLABEL&";
                    }
                    raw_data+="%EOLABELS&";
                    raw_data+="%EOQUESTION&";
                }
            }
            raw_data+="%EOQUIZZQUESTIONS&";
            raw_data+="%EOQUIZZ&";
        }
        raw_data+="%EOQUIZZES&";
        ciphered=CIPHER.cifra(raw_data);
        saver.write(ciphered);
        SSHConnector.putFile("resources/data/teachers",teacher+".data");
    }
    
    public static void exportTestdata(String teacher, Quizz te) throws Exception{
        String raw_data=te.getName()+"\nStudent,Question,Answer, Explanation, Qualification(0-10)\n";
        Quizz test=te;
        LinkedList<String> students=TeacherDatabaseLoad.studentDatabaseLoad();
        LinkedList<String> teachers=TeacherDatabaseLoad.databaseLoad();
        if(students!=null) for (String stu : students) if (!stu.equals("\n")){
            SSHConnector.getFile("resources/data/students",stu+".data");
            File file =new File("resources/data/students/"+stu+".data");
            int longness=(int)file.length();
            FileInputStream reader = new java.io.FileInputStream("resources/data/students/"+stu+".data");
            byte [] ciphered=new byte[longness];
            String t_key;
            longness=reader.read(ciphered);
            t_key=cipherUtils.descifra(ciphered).split("%EOKEY&")[0];
            Student studentToStudy=StudentLoader.loadStudent(stu, t_key, teachers);
            test=new Quizz(teacher+"\n"+te.getName(), te.getQuestions());
            for (Quizz quizzAux : studentToStudy.getTestsDone().keySet()) if (quizzAux.getName().equals(test.getName())) test=quizzAux;
            if (studentToStudy.getTestsDone().containsKey(test)){
                raw_data+=stu;
                raw_data+=",,,,";
                Double auxPoinTest=test.getPoints(studentToStudy.getTestsDone().get(test));
                auxPoinTest=auxPoinTest/test.getMax();
                raw_data+=auxPoinTest.toString();
                raw_data+="\n";
                for (GeneralQuestion que: test.getQuestions()){
                    raw_data+=",";
                    raw_data+=que.getName();
                    raw_data+=",";
                    raw_data+=studentToStudy.getTestsDone().get(test).get(que).getFirst();
                    raw_data+=",";
                    raw_data+=studentToStudy.getTestsDone().get(test).get(que).getSecond();
                    raw_data+=",";
                    raw_data+=que.getPoints(studentToStudy.getTestsDone().get(test).get(que).getFirst()).toString();
                    raw_data+="\n";
                }
            }
        }
        File file = new File(te.getName()+"_data.csv");
        if (!file.exists()) file.createNewFile();
        FileOutputStream saver = new java.io.FileOutputStream(te.getName()+"_data.csv");
        byte[] buffBytes;
        buffBytes=raw_data.getBytes("UTF-8");
        saver.write(buffBytes);
    }
    
    public static void exportQuestdata(String teacher, GeneralQuestion te) throws Exception{
        String raw_data=te.getName()+"\nStudent,Answer, Explanation, Qualification(0-10)\n";
        GeneralQuestion test=te;
        LinkedList<String> students=TeacherDatabaseLoad.studentDatabaseLoad();
        LinkedList<String> teachers=TeacherDatabaseLoad.databaseLoad();
        if(students!=null) for (String stu : students) if (!stu.equals("\n")){
            SSHConnector.getFile("resources/data/students",stu+".data");
            File file =new File("resources/data/students/"+stu+".data");
            int longness=(int)file.length();
            FileInputStream reader = new java.io.FileInputStream("resources/data/students/"+stu+".data");
            byte [] ciphered=new byte[longness];
            String t_key;
            longness=reader.read(ciphered);
            t_key=cipherUtils.descifra(ciphered).split("%EOKEY&")[0];
            Student studentToStudy=StudentLoader.loadStudent(stu, t_key, teachers);
            test.setName(teacher+"\n"+te.getName());
            for (GeneralQuestion quizzAux : studentToStudy.getQuestionsDone().keySet()) if (quizzAux.getName().equals(test.getName())) test=quizzAux;
            if (studentToStudy.getQuestionsDone().containsKey(test)){
                raw_data+=stu;
                raw_data+=",";
                raw_data+=studentToStudy.getQuestionsDone().get(test).getFirst();
                raw_data+=",";
                raw_data+=studentToStudy.getQuestionsDone().get(test).getSecond();
                raw_data+=",";
                Double auxPoinTest=test.getPoints(studentToStudy.getQuestionsDone().get(test).getFirst());
                raw_data+=auxPoinTest.toString();
                raw_data+="\n";
            }
        }
        File file = new File("question_"+te.getName()+"_data.csv");
        if (!file.exists()) file.createNewFile();
        FileOutputStream saver = new java.io.FileOutputStream("question_"+te.getName()+"_data.csv");
        byte[] buffBytes;
        buffBytes=raw_data.getBytes("UTF-8");
        saver.write(buffBytes);
    }
}

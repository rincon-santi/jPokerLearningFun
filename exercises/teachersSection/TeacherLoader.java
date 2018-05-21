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

import com.jcraft.jsch.JSchException;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import exercises.utils.SSHConnector;
import exercises.utils.GeneralQuestion;
import exercises.utils.OtherQuestion;
import exercises.utils.Pair;
import java.io.File;
import exercises.utils.Question;
import exercises.utils.Quizz;
import exercises.utils.cipherUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
public class TeacherLoader {
    private static final cipherUtils CIPHER=new cipherUtils();
    
    public static Pair<Pair<LinkedList<GeneralQuestion>,LinkedList<GeneralQuestion>>,LinkedList<Quizz>> load(String teacher, String key, LinkedList<String> database) throws FileNotFoundException,IOException,Exception{
        String raw_data, auxName, auxName2;
        String [] divided_data, divided_data2, working_question, working_quizz, aux, aux2;
        LinkedList<GeneralQuestion> toReturnQuestions=new LinkedList<GeneralQuestion>();
        LinkedList<Quizz> toReturnQuizzes=new LinkedList<Quizz>();
        LinkedList<GeneralQuestion> auxListQuestions;
        HashMap<String, Double> auxM;
        LinkedList<Card> comcards, mycards;
        LinkedList<String> mynotes;
        int noplayers;
        boolean skipNext=false;
        
        raw_data=validateNread(teacher, key);           //Key Validation
        divided_data=raw_data.split("%EOQUESTIONS&");
        if ((divided_data.length>1)&&(!divided_data[0].equals(""))){                     //If there are questions
            divided_data2=divided_data[0].split("%EOQUESTION&");        //Question sector divided by question
            for (String question : divided_data2){
                Integer kind;
                auxM=new HashMap<String, Double>();
                comcards=new LinkedList<Card>();
                mycards=new LinkedList<Card>();
                mynotes=new LinkedList<String>();
                working_question=question.split("%EOKIND&");
                kind=Integer.parseInt(working_question[0]);
                working_question=working_question[1].split("%EONAME&");
                auxName=working_question[0];
                working_question=working_question[1].split("%EOOPTIONS&");
                if(kind.equals(new Integer(1))){
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        aux=working_question[0].split("%EOOPTION&");
                        for (String opt : aux){
                            aux2=opt.split("%EOSLOT&");
                            auxM.put(aux2[0], Double.parseDouble(aux2[1]));
                        }
                        working_question=working_question[1].split("%EOCOMMUNITYCARDS&");
                    }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOCOMMUNITYCARDS");
                    else working_question=working_question[0].split("%EOCOMMUNITYCARDS&");
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        aux=working_question[0].split("%EOCOMMUNITYCARD&");
                        for (String card : aux){
                            comcards.add(Card.parseCard(card));
                        }   
                        working_question=working_question[1].split("%EOMYCARDS&");
                    }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOMYCARDS&");
                    else working_question=working_question[0].split("%EOMYCARDS&");
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        aux=working_question[0].split("%EOMYCARD&");
                        for (String card : aux){
                            mycards.add(Card.parseCard(card));
                        }
                        working_question=working_question[1].split("%EODESCRIPTION&");
                    }else if (working_question.length>1) working_question=working_question[1].split("%EODESCRIPTION&");
                    else working_question=working_question[0].split("%EODESCRIPTION&");
                    String desc=working_question[0];
                    working_question=working_question[1].split("%EOLABELS&");
                    Vector<String> lab=new Vector<String>();
                    if (working_question.length>0) if (!working_question[0].equals("")){
                        working_question=working_question[0].split("%EOLABEL&");
                        lab.addAll(Arrays.asList(working_question));
                    }
                    toReturnQuestions.add(new Question(auxName,auxM,comcards,mycards,desc, lab));
                }else if (kind.equals(new Integer(2))){
                    String imgRoute="";
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        aux=working_question[0].split("%EOOPTION&");
                        for (String opt : aux){
                            aux2=opt.split("%EOSLOT&");
                            auxM.put(aux2[0], Double.parseDouble(aux2[1]));
                        }
                        working_question=working_question[1].split("%EOIMGROUTE&");
                    }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOIMGROUTE");
                    else working_question=working_question[0].split("%EOIMGROUTE&");
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        imgRoute=working_question[0];
                        working_question=working_question[1].split("%EODESCRIPTION&");
                    }else if (working_question.length>1) working_question=working_question[1].split("%EODESCRIPTION&");
                    else working_question=working_question[0].split("%EODESCRIPTION&");
                    String desc=working_question[0];
                    working_question=working_question[1].split("%EOLABELS&");
                    Vector<String> lab=new Vector<String>();
                    if (working_question.length>0) if (!working_question[0].equals("")){
                        working_question=working_question[0].split("%EOLABEL&");
                        lab.addAll(Arrays.asList(working_question));
                    }
                    toReturnQuestions.add(new OtherQuestion(auxName,auxM,imgRoute,desc, lab));
                }
            }
            divided_data=divided_data[1].split("%EOQUIZZES&");
        }else if (divided_data[0].equals("")) divided_data=divided_data[1].split("%EOQUIZZES&");
        else divided_data=divided_data[0].split("%EOQUIZZES&");
        if ((divided_data.length>0)&&(!divided_data[0].equals(""))){                 //If there are quizzes
            divided_data=divided_data[0].split("%EOQUIZZ&");        //Quizzes section divided by quizz
            for (String quizz : divided_data){
                auxListQuestions=new LinkedList<GeneralQuestion>();
                working_quizz=quizz.split("%EOQUIZZNAME&");
                auxName2=working_quizz[0];
                working_quizz=working_quizz[1].split("%EOQUIZZQUESTIONS&");
                if ((working_quizz.length>0)&&(!working_quizz[0].equals(""))){            //If there are questions in the quizz
                    divided_data2=working_quizz[0].split("%EOQUESTION&");   //Quizz.questions section divided by question
                    for (String question : divided_data2){
                        Integer kind;
                        auxM=new HashMap<String, Double>();
                        comcards=new LinkedList<Card>();
                        mycards=new LinkedList<Card>();
                        mynotes=new LinkedList<String>();
                        working_question=question.split("%EOKIND&");
                        kind=Integer.parseInt(working_question[0]);
                        working_question=working_question[1].split("%EONAME&");
                        auxName=working_question[0];
                        working_question=working_question[1].split("%EOOPTIONS&");
                        if(kind.equals(new Integer(1))){
                            if((working_question.length>1)&&(!working_question[0].equals(""))){
                                aux=working_question[0].split("%EOOPTION&");
                                for (String opt : aux){
                                    aux2=opt.split("%EOSLOT&");
                                    auxM.put(aux2[0], Double.parseDouble(aux2[1]));
                                }
                                working_question=working_question[1].split("%EOCOMMUNITYCARDS&");
                            }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOCOMMUNITYCARDS");
                            else working_question=working_question[0].split("%EOCOMMUNITYCARDS&");
                            if((working_question.length>1)&&(!working_question[0].equals(""))){
                                aux=working_question[0].split("%EOCOMMUNITYCARD&");
                                for (String card : aux){
                                    comcards.add(Card.parseCard(card));
                                }   
                                working_question=working_question[1].split("%EOMYCARDS&");
                            }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOMYCARDS&");
                            else working_question=working_question[0].split("%EOMYCARDS&");
                            if((working_question.length>1)&&(!working_question[0].equals(""))){
                                aux=working_question[0].split("%EOMYCARD&");
                                for (String card : aux){
                                    mycards.add(Card.parseCard(card));
                                }   
                                working_question=working_question[1].split("%EODESCRIPTION&");
                            }else if (working_question.length>1) working_question=working_question[1].split("%EODESCRIPTION&");
                            else working_question=working_question[0].split("%EODESCRIPTION&");
                            String desc=working_question[0];
                            working_question=working_question[1].split("%EOLABELS&");
                            Vector<String> lab=new Vector<String>();
                            if (working_question.length>0) if (!working_question[0].equals("")){
                                working_question=working_question[0].split("%EOLABEL&");
                                lab.addAll(Arrays.asList(working_question));
                            }
                            auxListQuestions.add(new Question(auxName,auxM,comcards,mycards,desc, lab));
                        }else if (kind.equals(new Integer(2))){
                            String imgRoute="";
                            if((working_question.length>1)&&(!working_question[0].equals(""))){
                                aux=working_question[0].split("%EOOPTION&");
                                for (String opt : aux){
                                    aux2=opt.split("%EOSLOT&");
                                    auxM.put(aux2[0], Double.parseDouble(aux2[1]));
                                }
                                working_question=working_question[1].split("%EOIMGROUTE&");
                            }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOIMGROUTE");
                            else working_question=working_question[0].split("%EOIMGROUTE&");
                            if((working_question.length>1)&&(!working_question[0].equals(""))){
                                imgRoute=working_question[0];
                                working_question=working_question[1].split("%EODESCRIPTION&");
                            }else if (working_question.length>1) working_question=working_question[1].split("%EODESCRIPTION&");
                            else working_question=working_question[0].split("%EODESCRIPTION&");
                            String desc=working_question[0];
                            working_question=working_question[1].split("%EOLABELS&");
                            Vector<String> lab=new Vector<String>();
                            if (working_question.length>0) if (!working_question[0].equals("")){
                                working_question=working_question[0].split("%EOLABEL&");
                                lab.addAll(Arrays.asList(working_question));
                            }
                            auxListQuestions.add(new OtherQuestion(auxName,auxM,imgRoute, desc, lab));
                        }
                    }
                }
                toReturnQuizzes.add(new Quizz(auxName2, auxListQuestions));
            }
        }
        LinkedList<GeneralQuestion> otherTeachQuest=new LinkedList();
        for (String t : database) if ((!t.equals(teacher))&&(!t.equals("\n"))&&(!t.equals(""))) otherTeachQuest.addAll(getOnlyQuestions(t));
        return new Pair(new Pair(toReturnQuestions, otherTeachQuest), toReturnQuizzes);
    }
    
    public static Pair<LinkedList<GeneralQuestion>, LinkedList<Quizz>> getTeacherInfo(String name) throws Exception{
        String raw_data, auxName, auxName2;
        String [] divided_data, divided_data2, working_question, working_quizz, aux, aux2;
        LinkedList<GeneralQuestion> toReturnQuestions=new LinkedList<GeneralQuestion>();
        LinkedList<Quizz> toReturnQuizzes=new LinkedList<Quizz>();
        LinkedList<GeneralQuestion> auxListQuestions;
        HashMap<String, Double> auxM;
        LinkedList<Card> comcards, mycards;
        LinkedList<String> mynotes;
        int noplayers;
        boolean skipNext=false;
        
        SSHConnector.getFile("resources/data/teachers",name+".data");
        File file =new File("resources/data/teachers/"+name+".data");
        int longness=(int)file.length();
        FileInputStream reader = new java.io.FileInputStream("resources/data/teachers/"+name+".data");
        byte [] ciphered=new byte[longness];
        
        longness=reader.read(ciphered);
        raw_data=CIPHER.descifra(ciphered);
        divided_data=raw_data.split("%EOKEY&");
        divided_data=divided_data[1].split("%EOQUESTIONS&");
        if ((divided_data.length>1)&&(!divided_data[0].equals(""))){                     //If there are questions
            divided_data2=divided_data[0].split("%EOQUESTION&");        //Question sector divided by question
            for (String question : divided_data2){
                Integer kind;
                auxM=new HashMap<String, Double>();
                comcards=new LinkedList<Card>();
                mycards=new LinkedList<Card>();
                mynotes=new LinkedList<String>();
                working_question=question.split("%EOKIND&");
                kind=Integer.parseInt(working_question[0]);
                working_question=working_question[1].split("%EONAME&");
                auxName=working_question[0];
                working_question=working_question[1].split("%EOOPTIONS&");
                if(kind.equals(new Integer(1))){
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        aux=working_question[0].split("%EOOPTION&");
                        for (String opt : aux){
                            aux2=opt.split("%EOSLOT&");
                            auxM.put(aux2[0], Double.parseDouble(aux2[1]));
                        }
                        working_question=working_question[1].split("%EOCOMMUNITYCARDS&");
                    }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOCOMMUNITYCARDS");
                    else working_question=working_question[0].split("%EOCOMMUNITYCARDS&");
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        aux=working_question[0].split("%EOCOMMUNITYCARD&");
                        for (String card : aux){
                            comcards.add(Card.parseCard(card));
                        }   
                        working_question=working_question[1].split("%EOMYCARDS&");
                    }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOMYCARDS&");
                    else working_question=working_question[0].split("%EOMYCARDS&");
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        aux=working_question[0].split("%EOMYCARD&");
                        for (String card : aux){
                            mycards.add(Card.parseCard(card));
                        }
                        working_question=working_question[1].split("%EODESCRIPTION&");
                    }else if (working_question.length>1) working_question=working_question[1].split("%EODESCRIPTION&");
                    else working_question=working_question[0].split("%EODESCRIPTION&");
                    String desc=working_question[0];
                    working_question=working_question[1].split("%EOLABELS&");
                    Vector<String> lab=new Vector<String>();
                    if (working_question.length>0) if (!working_question[0].equals("")){
                        working_question=working_question[0].split("%EOLABEL&");
                        lab.addAll(Arrays.asList(working_question));
                    }
                    toReturnQuestions.add(new Question(auxName,auxM,comcards,mycards,desc, lab));
                }else if (kind.equals(new Integer(2))){
                    String imgRoute="";
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        aux=working_question[0].split("%EOOPTION&");
                        for (String opt : aux){
                            aux2=opt.split("%EOSLOT&");
                            auxM.put(aux2[0], Double.parseDouble(aux2[1]));
                        }
                        working_question=working_question[1].split("%EOIMGROUTE&");
                    }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOIMGROUTE");
                    else working_question=working_question[0].split("%EOIMGROUTE&");
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        imgRoute=working_question[0];
                        working_question=working_question[1].split("%EODESCRIPTION&");
                    }else if (working_question.length>1) working_question=working_question[1].split("%EODESCRIPTION&");
                    else working_question=working_question[0].split("%EODESCRIPTION&");
                    String desc=working_question[0];
                    working_question=working_question[1].split("%EOLABELS&");
                    Vector<String> lab=new Vector<String>();
                    if (working_question.length>0) if (!working_question[0].equals("")){
                        working_question=working_question[0].split("%EOLABEL&");
                        lab.addAll(Arrays.asList(working_question));
                    }
                    toReturnQuestions.add(new OtherQuestion(auxName,auxM,imgRoute,desc, lab));
                }
            }
            divided_data=divided_data[1].split("%EOQUIZZES&");
        }else if (divided_data[0].equals("")) divided_data=divided_data[1].split("%EOQUIZZES&");
        else divided_data=divided_data[0].split("%EOQUIZZES&");
        if ((divided_data.length>0)&&(!divided_data[0].equals(""))){                 //If there are quizzes
            divided_data=divided_data[0].split("%EOQUIZZ&");        //Quizzes section divided by quizz
            for (String quizz : divided_data){
                auxListQuestions=new LinkedList<GeneralQuestion>();
                working_quizz=quizz.split("%EOQUIZZNAME&");
                auxName2=working_quizz[0];
                working_quizz=working_quizz[1].split("%EOQUIZZQUESTIONS&");
                if ((working_quizz.length>0)&&(!working_quizz[0].equals(""))){            //If there are questions in the quizz
                    divided_data2=working_quizz[0].split("%EOQUESTION&");   //Quizz.questions section divided by question
                    for (String question : divided_data2){
                        Integer kind;
                        auxM=new HashMap<String, Double>();
                        comcards=new LinkedList<Card>();
                        mycards=new LinkedList<Card>();
                        mynotes=new LinkedList<String>();
                        working_question=question.split("%EOKIND&");
                        kind=Integer.parseInt(working_question[0]);
                        working_question=working_question[1].split("%EONAME&");
                        auxName=working_question[0];
                        working_question=working_question[1].split("%EOOPTIONS&");
                        if(kind.equals(new Integer(1))){
                            if((working_question.length>1)&&(!working_question[0].equals(""))){
                                aux=working_question[0].split("%EOOPTION&");
                                for (String opt : aux){
                                    aux2=opt.split("%EOSLOT&");
                                    auxM.put(aux2[0], Double.parseDouble(aux2[1]));
                                }
                                working_question=working_question[1].split("%EOCOMMUNITYCARDS&");
                            }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOCOMMUNITYCARDS");
                            else working_question=working_question[0].split("%EOCOMMUNITYCARDS&");
                            if((working_question.length>1)&&(!working_question[0].equals(""))){
                                aux=working_question[0].split("%EOCOMMUNITYCARD&");
                                for (String card : aux){
                                    comcards.add(Card.parseCard(card));
                                }   
                                working_question=working_question[1].split("%EOMYCARDS&");
                            }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOMYCARDS&");
                            else working_question=working_question[0].split("%EOMYCARDS&");
                            if((working_question.length>1)&&(!working_question[0].equals(""))){
                                aux=working_question[0].split("%EOMYCARD&");
                                for (String card : aux){
                                    mycards.add(Card.parseCard(card));
                                }   
                                working_question=working_question[1].split("%EODESCRIPTION&");
                            }else if (working_question.length>1) working_question=working_question[1].split("%EODESCRIPTION&");
                            else working_question=working_question[0].split("%EODESCRIPTION&");
                            String desc=working_question[0];
                            working_question=working_question[1].split("%EOLABELS&");
                            Vector<String> lab=new Vector<String>();
                            if (working_question.length>0) if (!working_question[0].equals("")){
                                working_question=working_question[0].split("%EOLABEL&");
                                lab.addAll(Arrays.asList(working_question));
                            }
                            auxListQuestions.add(new Question(auxName,auxM,comcards,mycards,desc, lab));
                        }else if (kind.equals(new Integer(2))){
                            String imgRoute="";
                            if((working_question.length>1)&&(!working_question[0].equals(""))){
                                aux=working_question[0].split("%EOOPTION&");
                                for (String opt : aux){
                                    aux2=opt.split("%EOSLOT&");
                                    auxM.put(aux2[0], Double.parseDouble(aux2[1]));
                                }
                                working_question=working_question[1].split("%EOIMGROUTE&");
                            }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOIMGROUTE");
                            else working_question=working_question[0].split("%EOIMGROUTE&");
                            if((working_question.length>1)&&(!working_question[0].equals(""))){
                                imgRoute=working_question[0];
                                working_question=working_question[1].split("%EODESCRIPTION&");
                            }else if (working_question.length>1) working_question=working_question[1].split("%EODESCRIPTION&");
                            else working_question=working_question[0].split("%EODESCRIPTION&");
                            String desc=working_question[0];
                            working_question=working_question[1].split("%EOLABELS&");
                            Vector<String> lab=new Vector<String>();
                            if (working_question.length>0) if (!working_question[0].equals("")){
                                working_question=working_question[0].split("%EOLABEL&");
                                lab.addAll(Arrays.asList(working_question));
                            }
                            auxListQuestions.add(new OtherQuestion(auxName,auxM,imgRoute, desc, lab));
                        }
                    }
                }
                toReturnQuizzes.add(new Quizz(auxName2, auxListQuestions));
            }
        }
        return new Pair(toReturnQuestions, toReturnQuizzes);
    }
    
    private static LinkedList<GeneralQuestion> getOnlyQuestions(String name) throws IOException, JSchException, Exception{
        SSHConnector.getFile("resources/data/teachers",name+".data");
        File file =new File("resources/data/teachers/"+name+".data");
        int longness=(int)file.length();
        FileInputStream reader = new java.io.FileInputStream("resources/data/teachers/"+name+".data");
        byte [] ciphered=new byte[longness];
        String raw_data, auxName;
        String [] divided_data, divided_data2, working_question, aux, aux2;
        HashMap<String,Double> auxM;
        LinkedList<Card> comcards, mycards;
        LinkedList<String> mynotes;
        
        longness=reader.read(ciphered);
        raw_data=CIPHER.descifra(ciphered);
        divided_data=raw_data.split("%EOKEY&");
        
        LinkedList<GeneralQuestion> toReturnQuestions=new LinkedList();
        
        divided_data=divided_data[1].split("%EOQUESTIONS&");
        if ((divided_data.length>1)&&(!divided_data[0].equals(""))){                     //If there are questions
            divided_data2=divided_data[0].split("%EOQUESTION&");        //Question sector divided by question
            for (String question : divided_data2){
                Integer kind;
                auxM=new HashMap<String, Double>();
                comcards=new LinkedList<Card>();
                mycards=new LinkedList<Card>();
                mynotes=new LinkedList<String>();
                working_question=question.split("%EOKIND&");
                kind=Integer.parseInt(working_question[0]);
                working_question=working_question[1].split("%EONAME&");
                auxName=working_question[0];
                working_question=working_question[1].split("%EOOPTIONS&");
                if(kind.equals(new Integer(1))){
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        aux=working_question[0].split("%EOOPTION&");
                        for (String opt : aux){
                            aux2=opt.split("%EOSLOT&");
                            auxM.put(aux2[0], Double.parseDouble(aux2[1]));
                        }
                        working_question=working_question[1].split("%EOCOMMUNITYCARDS&");
                    }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOCOMMUNITYCARDS");
                    else working_question=working_question[0].split("%EOCOMMUNITYCARDS&");
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        aux=working_question[0].split("%EOCOMMUNITYCARD&");
                        for (String card : aux){
                            comcards.add(Card.parseCard(card));
                        }   
                        working_question=working_question[1].split("%EOMYCARDS&");
                    }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOMYCARDS&");
                    else working_question=working_question[0].split("%EOMYCARDS&");
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        aux=working_question[0].split("%EOMYCARD&");
                        for (String card : aux){
                            mycards.add(Card.parseCard(card));
                        }
                        working_question=working_question[1].split("%EODESCRIPTION&");
                    }else if (working_question.length>1) working_question=working_question[1].split("%EODESCRIPTION&");
                    else working_question=working_question[0].split("%EODESCRIPTION&");
                    String desc=working_question[0];
                    working_question=working_question[1].split("%EOLABELS&");
                    Vector<String> lab=new Vector<String>();
                    if (working_question.length>0) if (!working_question[0].equals("")){
                        working_question=working_question[0].split("%EOLABEL&");
                        lab.addAll(Arrays.asList(working_question));
                    }
                    toReturnQuestions.add(new Question(auxName,auxM,comcards,mycards,desc, lab));
                }else if (kind.equals(new Integer(2))){
                    String imgRoute="";
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        aux=working_question[0].split("%EOOPTION&");
                        for (String opt : aux){
                            aux2=opt.split("%EOSLOT&");
                            auxM.put(aux2[0], Double.parseDouble(aux2[1]));
                        }
                        working_question=working_question[1].split("%EOIMGROUTE&");
                    }else if (working_question[0].equals("")) working_question=working_question[1].split("%EOIMGROUTE");
                    else working_question=working_question[0].split("%EOIMGROUTE&");
                    if((working_question.length>1)&&(!working_question[0].equals(""))){
                        imgRoute=working_question[0];
                        working_question=working_question[1].split("%EODESCRIPTION&");
                    }else if (working_question.length>1) working_question=working_question[1].split("%EODESCRIPTION&");
                    else working_question=working_question[0].split("%EODESCRIPTION&");
                    String desc=working_question[0];
                    working_question=working_question[1].split("%EOLABELS&");
                    Vector<String> lab=new Vector<String>();
                    if (working_question.length>0) if (!working_question[0].equals("")){
                        working_question=working_question[0].split("%EOLABEL&");
                        lab.addAll(Arrays.asList(working_question));
                    }
                    toReturnQuestions.add(new OtherQuestion(auxName,auxM,imgRoute,desc, lab));
                }
            }
        }
        return toReturnQuestions;
    }
    
    private static String validateNread(String teacher, String key) throws FileNotFoundException,IOException, Exception{
        SSHConnector.getFile("resources/data/teachers",teacher+".data");
        File file =new File("resources/data/teachers/"+teacher+".data");
        int longness=(int)file.length();
        FileInputStream reader = new java.io.FileInputStream("resources/data/teachers/"+teacher+".data");
        byte [] ciphered=new byte[longness];
        String raw_data;
        String [] divided_data;
        
        longness=reader.read(ciphered);
        raw_data=CIPHER.descifra(ciphered);
        divided_data=raw_data.split("%EOKEY&");
        if (!key.equals(divided_data[0]))
            throw (new Exception("Incorrect key"));
        if (divided_data.length==2) return divided_data[1];
        else return "";
    }
}
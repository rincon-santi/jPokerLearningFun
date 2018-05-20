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

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class TeacherDatabaseLoad {
    public static LinkedList<String> databaseLoad() throws Exception{
        SSHConnector.getFile("resources/data","database.dat");
        File file = new File("resources/data/database.dat");
        if (!file.exists()) file.createNewFile();
        LinkedList<String> toRet = new LinkedList<String>();
        int longness=(int)file.length();
        FileInputStream reader = new java.io.FileInputStream("resources/data/database.dat");
        byte [] buffBytes=new byte[longness];
        longness=reader.read(buffBytes);
        if(longness>0){
            String buff=new String(buffBytes, "UTF-8");
            String [] div_buff= buff.split("%EOTEACHER&");
            toRet.addAll(Arrays.asList(div_buff));
        }
        for (String s : toRet) if ((s.equals(""))||(s.equals("\n"))) toRet.remove(s);
        return toRet;
    }
    public static LinkedList<String> labelsLoad() throws Exception{
        SSHConnector.getFile("resources/data","labels.dat");
        File file = new File("resources/data/labels.dat");
        if (!file.exists()) file.createNewFile();
        LinkedList<String> toRet = new LinkedList<String>();
        int longness=(int)file.length();
        FileInputStream reader = new java.io.FileInputStream("resources/data/labels.dat");
        byte [] buffBytes=new byte[longness];
        longness=reader.read(buffBytes);
        if(longness>0){
            String buff=new String(buffBytes, "UTF-8");
            String [] div_buff= buff.split("%EOLABEL&");
            toRet.addAll(Arrays.asList(div_buff));
        }    
        for (String s : toRet) if ((s.equals(""))||(s.equals("\n"))) toRet.remove(s);
        return toRet;
    }
    public static LinkedList<String> studentDatabaseLoad() throws Exception{
        SSHConnector.getFile("resources/data","studatabase.dat");
        File file = new File("resources/data/studatabase.dat");
        if (!file.exists()) file.createNewFile();
        LinkedList<String> toRet = new LinkedList<String>();
        int longness=(int)file.length();
        FileInputStream reader = new java.io.FileInputStream("resources/data/studatabase.dat");
        byte [] buffBytes=new byte[longness];
        longness=reader.read(buffBytes);
        if(longness>0){
            String buff=new String(buffBytes, "UTF-8");
            String [] div_buff= buff.split("%EOSTUDENT&");
            toRet.addAll(Arrays.asList(div_buff));
        }    
        for (String s : toRet) if ((s.equals(""))||(s.equals("\n"))) toRet.remove(s);
        return toRet;
    }
}

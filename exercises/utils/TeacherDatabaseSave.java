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

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class TeacherDatabaseSave {
    public static void saveDatabase(LinkedList<String> database) throws IOException{
        File file = new File("resources/data/database.dat");
        if (!file.exists()) file.createNewFile();
        FileOutputStream saver = new java.io.FileOutputStream("resources/data/database.dat");
        String buff="";
        byte [] buffBytes;
        
        for(String teacher : database){
            buff+=teacher;
            buff+="%EOTEACHER&";
        }    
        buffBytes=buff.getBytes("UTF-8");
        saver.write(buffBytes);
        try {  
            try {
                SSHConnector.putFile("resources/data","database.dat");
            } catch (SftpException ex) {
                Logger.getLogger(TeacherDatabaseSave.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JSchException ex) {
            Logger.getLogger(TeacherDatabaseSave.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static void saveLabels(LinkedList<String> database) throws IOException{
        File file = new File("resources/data/labels.dat");
        if (!file.exists()) file.createNewFile();
        FileOutputStream saver = new java.io.FileOutputStream("resources/data/labels.dat");
        String buff="";
        byte [] buffBytes;
        
        for(String teacher : database){
            buff+=teacher;
            buff+="%EOLABEL&";
        }
        
        buffBytes=buff.getBytes("UTF-8");
        saver.write(buffBytes);
        try {
            try {
                SSHConnector.putFile("resources/data","labels.dat");
            } catch (SftpException ex) {
                Logger.getLogger(TeacherDatabaseSave.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JSchException ex) {
            Logger.getLogger(TeacherDatabaseSave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void saveStudentDatabase(LinkedList<String> database) throws IOException, JSchException{
        File file = new File("resources/data/studatabase.dat");
        if (!file.exists()) file.createNewFile();
        FileOutputStream saver = new java.io.FileOutputStream("resources/data/studatabase.dat");
        String buff="";
        byte [] buffBytes;
        
        for(String teacher : database){
            buff+=teacher;
            buff+="%EOSTUDENT&";
        }
        buffBytes=buff.getBytes("UTF-8");
        saver.write(buffBytes);
        try {  
            try {
                SSHConnector.putFile("resources/data","studatabase.dat");
            } catch (SftpException ex) {
                Logger.getLogger(TeacherDatabaseSave.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JSchException ex) {
            Logger.getLogger(TeacherDatabaseSave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

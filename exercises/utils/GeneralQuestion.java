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
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class GeneralQuestion {
    protected final HashMap<String, Double> options;
    protected String name;
    protected final int kind;
    protected final String description;
    protected final Vector<String> labels;
    
    public GeneralQuestion(HashMap<String, Double> opts, String n, int k, String descript, Vector<String> la){
        options=opts;
        name=n;
        kind=k;
        description=descript;
        labels=la;
    }
    public Vector<String> getLabels(){
        return labels;
    }
    public String getDescription(){
        return description;
    }
    public String getName(){
        return name;
    }
    
    public void setName(String newName){
        name=newName;
    }
    
    public HashMap<String, Double> getOpts(){
        return options;
    }
    
    
    public int getKind(){
        return kind;
    }
    
    public Double getPoints(String opt){
        if (!options.containsKey(opt)) return null;
        else return options.get(opt);
    }
    
}

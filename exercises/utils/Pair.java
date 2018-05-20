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

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class Pair<O1,O2> {
    private O1 _par1;
    private O2 _par2;
    public Pair(O1 par1, O2 par2){
        _par1=par1;
        _par2=par2;
    }
    
    public O1 getFirst(){
        return _par1;
    }    
    
    public O2 getSecond(){
        return _par2;
    }
}

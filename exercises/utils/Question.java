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
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class Question extends GeneralQuestion{
    private final List<Card> communityCards;
    private final List<Card> myCards;
    
    public Question(String na, HashMap<String, Double> opts, List<Card> comcards, List<Card> mc, String desc, Vector<String> la) {
        super(opts, na, 1, desc, la);
        communityCards=comcards;
        myCards=mc;
    }
     
    public List<Card> getCommmunityCards(){
        return communityCards;
    }
    
    public List<Card> getMyCards(){
        return myCards;
    }

}


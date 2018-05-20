/* 
 * Copyright (C) 2018 Santiago Rincon Martinez <rincon.santi@gmail.com>
 * based on work by 2016 David Pérez Cabrera <dperezcabrera@gmail.com>
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

//import net.jcip.annotations.Immutable;

import java.text.MessageFormat;
import static exercises.utils.ImageManager.IMAGES_PATH;


/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 * @since 1.0.0
 */
//@Immutable
public final class Card {
    public static final String NULL_ERR_MSG = "Argument {0} should not be null.";
    private static final String CARDS_EXTENSIONS = ".png";
    private static final String BACK_CARD = "back";
    private static final char[][] SUIT_SYMBOLS = {{'♦', 'D'}, {'♠', 'S'}, {'♥', 'H'}, {'♣', 'C'}};
    private static final String CARDS_PATH = IMAGES_PATH + "cards/png/";
    private static final String STRING_RANK_CARDS = "23456789TJQKA";

    public enum Suit {

        SPADE('♠'), HEART('♥'), DIAMOND('♦'), CLUB('♣');
        
        private final char c;

        private Suit(char c) {
            this.c = c;
        }
    }

    public enum Rank {
        TWO, TRHEE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }
    
    public static int toInt(Suit s){
        switch (s){
            case SPADE:
                return 0;
            case HEART:
                return 1;
            case DIAMOND:
                return 2;
            case CLUB:
                return 3;
            default:
                return -1;
        }
    }
    
    public static int toInt(Rank s){
        switch (s){
            case TWO:
                return 0;
            case TRHEE:
                return 1;
            case FOUR:
                return 2;
            case FIVE:
                return 3;
            case SIX:
                return 4;
            case SEVEN:
                return 5;
            case EIGHT:
                return 6;
            case NINE:
                return 7;
            case TEN:
                return 8;
            case JACK:
                return 9;
            case QUEEN:
                return 10;
            case KING:
                return 11;
            case ACE:
                return 12;
            default:
                return -1;
        }
    }
    
    public static String getCardPath(Card c) {
        String cardString = BACK_CARD;
        if (c != null) {
            cardString = c.toString();
            for (char[] suitSymbol : SUIT_SYMBOLS) {
                cardString = cardString.replace(suitSymbol[0], suitSymbol[1]);
            }
        }
        return CARDS_PATH.concat(cardString).concat(CARDS_EXTENSIONS);
    }
    
    public static Suit whatSuit(int i){
        switch (i){
            case 0:
                return Suit.SPADE;
            case 1:
                return Suit.HEART;
            case 2:
                return Suit.DIAMOND;
            case 3:
                return Suit.CLUB;
            default:
                return null;
        }
    }
    public static Rank whatRank(int i){
        switch (i){
            case 0:
                return Rank.TWO;
            case 1:
                return Rank.TRHEE;
            case 2:
                return Rank.FOUR;
            case 3:
                return Rank.FIVE;
            case 4:
                return Rank.SIX;
            case 5:
                return Rank.SEVEN;
            case 6:
                return Rank.EIGHT;
            case 7:
                return Rank.NINE;
            case 8:
                return Rank.TEN;
            case 9:
                return Rank.JACK;
            case 10:
                return Rank.QUEEN;
            case 11:
                return Rank.KING;
            case 12:
                return Rank.ACE;
            default:
                return null;
        }
    }
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        checkNullArgument(rank, "rank");
        checkNullArgument(suit, "suit");
        this.suit = suit;
        this.rank = rank;
    }
    
    public static void checkNullArgument(Object o, String name) {
        if (o == null) {
            throw new IllegalArgumentException(MessageFormat.format(NULL_ERR_MSG, name));
        }
    }    

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public int hashCode() {
        return rank.ordinal() * Suit.values().length + suit.ordinal();
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = true;
        if (this != obj) {
            result = false;
            if (obj != null && getClass() == obj.getClass()) {
                result = hashCode() == ((Card) obj).hashCode();
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return STRING_RANK_CARDS.substring(rank.ordinal(), rank.ordinal() + 1) + suit.c;
    }
    
    public static Card parseCard(String s) throws Exception{
        for(Card c : Deck.getAllCards()){
            if (c.toString().equals(s)) return c;
        }
        throw (new Exception("Can't Parse Card"));
    }
}

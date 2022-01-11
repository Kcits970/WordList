package util;

import word.*;

public class WordBaseChangedEvent extends MyEvent {
    public static final int LOAD_ACTION = 1;
    public static final int ADD_ACTION = 2;
    public static final int EDIT_ACTION = 3;
    public static final int REMOVE_ACTION = 4;
    Word modifiedWord;
    int actionType;

    public WordBaseChangedEvent(WordBase source, Word modifiedWord, int actionType) {
        super(source);
        this.modifiedWord = modifiedWord;
        this.actionType = actionType;
    }

    @Override
    public WordBase getSource() {return (WordBase) source;}

    public Word getModifiedWord() {return modifiedWord;}

    public int getActionType() {return actionType;}
}

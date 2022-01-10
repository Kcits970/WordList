package word;

import io.*;
import util.MyObservable;
import util.MyObserver;
import util.WordBaseChangedEvent;

import java.util.*;

public class WordBase extends MyObservable {
    public static final String DEFAULT_FILE_LOCATION = "words.txt";
    List<Word> words;
    Word changedElement;
    int actionType;

    public WordBase() {
        words = new ArrayList<>();
    }

    public boolean loadFrom(String filename) {
        try (UTF8Reader reader = UTF8Reader.getInstance(filename)) {
            WordParser parser = new WordParser(reader);

            words.clear();
            while (!parser.reachedEnd())
                add(parser.nextWord(), false);
            actionType = WordBaseChangedEvent.LOAD_ACTION;
            notifyObservers();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveTo(String filename) {
        try (UTF8Writer writer = UTF8Writer.getInstance(filename)) {
            for (Word w : words) {
                writer.write(WordParser.formatAsCSV(w));
                writer.write("\n");
            }

            writer.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(Word word, boolean notify) {
        if (word == null)
            return false;

        if (!words.contains(word) && word.isProperlyInitialized) {
            words.add(word);
            changedElement = word;
            actionType = WordBaseChangedEvent.ADD_ACTION;
            if (notify) notifyObservers();
            return true;
        }

        return false;
    }

    public boolean edit(Word originalWord, Word newWord) {
        if (originalWord == null || newWord == null)
            return false;

        if (words.contains(originalWord) && !words.contains(newWord) && newWord.isProperlyInitialized) {
            originalWord.setDetails(newWord);
            changedElement = originalWord;
            actionType = WordBaseChangedEvent.EDIT_ACTION;
            notifyObservers();
            return true;
        }

        return false;
    }

    public boolean remove(Word word) {
        if (words.contains(word)) {
            words.remove(word);
            changedElement = word;
            actionType = WordBaseChangedEvent.REMOVE_ACTION;
            notifyObservers();
            return true;
        }

        return false;
    }

    public List<Word> getWords() {return words;}

    @Override
    public void notifyObservers() {
        for (MyObserver observer : observers)
            observer.update(new WordBaseChangedEvent(this, changedElement, actionType));
    }

    public List<Word> getNRandomWords(int n) {
        n = (n > words.size()) ? words.size() : n;

        List<Word> shallowCopy = new ArrayList<>(words);
        Collections.shuffle(shallowCopy);

        return shallowCopy.subList(0, n);
    }
}

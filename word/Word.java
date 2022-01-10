package word;

import java.util.Iterator;

public class Word implements Comparable<Word> {
	public static final Word NULL_WORD;
	public boolean isProperlyInitialized = true;

	static {
		NULL_WORD = new Word();
		NULL_WORD.name = "null";
		NULL_WORD.pos = "null";
		NULL_WORD.definition = "";
	}

	private String name;
	private String definition;
	private String pos;

	public Word() {}
	public Word(Iterator<String> details) {setDetails(details);}

	public void setDetails(Iterator<String> details) {
		setName(details.next());
		setPOS(details.next());
		setDefinition(details.next());
	}

	public void setDetails(Word word) {
		setName(word.getName());
		setPOS(word.getPOS());
		setDefinition(word.getDefinition());
	}

	public void setName(String s) {
		if (s.isBlank()) {
			name = "null";
			isProperlyInitialized = false;
		}
		else name = s.trim();
	}

	public void setPOS(String s) {
		if (s.isBlank()) {
			pos = "null";
			isProperlyInitialized = false;
		}
		else pos = s.trim();
	}

	public void setDefinition(String s) {
		if (s.isBlank()) {
			pos = "";
			isProperlyInitialized = false;
		}
		else definition = s.trim();
	}
	
	public String getName() {return name;}
	public String getDefinition() {return definition;}
	public String getPOS() {return pos;}

	@Override
	public String toString() {return name;}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;

		Word compare = (Word) o;
		if (name.equals(compare.name) && pos.equals(compare.pos))
			return true;
		else
			return false;
	}

	@Override
	public int compareTo(Word o) {
		return CharSequence.compare(this.name, o.name);
	}
}
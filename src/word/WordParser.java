package word;

import io.UTF8Reader;
import java.util.Arrays;
import java.util.stream.Stream;

public class WordParser {
	private UTF8Reader reader;
	public WordParser(UTF8Reader reader) {
		this.reader = reader;
	}

	public Word nextWord() throws Exception {
		String line = reader.readLine();
		Stream<String> wordDetails = Arrays.stream(line.split(",")).map(s -> restoreEscapedChar(s));

		return new Word(wordDetails.iterator());
	}

	public static String formatAsCSV(Word w) {
		return String.format("%s,%s,%s",
				escapeSpecialChar(w.getName()),
				escapeSpecialChar(w.getPOS()),
				escapeSpecialChar(w.getDefinition()));
	}

	public static String restoreEscapedChar(String s) {
		return s.replaceAll("&com", ",").replaceAll("\n", "&ln").replaceAll("&amp", "&");
	}

	public static String escapeSpecialChar(String s) {
		return s.replaceAll("&", "&amp").replaceAll(",", "&com").replaceAll("\n", "&ln");
	}

	public boolean reachedEnd() throws Exception {
		return reader.isAtEOF();
	}
}

package io;

import java.io.*;

public class UTF8Writer extends BufferedWriter {
    private UTF8Writer(Writer out) {
        super(out);
    }

    public static UTF8Writer getInstance(String filename) throws IOException {
        FileOutputStream stream;
        OutputStreamWriter writer;

        stream = new FileOutputStream(filename);
        writer = new OutputStreamWriter(stream, "UTF-8");
        return new UTF8Writer(writer);
    }
}

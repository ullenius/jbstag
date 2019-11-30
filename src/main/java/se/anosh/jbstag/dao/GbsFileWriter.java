package se.anosh.jbstag.dao;

import static se.anosh.jbstag.dao.GbsFileReader.AUTHOR_OF_SONG_LENGTH;
import static se.anosh.jbstag.dao.GbsFileReader.AUTHOR_OF_SONG_OFFSET;
import static se.anosh.jbstag.dao.GbsFileReader.COPYRIGHT_LENGTH;
import static se.anosh.jbstag.dao.GbsFileReader.COPYRIGHT_OFFSET;
import static se.anosh.jbstag.dao.GbsFileReader.TITLE_LENGTH;
import static se.anosh.jbstag.dao.GbsFileReader.TITLE_OFFSET;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;

import se.anosh.jbstag.domain.GbsTag;
import se.anosh.jbstag.util.BinaryIO;

public class GbsFileWriter implements AutoCloseable {

	private static final String READ_WRITE = "rw";

	private Path file;
	private RandomAccessFile raf;
	private final GbsTag sourceTags;

	public GbsFileWriter(String filename, GbsTag tags) throws FileNotFoundException {
		file = Paths.get(filename);
		sourceTags = tags;
		raf = new RandomAccessFile(file.toString(), READ_WRITE);
	}

	@Override
	public void close() throws IOException {
		raf.close();
	}

	public void writeTitle() throws IOException {
		final String title = sourceTags.getTitle();
		writeBytes(title, TITLE_OFFSET, TITLE_LENGTH);
	}

	public void writeAuthor() throws IOException {
		final String author = sourceTags.getAuthor();
		writeBytes(author, AUTHOR_OF_SONG_OFFSET, AUTHOR_OF_SONG_LENGTH);
	}

	public void writeCopyright() throws IOException {
		final String copyright = sourceTags.getCopyright();
		writeBytes(copyright, COPYRIGHT_OFFSET, COPYRIGHT_LENGTH);
	}

	@Deprecated
	private void writeByte(byte b, int offset) throws IOException {
		BinaryIO.writeByte(raf, offset, b);
	}

	private void writeBytes(String text, int offset, int maxLength) throws IOException {
		text = text.trim();
		BinaryIO.writeBytes(raf,  offset, text, maxLength);
	}

}

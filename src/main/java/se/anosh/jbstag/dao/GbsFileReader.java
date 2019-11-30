package se.anosh.jbstag.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import se.anosh.jbstag.domain.GbsTag;
import se.anosh.jbstag.util.BinaryIO;

public class GbsFileReader {
	
	// Includes identifier string and version number
	public static final int IDENTIFIER_OFFSET = 0;
	public static final int IDENTIFIER_LENGTH = 0x3;

	public static final int TITLE_OFFSET = 0x10;
	public static final int TITLE_LENGTH = 32;

	public static final int AUTHOR_OF_SONG_OFFSET = 0x30;
	public static final int AUTHOR_OF_SONG_LENGTH = 32;
	
	public static final int COPYRIGHT_OFFSET = 0x50;
	public static final int COPYRIGHT_LENGTH = 32;
	
	public static final byte VERSION_NUMBER_OFFSET = 0x3;
	public static final byte NUMBER_OF_SONGS_OFFSET = 0x4;
	public static final byte FIRST_SONG_OFFSET = 0x5;
	
	private static final String CORRECT_HEADER = "GBS";
	private static final String READ_ONLY = "r";
	
	private final GbsTag tags;
	private final Path file;
	private final RandomAccessFile raf;

	public GbsFileReader(String filename) throws FileNotFoundException, IOException {
		file = Paths.get(filename);
		raf = new RandomAccessFile(file.toString(), READ_ONLY);
		tags = new GbsTag();
		
		if (!isValidGbsFile())
			throw new IOException("File is missing correct GBS-header.");
		readAndSetAllFields();
		raf.close();
	}
	
	private boolean isValidGbsFile() throws IOException {
		readHeader();
		return (tags.getHeader().equals(CORRECT_HEADER));
	}
	
	private void readAndSetAllFields() throws FileNotFoundException, IOException {
		// immutable tags
		//readHeader(); // already set by isValidGbsFile
		readVersionNumber();
		readNumberOfSongs();
		readFirstSong();
		
		// mutable tags
		readTitle();
		readAuthor();
		readCopyright();
	}
	
	private void readHeader() throws IOException {
		tags.setHeader(read(IDENTIFIER_OFFSET, IDENTIFIER_LENGTH).trim()); // removes NULL character
	}

	private void readVersionNumber() throws IOException {
		tags.setVersionNumber(read(VERSION_NUMBER_OFFSET));
	}
	
	private void readNumberOfSongs() throws IOException {
		tags.setNumberOfSongs(read(NUMBER_OF_SONGS_OFFSET));
	}
	
	private void readFirstSong() throws IOException {
		tags.setFirstSong(read(FIRST_SONG_OFFSET));
	}
	
	private void readTitle() throws IOException {
		tags.setTitle(read(TITLE_OFFSET, TITLE_LENGTH).trim());
	}
	
	private void readAuthor() throws IOException {
		tags.setAuthor(read(AUTHOR_OF_SONG_OFFSET, AUTHOR_OF_SONG_LENGTH).trim());
	}
	
	private void readCopyright() throws IOException {
		tags.setCopyright(read(COPYRIGHT_OFFSET, COPYRIGHT_LENGTH).trim());
	}
	
	private String read(int offset, int length) throws IOException {
		return BinaryIO.readString(raf, offset, length);
	}
	
	private byte read(int offset) throws IOException {
		return BinaryIO.readByte(raf, offset);
	}

	public GbsTag getTags() {
		Objects.requireNonNull(tags, "tags cannot be null!");
		return tags;
	}
	
}

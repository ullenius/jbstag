package se.anosh.jbstag.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/*
 * Helper class for low-level binary I/O
 * Only static methods
 */
public final class BinaryIO {
	
	private static final char NULL = '\0';

	private BinaryIO() {
		throw new AssertionError("This should never happen"); // prevents instantiation
	}

	public static String readString(final RandomAccessFile raf, final int offset, final int length) throws IOException {
		raf.seek(offset);
		byte[] bytes = new byte[length];
		raf.read(bytes);
		return new String(bytes, StandardCharsets.UTF_8);
	}

	public static byte readByte(final RandomAccessFile raf, final int offset) throws IOException {
		raf.seek(offset);
		return raf.readByte();
	}

	public static void writeByte(final RandomAccessFile raf, final int offset, byte b) throws IOException {
		raf.seek(offset);
		raf.write(b);
	}

	public static void writeBytes(final RandomAccessFile raf, final int offset, final String message, final int length) throws IOException {
		clearBytes(raf,offset,length);
		raf.seek(offset);
		byte[] utf8bytes = message.getBytes(StandardCharsets.UTF_8);
		System.out.println("Attempting to write " + utf8bytes.length + " bytes");
		if (utf8bytes.length > length) {
			throw new IllegalArgumentException("Message string is to long. Max is: " + length);
		}
		raf.write(utf8bytes);
	}

	/*
	 * Clears a field, that is: set all bytes to null '\0'
	 */
	private static void clearBytes(final RandomAccessFile raf, final int offset, final int length) throws IOException {
		byte[] nulls = new byte[length];
		System.out.println("Clearing " + length + " bytes at offset: " + Integer.toHexString(offset));
		for (int i = 0; i < length; i++) {
			nulls[i] = NULL;
		}
		raf.seek(offset);
		raf.write(nulls);
	}

}

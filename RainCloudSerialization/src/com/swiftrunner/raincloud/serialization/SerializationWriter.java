package com.swiftrunner.raincloud.serialization;

public class SerializationWriter{
	
	public static final byte[]	HEADER	= "RC".getBytes();
	public static final short	VERSION	= 0x0100;
	
	
	public static int writeBytes(byte[] dest, int pointer, byte[] src){
		for(int i=0; i<src.length; i++){ dest[pointer++] = src[i]; }
		return pointer;
	}
	
	
	public static int writeBytes(byte[] dest, int pointer, byte value){ 
		dest[pointer++] = value;
		return pointer;
	}
	
	
	public static int writeBytes(byte[] dest, int pointer, short value){ 
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)(value & 0xff);
		return pointer;
	}
	
	
	public static int writeBytes(byte[] dest, int pointer, char value){ 
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)(value & 0xff);
		return pointer;
	}
	
	
	public static int writeBytes(byte[] dest, int pointer, int value){ 
		dest[pointer++] = (byte)((value >> 24) & 0xff);
		dest[pointer++] = (byte)((value >> 16) & 0xff);
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)(value & 0xff);
		return pointer;
	}
	
	
	public static int writeBytes(byte[] dest, int pointer, long value){
		dest[pointer++] = (byte)((value >> 56) & 0xff);
		dest[pointer++] = (byte)((value >> 48) & 0xff);
		dest[pointer++] = (byte)((value >> 40) & 0xff);
		dest[pointer++] = (byte)((value >> 32) & 0xff);
		dest[pointer++] = (byte)((value >> 24) & 0xff);
		dest[pointer++] = (byte)((value >> 16) & 0xff);
		dest[pointer++] = (byte)((value >> 8) & 0xff);
		dest[pointer++] = (byte)(value & 0xff);
		return pointer;
	}
	
	
	public static int writeBytes(byte[] dest, int pointer, float value){
		int data = Float.floatToIntBits(value);
		return writeBytes(dest, pointer, data);
	}
	
	
	public static int writeBytes(byte[] dest, int pointer, double value){
		long data = Double.doubleToLongBits(value);
		return writeBytes(dest, pointer, data);
	}
	
	
	public static int writeBytes(byte[] dest, int pointer, boolean value){
		dest[pointer++] = (byte)(value ? 1 : 0);
		return pointer;
	}
	
	
	public static int writeBytes(byte[] dest, int pointer, String string){
		pointer = writeBytes(dest, pointer, (short) string.length());
		return writeBytes(dest, pointer, string.getBytes());
	}
	
	
	public static byte readbyte(byte[] src, int pointer){
		return src[pointer];
	}
	
	
	public static char readChar(byte[] src, int pointer){
		return (char) ((src[pointer] << 8) | (src[pointer + 1]));
	}
	
	
	public static short readShort(byte[] src, int pointer){
		return (short) ((src[pointer] << 8) | (src[pointer + 1]));
	}
	
	
	public static int readInt(byte[] src, int pointer){
		return (int) ((src[pointer] << 24) | (src[pointer + 1] << 16) | (src[pointer + 2] << 8) | (src[pointer + 3]));
	}
	
	
	public static long readLong(byte[] src, int pointer){
		return (long) 	((src[pointer] << 56) | (src[pointer + 1] << 48) | (src[pointer + 2] << 40) | (src[pointer + 3] << 32) | 
						(src[pointer + 4] << 24) | (src[pointer + 5] << 16) | (src[pointer + 6] << 8) | (src[pointer + 7]));
	}
	
	
	public static float readFloat(byte[] src, int pointer){
		return Float.intBitsToFloat(readInt(src, pointer));
	}
	
	
	public static double readDouble(byte[] src, int pointer){
		return Double.longBitsToDouble(readLong(src, pointer));
	}
	
	
	public static boolean readBoolean(byte[] src, int pointer){
		assert(src[pointer] == 0 || src[pointer] == 1);
		return src[pointer] != 0;
	}
}

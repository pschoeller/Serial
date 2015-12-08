package com.swiftrunner.raincloud.serialization;

public class SerializationWriter{
	
	public static final byte[]	HEADER	= "RC".getBytes();
	public static final short	VERSION	= 0x0100;
	
	
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
}

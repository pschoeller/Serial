package com.swiftrunner.raincloud.serialization;

import static com.swiftrunner.raincloud.serialization.SerializationWriter.*;

public class Field{
	
	public static final byte CONTAINER_TYPE = ContainerType.FIELD;
	public short nameLength;
	public byte[] name;
	public byte type;
	public byte[] data;
	
	
	public void setName(String name){
		assert(name.length() < Short.MAX_VALUE);
		this.nameLength = (short)name.length();
		this.name = name.getBytes();
	}
	
	
	public int getBytes(byte[] dest, int pointer){
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, type);
		pointer = writeBytes(dest, pointer, data);
		return pointer;
	}
}

package com.swiftrunner.raincloud.serialization;

import static com.swiftrunner.raincloud.serialization.SerializationWriter.*;

public class RCField{
	
	public static final byte CONTAINER_TYPE = ContainerType.FIELD;
	public short nameLength;
	public byte[] name;
	public byte type;
	public byte[] data;
	
	
	private RCField(){}
	
	
	public void setName(String name){
		assert(name.length() < Short.MAX_VALUE);
		this.nameLength = (short)name.length();
		this.name = name.getBytes();
	}
	
	
	public String getName(){
		return new String(name, 0, nameLength);
	}
	
	
	public int getBytes(byte[] dest, int pointer){
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, type);
		pointer = writeBytes(dest, pointer, data);
		return pointer;
	}
	
	
	public int getSize(){
		assert(data.length == Type.getSize(type));
		return 1 + 2 + name.length + 1 + data.length;
	}
	
	
	public static RCField Byte(String name, byte value){
		RCField field = new RCField();
		field.setName(name);
		field.type = Type.BYTE;
		field.data = new byte[Type.getSize(Type.BYTE)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	
	public static RCField Short(String name, short value){
		RCField field = new RCField();
		field.setName(name);
		field.type = Type.SHORT;
		field.data = new byte[Type.getSize(Type.SHORT)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	
	public static RCField Char(String name, char value){
		RCField field = new RCField();
		field.setName(name);
		field.type = Type.CHAR;
		field.data = new byte[Type.getSize(Type.CHAR)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	
	public static RCField Integer(String name, int value){
		RCField field = new RCField();
		field.setName(name);
		field.type = Type.INT;
		field.data = new byte[Type.getSize(Type.INT)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	
	public static RCField Long(String name, long value){
		RCField field = new RCField();
		field.setName(name);
		field.type = Type.LONG;
		field.data = new byte[Type.getSize(Type.LONG)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	
	public static RCField Float(String name, float value){
		RCField field = new RCField();
		field.setName(name);
		field.type = Type.FLOAT;
		field.data = new byte[Type.getSize(Type.FLOAT)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	
	public static RCField Double(String name, double value){
		RCField field = new RCField();
		field.setName(name);
		field.type = Type.DOUBLE;
		field.data = new byte[Type.getSize(Type.DOUBLE)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	
	public static RCField Boolean(String name, boolean value){
		RCField field = new RCField();
		field.setName(name);
		field.type = Type.BOOLEAN;
		field.data = new byte[Type.getSize(Type.BOOLEAN)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	
	public static RCField Deserialize(byte[] data, int pointer){
		byte containerType = data[pointer++];
		assert(containerType == CONTAINER_TYPE);
		
		RCField result = new RCField();
		
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer += result.nameLength;
		
		result.type = data[pointer++];
		result.data = new byte[Type.getSize(result.type)];
		readBytes(data, pointer, result.data);
		pointer += Type.getSize(result.type);
		return result;
	}
}

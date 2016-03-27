package com.swiftrunner.raincloud.serialization;

import static com.swiftrunner.raincloud.serialization.SerializationWriter.*;

import java.util.ArrayList;
import java.util.List;

public class RCObject{
	
	public static final byte CONTAINER_TYPE = ContainerType.OBJECT;
	public short nameLength;
	public byte[] name;
	
	private int size = 1 + 2 + 4 + 2 + 2 + 2;
	private short fieldCount;
	public List<RCField> fields = new ArrayList<RCField>();
	private short stringCount;
	public List<RCString> strings = new ArrayList<RCString>();
	private short arrayCount;
	public List<RCArray> arrays = new ArrayList<RCArray>();
	
	private static final int sizeOffset = 1 + 2 + 0 + 4;
	
	
	private RCObject(){}
	
	
	public RCObject(String name){
		setName(name);
	}
	
	
	public void setName(String name){
		assert(name.length() < Short.MAX_VALUE);
		
		if(this.name != null){ size -= this.name.length; } 
		
		this.nameLength = (short)name.length();
		this.name = name.getBytes();
		size += nameLength;
	}
	
	
	public String getName(){
		return new String(name, 0, nameLength);
	}
	
	
	public void addField(RCField field){
		fields.add(field);
		size += field.getSize();
		fieldCount = (short)fields.size();
	}
	
	
	public void addString(RCString string){
		strings.add(string);
		size += string.getSize();
		stringCount = (short)strings.size();
	}
	
	
	public void addArray(RCArray array){
		arrays.add(array);
		size += array.getSize();
		arrayCount = (short)arrays.size();
	}
	
	
	public int getSize(){
		return size;
	}


	public int getBytes(byte[] dest, int pointer){
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, size);
		
		pointer = writeBytes(dest, pointer, fieldCount);
		for(RCField field : fields){
			pointer = field.getBytes(dest, pointer);
		}
		
		pointer = writeBytes(dest, pointer, stringCount);
		for(RCString string : strings){
			pointer = string.getBytes(dest, pointer);
		}
		
		pointer = writeBytes(dest, pointer, arrayCount);
		for(RCArray array: arrays){
			pointer = array.getBytes(dest, pointer);
		}
		return pointer;
    }
	
	
	public static RCObject Deserialize(byte[] data, int pointer){
		byte containerType = data[pointer++];
		assert(containerType == CONTAINER_TYPE);
		
		RCObject result = new RCObject();
		
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer += result.nameLength;
		
		result.size = readInt(data, pointer);
		pointer += 4;
		
//		pointer += result.size - sizeOffset - result.nameLength;
//		if(true) return result;
		
		result.fieldCount = readShort(data, pointer);
		pointer += 2;
		for(int i = 0; i < result.fieldCount; i++){
			RCField field = RCField.Deserialize(data, pointer);
			result.fields.add(field);
			pointer += field.getSize();
		}
		
		result.stringCount = readShort(data, pointer);
		pointer += 2;
		for(int i = 0; i < result.stringCount; i++){
			RCString string = RCString.Deserialize(data, pointer);
			result.strings.add(string);
			pointer += string.getSize();
		}
		
		result.arrayCount = readShort(data, pointer);
		pointer += 2;
		for(int i = 0; i < result.arrayCount; i++){
			RCArray array = RCArray.Deserialize(data, pointer);
			result.arrays.add(array);
			pointer += array.getSize();
		}
		
		return result;
	}
}

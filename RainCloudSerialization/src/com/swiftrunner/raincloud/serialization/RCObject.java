package com.swiftrunner.raincloud.serialization;

import static com.swiftrunner.raincloud.serialization.SerializationWriter.writeBytes;

import java.util.ArrayList;
import java.util.List;

public class RCObject{
	
	public static final byte CONTAINER_TYPE = ContainerType.OBJECT;
	public short nameLength;
	public byte[] name;
	
	private short fieldCount;
	private short arrayCount;
	private List<RCField> fields = new ArrayList<RCField>();
	private List<RCArray> arrays = new ArrayList<RCArray>();
	
	private int size = 1 + 2 + 2 + 2;
	
	
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
	
	
	public void addField(RCField field){
		fields.add(field);
		size += field.getSize();
		fieldCount = (short)fields.size();
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
		
		pointer = writeBytes(dest, pointer, fieldCount);
		for(RCField field : fields){
			pointer = field.getBytes(dest, pointer);
		}
		
		pointer = writeBytes(dest, pointer, arrayCount);
		for(RCArray array: arrays){
			pointer = array.getBytes(dest, pointer);
		}
		return pointer;
    }
}

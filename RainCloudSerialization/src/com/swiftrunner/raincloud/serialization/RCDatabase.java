package com.swiftrunner.raincloud.serialization;

import static com.swiftrunner.raincloud.serialization.SerializationWriter.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class RCDatabase{
	
	public static final byte[] HEADER = "RCDB".getBytes(); 
	public static final byte CONTAINER_TYPE = ContainerType.DATABASE;
	public short nameLength;
	public byte[] name;
	public int size = HEADER.length + 1 + 2 + 4 + 2;
	public short objectCount;
	public List<RCObject> objects = new ArrayList<RCObject>();
	
	
	private RCDatabase(){}
	
	
	public RCDatabase(String name){
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
	
	
	public void addObject(RCObject object){
		objects.add(object);
		size += object.getSize();
		objectCount = (short)objects.size();
	}
	
	
	public int getSize(){
		return size;
	}


	public int getBytes(byte[] dest, int pointer){
		pointer = writeBytes(dest, pointer, HEADER);
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, size);
		
		pointer = writeBytes(dest, pointer, objectCount);
		for(RCObject object: objects){
			pointer = object.getBytes(dest, pointer);
		}
		return pointer;
    }
	
	
	public static RCDatabase Deserialize(byte[] data){
		int pointer = 0;
		assert(readString(data, pointer, HEADER.length).equals(HEADER));
		pointer += HEADER.length;
		
		byte containerType = readByte(data, pointer++);
		assert(containerType == CONTAINER_TYPE);
		
		RCDatabase result = new RCDatabase();
		
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer += result.nameLength;
		
		result.size = readInt(data, pointer);
		pointer += 4;
		
		result.objectCount = readShort(data, pointer);
		pointer += 2;
		
		for(int i=0; i<result.objectCount; i++){
			RCObject object = RCObject.Deserialize(data, pointer);
			result.objects.add(object);
			pointer += object.getSize();
		}
		
	    return result;
    }


	public static RCDatabase DeserializeFromFile(String path){
		byte[] buffer = null;
		try{
	        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path));
	        buffer = new byte[stream.available()];
	        stream.read(buffer);
	        stream.close();
        } catch(Exception e){
	        e.printStackTrace();
        }
	    return Deserialize(buffer);
    }
}

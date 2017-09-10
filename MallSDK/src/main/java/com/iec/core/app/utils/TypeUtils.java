package com.iec.core.app.utils;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * 类型工具
 * 
 * @author Tumq
 * @Date 2015年1月23日
 * @describe
 */
public class TypeUtils {
	
	public static boolean isParameterizedType(Type type){
		return type instanceof ParameterizedType;
	}
	
	public static ParameterizedType getTypeAsParameterizedType(Type type){
		return ((ParameterizedType)type);
	}
	
	public static boolean isGenericArrayType(Type type){
		return type instanceof GenericArrayType;
	}
	
	public static GenericArrayType getTypeAsGenericArrayType(Type type){
		return ((GenericArrayType)type);
	}
	
	public static boolean isTypeVariable(Type type){
		return type instanceof TypeVariable;
	}
	
	public static TypeVariable getTypeAsTypeVariable(Type type){
		return ((TypeVariable)type);
	}
	
	public static boolean isWildcardType(Type type){
		return type instanceof WildcardType;
	}
	
	public static WildcardType getTypeAsWildcardType(Type type){
		return ((WildcardType)type);
	}
	
	public static boolean isClass(Type type){
		return type instanceof Class;
	}
	
	public static Class getTypeAsClass(Type type){
		return ((Class)type);
	}
}

package com.cm.app.module.demo;

import java.io.Serializable;

public class User  implements Serializable{
   /**
	 * 
	 */
private static final long serialVersionUID = 1L;
private String name;
   private int age;
   private String sex;
public final String getName() {
	return name;
}
public final void setName(String name) {
	this.name = name;
}
public final int getAge() {
	return age;
}
public final void setAge(int age) {
	this.age = age;
}
public final String getSex() {
	return sex;
}
public final void setSex(String sex) {
	this.sex = sex;
}

 public String toString() {
	 return this.name+"-"+this.age+"-"+this.sex;
 }
   
  
}


package com.berchina.vo; 

/** 

 * @author 作者姓名 cwt

 * @version 创建时间：2017年5月5日 下午4:16:37 

 * 会员实体

 */

public class MemberVo extends BaseVo{

	private static final long serialVersionUID = -5442699748939318653L;
	private String memberId;
	private String nickName;
	private String mobile;
	private String regTime;
	private String passwd;
	private String email;
    
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "MemberVo [memberId=" + memberId + ", nickName=" + nickName
				+ ", mobile=" + mobile + ", regTime=" + regTime + ", passwd="
				+ passwd + ", email=" + email + "]";
	}
}
 
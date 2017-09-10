
package com.berchina.vo; 

/** 

 * @author 作者姓名 cwt

 * @version 创建时间：2017年5月5日 下午4:11:10 

 * 店铺实体

 */

public class ShopVo extends BaseVo{

	private static final long serialVersionUID = -8711518498795265291L;
    private String shopId;    //主键id
    private String shopName;//店铺名称
    private String shopAddress;//店铺地址
    private double longitude;//店铺精度
    private double latitude;//店铺纬度
	 
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	@Override
	public String toString() {
		return "ShopVo [shopId=" + shopId + ", shopName=" + shopName
				+ ", shopAddress=" + shopAddress + ", longitude=" + longitude
				+ ", latitude=" + latitude + "]";
	}	
}
 
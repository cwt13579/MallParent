
package com.berchina.vo; 

import java.math.BigDecimal;

/** 

 * @author 作者姓名  cwt

 * @version 创建时间：2017年5月5日 下午4:16:52 

 * 订单商品实体

 */

public class OrderGoodsVo extends BaseVo{
 
	private static final long serialVersionUID = -4163905645175212308L;
	private String orderGoodsId;
	private String orderId;
	private String goodsId;
	private String goodsName;
	private String skuId;
	private String skuName;
	private Integer goodsNum;
	private BigDecimal goodsPrice;
	private BigDecimal goodsPayPrice;
	private BigDecimal goodsPayTotal;
	private String goodsPic;
	public String getOrderGoodsId() {
		return orderGoodsId;
	}
	public void setOrderGoodsId(String orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public BigDecimal getGoodsPayPrice() {
		return goodsPayPrice;
	}
	public void setGoodsPayPrice(BigDecimal goodsPayPrice) {
		this.goodsPayPrice = goodsPayPrice;
	}
	public BigDecimal getGoodsPayTotal() {
		return goodsPayTotal;
	}
	public void setGoodsPayTotal(BigDecimal goodsPayTotal) {
		this.goodsPayTotal = goodsPayTotal;
	}
	public String getGoodsPic() {
		return goodsPic;
	}
	public void setGoodsPic(String goodsPic) {
		this.goodsPic = goodsPic;
	}
}
 
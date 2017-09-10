
package com.berchina.vo; 

import java.math.BigDecimal;

/** 

 * @author 作者姓名 cwt

 * @version 创建时间：2017年5月5日 下午4:16:17 

 * 商品实体

 */

public class GoodsVo extends BaseVo{

	private static final long serialVersionUID = 2624656792127363466L;
	private String goodsId;
	private String goodsName;
	private BigDecimal goodsPrice;
	private String category;
	private String shopId;
	private Integer stock;
	
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
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	@Override
	public String toString() {
		return "GoodsVO [goodsId=" + goodsId + ", goodsName=" + goodsName
				+ ", goodsPrice=" + goodsPrice + ", category=" + category
				+ ", shopId=" + shopId + ", stock=" + stock + "]";
	}
	
}
 
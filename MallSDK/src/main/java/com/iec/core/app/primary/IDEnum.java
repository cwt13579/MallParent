package com.iec.core.app.primary;


public enum IDEnum
{
  VOUCH(1, "DEMO", 100000000L, 999999999L, 100), 
  COMMUNITY(1, "社区表", 100000000L, 999999999L, 100), 
  SHOP(2, "商铺表", 100000000L, 999999999L, 100), 
  T_BANK_PRODUCT(3, "银行理财产品表", 100000000L, 999999999L, 100), 
  BULLETIN(13, "公告表", 100000000L, 999999999L, 100), 
  BANK_COMMUNITY(4, "银行服务社区表", 100000000L, 999999999L, 100), 
  SHOP_APPLY(5, "商铺申请表", 100000000L, 999999999L, 100), 
  BESPOKE(6, "预约就餐表", 100000000L, 999999999L, 100), 
  BESPOKE_GOODS(7, "预约商品表", 100000000L, 999999999L, 100), 
  HISCOMMUNITY(8, "历史社区表", 100000000L, 999999999L, 100), 
  CART(9, "购物车表", 100000000L, 999999999L, 100), 
  CITY(10, "城市地区表", 100000000L, 999999999L, 100), 

  GOODS_IMG(11, "商品图片表", 100000000L, 999999999L, 100), 
  LIFE_TEL(12, "生活号码表", 100000000L, 999999999L, 100), 
  ORDER(13, "订单表", 100000000L, 999999999L, 100), 
  SHOP_GOODS(14, "商铺商品表", 100000000L, 999999999L, 100), 
  SHOP_APPLY_COMM(15, "申请商铺服务社区表", 100000000L, 999999999L, 100), 
  TEL_FAV(16, "号码收藏表", 100000000L, 999999999L, 100), 

  USER(17, "用户表", 100000000L, 999999999L, 1000), 
  USER_COMMUNITY(18, "社区管理员服务社区表", 100000000L, 999999999L, 100), 
  TEL_LINK_COMM(18, "生活号码服务社区表", 100000000L, 999999999L, 100), 
  COMM_LIGHTIN(18, "点亮社区表", 100000000L, 999999999L, 100), 
  CARD(20, "银行卡", 100000000L, 999999999L, 100), 

  UNKNOW(-1, "未知类型", 0L, 0L, 0);

  private Integer BIZ_TYPE;
  private Integer INCRET;
  private Long MINVAL;
  private Long MAXVAL;
  private String BIZ_DESC;

  private IDEnum(Integer BIZ_TYPE, String BIZ_DESC, Long MINVAL, Long MAXVAL, Integer INCRET) { 
	  this.BIZ_TYPE = BIZ_TYPE;
    this.BIZ_DESC = BIZ_DESC;
    this.MINVAL = MINVAL;
    this.MAXVAL = MAXVAL;
    this.INCRET = INCRET; }

  public Integer getBIZ_TYPE() {
    return this.BIZ_TYPE;
  }
  public String getBIZ_DESC() {
    return this.BIZ_DESC;
  }
  public Integer getINCRET() {
    return this.INCRET;
  }
  public Long getMINVAL() {
    return this.MINVAL;
  }
  public Long getMAXVAL() {
    return this.MAXVAL;
  }
  public static IDEnum getEnumType(int BIZ_TYPE) {
    for (IDEnum ptEnum : values()) {
      if (ptEnum.BIZ_TYPE.intValue() == BIZ_TYPE)
        return ptEnum;
    }
    return UNKNOW;
  }
}

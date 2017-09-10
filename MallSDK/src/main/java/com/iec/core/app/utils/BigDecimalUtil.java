package com.iec.core.app.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 项目名称：manageApp 类名称：BigDecimalUtil 类描述： 创建人：ex_kjkfb_lvrz 创建时间：2014年3月12日
 * 上午8:58:08 修改人：ex_kjkfb_lvrz 修改时间：2014年3月12日 上午8:58:08 修改备注：
 * 
 * @version
 * 
 */
public class BigDecimalUtil {

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	/**
     *
     * */
	private BigDecimalUtil() {
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 将字符串型日期转换为制定格式
	 * 
	 * @param strDate
	 *            字符串型日期
	 * @param dstDateFormat
	 *            目标日期格式
	 * @return Date 返回的util.Date型日期
	 */
	public static String stringToDate(String strDate, String srcDateFormat,
			String dstDateFormat) {
		Date rtDate = null;String resultDate=null;
		Date tmpDate = (new SimpleDateFormat(srcDateFormat)).parse(strDate,
				new ParsePosition(0));
		String tmpString = null;
		if (tmpDate != null) {
			tmpString = (new SimpleDateFormat(dstDateFormat)).format(tmpDate);
		}
		if (tmpString != null) {
			rtDate = (new SimpleDateFormat(dstDateFormat)).parse(tmpString,
					new ParsePosition(0));
		}
		resultDate=new SimpleDateFormat(dstDateFormat).format(rtDate);
		return resultDate;
	}
	
	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 四舍五入后格式化返回浮点类型
	 * @author chenchao 991722899@qq.com
	 * @date 2014-4-4 下午4:40:15
	 * @param v
	 * @param scale
	 * @return
	 */
	public static double roundFormatDouble(double v, int scale) {
		DecimalFormat format=new DecimalFormat();
		format.applyPattern("##,###,###,###,##0.00");
		return Double.parseDouble(format.format(new BigDecimal(v).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()));
	}
	
	/**
	 * 四舍五入返回字符类型
	 * @author chenchao 991722899@qq.com
	 * @date 2014-4-4 下午4:49:20
	 * @param v
	 * @param scale
	 * @return
	 */
	public static String roundFormatString(double v, int scale) {
		DecimalFormat format=new DecimalFormat();
		format.applyPattern("##,###,###,###,###0.00");
		return format.format(new BigDecimal(v).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
	}

	public static String science(String value) {
		BigDecimal b = new BigDecimal(value);
		return b.toPlainString();
	}

	public static void main(String[] args) {
//		DecimalFormat format=new DecimalFormat();
//		format.applyPattern("##,###,###,###,###0.00");
//		System.out.println(new BigDecimal(0.125).setScale(2,BigDecimal.ROUND_HALF_UP));
//		System.out.println(format.format(new BigDecimal(456789.125).setScale(BigDecimal.ROUND_HALF_UP,2)));
		System.out.println(BigDecimalUtil.science("1.0E9"));
	}
}

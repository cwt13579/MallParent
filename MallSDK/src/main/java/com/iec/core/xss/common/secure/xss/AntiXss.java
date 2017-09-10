package com.iec.core.xss.common.secure.xss;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * xss过滤处理
 * @author LUBANG713
 * @date 2014-4-19
 */
public class AntiXss
{
	/**
	 * html元素过滤字符集合
	 */
	private static final Map<Character, String> ignoreHtmlElementsTable = new HashMap<Character, String>();
	static
	{
		ignoreHtmlElementsTable.put('&', "&amp;");	// 屏蔽这个字符是有问题的，因为下面的字符转化后悔带有&，然后又被转化掉了
		ignoreHtmlElementsTable.put('<', "&lt;");
		ignoreHtmlElementsTable.put('>', "&gt;");
		ignoreHtmlElementsTable.put('\'', "&#39;");
		ignoreHtmlElementsTable.put('\"', "&quot;");
		ignoreHtmlElementsTable.put('\\', "&#92;");
		ignoreHtmlElementsTable.put('\n', " ");
		ignoreHtmlElementsTable.put('\r', " ");
	}

	/**
	 * html属性过滤字符集合
	 */
	private static final Map<Character, String> ignoreHtmlAttributesTable = new HashMap<Character, String>();
	static
	{
		ignoreHtmlAttributesTable.putAll(ignoreHtmlElementsTable);
		ignoreHtmlAttributesTable.put('=', "&#61;");
		ignoreHtmlAttributesTable.put('`', "&#96;");
	}

	/**
	 * xml元素过滤字符集合
	 */
	private static final Map<Character, String> ignoreXmlElementsTable = new HashMap<Character, String>();
	static
	{
		ignoreXmlElementsTable.putAll(ignoreHtmlElementsTable);
		ignoreXmlElementsTable.put('\'', "&apos;");
	}

	/**
	 * xml属性过滤字符集合
	 */
	private static final Map<Character, String> ignoreXmlAttributesTable = new HashMap<Character, String>();
	static
	{
		ignoreXmlAttributesTable.putAll(ignoreHtmlAttributesTable);
		ignoreXmlAttributesTable.put('\'', "&apos;");
	}

	/**
	 * 对需要出现在HTML正文里(除了HTML属性外)的不信任输入进行编码 例如： <a
	 * href='http://www.contoso.com'>Click Here [Un-trusted input]</a> 以下字符将被编码:
	 * < --> &lt; > --> &gt; ' --> &#39; " --> &quot; & --> &amp;
	 * 
	 * @param source
	 *            要编码的输入串
	 * @return 编码后的串，可以直接插入HTML正文中
	 */
	public static String htmlEncode(String source)
	{
		// pre guard
		if (source == null)
		{
			return null;
		}
		StringBuilder sb = new StringBuilder(source.length() + 32);
		char[] chars = source.toCharArray();
		char ch;
		for (int _ = 0; _ < chars.length; ++_)
		{
			ch = chars[_];
			String rep = ignoreHtmlElementsTable.get(ch);
			if (rep != null)
			{
				sb.append(rep);
			} else
			{
				sb.append(ch);
			}
		}
//		return sb.toString().replaceAll("\\\\u003c", "&lt;").replaceAll("\\\\u003e", "&gt;");
		return sb.toString();
	}

	/**
	 * 对需要出现在HTML属性里的不信任输入进行编码 例如: <input name="searchword" value="[Un-trusted
	 * input]"> 以下字符将被编码: < --> &lt; > --> &gt; ' --> &#39; " --> &quot; & -->
	 * &amp; = --> &#61; ` --> &#96; 注意:
	 * (1)该函数不适用于属性为一个URL地址的编码.这些标记包括:a/img/frame
	 * /iframe/script/xml/embed/object...
	 * 属性包括:href/src/lowsrc/dynsrc/background/... (2)该函数不适用于属性名为
	 * style="[Un-trusted input]" 的编码
	 * 
	 * @param source
	 *            要编码的输入串
	 * @return 编码后的串，可以直接插入HTML属性里
	 */
	public static String htmlAttributeEncode(String source)
	{
		if (source == null)
		{
			return null;
		}

		StringBuilder sb = new StringBuilder(source.length() + 32);
		char[] chars = source.toCharArray();
		char ch;
		for (int _ = 0; _ < chars.length; ++_)
		{
			ch = chars[_];
			String rep = ignoreHtmlAttributesTable.get(ch);
			if (rep != null)
			{
				sb.append(rep);
			} else
			{
				sb.append(ch);
			}
		}

		return sb.toString();
	}

	private static final String xPrefix = "\\x";

	/**
	 * 对需要出现在JavaScript上下文中的不信任输入进行编码 例如: <script type="text/javascript"> … var
	 * mymsg="[Un-trusted input]"; var uin=[Un-trusted input]; … </script>
	 * 以下字符将会被 编码/转义: 除[a-zA-Z0-9.-_,]以及ASCII值大于0x80之外的所有字符
	 * 可以转义成"\xFF"形式的16进制ASCII值 注意: 不信任的输入只能作为脚本的数据部分;绝对不允许不信任的输入作为脚本的代码部分;
	 * 
	 * @param source
	 *            要编码的输入串
	 * @return 编码后的串，可以直接插入JavaScrip字符中
	 * 
	 */
	public static String javaScriptEncode(String source)
	{
		if (source == null)
			return "";
		StringBuilder sb = new StringBuilder();
		char ch;
		for (int i = 0; i < source.length(); i++)
		{
			ch = source.charAt(i);
			if ((ch >= 48 && ch <= 59) // 0~9
					|| (ch >= 65 && ch <= 90) // a~z
					|| (ch >= 97 && ch <= 122) // A~Z
					|| ch == '.' || ch == '-' || ch == '_'
					|| ch == ','
					|| ch >= 0x80)
			{
				sb.append(ch);
			} else
			{
				sb.append(xPrefix).append(Integer.toHexString((int) ch));
			}
		}
		return sb.toString();
	}

	/**
	 * 对出现在一个URI一部分的不信任输入进行编码 例如: <a
	 * href="http://search.msn.com/results.aspx?q1=[Un-trusted-input]&
	 * q2=[Un-trusted-input]">Click Here!</a> 以下字符将会被编码:
	 * 除[a-zA-Z0-9.-_]以外的所有字符会被替换为相应的URL编码 注意:
	 * 如果这个URL的全部都是由不信任的输入构成的，需要使用函数UrlFilterAndEncode;
	 * 
	 * @param source
	 * @return
	 */
	public static String uriComponentEncode(String source)
	{
		if (source == null)
			return null;

		try
		{
			return URLEncoder.encode(source,
					System.getProperty("file.encoding"));
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return "";
		}
	}

	private static final String urlPatternStr = "^(ht|f)tp(s?)://";
	private static final Pattern urlPattern = Pattern.compile(urlPatternStr);
	private static final Set<Character> unFilterChars = new HashSet<Character>();
	static
	{
		unFilterChars.add('!');
		unFilterChars.add('#');
		unFilterChars.add('$');
		unFilterChars.add('%');
		unFilterChars.add('&');
		unFilterChars.add('('); // 6
		unFilterChars.add(')');
		unFilterChars.add('*');
		unFilterChars.add('+');
		unFilterChars.add(',');
		unFilterChars.add('-'); // 11
		unFilterChars.add('.');
		unFilterChars.add('/');
		unFilterChars.add(':');
		unFilterChars.add(';');
		unFilterChars.add('='); // 16
		unFilterChars.add('?');
		unFilterChars.add('@');
		unFilterChars.add('_'); // 19
	}

	/**
	 * 对作为一个完整URL的不信任输入进行编码和过滤 例如: <a href="[Un-trusted-input]">Click Here!</a>
	 * 只允许以下格式的输入: (ht|f)tp(s?):// 非该格式的输入将会被添加"http://" 以下字符将会被编码:
	 * 除[a-zA-Z0-9!#$%&()*+,-.@/:;=?_]外的所有字符
	 * 
	 * @param source
	 * @return
	 */
	public static String urlFilterAndEncode(String source)
	{
		if (source == null)
			return "";
		Matcher matcher = urlPattern.matcher(source);
		if (!matcher.find())
		{
			source = "http://" + source;
		}

		StringBuilder sb = new StringBuilder();
		char ch;
		for (int i = 0; i < source.length(); i++)
		{
			ch = source.charAt(i);
			if ((ch >= 48 && ch <= 59) || (ch >= 65 && ch <= 90)
					|| (ch >= 97 && ch <= 122) || unFilterChars.contains(ch))
			{
				sb.append(ch);
			} else
			{
				sb.append(xPrefix).append(Integer.toHexString((int) ch));
			}
		}
		return sb.toString();
	}

	/**
	 * 对需要出现在XML正文里(除了XML属性外)的不信任输入进行编码 例如： <xml_tag>[Un-trusted
	 * input]</xml_tag> 以下字符将被编码: < --> &lt; > --> &gt; ' --> &apos; " -->
	 * &quot; & --> &amp;
	 * 
	 * @param source
	 * @return
	 */
	public static String xmlEncode(String source)
	{
		// pre guard
		if (source == null)
		{
			return null;
		}
		StringBuilder sb = new StringBuilder(source.length() + 32);
		char[] chars = source.toCharArray();
		char ch;
		for (int _ = 0; _ < chars.length; ++_)
		{
			ch = chars[_];
			String rep = ignoreXmlElementsTable.get(ch);
			if (rep != null)
			{
				sb.append(rep);
			} else
			{
				sb.append(ch);
			}
		}

		return sb.toString();
	}

	/**
	 * 对需要出现在XML属性里的不信任输入进行编码 例如: <xml_tag attribute=[Un-trusted input]>Some
	 * Text</xml_tag> 以下字符将被编码: < --> &lt; > --> &gt; ' --> &apos; " --> &quot;
	 * & --> &amp; = --> &#61; ` --> &#96; 注意: (1)所有的属性值必须用(单/双)引号括起来
	 * 
	 * @param source
	 * @return
	 */
	public static String xmlAttributeEncode(String source)
	{
		if (source == null)
		{
			return null;
		}

		StringBuilder sb = new StringBuilder(source.length() + 32);
		char[] chars = source.toCharArray();
		char ch;
		for (int _ = 0; _ < chars.length; ++_)
		{
			ch = chars[_];
			String rep = ignoreXmlAttributesTable.get(ch);
			if (rep != null)
			{
				sb.append(rep);
			} else
			{
				sb.append(ch);
			}
		}

		return sb.toString();
	}

	private static final Pattern validUrlPattern = Pattern
			.compile(
					"^(((https?:\\/\\/)?[\\w\\-.]+\\.(qq|paipai|soso)\\.com($|\\/|\\\\))|([\\w\\/][^\\/][\\w\\/\\.\\-_]+$))",
					Pattern.CASE_INSENSITIVE);

	/**
	 * 
	 * url转向验证
	 * 
	 * 描述：对通过javascript语句载入（或转向）的页面进行验证，防止转到第三方网页和跨站脚本攻击 返回值：true -- 合法；false --
	 * 非法 例：合法的值 http://xxx.qq.com/hi/redirect.html?url=http://www.qq.com
	 * http://xxx.qq.com/hi/redirect.html?url=a.html
	 * http://xxx.qq.com/hi/redirect.html?url=/a/1.html 非法的值
	 * http://xxx.qq.com/hi/redirect.html?url=http://www.baidu.com
	 * http://xxx.qq.com/hi/redirect.html?url=javascript:codehere
	 * http://xxx.qq.com/hi/redirect.html?url=//www.qq.com
	 * 
	 * @return
	 */
	public static boolean validUrl(String source)
	{
		Matcher m = validUrlPattern.matcher(source);
		return m.find();
	}

	private static final char[] INVALID_CHARS = new char[]{'<', '>', '\'', '\"', '\\'};
	private static final char[] VALID_CHARS = new char[]{'＜', '＞', '‘', '“', '＼'};
	public static String toChineseSymbols(String str){
		for(int j = 0; j < INVALID_CHARS.length; ++j){
			if(str.indexOf(INVALID_CHARS[j]) >= 0){
				str = str.replace(INVALID_CHARS[j], VALID_CHARS[j]);
			}
		}
		return str;
	}
}

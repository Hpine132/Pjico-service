package src.common.font;

import java.util.HashMap;
import java.util.Map;


/**
 * This class support to convert a string from encoded to other
 */
public class FontConverter {
	static Map<String, ConvertType> CACHE_TYPES = new HashMap<String, ConvertType>();

	public static String convert(FontType fromType, FontType toType, String str) {
		if (str == null) {
			return "";
		}

		if (fromType.equals(toType)) {
			return str;
		}

		// Cache it
		String key = fromType.getName() + '-' + toType.getName();
		ConvertType ct = CACHE_TYPES.get(key);
		if (ct == null) {
			ct = new ConvertType(fromType, toType);
			CACHE_TYPES.put(key, ct);
		}

		char[] chs = str.toCharArray();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chs.length;) {
			int max = fromType.getMaxChar();
			String ch = String.valueOf(chs[i]);

			if (max == 1) {
				if (!ct.convert(ch, sb)) {
					sb.append(ch);
				}
			} else {
				StringBuilder s2 = new StringBuilder();
				s2.append(ch);
				for (int j = 1; j < max; j++) {
					if (i + j < chs.length) {
						s2.append(chs[i + j]);
					}
				}
				if (ct.convert(s2.toString(), sb)) {
					i += max - 1;
				} else if (!ct.convert(ch, sb)) {
					sb.append(ch);
				}
			}
			i++;
		}
		return sb.toString();
	}
}

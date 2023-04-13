package src.common.font;

import java.util.ArrayList;
import java.util.List;

/**
 * A font type used for FontConverer
 */
public class FontType {
	static final List<FontType> SPECIAL_FONTS = new ArrayList<FontType>();

	private String name = "UNKNOW";
	private String[] symbols = null;
	// Use to find font
	private final List<String> fontCharaters = new ArrayList<String>();
	private int maxChar = 1;

	public static final FontType UNICODE;

	public static final FontType TCVN3;

	static {
		UNICODE = new FontType("UNICODE", new String[] { "à", "á", "ả", "ã", "ạ", 
				"ă", "ằ", "ắ", "ẳ", "ẵ", "ặ", 
				"â", "ầ", "ấ", "ẩ", "ẫ", "ậ", 
				"đ", "è", "é", "ẻ", "ẽ", "ẹ", 
				"ê", "ề", "ế", "ể", "ễ", "ệ", 
				"ì", "í", "ỉ", "ĩ", "ị", 
				"ò", "ó", "ỏ", "õ", "ọ", 
				"ô", "ồ", "ố", "ổ", "ỗ", "ộ", 
				"ơ", "ờ", "ớ", "ở", "ỡ", "ợ", 
				"ù", "ú", "ủ", "ũ", "ụ", 
				"ư", "ừ", "ứ", "ử", "ữ", "ự", 
				"ỳ", "ý", "ỷ", "ỹ", "ỵ",
				"À", "Á", "Ả", "Ã", "Ạ", 
				"Ă", "Ằ", "Ắ", "Ẳ", "Ẵ", "Ặ", 
				"Â", "Ầ", "Ấ", "Ẩ", "Ẫ", "Ậ", 
				"Đ", "È", "É", "Ẻ", "Ẽ", "Ẹ", 
				"Ê", "Ề", "Ế", "Ể", "Ễ", "Ệ", 
				"Ì", "Í", "Ỉ", "Ĩ", "Ị", 
				"Ò", "Ó", "Ỏ", "Õ", "Ọ", 
				"Ô", "Ồ", "Ố", "Ổ", "Ỗ", "Ộ", 
				"Ơ", "Ờ", "Ớ", "Ở", "Ỡ", "Ợ", 
				"Ù", "Ú", "Ủ", "Ũ", "Ụ", 
				"Ư", "Ừ", "Ứ", "Ử", "Ữ", "Ự", 
				"Ỳ", "Ý", "Ỷ", "Ỹ", "Ỵ"});

		TCVN3 = toSpecialFont("TCVN3", 1, new String[] { "µ", "¸", "¶", "·", "¹", 
				"¨", "»", "¾", "¼", "½", "Æ", 
				"©", "Ç", "Ê", "È", "É", "Ë", 
				"®", "Ì", "Ð", "Î", "Ï", "Ñ", 
				"ª", "Ò", "Õ", "Ó", "Ô", "Ö", 
				"×", "Ý", "Ø", "Ü", "Þ", 
				"ß", "ã", "á", "â", "ä", 
				"«", "å", "è", "æ", "ç", "é", 
				"¬", "ê", "í", "ë", "ì", "î", 
				"ï", "ó", "ñ", "ò", "ô", 
				"­", "õ", "ø", "ö", "÷", "ù", 
				"ú", "ý", "û", "ü", "þ",
				"µ", "¸", "¶", "·", "¹", 
				"¡", "»", "¾", "¼", "½", "Æ", 
				"¢", "Ç", "Ê", "È", "É", "Ë", 
				"§", "Ì", "Ð", "Î", "Ï", "Ñ", 
				"£", "Ò", "Õ", "Ó", "Ô", "Ö", 
				"×", "Ý", "Ø", "Ü", "Þ", 
				"ß", "ã", "á", "â", "ä", 
				"¤", "å", "è", "æ", "ç", "é", 
				"¥", "ê", "í", "ë", "ì", "î", 
				"ï", "ó", "ñ", "ò", "ô", 
				"¦", "õ", "ø", "ö", "÷", "ù", 
				"ú", "ý", "û", "ü", "þ" });
		
//		UNICODE = new FontType("UNICODE", new String[] { "à", "á", "ả", "ã", "ạ", 
//		        "ă", "ằ", "ắ", "ẳ", "ẵ", "ặ", 
//		        "â", "ầ", "ấ", "ẩ", "ẫ", "ậ", 
//		        "đ", "è", "é", "ẻ", "ẽ", "ẹ", 
//		        "ê", "ề", "ế", "ể", "ễ", "ệ", 
//		        "ì", "í", "ỉ", "ĩ", "ị", 
//		        "ò", "ó", "ỏ", "õ", "ọ", 
//		        "ô", "ồ", "ố", "ổ", "ỗ", "ộ", 
//		        "ơ", "ờ", "ớ", "ở", "ỡ", "ợ", 
//		        "ù", "ú", "ủ", "ũ", "ụ", 
//		        "ư", "ừ", "ứ", "ử", "ữ", "ự", 
//		        "ỳ", "ý", "ỷ", "ỹ", "ỵ", 
//		        "Ă", "Â", "Đ", "Ê", "Ô", "Ơ", "Ư" });
//
//		TCVN3 = toSpecialFont("TCVN3", 1, new String[] { "µ", "¸", "¶", "·", "¹", 
//		        "¨", "»", "¾", "¼", "½", "Æ", 
//		        "©", "Ç", "Ê", "È", "É", "Ë", 
//		        "®", "Ì", "Ð", "Î", "Ï", "Ñ", 
//		        "ª", "Ò", "Õ", "Ó", "Ô", "Ö", 
//		        "×", "Ý", "Ø", "Ü", "Þ", 
//		        "ß", "ã", "á", "â", "ä", 
//		        "«", "å", "è", "æ", "ç", "é", 
//		        "¬", "ê", "í", "ë", "ì", "î", 
//		        "ï", "ó", "ñ", "ò", "ô", 
//		        "­", "õ", "ø", "ö", "÷", "ù", 
//		        "ú", "ý", "û", "ü", "þ", 
//		        "¡", "¢", "§", "£", "¤", "¥", "¦" });

	}

	public FontType() {
		super();
	}

	public FontType(String name, String[] symbols) {
		super();
		this.name = name;
		this.symbols = symbols;
	}

	private static FontType toSpecialFont(String name, int maxChar,
			String[] symbols) {
		FontType type = new FontType(name, symbols);
		type.maxChar = maxChar;

		String[] symbolUnicodes = FontType.UNICODE.symbols;

		for (String str : symbols) {
			boolean ex = false;
			for (String strU : symbolUnicodes) {
				if (strU.equals(str)) {
					ex = true;
					break;
				}
			}
			if (ex) {
				continue;
			}

			type.fontCharaters.add(str);
		}
		return type;
	}

	/**
	 * Max characters in a String compared
	 *
	 * @return
	 */
	protected int getMaxChar() {
		return maxChar;
	}

	public String[] getSymbols() {
		return symbols;
	}

	/**
	 * Use to find font
	 *
	 * @return
	 */
	public List<String> getFontCharaters() {
		return fontCharaters;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		FontType other = (FontType) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}

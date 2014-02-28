public class FontUtil {

	private static FontUtil instance;
	private static AssetManager asset;
	private Hashtable<String, Typeface> fontMap = new Hashtable<String, Typeface>();
	public final static String FONT_BLACK = "AvenirLTStd-Black.otf";
	public final static String FONT_BOOK = "AvenirLTStd-Book.otf";
	public final static String FONT_HEAVY = "AvenirLTStd-Heavy.otf";
	public final static String FONT_LIGHT = "AvenirLTStd-Light.otf";

	public FontUtil(Context mContext) {
		asset = mContext.getAssets();
	}

	public static FontUtil getInstance(Context _context) {
		if (null == instance) {
			instance = new FontUtil(_context);
		}
		return instance;
	}

	public Typeface getFont(String font) {
		Typeface typeface = fontMap.get(font);
		if (typeface == null) {
			typeface = Typeface.createFromAsset(asset, "fonts/" + font);
			fontMap.put(font, typeface);
		}
		return typeface;
	}
}

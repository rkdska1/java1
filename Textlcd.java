package JNI;

public class Textlcd {
	static {
		System.loadLibrary("textlcd");
	}

	public native int TextLCDOut(String str, String str2);

	public native int IOCtlClear();

	public native int IOCtlReturnHome();

	public native int IOCtlDisplay(boolean bOn);

	public native int IOCtlCursor(boolean bOn);

	public native int IOCtlBlink(boolean bOn);

	int ret;
	boolean disp, cursor, blink;

	public Textlcd() {
		disp = true;
		cursor = false;
		blink = false;
		IOCtlClear();
		IOCtlReturnHome();
		IOCtlDisplay(disp);
		IOCtlCursor(cursor);
		IOCtlBlink(blink);
		ret = TextLCDOut("  HANBACK   ", "  Electronics!  ");
	}

	public void Write(String str, String str2) {
		ret = TextLCDOut(str, str2);
	}

	public void Clear() {
		IOCtlClear();
	}
}

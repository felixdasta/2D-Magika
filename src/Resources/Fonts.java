package Resources;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Fonts {
	public static Font primetimeFont;
	public static Font codeFont;
	public Fonts(){
		try {
			primetimeFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("res/Fonts/PRIMETIME Bold.ttf"))).deriveFont(Font.PLAIN, 10);
			codeFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("res/Fonts/CODE Bold.otf"))).deriveFont(Font.PLAIN, 14);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class RandomUtilities {
	private static Random r = new Random();

	public static String generarTexto() {
		return "texto" + Integer.toString(RandomUtilities.r.nextInt(20));
	}

	public static String generarURL() {
		return "https://facebook.com/image"
				+ Integer.toString(RandomUtilities.r.nextInt(20)) + ".jpg";
	}

	public static int generarNumero(final int min, final int max) {
		return (RandomUtilities.r.nextInt(max + 1 - min) + min);
	}

	public static String generarCCNumber() {
		return "4012888888881881";
	}

	public static Date generarFechaPasada() {
		final Calendar c = new GregorianCalendar();
		c.set(2017, RandomUtilities.generarNumero(1, 12),
				RandomUtilities.generarNumero(1, 29));
		return c.getTime();
	}

	public static void main(final String[] args) {
		System.out.println(RandomUtilities.generarNumero(0, 1));
	}
}

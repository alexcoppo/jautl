package net.sf.jautl.graphics.colors;

public class GammaCorrection {
	public static double linearToScreen(double value, double gamma) {
        return Math.pow(value, 1 / gamma);
	}

	public static double screenToLinear(double value, double gamma) {
        return Math.pow(value, gamma);
	}

	/**
	 * Perform the inverse gamma correction on the color.
	 * @param color the color to which operate on
	 * @param gamma the value to use for gamma
	 */
	public static void screenToLinear(ColorRGBAF color, double gamma) {
        if (gamma == 1) return;

        color.setR((float)screenToLinear(color.getR(), gamma));
        color.setG((float)screenToLinear(color.getG(), gamma));
        color.setB((float)screenToLinear(color.getB(), gamma));
    }

	/**
	 * Perform the inverse gamma correction on the color.
	 * @param color the color to which operate on
	 * @param gamma the value to use for gamma
	 */
	public static void linearToScreen(ColorRGBAF color, double gamma) {
        if (gamma == 1) return;

        color.setR((float)linearToScreen(color.getR(), gamma));
        color.setG((float)linearToScreen(color.getG(), gamma));
        color.setB((float)linearToScreen(color.getB(), gamma));
    }
}

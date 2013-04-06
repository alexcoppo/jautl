/*
    Copyright (c) 2000-2013 Alessandro Coppo
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:
    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.
    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.
    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.jautl.graphics.colors;

/**
 * This class contains the definitions used by the [A]rtist [C]olor [N]aming [S]ystem.
 */
public class ACNSConstants {
    /// <summary>
    /// Value of Black.
    /// </summary>
	public static double BLACK     = 0 / 6.0;
    /// <summary>
    /// Value of VeryDark colors.
    /// </summary>
    public static double VERYDARK  = 1 / 6.0;
    /// <summary>
    /// Value of Dark colors.
    /// </summary>
    public static double DARK      = 2 / 6.0;
    /// <summary>
    /// Value of Medium colors.
    /// </summary>
    public static double MEDIUM    = 3 / 6.0;
    /// <summary>
    /// Value of Light colors.
    /// </summary>
    public static double LIGHT     = 4 / 6.0;
    /// <summary>
    /// Value of VeryLight colors.
    /// </summary>
    public static double VERYLIGHT = 5 / 6.0;
    /// <summary>
    /// Value of Bright colors.
    /// </summary>
    public static double BRIGHT    = 6 / 6.0;

    //Saturation encodings

    /// <summary>
    /// Saturation of Greyish colors.
    /// </summary>
    public static double GREYISH  = 1 / 4.0;
    /// <summary>
    /// Saturation of Moderate colors.
    /// </summary>
    public static double MODERATE = 2 / 4.0;
    /// <summary>
    /// Saturation of Strong colors.
    /// </summary>
    public static double STRONG = 3 / 4.0;
    /// <summary>
    /// Saturation of Vivid colors.
    /// </summary>
    public static double VIVID = 4 / 4.0;

    //Hues encodings

    /// <summary>
    /// Hue of Red.
    /// </summary>
    public static double RED             =   0.0 / 360;
    /// <summary>
    /// Hue of OrangishRed.
    /// </summary>
    public static double ORANGISHRED = 7.5 / 360;
    /// <summary>
    /// Hue of RedOrange.
    /// </summary>
    public static double REDORANGE = 15.0 / 360;
    /// <summary>
    /// Hue of ReddishOrange.
    /// </summary>
    public static double REDDISHORANGE = 22.5 / 360;
    /// <summary>
    /// Hue of Orange.
    /// </summary>
    public static double ORANGE = 30.0 / 360;
    /// <summary>
    /// Hue of YellowishOrange.
    /// </summary>
    public static double YELLOWISHORANGE = 37.5 / 360;
    /// <summary>
    /// Hue of OrangeYellow.
    /// </summary>
    public static double ORANGEYELLOW = 45.0 / 360;
    /// <summary>
    /// Hue of OrangishYellow.
    /// </summary>
    public static double ORANGISHYELLOW = 52.5 / 360;
    /// <summary>
    /// Hue of Yellow.
    /// </summary>
    public static double YELLOW = 60.0 / 360;
    /// <summary>
    /// Hue of GreenishYellow.
    /// </summary>
    public static double GREENISHYELLOW = 135.0 / 360;
    /// <summary>
    /// Hue of YellowGreen.
    /// </summary>
    public static double YELLOWGREEN = 150.0 / 360;
    /// <summary>
    /// Hue of YellowishGreen.
    /// </summary>
    public static double YELLOWISHGREEN = 165.0 / 360;
    /// <summary>
    /// Hue of Green.
    /// </summary>
    public static double GREEN = 180.0 / 360;
    /// <summary>
    /// Hue of BluishGreen.
    /// </summary>
    public static double BLUISHGREEN = 195.0 / 360;
    /// <summary>
    /// Hue of GreenBlue.
    /// </summary>
    public static double GREENBLUE = 210.0 / 360;
    /// <summary>
    /// Hue of GreenishBlue.
    /// </summary>
    public static double GREENISHBLUE = 225.0 / 360;
    /// <summary>
    /// Hue of Blue.
    /// </summary>
    public static double BLUE = 240.0 / 360;
    /// <summary>
    /// Hue of PurplishBlue.
    /// </summary>
    public static double PURPLISHBLUE = 255.0 / 360;
    /// <summary>
    /// Hue of BluePurple.
    /// </summary>
    public static double BLUEPURPLE = 270.0 / 360;
    /// <summary>
    /// Hue of BlueishPurple.
    /// </summary>
    public static double BLUEISHPURPLE = 285.0 / 360;
    /// <summary>
    /// Hue of Purple.
    /// </summary>
    public static double PURPLE = 300.0 / 360;
    /// <summary>
    /// Hue of ReddishPurple.
    /// </summary>
    public static double REDDISHPURPLE = 315.0 / 360;
    /// <summary>
    /// Hue of PurpleRed.
    /// </summary>
    public static double PURPLERED = 330.0 / 360;
    /// <summary>
    /// Hue of PurplishRed.
    /// </summary>
    public static double PURPLISHRED = 345.0 / 360;

    /**
     * Perform a deformantion of hue values in order to make the
     * resulting mapping more similar to eye different sensibility
     * to hue differences in different places of the specturm.
     * @param hue the hue to be deformed
     * @return the modified hue
     */
    public static double compressHue(double hue) {
        //Compress range Green->Blue and ExpandRange range Red to Yellow
        //   Color   StdH    Delta   ModifH  Delta
        //   Red     0       -       0       -
        //   Yellow  60      60      120     120
        //   Green   120     60      180     60
        //   Cyan    180     60      210     30
        //   Blue    240     60      240     30
        //   Magenta 300     60      300     60
        //   Red     0       60      0       60
        switch ((int)(hue / 60.0001)) {
            case 0:
                return hue * 2;           //Red->Yellow
            case 1:
                return hue + 1 / 6.0;     //Yellow->Green
            case 2:
                return hue / 2 + 1 / 3.0; //Green->Cyan
            case 3:
                return hue / 2 + 1 / 3.0; //Cyan->Blue
            case 4:
                return hue;               //Blue->Magenta
            case 5:
                return hue;               //Magenta->Red
            default:
                return hue;
        }
    }

    /**
     * Perform the opposite operation of compressHue()
     * @param hue the hue to be un-deformed
     * @return the modified hue
     */
    public double expandHue(double hue) {
        hue /= 60.001;

        if (hue < 1 / 3.0)
            return hue / 2;
        if (hue < 1 / 2.0)
            return hue - 1 / 6.0;
        if (hue < 2 / 3.0)
            return 2 * (hue - 1 / 2.0) + 1 / 3.0;
        return hue;
    }
}

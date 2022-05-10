package physics;

/**
 * Utility class for converting between units.
 */
public final class Units {
    
    /**
     * Inhibit instantiations.
     */
    private Units() {
    
    }
    
    public enum IndexNotation {
        YOCTO("yocto", "y", Math.pow(10, -24)),
        ZEPTO("zepto", "z", Math.pow(10, -21)),
        ATTO("atto", "a", Math.pow(10, -18)),
        FEMTO("femto", "f", Math.pow(10, -15)),
        PICO("pico", "p", Math.pow(10, -12)),
        NANO("nano", "n", Math.pow(10, -9)),
        MICRO("micro", "m", Math.pow(10, -6)),
        MILLI("milli", "m", Math.pow(10, -3)),
        CENTI("centi", "c", Math.pow(10, -2)),
        DECI("deci", "d", Math.pow(10, -1)),
        DECA("deca", "da", Math.pow(10, 1)),
        HECTO("hecto", "h", Math.pow(10, 2)),
        KILO("kilo", "k", Math.pow(10, 3)),
        MEGA("mega", "M", Math.pow(10, 6)),
        GIGA("giga", "G", Math.pow(10, 9)),
        TERA("tera", "T", Math.pow(10, 12)),
        PETA("peta", "P", Math.pow(10, 15)),
        EXA("exa", "E", Math.pow(10, 18)),
        ZETTA("zetta", "Z", Math.pow(10, 21)),
        YOTTA("yotta", "Y", Math.pow(10, 24));

        private final String NAME;
        private final String SYMBOL;
        private final double BASE_10;

        IndexNotation(final String NAME, final String SYMBOL, final double BASE_10){
            this.NAME = NAME;
            this.SYMBOL = SYMBOL;
            this.BASE_10 = BASE_10;
        }
    }
    public static double convertInto(final double number, final IndexNotation notation){
        return number * notation.BASE_10;
    }
    public static String getFormat(final double number, final IndexNotation notation){
        return convertInto(number, notation) + " " + notation.SYMBOL;
    }
}

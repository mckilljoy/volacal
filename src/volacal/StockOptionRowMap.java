package volacal;

import java.util.HashMap;
import java.math.BigDecimal;

//
// Represents all data associated with an option
//
public class StockOptionRowMap {

    HashMap Map;

    static private final int PAD_WIDTH = 10;
    static private final int DOLLAR_DECIMALS = 2;
    static private final int GREEK_DECIMALS = 5;
    static private final int DAYS_DECIMALS = 2;

    // Constructors
    public StockOptionRowMap( String sy, String tp, double s ) {

        Map = new HashMap(8);

        Map.put( "symbol", sy );
        Map.put( "type", tp );
        Map.put( "strike", new Double(s) );

    }

    public StockOptionRowMap( String sy, String tp, double s, double p, double d, double g, double v, double t, double r, double da ) {

        Map = new HashMap(8);

        Map.put( "symbol", sy );
        Map.put( "type", tp );
        Map.put( "strike", new Double(s) );
        Map.put( "price", new Double(p) );
        Map.put( "delta", new Double(d) );
        Map.put( "gamma", new Double(g) );
        Map.put( "vega", new Double(v) );
        Map.put( "theta", new Double(t) );
        Map.put( "rho", new Double(r) );
        Map.put( "days", new Double(da) );

    }

    // Output
    private static String rightPad( String s, int n, boolean signAlign ) {

        if( signAlign == true ) {
            if( s.charAt(0) == '-' ) {
                // Do nothin fancy.
                return String.format("%1$-" + n + "s", s);  
            } else {
                // Pad this by one space
                return String.format("%1$-" + n + "s", " " + s);  
            }
        } else { 
            // Do nothin fancy.
            return String.format("%1$-" + n + "s", s);  
        }
    }

    public static String StringHeader() {

        String output = "";
        output += rightPad( "Symbol", PAD_WIDTH, false);
        output += rightPad( "Type", PAD_WIDTH, false);
        output += rightPad( "Strike", PAD_WIDTH, false);
        output += rightPad( "Price", PAD_WIDTH, false);
        output += rightPad( "Delta", PAD_WIDTH, false);
        output += rightPad( "Gamma", PAD_WIDTH, false);
        output += rightPad( "Vega", PAD_WIDTH, false);
        output += rightPad( "Theta", PAD_WIDTH, false);
        output += rightPad( "Rho", PAD_WIDTH, false);
        output += rightPad( "DaysLeft", PAD_WIDTH, false);

        return output;

    }
    
    public String StringData() {

        BigDecimal bd;

        String output = "";
        output += rightPad( (String)Map.get("symbol"), PAD_WIDTH, false );

        output += rightPad( (String)Map.get("type"), PAD_WIDTH, false );

        bd = new BigDecimal( (Double)Map.get("strike") );
        bd = bd.setScale( DOLLAR_DECIMALS, BigDecimal.ROUND_DOWN );
        output += rightPad( "$" + bd.toString(), PAD_WIDTH, false );

        bd = new BigDecimal( (Double)Map.get("price") );
        bd = bd.setScale( DOLLAR_DECIMALS, BigDecimal.ROUND_DOWN );
        output += rightPad( "$" + bd.toString(), PAD_WIDTH, false );

        bd = new BigDecimal( (Double)Map.get("delta") );
        bd = bd.setScale( GREEK_DECIMALS, BigDecimal.ROUND_DOWN );
        output += rightPad( bd.toString(), PAD_WIDTH, true );

        bd = new BigDecimal( (Double)Map.get("gamma") );
        bd = bd.setScale( GREEK_DECIMALS, BigDecimal.ROUND_DOWN );
        output += rightPad( bd.toString(), PAD_WIDTH, true );

        bd = new BigDecimal( (Double)Map.get("vega") );
        bd = bd.setScale( GREEK_DECIMALS, BigDecimal.ROUND_DOWN );
        output += rightPad( bd.toString(), PAD_WIDTH, true );

        bd = new BigDecimal( (Double)Map.get("theta") );
        bd = bd.setScale( GREEK_DECIMALS, BigDecimal.ROUND_DOWN );
        output += rightPad( bd.toString(), PAD_WIDTH, true );

        bd = new BigDecimal( (Double)Map.get("rho") );
        bd = bd.setScale( GREEK_DECIMALS, BigDecimal.ROUND_DOWN );
        output += rightPad( bd.toString(), PAD_WIDTH, true );

        bd = new BigDecimal( (Double)Map.get("days") );
        bd = bd.setScale( DAYS_DECIMALS, BigDecimal.ROUND_DOWN );
        output += rightPad( bd.toString(), PAD_WIDTH, false );

        return output;
        //        output += (Math.floor(this.delta * 1000) / 1000.0) + "    ";
    }


    // Get
    public Object get( String key ) {

        return Map.get( key );

    }

    // Put
    public Object put( String key, Object value ) {

        return Map.put( key, value );

    }

    public void UpdateGreeks( double d, double g, double v, double t, double r ) {

        Map.put( "delta", d );
        Map.put( "gamma", g );
        Map.put( "vega", v );
        Map.put( "theta", t );
        Map.put( "rho", r );

    }

    public void UpdatePrice( double p ) {

        Map.put( "price", p );

    }

    public void UpdateDays( double da ) {

        Map.put( "days", da );

    }
}
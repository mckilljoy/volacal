package volacal;

import java.math.BigDecimal;

//
// Represents all data associated with an option
//
public class StockOptionRow {

    public String type;
    public double strike;
    public double price;
    public double delta;
    public double gamma;
    public double vega;
    public double theta;
    public double rho;

    // Constructor
    public StockOptionRow( String tp, double s, double p, double d, double g, double v, double t, double r ) {

        type = tp;
        strike = s;
        price = p;
        delta = d;
        gamma = g;
        vega = v;
        theta = t;
        rho = r;

    }

    // Output
    public String StringHeading() {

        String output = "";
        output += "Type   ";
        output += "Strike ";
        output += "Delta  ";
        output += "Gamma  ";
        output += "Vega   ";
        output += "Theta  ";
        output += "Rho    ";

        return output;

    }
    
    public String StringData() {

        BigDecimal bd;

        String output = "";
        output += (this.type == "Put" ? "Put    " : "Call   ");

        bd = new BigDecimal( this.strike );
        bd = bd.setScale(6, BigDecimal.ROUND_DOWN);
        output+= bd.toString();

        bd = new BigDecimal( this.delta );
        bd = bd.setScale(6, BigDecimal.ROUND_DOWN);
        output+= bd.toString();

        bd = new BigDecimal( this.gamma );
        bd = bd.setScale(6, BigDecimal.ROUND_DOWN);
        output+= bd.toString();

        bd = new BigDecimal( this.vega );
        bd = bd.setScale(6, BigDecimal.ROUND_DOWN);
        output+= bd.toString();

        bd = new BigDecimal( this.theta );
        bd = bd.setScale(6, BigDecimal.ROUND_DOWN);
        output+= bd.toString();

        bd = new BigDecimal( this.rho );
        bd = bd.setScale(6, BigDecimal.ROUND_DOWN);
        output+= bd.toString();

        return output;
        //        output += (Math.floor(this.delta * 1000) / 1000.0) + "    ";
    }

    // Update
    public void UpdateGreeks( double d, double g, double v, double t, double r ) {

        delta = d;
        gamma = g;
        vega = v;
        theta = t;
        rho = r;

    }

}
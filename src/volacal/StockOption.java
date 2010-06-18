package volacal;

import gaussian.*;

public class StockOption extends Derivative {

    private static final double INVALID = -1.0;
    private static final double DEFAULT_RISK_FREE_RATE = 0.01; // 1% annually

    private static final double INVALID_STRIKE = -1.0;
    private static final double INVALID_RISK_FREE_RATE = -1.0;
    private static final double INVALID_TIME_TO_EXPIRATION = -1.0;

    // What this particular derivative strikes at
    public double Strike;

    // Years till expiration
    // Represented in years
    public double DaysToExpiration;
    public double TimeToExpiration;

    // Annualized risk-free interest rate
    // Percentage represented as a decimal percent
    public double RiskFreeRate;

    // The Greeks
    public StockOptionRowMap CallRowMap;
    public StockOptionRowMap PutRowMap;

    // Constructor
    public StockOption( Stock underlying ) {
        this( underlying, INVALID_STRIKE, INVALID_TIME_TO_EXPIRATION, DEFAULT_RISK_FREE_RATE);
    }

    public StockOption( Stock underlying, double strike ) {
        this( underlying, strike, INVALID_TIME_TO_EXPIRATION, DEFAULT_RISK_FREE_RATE);
    }

    public StockOption( Stock underlying, double strike, double daysToExpiration, double riskFreeRate ) {
        super( underlying );
        this.Strike = strike;
        this.RiskFreeRate = riskFreeRate;
        this.TimeToExpiration = daysToExpiration / 365.0;
        this.DaysToExpiration = daysToExpiration;

        CallRowMap = new StockOptionRowMap( this.Underlying.Symbol, "Call", Strike );
        PutRowMap = new StockOptionRowMap( this.Underlying.Symbol, "Put", Strike );

        CallRowMap.UpdateDays( daysToExpiration );
        PutRowMap.UpdateDays( daysToExpiration );
    }

    // Output
    public void Print() {
        super.Print();
        System.out.println("Strike: " + this.Strike +
                           " RiskFreeRate: " + this.RiskFreeRate +
                           " TimeToExpiration: " + this.TimeToExpiration );

    }

    public StockOptionRowMap GetCallRowMap() {

        return CallRowMap;

    }

    public StockOptionRowMap GetPutRowMap() {

        return PutRowMap;
    }

    // Event Handlers
    public void UpdateUnderylingSpotPrice( double newUnderlyingSpotPrice ) {
        super.UpdateUnderlyingSpotPrice( newUnderlyingSpotPrice );
    }

    public void UpdateTimeToExpiration( double timeToExpiration ) {
        this.TimeToExpiration = timeToExpiration;
    }

    // Static functions

    // Functions
    public boolean Calculate() {
        /*
        if( CalculatePrice() && CalculateGreeks() ) {
            return true;
        }

        return false;
        */

        CalculatePrice();
        CalculateGreeks();

        return true;

    }

    private double CalculateD1() {

        // Calculate d1
        // (ln( S / K ) + (r + sig^2 / 2)*t)  /  (sig * sqrt(t))
        double a = Math.log(this.Underlying.SpotPrice/this.Strike) +
            (this.RiskFreeRate + (this.Underlying.HistoricVolatility * this.Underlying.HistoricVolatility / 2.0)) *
            this.TimeToExpiration;

        double b = this.Underlying.HistoricVolatility * Math.sqrt(this.TimeToExpiration);

        return a / b;        
    }

    private double CalculateD2( double d1 ) {

        // Calculate d2
        // d1 - sig*sqrt(t)
        double a = this.Underlying.HistoricVolatility * Math.sqrt(this.TimeToExpiration);

        return d1 - a;
    }

    private double CalculateD2() {

        // Calculate d2
        // d1 - sig*sqrt(t)
        double d1 = CalculateD1();
        double a = this.Underlying.HistoricVolatility * Math.sqrt(this.TimeToExpiration);

        return d1 - a;
    }

    // Calculate options prices
    private boolean ValidateParameters() {
        if( this.Strike == INVALID_STRIKE ||
            this.RiskFreeRate == INVALID_RISK_FREE_RATE ||
            this.TimeToExpiration == INVALID_TIME_TO_EXPIRATION ) {
            return false;
        }

        return true;
    }

    public boolean CalculatePrice() {

        if( !ValidateParameters() ) {
            return false;
        }

        double d1;
        double d2;

        double call_price;
        double put_price;

        double right;
        double left;

        // Calculate d1, d2
        d1 = CalculateD1();
        d2 = CalculateD2(d1);

        // Calculate price
        // call_price = [ S * PHI(d1) ] - [ K * exp(-rt) * PHI(d2) ]
        // put_price = [ K * exp(-rt) * PHI(-d2) ] - [ S * PHI(-d1) ]
        left = this.Underlying.SpotPrice * Gaussian.Phi(d1);
        right = this.Strike * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Gaussian.Phi(d2);

        call_price = left - right;

        left = this.Strike * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Gaussian.Phi(-d2);
        right = this.Underlying.SpotPrice * Gaussian.Phi(-d1);

        put_price = left - right;

        CallRowMap.UpdatePrice( call_price );
        PutRowMap.UpdatePrice( put_price );

        return true;
    }

    public double CalculateCallPrice() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate d1
        double d1 = CalculateD1();
   
        // Calculate d2
        double d2 = CalculateD2(d1);

        // Calculate price
        // C = S * PHI(d1) - K * exp(-rt)*PHI(d2)
        double premium = this.Underlying.SpotPrice * Gaussian.Phi(d1) - 
            this.Strike * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Gaussian.Phi(d2);

        return premium;

    }

    public double CalculatePutPrice() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate d1
        double d1 = CalculateD1();
   
        // Calculate d2
        double d2 = CalculateD2(d1);

        // Calculate price
        // P = K * exp(-rt)*PHI(-d2) - S * PHI(-d1)
        double premium = this.Strike * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Gaussian.Phi(-d2) -
            this.Underlying.SpotPrice * Gaussian.Phi(-d1);

        return premium;

    }

    // Calculate Greeks
    public boolean CalculateGreeks() {

        //
        // The calculations used for the greeks here are somewhat simplified  
        // in that they don't take dividends into consideration.
        //

        double d1;
        double d2;

        double call_delta;
        double call_gamma;
        double call_vega;
        double call_theta;
        double call_rho;

        double put_delta;
        double put_gamma;
        double put_vega;
        double put_theta;
        double put_rho;

        double top;
        double bot;
        double right;

        // Calc D1 and D1
        d1 = CalculateD1();
        d2 = CalculateD2(d1);

        // Delta
        // call_delta = PHI(d1)
        // put_delta = PHI(d1) - 1
        call_delta = Gaussian.Phi(d1);
        put_delta = Gaussian.Phi(d1) - 1;

        // Gamma
        // call_gamma = put_gamma = [ exp(-d1*d1/2) ] / [ sqrt(2*PI) * (S*sig*sqrt(t)) ]
        top = Math.exp(-d1*d1/2.0);
        bot = Math.sqrt(2.0*Math.PI) * this.Underlying.SpotPrice * this.Underlying.HistoricVolatility * Math.sqrt(this.TimeToExpiration);

        call_gamma = put_gamma = top / bot;

        // Vega
        // call_vega = put_vega = [S * sqrt(t) * exp(-d1*d1/2) ] / [ sqrt(2*PI) * 100 ]
        top = this.Underlying.SpotPrice * Math.sqrt(this.TimeToExpiration) * Math.exp(-d1*d1/2.0); 
        bot = Math.sqrt( 2.0 * Math.PI) * 100.0;

        call_vega = put_vega = top / bot;

        // Theta
        // call_theta = [ [ -S * exp(-d1*d1/2) * sig ] / [ 2 * sqrt(t) * sqrt(2*PI) ] - [ rK*PHI(d2) ] ] / 365
        // call_put = [ [ -S * exp(-d1*d1/2) * sig ] / [ 2 * sqrt(t) * sqrt(2*PI) ] + [ rK*PHI(d2) ] ] / 365
        top = -this.Underlying.SpotPrice * Math.exp(-d1*d1/2.0) * this.Underlying.HistoricVolatility;
        bot = 2.0 * Math.sqrt(this.TimeToExpiration) * Math.sqrt(2.0*Math.PI);
        right = this.RiskFreeRate * this.Strike * Gaussian.Phi(d2);

        call_theta = (top / bot - right) / 365.0;
        put_theta = (top / bot + right) / 365.0;

        // Rho
        // call_rho = K * t * exp(-rt) * PHI(d2) / 100
        // put_rho = -K * t * exp(-rt) * PHI(-d2) / 100
        call_rho = this.Strike * this.TimeToExpiration * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Gaussian.Phi(d2) / 100.0;
        put_rho = -this.Strike * this.TimeToExpiration * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Gaussian.Phi(-d2) / 100.0;

        //
        // Store the results in a row stucture
        //
        CallRowMap.UpdateGreeks( call_delta, call_gamma, call_vega, call_theta, call_rho );
        PutRowMap.UpdateGreeks( put_delta, put_gamma, put_vega, put_theta, put_rho );
      
        return true;

    }

    // Delta
    public double CalculateCallDelta() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate delta
        // delta = PHI(d1)
        double d1 = CalculateD1();

        double delta = Gaussian.Phi(d1);

        return delta;

    }

    public double CalculatePutDelta() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate delta
        // delta = PHI(d1) - 1
        double d1 = CalculateD1(); 

        double delta = Gaussian.Phi(d1) - 1;

        return delta;

    }

    // Gamma
    public double CalculateGamma() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate gamma -- note it is the same for put/call
        // gamma = exp(-d1*d1/2) / sqrt(2*PI) / (S * sig *sqrt(t))
        double d1 = CalculateD1();

        double a = Math.exp(-d1*d1/2.0) / Math.sqrt( 2.0 * Math.PI);
        double b = this.Underlying.SpotPrice * this.Underlying.HistoricVolatility * Math.sqrt(this.TimeToExpiration);

        double gamma = a / b;

        return gamma;

    }

    public double CalculateCallGamma() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate gamma -- note it is the same for put/call
        double gamma = CalculateGamma();

        return gamma;

    }

    public double CalculatePutGamma() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate gamma -- note it is the same for put/call
        double gamma = CalculateGamma();

        return gamma;

    }

    // Vega
    public double CalculateVega() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate vega -- note it is the same for put/call
        // S * sqrt(t) * exp(-d1*d1/2) / sqrt(2*PI) / 100
        double d1 = CalculateD1();

        //        double vega = this.Underlying.SpotPrice * Gaussian.Phi(d1) * Math.sqrt(this.TimeToExpiration);
        //        double vega = this.Underlying.SpotPrice * Gaussian.Phi(d1) * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Math.sqrt(this.TimeToExpiration);
        double a = this.Underlying.SpotPrice * Math.sqrt(this.TimeToExpiration); 
        double b = Math.exp(-d1*d1/2.0) / Math.sqrt( 2.0 * Math.PI) / 100;

        double vega = a * b;

        return vega;

    }

    public double CalculateCallVega() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate vega
        double vega = CalculateVega();

        return vega;
    
    }

    public double CalculatePutVega() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate vega
        double vega = CalculateVega();

        return vega;
    
    }

    // Theta
    public double CalculateCallTheta() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate theta -- it is very similar for put/call
        // -S * (exp(-d1*d1/2)/sqrt(2*PI) * sig / (2 * sqrt(t)) - rK*PHI(d2) / 365
        double d1 = CalculateD1();
        double d2 = CalculateD2(d1);

        //        double a = -this.Underlying.SpotPrice * Gaussian.Phi(d1) * this.Underlying.HistoricVolatility / (2.0 * Math.sqrt( this.TimeToExpiration) );
        //        double b = this.Strike * this.RiskFreeRate * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Gaussian.Phi(d2);
        double a = -this.Underlying.SpotPrice * Math.exp(-d1*d1/2.0) / Math.sqrt(2.0*Math.PI);
        double b = this.Underlying.HistoricVolatility / (2.0 * Math.sqrt(this.TimeToExpiration));
        double c = this.RiskFreeRate * this.Strike * Gaussian.Phi(d2);

        double theta = (a * b - c) / 365.0;

        return theta;

    }

    public double CalculatePutTheta() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate theta -- it is very similar for put/call
        // -S * (exp(-d1*d1/2)/sqrt(2*PI) * sig / (2 * sqrt(t)) + rK*PHI(d2) / 365
        double d1 = CalculateD1();
        double d2 = CalculateD2(d1);

        double a = -this.Underlying.SpotPrice * Math.exp(-d1*d1/2.0) / Math.sqrt(2.0*Math.PI);
        double b = this.Underlying.HistoricVolatility / (2.0 * Math.sqrt(this.TimeToExpiration));
        double c = this.RiskFreeRate * this.Strike * Gaussian.Phi(d2);

        double theta = (a * b + c) / 365.0;

        return theta;

    }

    // Rho
    public double CalculateCallRho() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate rho
        // K * t*exp(-rt)*PHI(d2) / 100
        double d1 = CalculateD1();
        double d2 = CalculateD2(d1);

        double rho = this.Strike * this.TimeToExpiration * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Gaussian.Phi(d2) / 100;

        return rho;

    }

    public double CalculatePutRho() {

        if( !ValidateParameters() ) {
            return -1.0;
        }

        // Calculate rho
        // -K * t*exp(-rt)*PHI(-d2) / 100
        double d1 = CalculateD1();
        double d2 = CalculateD2(d1);

        double rho = -this.Strike * this.TimeToExpiration * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Gaussian.Phi(-d2) / 100;

        return rho;

    }
}

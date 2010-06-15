package volacal;

import gaussian.*;

public class StockOption extends Derivative {

    private static final double INVALID_STRIKE = -1.0;
    private static final double INVALID_RISK_FREE_RATE = -1.0;
    private static final double INVALID_TIME_TO_EXPIRATION = -1.0;

    // What this particular derivative strikes at
    public double Strike;

    // Annualized risk-free interest rate
    // Represented as a decimal percent (0.0-1.0)
    public double RiskFreeRate;

    // Years till expiration
    // Represented in years
    public double TimeToExpiration;

    // Constructor
    public StockOption( Stock underlying ) {
        super( underlying );
        this.Strike = INVALID_STRIKE;
        this.RiskFreeRate = INVALID_RISK_FREE_RATE;
        this.TimeToExpiration = INVALID_TIME_TO_EXPIRATION;
    }

    public StockOption( Stock underlying, double strike, double riskFreeRate, double timeToExpiration ) {
        super( underlying );
        this.Strike = strike;
        this.RiskFreeRate = riskFreeRate;
        this.TimeToExpiration = timeToExpiration;
    }

    // Output
    public void Print() {
        super.Print();
        System.out.println("Strike: " + this.Strike +
                           " RiskFreeRate: " + this.RiskFreeRate +
                           " TimeToExpiration: " + this.TimeToExpiration );

    }

    // Event Handlers
    public void UpdateUnderylingSpotPrice( double newUnderlyingSpotPrice ) {
        super.UpdateUnderlyingSpotPrice( newUnderlyingSpotPrice );
    }

    // Static functions

    // Functions
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
    public double CalculateCallPrice() {

        if( this.Strike == INVALID_STRIKE ||
            this.RiskFreeRate == INVALID_RISK_FREE_RATE ||
            this.TimeToExpiration == INVALID_TIME_TO_EXPIRATION ) {
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

        if( this.Strike == INVALID_STRIKE ||
            this.RiskFreeRate == INVALID_RISK_FREE_RATE ||
            this.TimeToExpiration == INVALID_TIME_TO_EXPIRATION ) {
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
    // Delta
    public double CalculateCallDelta() {

        // Calculate delta
        // delta = PHI(d1)
        double d1 = CalculateD1();

        double delta = Gaussian.Phi(d1);

        return delta;

    }

    public double CalculatePutDelta() {

        // Calculate delta
        // delta = PHI(d1) - 1
        double d1 = CalculateD1(); 

        double delta = Gaussian.Phi(d1) - 1;

        return delta;

    }

    // Gamma
    public double CalculateCallGamma() {

        // Calculate gamma -- note it is the same for put/call
        // gama = PHI(d1) / (s*sig*sqrt(t))
        double d1 = CalculateD1();
      
        double a = Gaussian.Phi(d1);
        double b = this.Underlying.SpotPrice * this.Underlying.HistoricVolatility * Math.sqrt(this.TimeToExpiration);

        double gamma = a / b;

        return gamma;

    }

    public double CalculatePutGamma() {

        // Calculate gamma -- note it is the same for put/call
        // gama = PHI(d1) / (s*sig*sqrt(t))
        double d1 = CalculateD1();
      
        double a = Gaussian.Phi(d1);
        double b = this.Underlying.SpotPrice * this.Underlying.HistoricVolatility * Math.sqrt(this.TimeToExpiration);

        double gamma = a / b;

        return gamma;

    }

    // Vega
    public double CalculateVega() {

        // Calculate vega -- note it is the same for put/call
        // S * PHI(d1)*sqrt(t)
        double d1 = CalculateD1();

        double vega = this.Underlying.SpotPrice * Gaussian.Phi(d1) * Math.sqrt(this.TimeToExpiration);

        return vega;

    }

    public double CalculateCallVega() {

        // Calculate vega
        double vega = CalculateVega();

        return vega;
    
    }

    public double CalculatePutVega() {

        // Calculate vega
        double vega = CalculateVega();

        return vega;
    
    }

    // Theta
    public double CalculateCallTheta() {

        // Calculate theta
        // -S * PHI(d1)*sig / (2*sqrt(t)) - K * r*exp(-rt)*PHI(d2)
        double d1 = CalculateD1();
        double d2 = CalculateD2(d1);

        double a = -this.Underlying.SpotPrice * Gaussian.Phi(d1) * this.Underlying.HistoricVolatility / (2.0 * Math.sqrt( this.TimeToExpiration) );
        double b = this.Strike * this.RiskFreeRate * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Gaussian.Phi(d2);

        double theta = a - b;

        return theta;

    }

    public double CalculatePutTheta() {

        // Calculate theta
        // -S * PHI(d1)*sig / (2*sqrt(t)) + K * r*exp(-rt)*PHI(-d2)
        double d1 = CalculateD1();
        double d2 = CalculateD2(d1);

        double a = -this.Underlying.SpotPrice * Gaussian.Phi(d1) * this.Underlying.HistoricVolatility / (2.0 * Math.sqrt( this.TimeToExpiration) );
        double b = this.Strike * this.RiskFreeRate * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Gaussian.Phi(-d2);

        double theta = a + b;

        return theta;

    }

    // Rho
    public double CalculateCallRho() {

        // Calculate rho
        // K * t*exp(-rt)*PHI(d2)
        double d1 = CalculateD1();
        double d2 = CalculateD2(d1);

        double rho = this.Strike * this.TimeToExpiration * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Gaussian.Phi(d2);

        return rho;

    }

    public double CalculatePutRho() {

        // Calculate rho
        // -K * t*exp(-rt)*PHI(-d2)
        double d1 = CalculateD1();
        double d2 = CalculateD2(d1);

        double rho = -this.Strike * this.TimeToExpiration * Math.exp(-this.RiskFreeRate * this.TimeToExpiration) * Gaussian.Phi(-d2);

        return rho;

    }
}

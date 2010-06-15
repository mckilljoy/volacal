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
    public double CalculateCallPrice() {
        if( this.Strike == INVALID_STRIKE ||
            this.RiskFreeRate == INVALID_RISK_FREE_RATE ||
            this.TimeToExpiration == INVALID_TIME_TO_EXPIRATION ) {
            return -1.0;
        }

        // Calculate d1
        System.out.println("Calc d1");

        System.out.println(this.Underlying.SpotPrice + " " + this.Strike);
        double aa = Math.log(this.Underlying.SpotPrice/this.Strike);
        double ab = this.RiskFreeRate + (this.Underlying.HistoricVolatility * this.Underlying.HistoricVolatility / 2.0);
        double ac = this.TimeToExpiration;
        double a = aa + ab * ac;
        double b = this.Underlying.HistoricVolatility * Math.sqrt(this.TimeToExpiration);
        double d1 = a / b;
        System.out.println(aa + " " + ab + " " + ac);
   
        // Calculate d2
        System.out.println("Done d1, Cald d2: " + a + " " + b + " " + d1);
        double c = this.Underlying.HistoricVolatility * Math.sqrt(this.TimeToExpiration);
        double d2 = d1 - c;

        // Calculate premium
        System.out.println("Done d2, Calc premium: " + c + " " + d2);
        double premium = this.Underlying.SpotPrice * Gaussian.Phi(d1) - 
            this.Strike * Math.exp( -this.RiskFreeRate * this.TimeToExpiration) *
            Gaussian.Phi(d2);


        System.out.println("Done premium");
        return premium;

    }

    public double CalculatePutPrice() {
        return -1.0;
    }
}


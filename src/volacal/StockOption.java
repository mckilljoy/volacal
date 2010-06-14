package volacal;

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

}


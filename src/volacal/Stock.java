package volacal;

public class Stock extends Security {

    // Defines
    //    private static final Derivative INVALID_OVERLYING = null;

    // Member fields
    //    public Derivative Overlying;

    // Constructor
    public Stock() {
        super();
        //        this.Overlying = INVALID_OVERLYING;
    }

    public Stock( String symbol ) {
        super( symbol );
    }

    public Stock( String symbol, double spotPrice ) {
        super( symbol, spotPrice );
    }

    public Stock( String symbol, double spotPrice, double historicVolatility ) {
        super( symbol, spotPrice, historicVolatility );
    }

    // Output
    public void Print() {
        super.Print();
    }

    // Event handlers
    public void UpdateSpotPrice( double newSpotPrice ) {
        super.UpdateSpotPrice( newSpotPrice );
    }

}

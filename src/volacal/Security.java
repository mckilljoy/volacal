package volacal;

public class Security {

    // Defines
    private static final String INVALID_SYMBOL = "none";
    private static final double INVALID_SPOT_PRICE = -1.0;
    private static final double INVALID_HISTORIC_VOLATILITY = -1.0;

    // Member fields
    public String Symbol;
    public double SpotPrice;
    public double HistoricVolatility;

    // Constructor
    public Security( String symbol, double spotPrice, double historicVolatility ) {
        this.Symbol = symbol;
        this.SpotPrice = spotPrice;
        this.HistoricVolatility = historicVolatility;
    }
    
    public Security( String symbol, double spotPrice ) {
        this.Symbol = symbol;
        this.SpotPrice = spotPrice;
        this.HistoricVolatility = INVALID_HISTORIC_VOLATILITY;
    }

    public Security( String symbol ) {
        this.Symbol = symbol;
        this.SpotPrice = INVALID_SPOT_PRICE;
        this.HistoricVolatility = INVALID_HISTORIC_VOLATILITY;
    }

    public Security() {
        this.Symbol = INVALID_SYMBOL;
        this.SpotPrice = INVALID_SPOT_PRICE;
        this.HistoricVolatility = INVALID_HISTORIC_VOLATILITY;
    }

    // Output
    public void Print() {
        System.out.println("Symbol: " + this.Symbol + 
                           " SpotPrice: " + this.SpotPrice + 
                           " HistoricVolatility: " + this.HistoricVolatility );
    }

    // Event handlers
    public void UpdateSpotPrice( double newSpotPrice ) {
        this.SpotPrice = newSpotPrice;
    }

    public void UpdateHistoricVolatility( double newHistoricVolatility ) {
        this.HistoricVolatility = newHistoricVolatility;
    }
}
package volacal;

import java.util.ArrayList;

public class StockOptionChain {

    ArrayList Chain;
    Stock Underlying;

    // Constructors
    public StockOptionChain( Stock underlying, StrikePriceRange strikePriceRange, double timeToExpiration, double riskFreeRate ) {

        this.Underlying = underlying;

        this.Chain = new ArrayList();

        for( int i = 0; i < strikePriceRange.length; i++ ) {

            StockOption stockOption = new StockOption( underlying, strikePriceRange.get(i), riskFreeRate, timeToExpiration );

            Chain.add( stockOption );

        }
    }

    public StockOptionChain( Stock underlying, StrikePriceRange strikePriceRange, double timeToExpiration ) {

        // default RiskFreeRate of 0%
        this( underlying, strikePriceRange, timeToExpiration, 0.0 );

    }

    public StockOptionChain( Stock underlying, StrikePriceRange strikePriceRange ) {

        // default RiskFreeRate of 0%, TimeToExpiration 1.0 year
        this( underlying, strikePriceRange, 1.0, 0.0 );

    }

    // Update values
    public void UpdateTimeToExpiration( double timeToExpiration ) {

        for( int i = 0; i < Chain.size(); i++ ) {

            StockOption stockOption = (StockOption)Chain.get(i);
            stockOption.UpdateTimeToExpiration( timeToExpiration );

        }
    }

    public void UpdateUnderlyingSpotPrice( double spotPrice ) {

        this.Underlying.SpotPrice = spotPrice;

    }



}
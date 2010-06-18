package volacal;

//
// This is basically a fancy array.
// It might be worth adding some other functionality later.
//

public class StrikePriceRange {

    public int length;
    public double[] StrikePrices;

    public StrikePriceRange( double[] strikePrices ) {
        StrikePrices = strikePrices;
        length = strikePrices.length;
    }

    public double get( int index ) {
        return StrikePrices[index];
    }

}
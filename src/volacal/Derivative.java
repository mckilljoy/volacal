package volacal;

public class Derivative {

    // Defines
    private static final Security INVALID_UNDERLYING = null;

    // Member fields
    public Security Underlying;

    // Constructors
    public Derivative( Security underlying ) {
        this.Underlying = underlying;
    }

    // Output
    public void Print() {
        this.Underlying.Print();
    }

    // Event handlers
    public void UpdateUnderlyingSpotPrice( double newUnderlyingSpotPrice ) {
        Underlying.UpdateSpotPrice( newUnderlyingSpotPrice );
    }
}
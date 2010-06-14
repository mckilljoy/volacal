import volacal.*;

class Main {
    public static void main(String[] args) {


        Volacal vol = new Volacal();

        Stock stock1 = new Stock();
        Stock stock2 = new Stock( "MSFT" );
        Stock stock3 = new Stock( "MSFT", 30.0 );
        Stock stock4 = new Stock( "MSFT", 30.0, 15.0);

        StockOption option1 = new StockOption( stock1 );
        StockOption option2 = new StockOption( stock2 );
        StockOption option3 = new StockOption( stock3 );
        StockOption option4 = new StockOption( stock4 );

        option1.Print();
        option2.Print();
        option3.Print();
        option4.Print();
    }
}

import volacal.*;

class Main {
    public static void main(String[] args) {


        Volacal vol = new Volacal();

        Stock stock1 = new Stock( "MSFT", 23.75, 0.35);
        Stock stock2 = new Stock( "GE", 30.14, 0.332 );
        Stock stock3 = new Stock( "MSFT", 26.58, 0.264 );
        Stock stock4 = new Stock( "MSFT", 26.58, 0.264);

        StockOption option1 = new StockOption( stock1, 15.0, 0.01, 0.5 );
        StockOption option2 = new StockOption( stock2, 15.0, 0.01, 0.25 );
        StockOption option3 = new StockOption( stock3, 25.0, 0.01, 31.0/365.0 );
        StockOption option4 = new StockOption( stock4, 26.0, 0.01, 31.0/365.0 );

        option1.Print();
        option2.Print();
        option3.Print();
        option4.Print();

        System.out.println("\n");

        System.out.println("Option price: " + option1.CalculateCallPrice() );
        System.out.println("Option price: " + option2.CalculateCallPrice() );
        System.out.println("Option price: " + option3.CalculateCallPrice() );
        System.out.println("Option price: " + option4.CalculateCallPrice() );
    }
}

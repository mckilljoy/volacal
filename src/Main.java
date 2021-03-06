import volacal.*;

class Main {
    public static void main(String[] args) {

        Stock stock1 = new Stock( "MSFT", 26.32, 0.25 );
        Stock stock2 = new Stock( "AAPL", 267.25, 0.304 );
        Stock stock3 = new Stock( "AAPL", 267.25, 0.344 );
        Stock stock4 = new Stock( "MSFT", 26.32, 0.267 );

        StockOption option1 = new StockOption( stock1, 26.0, 2.0, 0.000 );
        StockOption option2 = new StockOption( stock2, 260.0, 2.0, 0.0003 );
        StockOption option3 = new StockOption( stock3, 260.0, 30.0, 0.0003 );
        StockOption option4 = new StockOption( stock4, 26.0, 30.0, 0.0006 );

        StockOption temp;

        temp = option1;

        temp.Calculate();

        System.out.println( StockOptionRowMap.StringHeader() );
        System.out.println( temp.GetCallRowMap().StringData() );
        System.out.println( temp.GetPutRowMap().StringData() );


        /*
        temp.Print();
        System.out.println("Option price: " + temp.CalculateCallPrice() + " " + temp.CalculatePutPrice() );
        System.out.println("Greeks: D: " + temp.CalculateCallDelta() + " G: " + temp.CalculateCallGamma() + " V: " + temp.CalculateCallVega() + " T: " + temp.CalculateCallTheta() + " R: " + temp.CalculateCallRho() );
        System.out.println("Greeks: D: " + temp.CalculatePutDelta() + " G: " + temp.CalculatePutGamma() + " V: " + temp.CalculatePutVega() + " T: " + temp.CalculatePutTheta() + " R: " + temp.CalculatePutRho() );
        System.out.println("\n");

        temp = option2;
        temp.Print();
        System.out.println("Option price: " + temp.CalculateCallPrice() + " " + temp.CalculatePutPrice() );
        System.out.println("Greeks: D: " + temp.CalculateCallDelta() + " G: " + temp.CalculateCallGamma() + " V: " + temp.CalculateCallVega() + " T: " + temp.CalculateCallTheta() + " R: " + temp.CalculateCallRho() );
        System.out.println("Greeks: D: " + temp.CalculatePutDelta() + " G: " + temp.CalculatePutGamma() + " V: " + temp.CalculatePutVega() + " T: " + temp.CalculatePutTheta() + " R: " + temp.CalculatePutRho() );
        System.out.println("\n");

        temp = option3;
        temp.Print();
        System.out.println("Option price: " + temp.CalculateCallPrice() + " " + temp.CalculatePutPrice() );
        System.out.println("Greeks: D: " + temp.CalculateCallDelta() + " G: " + temp.CalculateCallGamma() + " V: " + temp.CalculateCallVega() + " T: " + temp.CalculateCallTheta() + " R: " + temp.CalculateCallRho() );
        System.out.println("Greeks: D: " + temp.CalculatePutDelta() + " G: " + temp.CalculatePutGamma() + " V: " + temp.CalculatePutVega() + " T: " + temp.CalculatePutTheta() + " R: " + temp.CalculatePutRho() );
        System.out.println("\n");

        temp = option4;
        temp.Print();
        System.out.println("Option price: " + temp.CalculateCallPrice() + " " + temp.CalculatePutPrice() );
        System.out.println("Greeks: D: " + temp.CalculateCallDelta() + " G: " + temp.CalculateCallGamma() + " V: " + temp.CalculateCallVega() + " T: " + temp.CalculateCallTheta() + " R: " + temp.CalculateCallRho() );
        System.out.println("Greeks: D: " + temp.CalculatePutDelta() + " G: " + temp.CalculatePutGamma() + " V: " + temp.CalculatePutVega() + " T: " + temp.CalculatePutTheta() + " R: " + temp.CalculatePutRho() );
        System.out.println("\n");
        */
        /*
        option2.Print();
        System.out.println("Option price: " + option2.CalculateCallPrice() + " " + option2.CalculatePutPrice() );
        System.out.println("\n");

        option3.Print();
        System.out.println("Option price: " + option3.CalculateCallPrice() + " " + option3.CalculatePutPrice() );
        System.out.println("\n");

        option4.Print();
        System.out.println("Option price: " + option4.CalculateCallPrice() + " " + option4.CalculatePutPrice() );
        System.out.println("\n");
        */
    }
}

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
        StockOption option3 = new StockOption( stock3, 25.0, 0.02, 31.0/365.0 );
        StockOption option4 = new StockOption( stock4, 26.0, 0.02, 31.0/365.0 );

        StockOption temp;

        temp = option1;
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

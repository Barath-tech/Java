package me.barath.compound_calculator;

public class Utils {

    public static double compound_interest(double principle,double interest,double years, double compoundingperiod){
        return Math.round(principle*(Math.pow((1+(interest/compoundingperiod)),(compoundingperiod*years))));
    }

}

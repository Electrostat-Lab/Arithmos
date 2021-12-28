package math.statistics;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Statistics {
    
    private static final Logger logger=Logger.getLogger(Statistics.class.getName());
    
    private Statistics(){

    }

    /**
     * get the mean of a single data set (average) number
     * @param args the data set arguments
     * @return the average(mean) of this data set
     */
    public static double mean(double...args){
        if(args ==null){
            return 0.0;
        }
        double sum=0.0d;
        /*get the sum of the args numbers*/
        for (double arg : args) {
            sum+=arg;
        }
        /* mean(average) = sum / length;*/
        return sum/args.length;
    }

    /**
     * gets the centered value inside this data set
     * @param args the data set arguments to parse
     * @return the centered value inside this data set
     */
   public static double median(double...args){
        if(args==null){
            return 0.0f;
        }
        double preMidPos;
        double postMidPos;
        if(args.length % 2 == 0){
            postMidPos=args.length/2d;
        }else {
            //get the decimals up to +Infinity
            postMidPos=Math.ceil(args.length/2d);
        }
       preMidPos=postMidPos-1;
       return (args[(int) postMidPos] + args[(int)preMidPos])/2d;
   }

    /**
     * gets the mode of a data set (Quantitative data type set) , the mode are all the numbers that happened to repeat over the data set
     * @param args the data set to parse
     * @return an arrayList of the modes
     */
    public static ArrayList<Double> mode(double...args){
        ArrayList<Double> modeList=new ArrayList<>();
        for (double arg : args) {
            /*format number of modes for each new data val in the data set*/
            int numberOfModes = 0;
            /*loop over the data set for each value inside the data set*/
            for (double v : args) {
                /*subtract the values looped over from the current value inside the data set*/
                if (arg - v == 0) {
                    /*add up modes if the result of subtraction is zero(the 2 numbers are coincident(equal))*/
                    ++numberOfModes;
                    if (numberOfModes > 1) {
                        /* add the value arg to the modeList if it haven't been added before */
                        if (!modeList.contains(arg)) {
                            modeList.add(arg);
                            /* break the 2nd loop & move up to the next value*/
                            break;
                        }
                    }
                }
            }
        }
        return modeList;
    }

    /**
     * calculates the variance , how the values varies or disperses or spread around the mean
     * @param args the data set to get the variance from
     * @return the variance in doubles
     * {@link Statistics#mean(double...)}
     */
    public static double variance(double...args){
        double summation=0.0d;
        for (double arg : args) {
            summation+=Math.pow(arg-Statistics.mean(args),2);
        }

        return summation/(args.length-1d);
    }

    /**
     * another way of calculating the variance in a Population of Data which is actually a mean of the square the difference between the values of the PopulationData & their mean
     * @param args the population data in doubles
     */
    public static double variance2(double...args){
        final double[] dispersionFromMean=new double[args.length];
        for (int pos=0;pos<args.length;pos++) {
            dispersionFromMean[pos]=Math.pow(args[pos]-Statistics.mean(args),2);
        }
        return Statistics.mean(dispersionFromMean);
    }
    /**
     * calculates the standard deviation , describing how the values varies/disperses or spread around the mean positively & negatively
     * @param args the data set to calculate the standard deviation from
     * @return the SD in doubles
     * {@link Statistics#variance}
     */
    public static double standardDeviation(double...args){
        return Math.sqrt(Statistics.variance(args));
    }

    /**
     * calculates the coefficient of variation , describes by how much the values disperse/spread from the mean.
     * @param args the data set to calculate the coefficient of variation from
     * @return the coefficient of variation in doubles
     * {@link Statistics#standardDeviation(double...)} , {@link Statistics#mean(double...)}
     */
    public static double coefficientVariation(double...args){
        return Statistics.standardDeviation(args) / Statistics.mean(args);
    }

    /**
     * Calculates how much 2 dataSets are Synchronized together in the same direction
     * @apiNote values > 0 ; means data move in the same direction , values < 0 ; means data move(disperse) in an inverse direction
     * @param argsX the 1st dataSet
     * @param argsY the 2nd dataSet
     * @return the co-variance in doubles
     * {@link Statistics#mean(double...)}
     */
    public static double coVariance(double[] argsX, double[] argsY){
        if(argsX.length != argsY.length){
            return 0.0;
        }
        double summation=0.0d;
        for(int position=0;position<argsX.length;position++){
            summation+=(argsX[position]-Statistics.mean(argsX)) * (argsY[position]-Statistics.mean(argsY));
        }
        return summation / (argsX.length-1);
    }

    /**
     * calculates the correlationCoefficient between 2 dataSets, that adjusts coVariance to see the relationship between 2 dataSets
     * @apiNote values = 1 are best correlated , negative values indicate inverse correlation , 0 means the 2 dataSets are independent
     * @param argsX 1st dataSet
     * @param argsY 2nd dataSet
     * @return the correlationCoefficient value in doubles
     */
    public static double correlationCoefficient(double[] argsX, double[]argsY){
        if(argsX.length != argsY.length){
            return 0.0d;
        }
        return Statistics.coVariance(argsX,argsY) / (Statistics.standardDeviation(argsX) * Statistics.standardDeviation(argsY));
    }

    public static Quartile getQuartile(double[] args){
        return new Quartile(args,Math.ceil(args.length/4.0d));
    }
}

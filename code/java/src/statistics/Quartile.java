package statistics;

public final class Quartile {
    private final double[] args;
    private final double quartile;
    public Quartile(double[] args,double quartile) {
        this.quartile=quartile;
        this.args=args;
    }

    public double getLastIndexOf(int quartileIndex){
        if(quartile*quartileIndex >= args.length){
            throw new ArrayIndexOutOfBoundsException("This Sample dataSet has a total of "+" only");
        }
        return args[(int) (quartile*quartileIndex)];
    }
}

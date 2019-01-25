package AINT255;

import evaluator.FitnessFunction;

public class FitnessMeasure implements FitnessFunction {

    public Object getFitness(double bestlap, double topspeed, double distraced, double damage) {

        
        double fitness;
        
        // for debug purposes only
       System.out.println(" bestlap " + bestlap + " top speed " + topspeed + " distance raced " + distraced + " damage " + damage);
        
       // fitness of both top speed and distance raced
        fitness = distraced - (2 * damage);
        if (fitness < 0)
        {
            fitness = 0;
        }
        return new Double(fitness);
    }

}

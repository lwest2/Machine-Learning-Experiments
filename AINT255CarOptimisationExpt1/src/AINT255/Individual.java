package AINT255;

import static java.lang.Math.random;
import java.util.Random;

public class Individual {

    // these are the parameters being evolved.
    private double[] phenoType;

    private int numberEvolvedParameters;

    // these are the parameters passed to the TORCS simulator
    // this will be a fixed length of 22
    private double[] simulationParameters;

    private double fitness;

    // random object use for crossover and mutation
    private Random random;

    
    public Individual() {
        numberEvolvedParameters = 0;
    }

    public Individual(int numParams, Random rand) {
        numberEvolvedParameters = numParams;

        random = rand;

        initialise();
    }

    /**
     * Copy constructor: Creates a deep copy of Ind passed as a parameter
     *
     * @param individual the individual to be copied
     */
    public Individual(Individual individual) {

        numberEvolvedParameters = individual.numberEvolvedParameters;

        random = individual.random;

        fitness = individual.fitness;

        phenoType = copyArray(individual.getPhenoType());

        simulationParameters = copyArray(individual.getSimulationParameters());
    }

    public void initialise() {

        // firstly the phenotype
        phenoType = new double[numberEvolvedParameters];

        for (int i = 0; i < numberEvolvedParameters; i++) {

            phenoType[i] = random.nextDouble();
        }

        // now the parameters passed to the TORCS simulator
        simulationParameters = new double[SimulationFrame.numberSimulationParameters];

        // If using default values for some the simulation parameters
        // then set their values here,
        // e.g. simulationParameters[5] = 0.25;
    }

    private void buildSimulationParameters() {
        // The value of each parameter should be in range 0...1
        // The server will scale these values to the appropiate
        // value for that paramter.
        // Please see Table 1, page 7 of the 
        // Car Setup Optimization Competition Software Manual

        if (simulationParameters.length != phenoType.length) {

            // if only evloving a subset of the paramters then
            // add code in here so so the values of the simulationParameters being evolved 
            // are taken from the phenoType
        } else {

            // If all the parameters are being evoled
            // then simply copy across all from the phenotype
            simulationParameters = copyArray(phenoType);
        }
    }

    // =================================================
    // methods to be completed / modified
    public void crossOver(Individual parent2, double probabilityCrossover) {
       
          // AVERAGE CROSSOVER
          // random number between 1 and 0
          double randomNumber = random() * (1 - 0);
          
          // if random number is less than probability of crossover
          if (randomNumber < probabilityCrossover)
          {
              // make every gene in parent2 the average of both parents
              for (int i = 0; i < simulationParameters.length; i++)
              {
                  parent2.simulationParameters[i] = (parent2.simulationParameters[i] + this.simulationParameters[i]) / 2;
              }
              
              Random r = new Random();
              
              int point = r.nextInt(simulationParameters.length);
              
              for (int i = 0; i < point; i++)
              {
                  double temporaryValues = this.simulationParameters[i];
                  this.simulationParameters[i] = parent2.simulationParameters[i];
                  parent2.simulationParameters[i] = temporaryValues;
              }
           
          }
    }

    public void mutateIndividual(double mutationScale) {

        // add code to decide if to  mutate a parameter
        // and if so by how much
        
        // for every simulation parameter
        for (int i = 0; i < simulationParameters.length; i++)
        {
            // get random number to test mutationrate
            double randomNumber = random() * (1 - 0);
            
            if (randomNumber < mutationScale)
            {
                // get nextguassian
                Random r = new Random();
                double randomGuassian = r.nextGaussian();
                // by a small chance it will double this value for more exploration
                randomNumber = random() * (1 - 0);
                if (randomNumber < 0.2)
                {
                    randomGuassian *= 2;
                }
                // add the guassian value
                simulationParameters[i] += randomGuassian;
            }
        }
    }

    // =================================================
    // ============================================
    // please do not amend any methods below
    public double[] getSimulationParameters() {

        // build the parametes for this method call
        buildSimulationParameters();

        // now return a copy
        return copyArray(simulationParameters);
    }



    public double[] getPhenoType() {
        return phenoType;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    private double[] copyArray(double[] anArray) {

        double[] copy = new double[anArray.length];

        System.arraycopy(anArray, 0, copy, 0, copy.length);

        return copy;
    }

}

package AINT255;

import evaluator.ServerCommunication;
import evaluator.TimeOverException;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Random;

public class SimpleGA {

    private final int populationSize;
    private final int numberParams;

    private int numberGenerations;

    private double mutationMagnitude;

    private double probabilityCrossover;

    private ArrayList<Individual> population;
    private ArrayList<Individual> newpopulation;
    
    private Random random;

    private int simulationTime;

    private ServerCommunication server;
    private StatsCollector statsCollector;

    private int index;
    
    public SimpleGA() {

        populationSize = 0;

        numberParams = 0;

        server = null;

    }

    public SimpleGA(int numParams, int popSize, int numGens, double probCrossover, double mutScaling, ServerCommunication ser, int sTime) {

        numberParams = numParams;

        populationSize = popSize;

        server = ser;

        simulationTime = sTime;

        numberGenerations = numGens;

        random = new Random();

        mutationMagnitude = mutScaling;

        probabilityCrossover = probCrossover;

        initialsePopulation();
    }

    public void initialsePopulation() {

        population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            population.add(new Individual(numberParams, random));
        }
    }

    public void evolvePopulation() {

        statsCollector = new StatsCollector();

        evaluatePopulation();

        collectStats();

        boolean timeover = (simulationTime * populationSize > server.getRemainingTime());

        for (int i = 1; i <= numberGenerations && !timeover; i++) {

            selectIndividuals();

            crossoverPopulation();

            mutatePopulation();

            evaluatePopulation();

            collectStats();

            timeover = (simulationTime * populationSize > server.getRemainingTime());

            if (!timeover) {
                System.out.println("End generation " + i + " of " + numberGenerations);
            } else {
                System.out.println("**** Evolution stopped due to no more time left on server");
            }

        }
    }

    // ============================================
    // methods to be completed              
    private void selectIndividuals() {

        // add your code in here
        
        // RWS
        double sumFitness = 0;
        newpopulation = new ArrayList<>();
        
        // get the sum fitness of every individual
        for (Individual individual : population)
        {
            if (individual.getFitness() > -1)
            sumFitness += individual.getFitness();
        }
        
            // for the size of the population                
            for (int i = 0; i < population.size(); i++)
            {
                int total = 0;
                index = -1;
                            
                 // get random value in the midst of the sumfitness
                double spinValue = random() * sumFitness;
        
                System.out.println("spinValue: " + spinValue);
                System.out.println("sumFitness: " + sumFitness);
                
                // find the individual the spinner lands on
                
                while ((total < spinValue) && (index < population.size() - 1))
                {       
                    index++;
                    
                    System.out.println("New Index: " + index);
                                      
                    total += population.get(index).getFitness();   
                    
                         
                }
                
                if (index == -1)
                {
                    index = 0;
                }
    
                if (newpopulation.size() < population.size())
                {
                    newpopulation.add(population.get(index));  
                }
                
                System.out.println("population size: " + newpopulation.size());
            }
            
            // assign new population to current population
            population = newpopulation;
    }

    private void crossoverPopulation() {
         
        
        // add your code in here
        System.out.println("crossoverPopulation from simpleGA");
        
        // go through each individual in population in pairs
        for (int i = 0; i < populationSize - 2; i+=2)
        {
            Individual individual = population.get(i);
            Individual individual2 = population.get(i + 1);
            
            // the individual at i will crossover with the indivdual at i + 1
            individual.crossOver(individual2, probabilityCrossover);
            
        }
        
    }

    private void mutatePopulation() {
        
        // mutation applies to each individual
        for (Individual indvidual : population) {
            // need to supply code for the mutateIndividual() method
            indvidual.mutateIndividual(mutationMagnitude);
        }

    }

    // ============================================
    
    // ============================================
    // please do not amend any methods below
    private void evaluatePopulation() {

        Double fitness = null;

        for (Individual indvidual : population) {

            try {

                fitness = (Double) evaluateOneIndividual(indvidual);

                indvidual.setFitness(fitness);

            } catch (TimeOverException e) {
                //This exception is raised when simulation time is over
                //It is an alternative to the use of server.getRemainingTime()
                System.out.println("***** Simulation finished due to timeout");
                System.exit(-1);
            } catch (Exception e) {
            }
        }
    }

    private double evaluateOneIndividual(Individual ind) throws Exception {

        Double fitness = null;
        try {

            fitness = (Double) server.launchSimulation(ind.getSimulationParameters(), simulationTime);

        } catch (TimeOverException e) {
            //This exception is raised when simulation time is over
            //It is an alternative to the use of server.getRemainingTime()
            System.out.println("***** Simulation finished due to timeout");
            System.exit(-1);
        }
        return fitness;
    }

    private void collectStats() {

        // after evaluating all the indviduals, collect some stats for this generation
        statsCollector.collectStats(population);

        statsCollector.writeStats();

        try {
            server.saveBest(statsCollector.getBestIndividual().getSimulationParameters());
        } catch (Exception e) {
        }
    }

    public void saveEvolutionStats(String fileName) {

        if (statsCollector != null) {

            statsCollector.saveStats(fileName);
        }
    }

    public double[] getIndSimParameters(int indIndex) {
        return population.get(indIndex).getSimulationParameters();
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getNumberGenerations() {
        return numberGenerations;
    }

    public void setNumberGenerations(int numberGenerations) {
        this.numberGenerations = numberGenerations;
    }

    public double getProbabilityMutation() {
        return mutationMagnitude;
    }

    public void setProbabilityMutation(double probabilityMutation) {
        this.mutationMagnitude = probabilityMutation;
    }

    public double getProbabilityCrossover() {
        return probabilityCrossover;
    }

    public void setProbabilityCrossover(double probabilityCrossover) {
        this.probabilityCrossover = probabilityCrossover;
    }
}

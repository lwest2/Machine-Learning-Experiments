package AINT255;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class StatsCollector {

    /**
     * list to hold the average fitness across a generation
     */
    private ArrayList<Double> averageFitness;

    private Individual bestIndividual;

    public StatsCollector() {
        averageFitness = new ArrayList<>();

        bestIndividual = null;
    }

    /**
     *
     * @param individuals
     */
    public void collectStats(ArrayList<Individual> population) {

        recordBestIndividual(population);

        double average = 0.0;

        for (Individual ind : population) {
            average += ind.getFitness();
        }

        average = average / population.size();

        averageFitness.add(average);
    }

    private void recordBestIndividual(ArrayList<Individual> population) {

        // if there is no best individual, then create a deafult individual
        if (bestIndividual == null) {
            bestIndividual = new Individual();
        }

        // need to add / ammend code here to accuratly find the best indvidual so far
        // (so far the first individual is taken as the best.. this is not likely to be the case)
        int bestIndex = 0;
        double maxFitness = 0;
        
        // for every individual 
        for (int i = 0; i < population.size(); i++)
        {          
            // if fitness of individual is more  than max fitness
            if (population.get(i).getFitness() > maxFitness)
            {
                // set max fitness
                 maxFitness = population.get(i).getFitness();
                // set as bestindex
                 bestIndex = population.indexOf(population.get(i));
                 System.out.println("max fitness: " + maxFitness);
                 System.out.println("indexOf: " + bestIndex);
            }
        }
        
        if (bestIndividual.getFitness() < population.get(bestIndex).getFitness())
        {
            bestIndividual = new Individual(population.get(bestIndex));
        }
        
    }

    public void writeStats() {

        System.out.println("Population stats");

        averageFitness.stream().forEach((d) -> {
            System.out.println(d.toString());
        });
    }

    /**
     * Save the current collected statistics to a .csv file while fileName given
     * as a parameter
     *
     * @param fileName
     */
    public void saveStats(String fileName) {

        
        double[] average = new double [averageFitness.size()];
        
        // need the averages in a double[] type
        for(int i = 0; i < averageFitness.size(); i++) {
        
            average[i] = averageFitness.get(i);
        
        }
        // save the average fitnesses
        saveArray(fileName, average);
        
        
        // now save the best individual and append to the filename
       saveArray("bestIndividual" + fileName, bestIndividual.getSimulationParameters());
    }

    private void saveArray(String fileName, double[] data) {

        Writer writer = null;

        try {
            writer = new FileWriter(fileName);

            for (double d : data) {
                writer.write(d + ", ");
            }
        } catch (IOException e) {

            System.err.println("Error writing the file : ");
            e.printStackTrace();
            
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error closing the file : ");
                    e.printStackTrace();
                }
            }
        }
    }

    public Individual getBestIndividual() {
        return bestIndividual;
    }

}  // end class StatsCollector

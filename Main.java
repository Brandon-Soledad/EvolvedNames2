package app;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws IOException {
        File trace = new File("trace2.txt");
        trace.createNewFile();
        PrintStream ps = new PrintStream(trace);
        String target = "PAULO SERGIO LICCIARDI MESSEDER BARRETO";
        Population population = new Population(100, 0.05);
        population.setTargetName(target);
        int i = 0;
        long timeStart = System.currentTimeMillis();
        do {
            population.day();
            ps.write(((population.getMostFit()).toString() + "\n").getBytes());
            i++;
        } while (population.getMostFit().getFitness() != 0);
        //GENERATIONS AND RUNNING TIME TO BE PRINTED ON trace2.txt
        ps.write(("Generations: " + i + "\n").getBytes());
        ps.write(("Running Time: " + (System.currentTimeMillis() - timeStart) + " milliseconds").getBytes());
        ps.close();
        //GENERATIONS AND RUNNING TIME TO BE PRINTED IN THE TERMINAL
        System.out.println(("Generations: " + i));
        System.out.println(("Running Time: " + (System.currentTimeMillis() - timeStart) + " milliseconds "));
        //testGenome();
        //testPopulation();
    }

    public static void testGenome() {
        Genome genome = new Genome(0.05);
        genome.setTargetName("PAULO SERGIO LICCIARDI MESSEDER BARRETO");
        Genome genomeClone = new Genome(genome);
        System.out.println(genome);
        genome.mutate();
        System.out.println(genome);
        genome.crossover(genomeClone);
        System.out.println(genome);
        System.out.println(genome.fitness());
        System.out.println(genome.fitness2());

    }

    public static void testPopulation() {
        Population pop = new Population(30, 0.5);
        pop.setTargetName("PAULO SERGIO LICCIARDI MESSEDER BARRETO");
        System.out.println(pop.getMostFit());
        pop.day();
        System.out.println(pop.getMostFit());
    }
}
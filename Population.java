package app;

import java.util.Random;

public class Population {

    private Genome[] genomes = null;
    private final int numOfGenomes;
    private Genome mostFitGenome = null;

    
    public Population(final Integer numGenomes, final Double mutationRate) {
        genomes = new Genome[numGenomes];
        this.numOfGenomes = numGenomes;
        for (int i = 0; i < numGenomes; i++) {
            final Genome newGenome = new Genome(mutationRate);
            genomes[i] = newGenome;
        }
    }

    public void day() {
        mergeSort(genomes, 0, genomes.length - 1, new Genome[genomes.length]);
        mostFitGenome = genomes[0];
        
        int i = numOfGenomes / 2;
        final int j = numOfGenomes / 2;

        while (i++ < numOfGenomes - 1) {
            final Random random = new Random();
            // pick a remaining genome at random
            final Genome genomeSelect = genomes[random.nextInt(j)];
            final int methodSelected = random.nextInt(2);
            if (methodSelected == 0) {
                final Genome genomeAdd = new Genome(genomeSelect);
                genomeAdd.mutate();
                genomes[i] = genomeAdd;
            } else {
                final Genome genomeAdd = new Genome(genomeSelect);
                final Genome genomeSlected2 = genomes[random.nextInt(j)];
                genomeAdd.crossover(genomeSlected2);
                genomeAdd.mutate();
                genomes[i] = genomeAdd;
            }
        }
    }

    // Merges two arrays of genomes into one
    private void mergeArray(final Genome[] genomes, final int first, final int mid, final int last,
            final Genome[] genomeTemp) {
        int i = first, j = mid + 1;
        final int m = mid, n = last;
        int k = 0;
        while (i <= m && j <= n) {
            if (genomes[i].getFitness() <= genomes[j].getFitness())
                genomeTemp[k++] = genomes[i++];
            else
                genomeTemp[k++] = genomes[j++];
        }
        while (i <= m)
            genomeTemp[k++] = genomes[i++];
        while (j <= n)
            genomeTemp[k++] = genomes[j++];
        for (i = 0; i < k; i++)
            genomes[first + i] = genomeTemp[i];
    }

    // Merge sorts the genomes.
    private void mergeSort(final Genome[] genomes, final int first, final int last, final Genome[] genomeTemp) {
        if (first < last) {
            final int mid = (first + last) / 2;
            mergeSort(genomes, first, mid, genomeTemp);
            mergeSort(genomes, mid + 1, last, genomeTemp);
            mergeArray(genomes, first, mid, last, genomeTemp);
        }
    }


    /**
     * get the mostFit of population
     * 
     * @return the mostFit of population
     */
    public Genome getMostFit() {
        return mostFitGenome;
    }

    public void setTargetName(final String targetName) {
        for (final Genome genome : genomes) {
            genome.setTargetName(targetName);
        }
    }

}
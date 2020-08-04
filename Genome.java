package app;
import java.util.Random;

public class Genome {

    public static final Character[] characters = new Character[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '-', '\'' };
    public static final int FITNESS = 100000;
    private double mutationRate;
    private int fitness = Genome.FITNESS;
    private StringBuilder string = new StringBuilder("A");
    private String target = null;
    private boolean calculateFitnessflag;

    
    public Genome(final double mutationRate) {
        this.mutationRate = mutationRate;
        this.calculateFitnessflag = false;
    }

    
    public Genome(final Genome gene) {
        this.mutationRate = gene.getMutationRate();
        this.string = new StringBuilder(gene.getString());
        this.fitness = gene.getFitness();
        this.target = gene.getTargetName();
        this.calculateFitnessflag = false;
    }

    /**
     * mutate the string in this genome
     */
    public void mutate() {
        final Random random = new Random();
        double rate = random.nextDouble();
        if (rate <= mutationRate) {
            addChar();
        }
        if ((rate = random.nextDouble()) <= mutationRate) {
            deleteChar();
        }
        for (int i = 0, len = string.length(); i < len; i++) {
            if ((rate = random.nextDouble()) <= mutationRate) {
                replaceChar(i);
            }
        }
    }

    /**
     * add a randomly selected character to a randomly selected position in the
     * string
     */
    private void addChar() {
        final Random random = new Random();
        final int charIndex = random.nextInt(29);
        final int position = random.nextInt(string.length() + 1);

        if (position < string.length()) {
            string.insert(position, Genome.characters[charIndex]);
        } else {
            string.append(Genome.characters[charIndex]);
        }
    }

    /**
     * delete a single character from a randomly selected
     */
    private void deleteChar() {
        if (string.length() == 1)
            return;
        final Random random = new Random();
        final int position = random.nextInt(string.length());
        string.deleteCharAt(position);
    }

    /**
     * replace the character in this position by a randomly selected character
     * 
     * @param replacePos certaion position
     */
    private void replaceChar(final int replacePos) {
        if (replacePos >= string.length())
            return;
        final Random random = new Random();
        final int charIndex = random.nextInt(29);
        string.setCharAt(replacePos, Genome.characters[charIndex]);
    }

    /**
     * update the current Genome by crossing it over with other
     * 
     * @param other certain Genome
     */
    public void crossover(final Genome other) {
        final Random random = new Random();
        // record the length of other's string
        final int string_two_length = other.getString().length();
        for (int i = 0, len = string.length(); i < len; i++) {
            final int selectedString = random.nextInt(2);
            if (selectedString == 0) {
                continue;
            } else {
                if (i < string_two_length) {
                    string.setCharAt(i, other.getString().charAt(i));
                } else {
                    string.delete(i, string.length());
                    break;
                }
            }
        }
    }

    public Integer fitness() {
        final int n = string.length(), m = target.length();
        final int l = n < m ? n : m;
        int fitnessTemp = Math.abs(n - m) * 2;
        for (int i = 0; i < l; i++) {
            if (string.charAt(i) != target.charAt(i))
                fitnessTemp = fitnessTemp + 1;
        }
        this.fitness = fitnessTemp;
        this.calculateFitnessflag = true;
        return fitnessTemp;
    }

    /**
     * use the Wagner-Fischer algorithm for calculating Levenshtein edit distance
     * 
     * @return fitness
     */
    public Integer fitness2() {
        final int n = string.length(), m = target.length();

        final int D[][] = new int[n + 1][m + 1];

        for (int i = 0, len = n + 1; i < len; i++) {
            D[i][0] = i;
        }
        for (int i = 0, len = m + 1; i < len; i++) {
            D[0][i] = i;
        }
        for (int i = 1, leni = n + 1; i < leni; i++)
            for (int j = 1, lenj = m + 1; j < lenj; j++) {
                if (string.charAt(i - 1) == target.charAt(j - 1))
                    D[i][j] = D[i - 1][j - 1];
                else {
                    final int d1 = D[i - 1][j] + 1;
                    final int d2 = D[i][j - 1] + 1;
                    final int d3 = D[i - 1][j - 1] + 1;
                    D[i][j] = d1 < d2 ? (d1 < d3 ? d1 : d3) : (d2 < d3 ? d2 : d3);
                }
            }
        final int fitnessTemp = D[n][m] + (Math.abs(n - m) + 1) / 2;
        this.fitness = fitnessTemp;
        this.calculateFitnessflag = true;
        return fitnessTemp;
    }

    /**
     * return the Genome's character string and fitness
     */
    public String toString() {
        return "(\"" + string.toString() + "\", " + getFitness() + ")";
    }

    /* getters and setters */
    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(final double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public StringBuilder getString() {
        return string;
    }

    public int getFitness() {
        if (!calculateFitnessflag)
            fitness();
        return this.fitness;
    }

    public String getTargetName() {
        return target;
    }

    public void setTargetName(final String targetName) {
        this.target = targetName;
    }

}
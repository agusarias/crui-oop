// Static utility class for random number generation. Do not instantiate.
static final class RNG {
    private static final Random RANDOM = new Random();
    
    // Private constructor to prevent instantiation.
    private RNG() {
      throw new AssertionError("Cannot instantiate utility class.");
    }
    
    /**
     * Generates a random integer between 0 (inclusive) and the specified bound (exclusive).
     */
    public static int nextInt(int bound) {
      return RANDOM.nextInt(bound);
    }

    /**
     * Generates a random boolean value.
     */
    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }
}
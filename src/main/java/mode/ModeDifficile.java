package mode;

import strategy.GenerationAleatoireStrategy;
import strategy.GenerationStrategy;
import strategy.ScoreStrategy;
import strategy.ScoreSurfaceStrategy;

public class ModeDifficile implements ModeJeu {
    @Override
    public GenerationStrategy getGenerationStrategy() {
        return new GenerationAleatoireStrategy(4, 1100, 600);
    }

    @Override
    public ScoreStrategy getScoreStrategy() {
        return new ScoreSurfaceStrategy();
    }

    @Override
    public int getNombreFormesMax() {
        return 2;
    }

    @Override
    public String getNomMode() {
        return "Difficile";
    }
}

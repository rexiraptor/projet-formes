package mode;

import strategy.GenerationAleatoireStrategy;
import strategy.GenerationStrategy;
import strategy.ScoreStrategy;
import strategy.ScoreSurfaceStrategy;

public class ModeClassique implements ModeJeu {
    @Override
    public GenerationStrategy getGenerationStrategy() {
        return new GenerationAleatoireStrategy(2, 1100, 600);
    }

    @Override
    public ScoreStrategy getScoreStrategy() {
        return new ScoreSurfaceStrategy();
    }

    @Override
    public int getNombreFormesMax() {
        return 4;
    }

    @Override
    public String getNomMode() {
        return "Classique";
    }
}

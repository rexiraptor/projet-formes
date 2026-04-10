package mode;

import strategy.GenerationStrategy;
import strategy.ScoreStrategy;

public interface ModeJeu {
    GenerationStrategy getGenerationStrategy();
    ScoreStrategy getScoreStrategy();
    int getNombreFormesMax();
    String getNomMode();
}

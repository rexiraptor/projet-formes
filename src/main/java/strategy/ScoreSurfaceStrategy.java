package strategy;

import model.Forme;
import model.GroupeFormes;

import java.awt.geom.Area;
import java.awt.geom.PathIterator;

public class ScoreSurfaceStrategy implements ScoreStrategy {

    @Override
    public double calculerScore(GroupeFormes reference, GroupeFormes joueur, double largeurZone, double hauteurZone) {
        Area unionRouges = new Area();
        for (Forme forme : reference.getFormes()) {
            unionRouges.add(forme.toArea());
        }

        Area unionBleuesValides = new Area();
        for (Forme formeJoueur : joueur.getFormes()) {
            boolean valide = true;

            for (Forme formeReference : reference.getFormes()) {
                if (formeJoueur.intersecte(formeReference)) {
                    valide = false;
                    break;
                }
            }

            if (valide) {
                unionBleuesValides.add(formeJoueur.toArea());
            }
        }

        double surfaceTotale = largeurZone * hauteurZone;
        double surfaceRouge = calculerAire(unionRouges);
        double surfaceLibre = surfaceTotale - surfaceRouge;

        if (surfaceLibre <= 0) {
            return 0;
        }

        double surfaceBleueValide = calculerAire(unionBleuesValides);
        double score = (surfaceBleueValide / surfaceLibre) * 100.0;

        return Math.max(0, Math.min(100, score));
    }

    private double calculerAire(Area area) {
        PathIterator it = area.getPathIterator(null, 0.5);
        double[] coords = new double[6];

        java.util.List<double[]> points = new java.util.ArrayList<>();
        double aireTotale = 0;

        while (!it.isDone()) {
            int type = it.currentSegment(coords);

            if (type == PathIterator.SEG_MOVETO) {
                if (!points.isEmpty()) {
                    aireTotale += airePolygone(points);
                    points.clear();
                }
                points.add(new double[]{coords[0], coords[1]});
            } else if (type == PathIterator.SEG_LINETO) {
                points.add(new double[]{coords[0], coords[1]});
            } else if (type == PathIterator.SEG_CLOSE) {
                if (!points.isEmpty()) {
                    aireTotale += airePolygone(points);
                    points.clear();
                }
            }

            it.next();
        }

        if (!points.isEmpty()) {
            aireTotale += airePolygone(points);
        }

        return Math.abs(aireTotale);
    }

    private double airePolygone(java.util.List<double[]> points) {
        if (points.size() < 3) {
            return 0;
        }

        double somme = 0;
        for (int i = 0; i < points.size(); i++) {
            double[] p1 = points.get(i);
            double[] p2 = points.get((i + 1) % points.size());
            somme += (p1[0] * p2[1]) - (p2[0] * p1[1]);
        }

        return 0.5 * somme;
    }
}

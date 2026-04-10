package command;

import java.util.Stack;

public class HistoriqueCommandes {
    private final Stack<Commande> pileUndo = new Stack<>();
    private final Stack<Commande> pileRedo = new Stack<>();

    public void executerCommande(Commande commande) {
        commande.executer();
        pileUndo.push(commande);
        pileRedo.clear();
    }

    public void enregistrerCommandeExecutee(Commande commande) {
        pileUndo.push(commande);
        pileRedo.clear();
    }

    public void undo() {
        if (!pileUndo.isEmpty()) {
            Commande commande = pileUndo.pop();
            commande.annuler();
            pileRedo.push(commande);
        }
    }

    public void redo() {
        if (!pileRedo.isEmpty()) {
            Commande commande = pileRedo.pop();
            commande.executer();
            pileUndo.push(commande);
        }
    }

    public void vider() {
        pileUndo.clear();
        pileRedo.clear();
    }
}

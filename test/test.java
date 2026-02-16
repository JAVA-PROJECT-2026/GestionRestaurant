
import main.model.entite.Categorie;
import main.model.entite.Utilisateur;
import main.model.dao.CategorieDAO;
import main.model.dao.UtilisateurDAO;

public class test {
    public static void main(String[] args) {

        UtilisateurDAO userDao = new UtilisateurDAO();
        CategorieDAO catDao = new CategorieDAO();

        // Test Utilisateur
        Utilisateur u = new Utilisateur("admin", "1234");
        userDao.insert(u);

        System.out.println("Utilisateur ajouté : " + u.getEmail());

        // Test Categorie
        Categorie c = new Categorie("Boissons");
        catDao.insert(c);

        System.out.println("Categorie ajoutée : " + c.getLibelle());

        // Test lecture
        System.out.println("\nListe des catégories :");
        for (Categorie cat : catDao.findAll()) {
            System.out.println(cat.getLibelle());
        }
    }
}

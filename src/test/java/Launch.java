import fun.archyonix.rpg.DatabaseTools;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

public class Launch {
    public static DatabaseTools databaseTools
            = new DatabaseTools("localhost:5432", "postgres", "postgres");

    @Test
    void createItem() {
        Session session = databaseTools.getSession();
        session.beginTransaction();

        session.createNativeQuery("insert into rpg_items(id,link,category,rarity,classController,display_name,description,data)")

        session.getTransaction().commit();
    }
}

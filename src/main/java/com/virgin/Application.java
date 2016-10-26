package com.virgin;

import com.jmethods.catatumbo.EntityManager;
import com.jmethods.catatumbo.EntityManagerFactory;
import com.virgin.dao.repository.config.EnableDataStoreRepositories;
import com.virgin.example.KindRepository;
import com.virgin.example.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDataStoreRepositories
@ComponentScan({"sample", "com.virgin", "com.virgin.dao"})
public class Application {

    @Autowired
    private KindRepository kindRepository;

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Application mainObj = context.getBean(Application.class);
        mainObj.init();
    }


    public void init() {
        kindRepository.delete(2l);
        Settings settings = kindRepository.findOne(4876109476790272l);
        System.out.println("Fetching settings details");
        System.out.println(settings.getId());
        System.out.println(settings.getFeature());
        System.out.println(settings.getValue());
        System.out.println(settings.getEnvironment());

        EntityManagerFactory emf = EntityManagerFactory.getInstance();
        EntityManager em = emf.createDefaultEntityManager();
//        String s = "devBackups/datastore_backup_BackupInto_devBackups_2016_10_18_UserBrandInfo/1570308648017407187851861401ABA/output-9";
//        System.out.println(getEntityByFolderName(s));
        /*Settings settings = em.load(Settings.class, 4876109476790272l);
        System.out.println(settings);*/

//        VirginRedUser virginRedUser = em.load(VirginRedUser.class, 4503633047584768l);
//        System.out.println(virginRedUser);

        /*EntityQueryRequest request = em.createEntityQueryRequest("SELECT * FROM VirginRedUser");
        QueryResponse<VirginRedUser> response = em.executeEntityQueryRequest(VirginRedUser.class, request);
        List<VirginRedUser> persons = response.getResults();
        System.out.println(persons);*/

        //Fetch all settings
       /* List<Settings> settings = new ArrayList<Settings>();

        final String baseQuery = "SELECT * FROM Settings";
        EntityQueryRequest req = em.createEntityQueryRequest(baseQuery);
//        req.setNamedBinding("Limit", 1000);
        QueryResponse<Settings> res = em.executeEntityQueryRequest(Settings.class, req);
        settings.addAll(res.getResults());
        System.out.println(settings);
        System.out.println(settings.size());*/

        //This will fetch all the users
        /*List<VirginRedUser> settings = new ArrayList<VirginRedUser>();

        final String baseQuery = "SELECT * FROM VirginRedUser LIMIT @Limit";
        String query = baseQuery;
        EntityQueryRequest req = em.createEntityQueryRequest(query);
        req.setNamedBinding("Limit", 1000);
        QueryResponse<VirginRedUser> res = em.executeEntityQueryRequest(VirginRedUser.class, req);
        settings.addAll(res.getResults());
        System.out.println(settings);
        System.out.println(settings.size());
        do {
            String query1 = baseQuery + " OFFSET @Offset";
            req.setQuery(query1);
            req.setNamedBinding("Offset", res.getEndCursor());
            res = em.executeEntityQueryRequest(VirginRedUser.class, req);
            System.out.println(res.getResults());
            settings.addAll(res.getResults());
        } while (!res.getResults().isEmpty());

        System.out.println(settings);
        System.out.println(settings.size());*/
      /*  Datastore datastore = DatastoreOptions.defaultInstance().service();
        KeyFactory keyFactory = datastore.newKeyFactory().kind("Settings");


        EntityQuery query = Query.entityQueryBuilder().kind("Settings").filter(StructuredQuery.PropertyFilter.eq("environment", "PROD")).build();
        EntityQuery query1 = Query.entityQueryBuilder().kind("Settings").limit(2).build();
        QueryResults<Entity> results = datastore.run(query);


        QueryResults results1 = datastore.run(query1);
        System.out.println("..........................................");
        System.out.println(results);
        while (results.hasNext()) {
            Entity entity = results.next();
            System.out.println(entity.key());
            System.out.println(entity.getValue("environment").get());
            System.out.println(entity.getString("environment"));
            System.out.println(entity.getString("feature"));
        }

        Entity entity1 = datastore.get(keyFactory.newKey(4876109476790272l));
        System.out.println("................................");
        System.out.println(entity1.names());
        System.out.println(entity1);
        System.out.println(entity1.getKey("feature"));*/

//        System.out.println(settingsRepository.get(4876109476790272l));
//        System.out.println(settingsRepository.exists(487610947672l));

//        Entity entity2 = datastore.get(keyFactory.newKey("CREATE_USER"));
//        System.out.println("................................");
//        System.out.println(entity2);
    }
}

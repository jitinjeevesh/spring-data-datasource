package com.virgin;

import com.spring.datasource.repository.config.EnableDataStoreRepositories;
import com.virgin.example.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableDataStoreRepositories
@ComponentScan({"sample", "com.virgin"})
public class Application {

    @Autowired
    private SettingsRepository settingsRepository;
    @Autowired
    private VirginUserRepository virginUserRepository;
    @Autowired
    private TestKindRepository testKindRepository;

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Application mainObj = context.getBean(Application.class);
        mainObj.init();
    }


    public void init() {
        //TODO:Dynamic Query
//        System.out.println(settingsRepository.findByFeature("STRIPE"));
//        System.out.println(testKindRepository.findByNameAndBooleanPremitive("Jeevesh", true));
//        System.out.println(testKindRepository.findAllByName("Jeevesh"));
//        System.out.println(testKindRepository.updateByName(5728694408577024l, "Jeevesh Pan"));
//        System.out.println(testKindRepository.findByName("Jeevesh Pan",true));
        //TODO:User
        VirginRedUser virginRedUser = virginUserRepository.findOne(4503633047584768l);
        System.out.println(virginRedUser.getPartnerList());
        if (!virginRedUser.getPartnerList().isEmpty())
            for (Object l : virginRedUser.getPartnerList()) {
                System.out.println(l);
                System.out.println(l.getClass());
            }
        System.out.println(virginRedUser.getRoles());
        for (Object s : virginRedUser.getRoles()) {
            System.out.println(s);
            System.out.println(s.getClass());
        }
        System.out.println(virginRedUser.getContactInfo());
        System.out.println(virginRedUser);

        //TODO:Test updating Kind
       /* ContactInfo contactInfo = new ContactInfo();
        contactInfo.setCity("Lucknow");
        contactInfo.setCountry("India");
        TestKind testKind = new TestKind();
//        testKind.setId(5728694408577024l);
        testKind.setName("Jeevesh");
        testKind.setContactInfo(contactInfo);
        testKind.setRoles(new ArrayList<String>() {{
            add("USER");
            add("ADMIN");
        }});
        testKind.setBooleanPremitive(true);
        testKind.setIsActive(true);
        testKind.setCurrentDate(new Date());
        testKind.setKindType(KindType.TEST);
        testKindRepository.save(testKind);
        System.out.println("Test kind save successfully");*/

        //TODO:Test saving new Kind
        /*ContactInfo contactInfo = new ContactInfo();
        contactInfo.setCity("Lucknow");
        contactInfo.setCountry("India");
        TestKind testKind = new TestKind();
        testKind.setName("Jeevesh");
        testKind.setContactInfo(contactInfo);
        testKind.setRoles(new ArrayList<String>() {{
            add("USER");
            add("ADMIN");
        }});
        testKind.setBooleanPremitive(true);
        testKind.setIsActive(true);
        testKind.setCurrentDate(new Date());
        testKind.setKindType(KindType.TEST);
        testKindRepository.save(testKind);
        System.out.println("Test kind save successfully");*/

        //TODO:Fetch TestKind
       /* TestKind testKind = testKindRepository.findOne(5728694408577024l);
        System.out.println(testKind.getContactInfo());
        if (!testKind.getRoles().isEmpty())
            for (Object l : testKind.getRoles()) {
                System.out.println(l);
            }
        System.out.println(testKind);*/


        //TODO:Save object
        /*Settings settings = new Settings();
        settings.setValue(1);
        settings.setFeature("FEATURE");
        settings.setEnvironment("DEVELOPMENT");
        settings.setDefaultValue(true);
        settingsRepository.save(settings);*/
        //TODO:Datastore count
//        System.out.println(settingsRepository.count());

        //TODO:Count query

       /* Datastore datastore = DatastoreOptions.defaultInstance().service();
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("__Stat_PropertyName_Kind__").setFilter(StructuredQuery.CompositeFilter.and(StructuredQuery.PropertyFilter.eq("kind_name", "TestKind"),
                        StructuredQuery.PropertyFilter.eq("property_name", "name")))
                .build();
        QueryResults<Entity> results = datastore.run(query);
        long count = 0;
        while (results.hasNext()) {
            Entity newEntity = results.next();
            System.out.println("............................................................");
            System.out.println(newEntity);
            count = newEntity.getLong(DataStoreConstants.TOTAL_COUNT_PROPERTY_NAME);
        }
        System.out.println(count);*/

        //TODO:Page request
       /* Long startTime = new Date().getTime();
        Page<Settings> settingses = settingsRepository.findAll(new PageRequest(2, 3));
        System.out.println("............................Settings records..................................................");
        System.out.println(settingses.getContent());
        System.out.println(settingses.hasNext());

        Long endTime = new Date().getTime();
        System.out.println("::::::::::::::::::::::::::::::Time taken by read::::::::::::::::::::::::::::::::::::::::");
        System.out.println(endTime - startTime);
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");*/

       /* EntityManagerFactory emf = EntityManagerFactory.getInstance();
        EntityManager em = emf.createDefaultEntityManager();*/
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
//        EntityQuery query = Query.newEntityQueryBuilder().setKind("Settings").setFilter(StructuredQuery.PropertyFilter.eq("environment", "PROD")).build();
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
//        System.out.println(entitresultClassy2);
    }
}

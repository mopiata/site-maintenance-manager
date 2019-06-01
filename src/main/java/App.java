import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Sql2oEngineerDao;
import dao.DB;
import dao.Sql2oSiteDao;
import models.Engineer;
import models.Site;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        ProcessBuilder process=new ProcessBuilder();
        Integer port;

        if(process.environment().get("PORT")!=null){
            port=Integer.parseInt(process.environment().get("PORT"));
        }else{
            port=4567;
        }

        port(port);

        staticFileLocation("/public");

        Sql2oEngineerDao engineerDao=new Sql2oEngineerDao(DB.sql2o);
        Sql2oSiteDao siteDao=new Sql2oSiteDao(DB.sql2o);

        get("/engineers/:id/delete", (request, response) -> {
            Map<String, Object> model=new HashMap<>();
            int idOfEngineerToDelete=Integer.parseInt(request.params("id"));

            engineerDao.deleteById(idOfEngineerToDelete);
            return new ModelAndView(model,"success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/sites/:id/delete", (request, response) -> {
            Map<String, Object> model=new HashMap<>();
            int idOfSiteToDelete=Integer.parseInt(request.params("id"));

            siteDao.deleteById(idOfSiteToDelete);
            return new ModelAndView(model,"success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/", (request, response) -> {
            Map<String, Object> model=new HashMap<>();
            List<Site> sites=siteDao.all();
            List<Engineer> engineers=engineerDao.all();

            List<Site> unassignedSites=new ArrayList<Site>();
            List<Engineer> unassignedEngineers= new ArrayList<Engineer>();

            boolean siteAssigned=false;
            boolean engineerAssigned=false;

            for(Site site:sites){
                for (Engineer engineer:engineers){
                    if(site.getEngineerId()==engineer.getId()){
                        System.out.println("Site is assigned");
                        siteAssigned=true;
                        break;
                    }else{
                        siteAssigned=false;
                    }
                }
                if(siteAssigned==false){
                    unassignedSites.add(site);
                }
            }

            for(Engineer engineer:engineers){
                for (Site site:sites){
                    if(site.getEngineerId()==engineer.getId()){
                        System.out.println("Engineer is assigned");
                        engineerAssigned=true;
                        break;
                    }else{
                        engineerAssigned=false;
                    }
                }
                if(engineerAssigned==false){
                    unassignedEngineers.add(engineer);
                }
            }

            model.put("engineers",unassignedEngineers);
            model.put("sites",unassignedSites);

            return new ModelAndView(model,"index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/engineers/new",(request, response) -> {
            Map<String, Object> model=new HashMap<>();
            List<Engineer> engineers=engineerDao.all();
            model.put("engineers",engineers);
            return new ModelAndView(model,"engineersForm.hbs");
        },new HandlebarsTemplateEngine());

        post("/engineers/new",(request, response) -> {
            Map<String, Object> model=new HashMap<>();
            List<Engineer> engineer=engineerDao.all();

            String name=request.queryParams("name");
            String phone=request.queryParams("phone");
            int eknumber=Integer.parseInt(request.queryParams("eknumber"));

            boolean engineerExists=false;

            if(engineer.size()==0){
                System.out.println("Engineer to be added.");
                engineerExists=false;
            }else {
                for (Engineer singleEngineer : engineer) {
                    if (singleEngineer.getEkNumber() == eknumber) {
                        System.out.println("Engineer Exists!");
                        engineerExists = true;
                        break;
                    } else {
                        System.out.println("Engineer to be added.");
                        engineerExists = false;
                    }
                }
            }


            if(!engineerExists){
                engineerDao.save(new Engineer(eknumber,name,phone));
                return new ModelAndView(model,"success.hbs");
            }else {
                return new ModelAndView(model,"reject.hbs");
            }
        }, new HandlebarsTemplateEngine());

        get("engineers/:id", (request, response) -> {
            Map<String, Object> model=new HashMap<>();
            int engineerId=Integer.parseInt(request.params("id"));
            List<Site> sites=siteDao.all();
            List<Site> engineerSites=new ArrayList<Site>();

            for(Site engineerSite:sites){
                if(engineerSite.getEngineerId()==engineerId){
                    engineerSites.add(engineerSite);
                }
            }
            Engineer foundEngineer=engineerDao.findById(engineerId);
            model.put("engineer",foundEngineer);
            model.put("sites", engineerSites);

            return new ModelAndView(model,"engineer-details.hbs");
        }, new HandlebarsTemplateEngine());

        get("/engineers/:id/update", (request, response) -> {
            Map<String, Object> model=new HashMap<>();
            int idOfEngineerToUpdate=Integer.parseInt(request.params("id"));

            Engineer editEngineer=engineerDao.findById(idOfEngineerToUpdate);
            model.put("editEngineer",editEngineer);

            return new ModelAndView(model,"engineersForm.hbs");
        }, new HandlebarsTemplateEngine());

        post("/engineers/:id/update", (request, response) -> {
            Map<String, Object> model=new HashMap<>();

            String name=request.queryParams("name");
            String phone=request.queryParams("phone");
            int ek=Integer.parseInt(request.queryParams("eknumber"));

            int idOfEngineerToUpdate=Integer.parseInt(request.params("id"));

            engineerDao.update(idOfEngineerToUpdate,ek,name,phone);
            return new ModelAndView(model,"success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/sites/new",(request, response) -> {
            Map<String, Object> model=new HashMap<>();
            List<Site> sites = siteDao.all();
            List<Engineer> engineers=engineerDao.all();

            model.put("engineers",engineers);
            model.put("sites",sites);
            return new ModelAndView(model,"sitesForm.hbs");
        },new HandlebarsTemplateEngine());

        post("/sites/new", (request, response) -> {
            Map<String, Object> model=new HashMap<>();

            String siteName=request.queryParams("name");
            String siteLocation = request.queryParams("location");
            int engineerId = Integer.parseInt(request.queryParams("engineer"));

            Site newsite = new Site(siteName,siteLocation,engineerId);
            siteDao.save(newsite);

            model.put("newsite",newsite);
            return new ModelAndView(model,"success.hbs");
        }, new HandlebarsTemplateEngine());

        get("sites/:id", (request, response) -> {
            Map<String, Object> model=new HashMap<>();
            int siteId=Integer.parseInt(request.params("id"));

            Site foundSite=siteDao.findById(siteId);
            Engineer siteEngineer=engineerDao.findById(foundSite.getEngineerId());
            model.put("site", foundSite);
            model.put("engineer", siteEngineer);

            return new ModelAndView(model,"site-details.hbs");

        }, new HandlebarsTemplateEngine());

        get("/sites/:id/update", (request, response) -> {
            Map<String, Object> model=new HashMap<>();
            int idOfSiteToUpdate=Integer.parseInt(request.params("id"));


            Site editSite=siteDao.findById(idOfSiteToUpdate);
            Engineer engineer=engineerDao.findById(editSite.getEngineerId());
            List<Engineer> engineers=engineerDao.all();

            model.put("engineers",engineers);
            model.put("editSite",editSite);
            model.put("engineer",engineer);

            return new ModelAndView(model,"sitesForm.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sites/:id/update", (request, response) -> {
            Map<String, Object> model=new HashMap<>();

            String name=request.queryParams("name");
            String location=request.queryParams("location");
            int engineer=Integer.parseInt(request.queryParams("engineer"));

            int idOfSiteToUpdate=Integer.parseInt(request.params("id"));

            siteDao.update(idOfSiteToUpdate,name,location,engineer);
            return new ModelAndView(model,"success.hbs");
        }, new HandlebarsTemplateEngine());

    }
}

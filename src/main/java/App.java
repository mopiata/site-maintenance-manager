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

        get("/", (request, response) -> {
            Map<String, Object> model=new HashMap<>();
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
            String name=request.queryParams("name");
            String phone=request.queryParams("phone");
            int eknumber=Integer.parseInt(request.queryParams("eknumber"));
            engineerDao.save(new Engineer(eknumber,name,phone));
//            response.redirect("success.hbs");
            return new ModelAndView(model,"success.hbs");
        }, new HandlebarsTemplateEngine());

        get("engineers/:id", (request, response) -> {
            Map<String, Object> model=new HashMap<>();
            int engineerId=Integer.parseInt(request.params("id"));
            List<Site> engineerSites=siteDao.all();

            for(Site engineerSite:engineerSites){
                if(engineerSite.getEngineerId()!=engineerId){
                    engineerSites.remove(engineerSite);
                }
            }

            System.out.println(engineerSites);
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

    }
}

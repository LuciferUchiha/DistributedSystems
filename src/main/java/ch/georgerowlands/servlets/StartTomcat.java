package ch.georgerowlands.servlets;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class StartTomcat {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.setBaseDir("build");

        // adds a webapp directory under a particular root name
        String webappDirLocation = "src/main/java/ch/georgerowlands/servlets/webapp/";
        StandardContext ctx = (StandardContext) tomcat.addWebapp("/tomcat", new File(webappDirLocation).getAbsolutePath());

        // looks for servlets with @WebServlet instead of in web.xml
        File additionWebInfClasses = new File("build/classes/java/main");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(
                new DirResourceSet(
                        resources,
                        "/WEB-INF/classes",
                        additionWebInfClasses.getAbsolutePath(),
                        "/"));
        ctx.setResources(resources);
        tomcat.getConnector();
        tomcat.start();
        tomcat.getServer().await();
    }
}

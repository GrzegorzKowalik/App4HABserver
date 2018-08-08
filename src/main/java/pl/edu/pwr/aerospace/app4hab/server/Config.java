package pl.edu.pwr.aerospace.app4hab.server;

public class Config {
    /**
     * Path to folder where transmitted photos should be saved
     */
    public static String uploadsFolder;
    /**
     * Path to folder containing deployed application. It's needed for saved images to be displayed in GUI
     */
    public static String tomcatFolder;
    /**
     * Authentication token, messages coming from phone without or with wrong token as 'auth' header will be rejected
     */
    public static String authToken;
    /**
     * Secred needed for phone commands change using GUI
     */
    public static String controlSecret;

    static {
        uploadsFolder = System.getenv("APP4HAB_UPLOADS");
        tomcatFolder = System.getenv("APP4HAB_WEBAPP");
        authToken = System.getenv("APP4HAB_AUTH");
        controlSecret = System.getenv("APP4HAB_SECRET");

        if (uploadsFolder != null)
            uploadsFolder += "/";
        else uploadsFolder = "";

        if (tomcatFolder == null)
            tomcatFolder = "src/main/webapp";
        tomcatFolder += "/img/";

        if (authToken == null)
            authToken = "915e9592-43dd-11e8-842f-0ed5f89f718b";

        if (controlSecret == null)
            authToken = "app4hab";
    }
}

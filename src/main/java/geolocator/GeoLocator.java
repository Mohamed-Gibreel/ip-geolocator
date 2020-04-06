package geolocator;

import java.net.URL;

import java.io.IOException;

import com.google.gson.Gson;

import com.google.common.net.UrlEscapers;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.tinylog.slf4j.TinylogLogger;

public class GeoLocator {

    private static final TinylogLogger LOGGER = (TinylogLogger) LoggerFactory.getLogger(GeoLocator.class);
//    private static final TinylogLogger LOGGER = new TinylogLogger("GeoLocator Logger");

    public static final String GEOLOCATOR_SERVICE_URI = "http://ip-api.com/json/";

    private static Gson GSON = new Gson();

    public GeoLocator() {}

    public GeoLocation getGeoLocation() throws IOException {
        return getGeoLocation(null);
    }

    public GeoLocation getGeoLocation(String ipAddrOrHost) throws IOException {
        URL url;
//        System.out.println(LOGGER.getName());
        LOGGER.debug("Getting URL...");
        if (ipAddrOrHost != null) {
            ipAddrOrHost = UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost);
            url = new URL(GEOLOCATOR_SERVICE_URI + ipAddrOrHost);
        } else {
            url = new URL(GEOLOCATOR_SERVICE_URI);
        }
        LOGGER.debug("URL Obtained!");
        LOGGER.info("The URL obtained is {}", url);
        LOGGER.warn("URL obtained might not contain JSON Objects!");
        LOGGER.info("Trying to get JSON Object ...");
        String s = IOUtils.toString(url, "UTF-8");
        LOGGER.debug("A JSON Object has been obtained from {}",url);
        return GSON.fromJson(s, GeoLocation.class);
    }

    public static void main(String[] args) throws IOException {
        try {
            String arg = args.length > 0 ? args[0] : null;
            GeoLocation Country = new GeoLocator().getGeoLocation(arg);
            LOGGER.info("Country Found!, the user's country is {}!",Country.getCountry());
        } catch (IOException e) {
            LOGGER.error("{} does not contain any JSON Object.", GEOLOCATOR_SERVICE_URI);
        }
    }
}

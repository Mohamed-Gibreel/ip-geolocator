package geolocator;

import java.net.URL;

import java.io.IOException;

import com.google.gson.Gson;

import com.google.common.net.UrlEscapers;

import org.apache.commons.io.IOUtils;
import org.tinylog.Logger;

public class GeoLocator {

    public static final String GEOLOCATOR_SERVICE_URI = "http://ip-api.com/json/";

    private static Gson GSON = new Gson();

    public GeoLocator() {}

    public GeoLocation getGeoLocation() throws IOException {
        return getGeoLocation(null);
    }

    public GeoLocation getGeoLocation(String ipAddrOrHost) throws IOException {
        URL url;
        Logger.debug("Getting URL...");
        if (ipAddrOrHost != null) {
            ipAddrOrHost = UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost);
            url = new URL(GEOLOCATOR_SERVICE_URI + ipAddrOrHost);
        } else {
            url = new URL(GEOLOCATOR_SERVICE_URI);
        }
        Logger.debug("URL Obtained!");
        Logger.info("The URL obtained is {}", url);
        Logger.warn("URL obtained might not contain JSON Objects!");
        Logger.info("Trying to get JSON Object ...");
        String s = IOUtils.toString(url, "UTF-8");
        Logger.debug("A JSON Object has been obtained from {}",url);
        return GSON.fromJson(s, GeoLocation.class);
    }

    public static void main(String[] args) throws IOException {
        try {
            String arg = args.length > 0 ? args[0] : null;
            GeoLocation Country = new GeoLocator().getGeoLocation(arg);
            Logger.info("Country Found!, the user's country is {}!",Country.getCountry());
        } catch (IOException e) {
            Logger.error("{} does not contain any JSON Object.", GEOLOCATOR_SERVICE_URI);
        }
    }
}

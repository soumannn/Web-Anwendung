package de.hsrm.mi.web.projekt.services.tour;

import java.time.LocalDateTime;


import de.hsrm.mi.web.projekt.entities.tour.Tour;

public record TourDTD(
    long id,
    LocalDateTime abfahrDateTime,
    int preis,
    int plaetze,
    int buchungen,
    String startOrtName,
    long startOrtId,
    String zielOrtName,
    long zielOrtId,
    String anbieterName,
    long anbieterId,
    double distanz,
    String info
) {

    public static final double EARTH_RADIUS = 6371.;

    public static TourDTD fromTour(Tour t) {
        return new TourDTD(
            t.getId(),
            t.getAbfahrDateTime(),
            t.getPreis(),
            t.getPlaetze(),
            t.getMitfahrgaeste().size(),
            t.getStartort().getName(),
            t.getStartort().getId(),
            t.getZielort().getName(),
            t.getZielort().getId(),
            t.getAnbieter().getName(),
            t.getAnbieter().getId(),
            calculateDistance(t.getStartort().getGeobreite(), t.getStartort().getGeolaenge(), t.getZielort().getGeobreite(), t.getZielort().getGeolaenge()),
            t.getInfo()
        );
    }

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);
        double distance = Math.sqrt(x * x + y * y) * EARTH_RADIUS;

        return distance;
    }
}

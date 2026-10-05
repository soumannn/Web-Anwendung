package de.hsrm.mi.web.projekt.api.ort;

import de.hsrm.mi.web.projekt.entities.ort.Ort;

public record OrtDTO(long id, String name, double geoBreite, double geoLaenge) {

    public static OrtDTO fromOrt(Ort o){
        return new OrtDTO(o.getId(), o.getName(), o.getGeobreite(), o.getGeolaenge());
    }
    public Ort toOrt(OrtDTO dto){
        Ort neu_Ort = new Ort();
        neu_Ort.setId(id);
        neu_Ort.setName(name);
        neu_Ort.setGeobreite(geoBreite);
        neu_Ort.setGeolaenge(geoLaenge);
        return neu_Ort;
    }
}

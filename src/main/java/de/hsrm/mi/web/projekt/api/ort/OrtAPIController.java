package de.hsrm.mi.web.projekt.api.ort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import de.hsrm.mi.web.projekt.entities.ort.Ort;
import de.hsrm.mi.web.projekt.services.ort.OrtService;

@RestController
@RequestMapping("/api/ort")
public class OrtAPIController {
    @Autowired
    OrtService ortService;

    @GetMapping()
    public ResponseEntity<List<OrtDTO>> getAllOrte() {
        List<Ort> alleOrte = ortService.holeAlleOrte();
        List<OrtDTO> ortDTOs = alleOrte.stream()
                                        .map(ort -> OrtDTO.fromOrt(ort))
                                        .collect(Collectors.toList());
        return new ResponseEntity<>(ortDTOs, HttpStatus.OK);
    }

    // @GetMapping("/{i}")
    // public ResponseEntity<String> get(@PathVariable Long i) {


    //     return new ResponseEntity<>("hallo", HttpStatus.OK);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<OrtDTO> getOrtById(@PathVariable("id") Long id) {
        Optional<Ort> op_ort = ortService.holeOrtMitId(id);
        Ort ort;
        if(op_ort.isPresent()){
            ort = op_ort.get();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        OrtDTO ortDTO = OrtDTO.fromOrt(ort);
        return new ResponseEntity<>(ortDTO, HttpStatus.OK);
    }

}

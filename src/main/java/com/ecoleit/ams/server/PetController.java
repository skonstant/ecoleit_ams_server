package com.ecoleit.ams.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PetController {

    static List<Pet> petsList = new ArrayList<>();

    @GetMapping("/pet/{petId}")
    public ResponseEntity<Pet> getPet(@PathVariable int petId) {
        for(var p : petsList){
            if(p.id() == petId) {
                return new ResponseEntity<>(p, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/pets")
    public List<Pet> getPets() {
        return petsList;
    }

    @DeleteMapping("/pet")
    public ResponseEntity deletePet(@RequestParam int petId) {

        petsList.removeIf(pet -> pet.id() == petId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/pet/{petId}")
    public ResponseEntity createPet(@RequestParam int petId, @RequestParam String name, @RequestParam String status) {
        if(name == null) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        if(name.length() > 10) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        var pet = new Pet(petId, name, status);
        petsList.add(pet);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/pet")
    public ResponseEntity updatePet(@RequestBody Pet pet){
        int idx = 0;

        for(var p : petsList){
            if(p.id() == pet.id()) {
                break;
            }
            idx++;
        }

        petsList.remove(idx);
        petsList.add(pet);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}

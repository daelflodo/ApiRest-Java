package com.dael.ApiRest.controllers;

import com.dael.ApiRest.controllers.dto.MakerDTO;
import com.dael.ApiRest.persistence.entities.Maker;
import com.dael.ApiRest.service.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/maker")
public class MakerController {

    @Autowired
    private IMakerService makerService;

    @GetMapping("/findAll")
    public ResponseEntity findAll() {
        List<MakerDTO> makerDTOList = makerService.findAll()
                .stream().map(maker -> MakerDTO.builder()
                        .id(maker.getId())
                        .name(maker.getName())
                        .productList(maker.getProductList())
                        .build())
                .toList();
        return ResponseEntity.ok(makerDTOList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        System.out.println("Estoy en /findOne");
        Optional<Maker> makerOptional = makerService.findById(id);
        if (makerOptional.isPresent()) {
            Maker maker = makerOptional.get();
            MakerDTO makerDTO = MakerDTO.builder().id(maker.getId()).name(maker.getName()).productList(maker.getProductList()).build();
            return ResponseEntity.ok(makerDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Maker not found");
    }

    @PostMapping("/create")
//        @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO) throws URISyntaxException {
        System.out.println("Estoy en /create");
        if (makerDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        makerService.save(Maker.builder()
                .name(makerDTO.getName())
                .build());
        return ResponseEntity.created(new URI("/api/maker/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MakerDTO makerDTO) {

        Optional<Maker> makerOptional = makerService.findById(id);
        if (makerOptional.isPresent()) {
            Maker maker = makerOptional.get();// con esto recuperamos el entity
            maker.setName(makerDTO.getName()); //aqui setteamos el name con lo que viene en el dto
            makerService.save(maker);
            return ResponseEntity.ok("Registro Actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (id != null) {
            Optional<Maker> makerOptional = makerService.findById(id);
            if (makerOptional.isPresent()) {
                Maker maker = makerOptional.get();
                makerService.deleteById(id);
                return ResponseEntity.ok("Registro Eliminado");
            }
        return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();

    }
}

package br.edu.josifHubapi.controller;

import br.edu.josifHubapi.domain.Area;
import br.edu.josifHubapi.dto.AreaDTO;
import br.edu.josifHubapi.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/area", produces = MediaType.APPLICATION_JSON_VALUE)
public class AreaControler {
    @Autowired
    AreaService areaService;

    @GetMapping
    public ResponseEntity<List<Area>> findAll() {
        return ResponseEntity.ok(areaService.findAll());
    }

    @PostMapping
    public ResponseEntity<Area> insert(@RequestBody AreaDTO areaDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(areaService.insert(areaDTO));
    }

}

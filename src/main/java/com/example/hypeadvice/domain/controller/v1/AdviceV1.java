package com.example.hypeadvice.domain.controller.v1;

import com.example.hypeadvice.domain.entity.Advice;
import com.example.hypeadvice.domain.service.AdviceService;
import com.example.hypeadvice.domain.service.AdvicesLIPService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "Advice", description = "Gestão dos conselhos")
@CrossOrigin
@RestController
@RequestMapping("/advice/v1")
public class AdviceV1 {

    @Autowired public AdviceService adviceService;
    @Autowired public AdvicesLIPService adviceAPIService;

    @GetMapping("/listar")
    @Operation(
    		summary = "Listar todos os conselhos cadastrados.",
    		description = "Método utilizado para listar todos os conselhos cadastrados."
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso.",
                    content =  @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Advice.class)))),
    })

    public ResponseEntity<List<Advice>> listar(HttpServletRequest request) {
        List<Advice> all = adviceService.findAll();
        ResponseEntity responseEntity = new ResponseEntity(all, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/consulta/{id}")
    @Operation(
            summary = "Consulta um conselho cadastrado através de ID informado",
            description = "Método utilizado para consultar um conselho específico através de GET http://localhost:8080/advice/v1/consulta/{id}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso.",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Advice.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na consulta")

        })

    public ResponseEntity<?> consultaId(@PathVariable Integer id) {
        try {
            Advice advice = adviceService.findById(id);

            if (advice == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Item não encontrado para o ID: " + id);
            }

            ResponseEntity<Advice> responseEntity = new ResponseEntity<>(advice, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar item: " + e.getMessage());
        }
    }

    @GetMapping("/consultadesc/{descricao}")
    @Operation(
            summary = "Consulta um conselho cadastrado através da descrição informado",
            description = "Método utilizado para consultar uma descrição através de GET http://localhost:8080/advice/v1/consultadesc/{desc}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Advice.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro na consulta")

    })

    public ResponseEntity<?> consultaDesc(@PathVariable String descricao) {
        try {
            Advice advice = adviceService.findByDescr(descricao);

            if (advice == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Item não encontrado para a descrição: " + descricao);
            }

            ResponseEntity<Advice> responseEntity = new ResponseEntity<>(advice, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar o item: " + e.getMessage());
        }
    }
}

package com.example.hypeadvice.domain.service;

import com.example.hypeadvice.domain.entity.Advice;
import com.example.hypeadvice.domain.utils.Utils;
import com.example.hypeadvice.domain.vo.AdviceListVO;
import com.example.hypeadvice.domain.vo.AdviceVO;
import com.example.hypeadvice.domain.vo.Slip;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.apache.tomcat.util.http.parser.AcceptLanguage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class AdvicesLIPService {

    public Advice gerar() throws UnirestException {
        HttpResponse<String> response = Unirest.get("https://api.adviceslip.com/advice")
                .header("Accept-Language", "br")
                .header("Content-Type", "application/json")
                .asString();
        int status = response.getStatus();
        if (HttpStatus.SC_OK == status) {
            AdviceVO adviceVO = null;
            try {
                String body = response.getBody();
                adviceVO = Utils.jsonToObject(AdviceVO.class, body);
            } catch (Exception e) {
                throw new RuntimeException("Status Code" + status + ", message " + e.getMessage());
            }

            if (adviceVO != null) {
                Slip slip = adviceVO.getSlip();
                String adviceStr = slip.getAdvice();
                return new Advice(adviceStr);
            } else {
                throw new RuntimeException("Status Code" + status + ", message " + response.getStatusText());
            }
        }
        else {
            throw new RuntimeException("Status Code" + status + ", message " + response.getStatusText());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Advice buscarByDescricao(String descricao) throws UnirestException {
       try {
           String encodedDescricao = URLEncoder.encode(descricao, String.valueOf(StandardCharsets.UTF_8));
           HttpResponse<String> response = Unirest.get("http://localhost:8080/advice/v1/consultadesc/" + encodedDescricao)
                   .header("Accept-Language", "br")
                   .header("Content-Type", "application/json")
                   .asString();
           int status = response.getStatus();
           if (status == 200) {
               String body = response.getBody();
               return Utils.jsonToObject(Advice.class, body);
           } else if (status == 404) {
               throw new RuntimeException("Item não encontrado para a Descrição: " + descricao);
           } else {
               throw new RuntimeException("Erro: " + response.getStatusText());
           }
       } catch (Exception e) {
           throw new RuntimeException("Erro ao consultar: " + e.getMessage(), e);
       }
    }

    @Transactional(rollbackFor = Exception.class)
    public Advice buscarById(Integer id) throws UnirestException, JsonProcessingException {
        try {
            HttpResponse<String> response = Unirest.get("http://localhost:8080/advice/v1/consulta/" + id)
                    .header("Accept-Language", "br")
                    .header("Content-type", "application/json")
                    .asString();

            int status = response.getStatus();

            if (status == 200) {
                String body = response.getBody();
                return Utils.jsonToObject(Advice.class, body);
            } else if (status == 404) {
                throw new RuntimeException("Item não encontrado para o ID: " + id);
            } else {
                throw new RuntimeException("Erro: " + response.getStatusText());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar: " + e.getMessage(), e);
        }
    }
}

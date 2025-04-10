package com.example.hypeadvice.domain.service;

import com.example.hypeadvice.domain.entity.Advice;
import com.example.hypeadvice.domain.repository.AdviceRepository;
import com.example.hypeadvice.domain.vo.AdviceListVO;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdviceService {

    @Autowired public AdviceRepository adviceRepository;
    @Autowired public AdvicesLIPService advicesLIPService;

    @Transactional(rollbackFor = Exception.class)
    public Advice save(Advice analiseContrato) {
        return adviceRepository.save(analiseContrato);
    }

    @Transactional (rollbackFor = Exception.class)
    public void delete(Advice analiseContrato) {
        adviceRepository.delete(analiseContrato);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Advice> findAll() {
        return adviceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Advice findById(Integer id) {
        return adviceRepository.findById(Long.valueOf(id)).orElse(null);
    }

    @Transactional(readOnly = true)
    public Advice findByDescr(String descricao) {
        return adviceRepository.findByDescricaoContaining(descricao).orElse(null);
    }

    public Advice gerar() throws UnirestException {
        return advicesLIPService.gerar();
    }
}

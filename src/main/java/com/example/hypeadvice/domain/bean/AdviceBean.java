package com.example.hypeadvice.domain.bean;

import com.example.hypeadvice.domain.entity.Advice;
import com.example.hypeadvice.domain.entity.TipoConselho;
import com.example.hypeadvice.domain.service.AdviceService;
import com.example.hypeadvice.domain.service.AdvicesLIPService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

@Named
@ViewScoped
@Component
public class AdviceBean extends Bean {

    @Autowired AdviceService adviceService;
    @Autowired AdvicesLIPService adviceAPIService;

    private Advice advice = new Advice();
    private List<Advice> advices;

    public void initBean() {
        advices = adviceService.findAll();
    }

    public List<Advice> getAdvices() {
        return advices;
    }

    public void setAdvices(List<Advice> advices) {
        this.advices = advices;
    }

    public void salvar() {
        if (!advice.getNome().matches("^[A-Za-z ]+$")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nome inválido.", "Nome inválido."));
            return;
        }

        adviceService.save(advice);
        if(advice.getId() == null){
            advices.add(advice);
        }
        adicionarAdvice();
        addFaceMessage(FacesMessage.SEVERITY_INFO, "Item salvo com sucesso", null);

    }

    public void excluir(Advice advice) {
        adviceService.delete(advice);
        advices.remove(advice);

        addFaceMessage(FacesMessage.SEVERITY_INFO, "Item excluido com sucesso", null);

    }

    public void editar(Advice advice) {
        this.advice = advice;
    }

    public void gerar() {
        try {
            advice = adviceService.gerar();
        } catch (UnirestException e) {
            addMessageError(e);
        }
    }

    public void consultaID(Integer id){
        try{
            Advice item = adviceAPIService.buscarById(id);
            advices.clear();
            advices.add(item);
            addFaceMessage(FacesMessage.SEVERITY_INFO, "Item encontrado!", null);
        } catch (Exception e) {
            addFaceMessage(FacesMessage.SEVERITY_ERROR, "Erro na consulta.", e.getMessage());
        }

    }

    public void consultaDescricao(String descricao){
        try{
            Advice item = adviceAPIService.buscarByDescricao(descricao);
            advices.clear();
            advices.add(item);
            addFaceMessage(FacesMessage.SEVERITY_INFO, "Item encontrado!", null);
        } catch (Exception e) {
            addFaceMessage(FacesMessage.SEVERITY_ERROR, "Erro na consulta.", e.getMessage());
        }

    }

    public List<TipoConselho> getTiposConselho() {
        return Arrays.asList(TipoConselho.values());
    }

    public void adicionarAdvice() {
        advice = new Advice();
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}

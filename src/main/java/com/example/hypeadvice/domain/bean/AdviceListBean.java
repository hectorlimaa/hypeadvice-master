package com.example.hypeadvice.domain.bean;

import com.example.hypeadvice.domain.entity.Advice;
import com.example.hypeadvice.domain.service.AdviceService;
import com.example.hypeadvice.domain.service.AdvicesLIPService;
import com.example.hypeadvice.domain.vo.AdviceListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class AdviceListBean extends Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    AdviceService adviceService;
    @Autowired
    AdvicesLIPService adviceAPIService;

    private Advice advice = new Advice();
    private AdviceListVO adviceListVO;
    private Integer id;
    private String descricao;
    private List<Advice> itens;

    public void initBean() {
        advice = new Advice();
        itens = new ArrayList<>();
    }


    public void buscarID() {
        if (id != null) {
            itens.clear();
            try {
                Advice item = adviceAPIService.buscarById(id);
                if (item != null) {
                    itens.add(item);
                    addFaceMessage(FacesMessage.SEVERITY_INFO, "Sucesso", null);
                } else {
                    addFaceMessage(FacesMessage.SEVERITY_ERROR, "Erro", null);
                }
            } catch (Exception e) {
                addMessageError(e);
            }
        }

    }

    public void buscarDesc() {
        if (descricao != null) {
            itens.clear();
            try {
                Advice item = adviceAPIService.buscarByDescricao(descricao);
                if (item != null) {
                    itens.add(item);
                    addFaceMessage(FacesMessage.SEVERITY_INFO, "Sucesso", null);
                } else {
                    addFaceMessage(FacesMessage.SEVERITY_ERROR, "Erro", null);
                }
            } catch (Exception e) {
                addMessageError(e);
            }
        }

    }

    public AdviceListVO getAdviceListVO() {
        return adviceListVO;
    }

    public void setAdviceListVO(AdviceListVO adviceListVO) {
        this.adviceListVO = adviceListVO;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public List<Advice> getItens() {
        return itens;
    }

    public void setItens(List<Advice> itens) {
        this.itens = itens;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}